package ch.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.model.Ingredient;
import ch.model.ReadyFood;

public class FoodXmlDAO implements FoodDAO {
	private Document ingredientDoc;
	private Document rfDoc;
	private File ingredientFile;
	private File rfFile;
	

	public FoodXmlDAO() throws FoodDaoException {
		ingredientFile = new File(new File(System.getProperty("user.home"),
				"CarboHydrate"), "ingredients.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = dbf.newDocumentBuilder();
			if (ingredientFile.exists()) {
				ingredientDoc = builder.parse(ingredientFile);
			} else {
				ingredientFile.getParentFile().mkdirs();
				ingredientDoc = builder.newDocument();
				Node root = ingredientDoc.appendChild(ingredientDoc.createElement("Foods"));
				root.appendChild(ingredientDoc.createElement("Ingredients"));
				root.appendChild(ingredientDoc.createElement("ReadyFoods"));
			}
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new FoodDaoException("Error in data access", e);
		}
	}

	public List<Ingredient> getIngredients() {
		List<Ingredient> list = new ArrayList<Ingredient>();

		NodeList ingredientNodeList = ingredientDoc.getElementsByTagName("Ingredient");
		for (int i = 0; i < ingredientNodeList.getLength(); i++) {
			Element ingredientElement = (Element) ingredientNodeList.item(i);

			Ingredient ig = elementToIngredient(ingredientElement);
			if (ig != null)
				list.add(ig);
			else
				System.err.println("Error creating an ingredient");
		}

		return list;
	}

