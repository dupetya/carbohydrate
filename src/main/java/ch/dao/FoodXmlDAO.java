package ch.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import ch.model.Ingredient;

public class FoodXmlDAO implements FoodDAO {
	private static File directory = new File(System.getProperty("user.home"),
			"CarboHydrate");
	private static File file = new File(directory, "ingredients.xml");

	public List<Ingredient> getIngredients() {
		List<Ingredient> list = new ArrayList<Ingredient>();

		try {
			if (!file.exists()) {
				return list;
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			NodeList inl = doc.getElementsByTagName("Ingredient");
			for (int i = 0; i < inl.getLength(); i++) {
				Node in = inl.item(i);
				Element ie = (Element) in;

				try {
					Ingredient ig = buildIngredient(ie);
					list.add(ig);
				} catch (NumberFormatException e) {

				}

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	private Ingredient buildIngredient(Element ingredientElement) {
		Ingredient ig = new Ingredient(ingredientElement.getAttribute("id"));
		ig.setName(ingredientElement.getElementsByTagName("name").item(0)
				.getTextContent());
		ig.setCalories(Double.valueOf(ingredientElement
				.getElementsByTagName("calories").item(0)
				.getTextContent()));
		ig.setFat(Double.valueOf(ingredientElement.getElementsByTagName("fat")
				.item(0).getTextContent()));
		ig.setCarbons(Double.valueOf(ingredientElement
				.getElementsByTagName("carbons").item(0)
				.getTextContent()));
		ig.setProteins(Double.valueOf(ingredientElement
				.getElementsByTagName("proteins").item(0)
				.getTextContent()));
		return ig;
	}

	public Ingredient getIngredientByID(String id) {
		if (!file.exists()) {
			return null;
		}
		try {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		NodeList inl = doc.getElementsByTagName("Ingredient");
		for (int i = 0; i < inl.getLength(); i++) {
			Element ingredientElement = (Element) inl.item(i);
			if (id.equals(ingredientElement.getAttribute("id"))) {
				return buildIngredient(ingredientElement);
			}
		}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public void updateIngredient(Ingredient ig) {
		// TODO Auto-generated method stub

	}

	public void insertIngredient(Ingredient ig) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc;

			if (!file.exists()) {
				file.getParentFile().mkdirs();
				doc = db.newDocument();
				doc.appendChild(doc.createElement("Ingredients"));
			} else {
				doc = db.parse(file);
			}

			Node ingredientsNode = doc.getElementsByTagName("Ingredients")
					.item(0);
			NodeList ingredients = doc.getElementsByTagName("Ingredient");
			for (int i = 0; i < ingredients.getLength(); ++i) {
				Element ingredientElement = (Element) ingredients.item(i);
				if (ig.equals(buildIngredient(ingredientElement))) {
					System.out.println(ig + " was not inserted");
					return;
				}
			}
			
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
			Node ingNode = ingredientsNode.appendChild(ingElement);

			DOMImplementationLS dils = (DOMImplementationLS) db
					.getDOMImplementation();
			LSSerializer lsser = dils.createLSSerializer();
			lsser.getDomConfig().setParameter("format-pretty-print", true);
			LSOutput lsout = dils.createLSOutput();
			lsout.setCharacterStream(new FileWriter(file));
			lsser.write(doc, lsout);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
