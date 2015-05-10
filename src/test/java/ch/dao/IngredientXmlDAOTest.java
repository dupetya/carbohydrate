package ch.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.model.Ingredient;

public class IngredientXmlDAOTest {

	FoodDAO dao;

	@Before
	public void setUp() throws Exception {
		dao = new FoodXmlDAO();
		dao.insertIngredient(new Ingredient("ing01", "Alma", 0.1, 0.2, 0.3, 0.4));
		dao.insertIngredient(new Ingredient("ing02", "Alma2", 0.13, 0.21, 0.35, 0.42));
	}

	@Test
	public void testGetIngredients() {
		assertEquals(new Ingredient("ing01", "Alma", 0.1, 0.2, 0.3, 0.4), dao
				.getIngredients().get(0));
		assertEquals(new Ingredient("ing02", "Alma2", 0.13, 0.21, 0.35, 0.42), dao
				.getIngredients().get(1));
	}

}