	public Ingredient getIngredientByID(String id) {
		try {
			NodeList inl = ingredientDoc.getElementsByTagName("Ingredient");
			for (int i = 0; i < inl.getLength(); i++) {
				Element ingredientElement = (Element) inl.item(i);
				if (id.equals(ingredientElement.getAttribute("id"))) {
					return elementToIngredient(ingredientElement);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public void updateIngredient(Ingredient ig) throws FoodDaoException {
		NodeList ingredientNodeList = ingredientDoc.getElementsByTagName("Ingredient");
		for (int i = 0; i < ingredientNodeList.getLength(); i++) {
			Element ingredientElement = (Element) ingredientNodeList.item(i);
			if (ig.equals(elementToIngredient(ingredientElement))) {
				ingredientElement.getElementsByTagName("name").item(0)
						.setTextContent(ig.getName());
				ingredientElement.getElementsByTagName("proteins").item(0)
						.setTextContent(String.valueOf((ig.getProteins())));
				ingredientElement.getElementsByTagName("fat").item(0)
						.setTextContent(String.valueOf((ig.getFat())));
				ingredientElement.getElementsByTagName("carbons").item(0)
						.setTextContent(String.valueOf((ig.getCarbons())));
				ingredientElement.getElementsByTagName("calories").item(0)
						.setTextContent(String.valueOf((ig.getCalories())));

				writeXML();

			}
		}
	}

	@Override
	public void deleteIngredient(Ingredient ig) throws FoodDaoException {
		boolean deleted = false;
		Element ingsElement = (Element) ingredientDoc.getElementsByTagName("Ingredients")
				.item(0);
		NodeList ingNL = ingsElement.getElementsByTagName("Ingredient");
		for (int i = 0; i < ingNL.getLength(); i++) {
			Element ingElem = (Element) ingNL.item(i);
			if (ingElem.getAttribute("id").equals(ig.getId())) {
				Node prev = ingElem.getPreviousSibling();
				if (prev != null && 
					     prev.getNodeType() == Node.TEXT_NODE &&
					     prev.getNodeValue().trim().length() == 0) {
					     ingsElement.removeChild(prev);
					 }
				ingsElement.removeChild(ingElem);
				deleted = true;
				break;
			}
		}

		if (deleted)
			writeXML();
	}

	public void insertIngredient(Ingredient ig) throws FoodDaoException {
		Node ingredientsNode = ingredientDoc.getElementsByTagName("Ingredients").item(0);
		NodeList ingredients = ingredientDoc.getElementsByTagName("Ingredient");
		for (int i = 0; i < ingredients.getLength(); ++i) {
			Element ingredientElement = (Element) ingredients.item(i);
			if (ig.equals(elementToIngredient(ingredientElement))) {
				System.out.println(ig + " was not inserted");
				return;
			}
		}

		Element ingElement = ingredientToElement(ig);
		ingredientsNode.appendChild(ingElement);

		writeXML();

	}

	@Override
	public List<ReadyFood> getReadyFoods() {
		List<ReadyFood> result = new ArrayList<ReadyFood>();

		NodeList readyFoodNodeList = ingredientDoc.getElementsByTagName("ReadyFood");
		for (int i = 0; i < readyFoodNodeList.getLength(); i++) {
			Element rfElement = (Element) readyFoodNodeList.item(i);
			ReadyFood rf = elementToReadyFood(rfElement);
			if (rf != null)
				result.add(rf);
		}
		return result;
	}

	@Override
	public ReadyFood getReadyFoodByID(String id) throws FoodDaoException {
		NodeList readyFoodNodeList = ingredientDoc.getElementsByTagName("ReadyFood");
		for (int i = 0; i < readyFoodNodeList.getLength(); i++) {
			Element rfElement = (Element) readyFoodNodeList.item(i);
			if (rfElement.getAttribute("id").equals(id)) {
				return elementToReadyFood(rfElement);
			}
		}
		return null;
	}

	@Override
	public void updateReadyFood(ReadyFood rf) throws FoodDaoException {
		Element rfsElement = (Element) ingredientDoc.getElementsByTagName("ReadyFoods")
				.item(0);
		NodeList readyFoodNodeList = rfsElement
				.getElementsByTagName("ReadyFood");
		boolean updated = false;
		for (int i = 0; i < readyFoodNodeList.getLength(); i++) {
			Element rfElement = (Element) readyFoodNodeList.item(i);
			if (rfElement.getAttribute("id").equals(rf.getId())) {
				Element newElem = readyfoodToElement(rf);
				Node prev = rfElement.getPreviousSibling();
				if (prev != null && 
					     prev.getNodeType() == Node.TEXT_NODE &&
					     prev.getNodeValue().trim().length() == 0) {
					     rfsElement.removeChild(prev);
					 }
				rfsElement.removeChild(rfElement);
				rfsElement.appendChild(newElem);
				updated = true;
				break;
			}
		}
		if(updated)
			writeXML();
	}

	@Override
	public void insertReadyFood(ReadyFood rf) throws FoodDaoException {

		Element rfsElement = (Element) ingredientDoc.getElementsByTagName("ReadyFoods")
				.item(0);
		NodeList rfNodeList = rfsElement.getElementsByTagName("ReadyFood");

		if (rfNodeList.getLength() > 0) {
			for (int i = 0; i < rfNodeList.getLength(); i++) {
				Element rfElem = (Element) rfNodeList.item(i);
				if (rfElem.getAttribute("id").equals(rf.getId())) {
					System.out.println(rf + " was not inserted");
					return;
				}
			}
		}

		Element rfElement = readyfoodToElement(rf);
		if (rfElement == null)
			return;
		rfsElement.appendChild(rfElement);

		writeXML();

	}
	
	@Override
	public void deleteReadyFood(ReadyFood rf) throws FoodDaoException {
		Element rfsElement = (Element) ingredientDoc.getElementsByTagName("ReadyFoods")
				.item(0);
		NodeList rfNodeList = rfsElement.getElementsByTagName("ReadyFood");
		boolean deleted = false;
		if (rfNodeList.getLength() > 0) {
			for (int i = 0; i < rfNodeList.getLength(); i++) {
				Element rfElem = (Element) rfNodeList.item(i);
				if (rfElem.getAttribute("id").equals(rf.getId())) {
					Node prev = rfElem.getPreviousSibling();
					if (prev != null && 
						     prev.getNodeType() == Node.TEXT_NODE &&
						     prev.getNodeValue().trim().length() == 0) {
						     rfsElement.removeChild(prev);
						 }
					deleted = true;
					rfsElement.removeChild(rfElem);
				}
			}
		}
		if(deleted){
			writeXML();
		}

	}

	private Element readyfoodToElement(ReadyFood rf) {
		Element rfElement = ingredientDoc.createElement("ReadyFood");
		rfElement.setAttribute("id", rf.getId());
		rfElement.appendChild(ingredientDoc.createElement("name")).appendChild(
				ingredientDoc.createTextNode(rf.getName()));

		Node mapNode = rfElement.appendChild(ingredientDoc.createElement("Map"));
		for (Ingredient ing : rf.getIngredients()) {

			if (getIngredientByID(ing.getId()) == null)
				return null;

			Node pairNode = mapNode.appendChild(ingredientDoc.createElement("Pair"));

			pairNode.appendChild(ingredientDoc.createElement("id")).appendChild(
					ingredientDoc.createTextNode(ing.getId()));
			pairNode.appendChild(ingredientDoc.createElement("value")).appendChild(
					ingredientDoc.createTextNode(String.valueOf(rf
							.getIngredientWeight(ing))));
		}
		return rfElement;
	}

	private Element ingredientToElement(Ingredient ig) {
		Element ingElement = ingredientDoc.createElement("Ingredient");
		ingElement.setAttribute("id", ig.getId());
		ingElement.appendChild(ingredientDoc.createElement("name")).appendChild(
				ingredientDoc.createTextNode(ig.getName()));
		ingElement.appendChild(ingredientDoc.createElement("proteins")).appendChild(
				ingredientDoc.createTextNode(String.valueOf(ig.getProteins())));
		ingElement.appendChild(ingredientDoc.createElement("fat")).appendChild(
				ingredientDoc.createTextNode(String.valueOf(ig.getFat())));
		ingElement.appendChild(ingredientDoc.createElement("carbons")).appendChild(
				ingredientDoc.createTextNode(String.valueOf(ig.getCarbons())));
		ingElement.appendChild(ingredientDoc.createElement("calories")).appendChild(
				ingredientDoc.createTextNode(String.valueOf(ig.getCalories())));
		return ingElement;
	}

	private Ingredient elementToIngredient(Element ingredientElement) {
		Ingredient ig = new Ingredient(ingredientElement.getAttribute("id"));
		try {
			ig.setName(ingredientElement.getElementsByTagName("name").item(0)
					.getTextContent());
			ig.setCalories(Double.valueOf(ingredientElement
					.getElementsByTagName("calories").item(0).getTextContent()));
			ig.setFat(Double.valueOf(ingredientElement
					.getElementsByTagName("fat").item(0).getTextContent()));
			ig.setCarbons(Double.valueOf(ingredientElement
					.getElementsByTagName("carbons").item(0).getTextContent()));
			ig.setProteins(Double.valueOf(ingredientElement
					.getElementsByTagName("proteins").item(0).getTextContent()));
			return ig;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private ReadyFood elementToReadyFood(Element rfElement) {
		ReadyFood rf = new ReadyFood(rfElement.getAttribute("id"));
		rf.setName(rfElement.getElementsByTagName("name").item(0)
				.getTextContent());

		NodeList mapPairNodeList = rfElement.getElementsByTagName("Pair");
		for (int j = 0; j < mapPairNodeList.getLength(); j++) {
			Element pairElement = (Element) mapPairNodeList.item(0);

			Element keyElem = (Element) pairElement.getElementsByTagName("id")
					.item(0);
			Element valElem = (Element) pairElement.getElementsByTagName(
					"value").item(0);

			Ingredient ing = getIngredientByID(keyElem.getTextContent());
			if (ing == null) {
				return null;
			}

			rf.addIngredient(ing, Double.valueOf(valElem.getTextContent()));
		}
		return rf;
	}

	private void writeXML() throws FoodDaoException {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(ingredientDoc);
			StreamResult res = new StreamResult(ingredientFile);
			trans.transform(source, res);
		} catch (TransformerException e) {
			throw new FoodDaoException("Error while writing XML", e);
		}
	}
}
