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
import javax.xml.transform.TransformerConfigurationException;
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
	private Document doc;
	private File file;

	public FoodXmlDAO() throws FoodDaoException {
		file = new File(new File(System.getProperty("user.home"),
				"CarboHydrate"), "data.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = dbf.newDocumentBuilder();
			if (file.exists()) {
				doc = builder.parse(file);
			} else {
				file.getParentFile().mkdirs();
				doc = builder.newDocument();
				Node root = doc.appendChild(doc.createElement("Foods"));
				root.appendChild(doc.createElement("Ingredients"));
				root.appendChild(doc.createElement("ReadyFoods"));
			}
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new FoodDaoException("Error in data access", e);
		}
	}

	public List<Ingredient> getIngredients() {
		List<Ingredient> list = new ArrayList<Ingredient>();

		NodeList ingredientNodeList = doc.getElementsByTagName("Ingredient");
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
			NodeList inl = doc.getElementsByTagName("Ingredient");
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
		NodeList ingredientNodeList = doc.getElementsByTagName("Ingredient");
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

	public void insertIngredient(Ingredient ig) throws FoodDaoException {
		Node ingredientsNode = doc.getElementsByTagName("Ingredients").item(0);
		NodeList ingredients = doc.getElementsByTagName("Ingredient");
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

		NodeList readyFoodNodeList = doc.getElementsByTagName("ReadyFood");
		for (int i = 0; i < readyFoodNodeList.getLength(); i++) {
			Element rfElement = (Element) readyFoodNodeList.item(i);
		}
		return result;
	}

	@Override
	public Ingredient getReadyFoodByID(String id) throws FoodDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReadyFood(ReadyFood rf) throws FoodDaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertReadyFood(ReadyFood rf) throws FoodDaoException {

		Element rfsElement = (Element) doc.getElementsByTagName("ReadyFoods")
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

	private Element readyfoodToElement(ReadyFood rf) {
		Element rfElement = doc.createElement("ReadyFood");
		rfElement.setAttribute("id", rf.getId());
		rfElement.appendChild(doc.createElement("name")).appendChild(
				doc.createTextNode(rf.getName()));

		Node mapNode = rfElement.appendChild(doc.createElement("Map"));
		for (Ingredient ing : rf.getIngredients()) {

			if (getIngredientByID(ing.getId()) == null)
				try {
					insertIngredient(ing);
				} catch (FoodDaoException e) {
					return null;
				}

			Node pairNode = mapNode.appendChild(doc.createElement("Pair"));

			pairNode.appendChild(doc.createElement("id")).appendChild(
					doc.createTextNode(ing.getId()));
			pairNode.appendChild(doc.createElement("value")).appendChild(
					doc.createTextNode(String.valueOf(rf
							.getIngredientWeight(ing))));
		}
		return rfElement;
	}

	private Element ingredientToElement(Ingredient ig) {
		Element ingElement = doc.createElement("Ingredient");
		ingElement.setAttribute("id", ig.getId());
		ingElement.appendChild(doc.createElement("name")).appendChild(
				doc.createTextNode(ig.getName()));
		ingElement.appendChild(doc.createElement("proteins")).appendChild(
				doc.createTextNode(String.valueOf(ig.getProteins())));
		ingElement.appendChild(doc.createElement("fat")).appendChild(
				doc.createTextNode(String.valueOf(ig.getFat())));
		ingElement.appendChild(doc.createElement("carbons")).appendChild(
				doc.createTextNode(String.valueOf(ig.getCarbons())));
		ingElement.appendChild(doc.createElement("calories")).appendChild(
				doc.createTextNode(String.valueOf(ig.getCalories())));
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

	private void writeXML() throws FoodDaoException {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult res = new StreamResult(file);
			trans.transform(source, res);
		} catch (TransformerException e) {
			throw new FoodDaoException("Error while writing XML", e);
		}
	}
}
