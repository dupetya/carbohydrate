package ch.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ReadyFoodTest {

	ReadyFood rf;
	Ingredient ig;

	@Before
	public void setUp() throws Exception {
		rf = new ReadyFood("rf1");
		ig = new Ingredient("ig01", "alma", 1.5, 2.8, 3.3, 4.9);
	}

	@Test
	public void testCtors() {
		assertTrue(rf != null);
		assertTrue(new ReadyFood("as", "ss", new HashMap<Ingredient, Double>()) != null);
	}

	@Test
	public void testGeneralGettersAndSetters() {
		assertEquals("rf1", rf.getId());
		assertEquals("", rf.getName());
		rf.setName("Pite");
		assertEquals("Pite", rf.getName());
	}

	@Test
	public void testAddOneIngredient() {
		boolean a = rf.addIngredient(ig, 100.0);

		try {
			rf.addIngredient(new Ingredient("a"), 0);
			fail();
		} catch (FoodException e) {
		}
		assertTrue(a);
	}

	@Test
	public void testGetTotalWeightWithOneIngredient() {
		rf.addIngredient(ig, 147.7);
		assertEquals(147.7, rf.getTotalWeight(), 0.0001);
	}

	@Test
	public void testNutritionGettersWithOneIngredientNoParameter() {
		rf.addIngredient(ig, 147.7);
		assertEquals(1.5, rf.getProteins(), 0.0001);
		assertEquals(2.8, rf.getFat(), 0.0001);
		assertEquals(3.3, rf.getCalories(), 0.0001);
		assertEquals(4.9, rf.getCarbons(), 0.0001);
	}

	@Test
	public void testNutritionGettersWithOneIngredientWithParameter() {
		rf.addIngredient(ig, 147.7);
		assertEquals(2.25, rf.getProteins(150), 0.0001);
		assertEquals(5.6, rf.getFat(200), 0.0001);
		assertEquals(1.65, rf.getCalories(50), 0.0001);
		assertEquals(0.49, rf.getCarbons(10), 0.0001);
	}

	@Test
	public void testGetTotalWeightWithMoreIngredient() {
		rf.addIngredient(ig, 147.7);
		rf.addIngredient(new Ingredient("a1", "ab", 54, 12.1, 11.7, 8.4), 178.0);
		assertEquals(325.7, rf.getTotalWeight(), 0.0001);
	}

	@Test
	public void testNutritionGettersWithIngredientNoParameter() {
		rf.addIngredient(ig, 147.7);
		rf.addIngredient(new Ingredient("a1", "ab", 54, 12.1, 11.7, 8.4), 178.0);

		// totalw 325.7
		// 96.12 + 2.55 = 98.3355 / totalw * 100 = 30.1920
		assertEquals(30.1920, rf.getProteins(), 0.0001);

		// 21.538 + 4.1356 = 25,6736 / totalw * 100 = 7,8825
		assertEquals(7.8825, rf.getFat(), 0.0001);

		// 20,826 + 4,8741 = 25,7001 / totalw * 100 = 7,8907
		assertEquals(7.8907, rf.getCalories(), 0.0001);

		// 14,952 + 7,2373 = 22,1893 / tw *100 = 6,8128
		assertEquals(6.8128, rf.getCarbons(), 0.0001);
	}

	@Test
	public void testNutritionGettersWithMoreIngredientWithParameter() {
		rf.addIngredient(ig, 147.7);
		rf.addIngredient(new Ingredient("a1", "ab", 54, 12.1, 11.7, 8.4), 178.0);

		// totalw 325.7
		// 30.1920 / 100 * 150 = 45,288
		assertEquals(45.288, rf.getProteins(150), 0.0001);

		// 7,8825 / 100 * 50 = 3,94125
		assertEquals(3.94125, rf.getFat(50), 0.0001);

		// 7,8907 / 100 * 10 = 0,78907
		assertEquals(0.78907, rf.getCalories(10), 0.0001);

		// 6,8128 / 100 * 135 = 9,19728
		assertEquals(9.19728, rf.getCarbons(135), 0.0001);
	}

	@Test
	public void testWrongGetters() {
		rf.addIngredient(ig, 110);
		try {
			rf.getCalories(-4);
			fail();
		} catch (FoodException e) {
		}
		try {
			rf.getFat(-4);
			fail();
		} catch (FoodException e) {
		}
		try {
			rf.getCarbons(-4);
			fail();
		} catch (FoodException e) {
		}
		try {
			rf.getProteins(-8);
			fail();
		} catch (FoodException e) {
		}
	}

	@Test
	public void testMultipleAddIngredient() {
		Ingredient ig2 = new Ingredient("ig02", "Teszt", 1.0, 2.5, 4.0, 5.5);
		Ingredient ig3 = new Ingredient("ig03", "Teszt2", 10.0, 20.5, 4.05,
				5.54);
		assertTrue(rf.addIngredient(ig, 100));
		assertTrue(rf.addIngredient(ig2, 100));
		assertTrue(rf.addIngredient(ig3, 100));
		assertFalse(rf.addIngredient(ig, 15));
	}

	@Test
	public void testGetIngredients() {
		Ingredient ig2 = new Ingredient("ig02", "Teszt", 1.0, 2.5, 4.0, 5.5);
		Ingredient ig3 = new Ingredient("ig03", "Teszt2", 10.0, 20.5, 4.05,
				5.54);

		rf.addIngredient(ig, 100);
		rf.addIngredient(ig2, 100);
		rf.addIngredient(ig3, 100);

		List<Ingredient> list = new ArrayList<Ingredient>();
		list.add(ig);
		list.add(ig2);
		list.add(ig3);

		List<Ingredient> list2 = rf.getIngredients();
		assertEquals(list.size(), list2.size());
		for (Ingredient ingredient : list) {
			if (!list2.contains(ingredient))
				fail();
		}
	}

	@Test
	public void testGetIngredientWeight() {
		Ingredient ig2 = new Ingredient("ig02", "Teszt", 1.0, 2.5, 4.0, 5.5);
		Ingredient ig3 = new Ingredient("ig03", "Teszt2", 10.0, 20.5, 4.05,
				5.54);

		rf.addIngredient(ig, 170);
		rf.addIngredient(ig2, 120);
		rf.addIngredient(ig3, 57.9);

		assertEquals(170, rf.getIngredientWeight(ig), 0.001);
		assertEquals(120, rf.getIngredientWeight(ig2), 0.001);
		assertEquals(57.9, rf.getIngredientWeight(ig3), 0.001);

		assertEquals(0.0, rf.getIngredientWeight(new Ingredient("x")), 0.0001);
	}

	@Test
	public void testRemoveIngredient() {
		Ingredient ig2 = new Ingredient("ig02", "Teszt", 1.0, 2.5, 4.0, 5.5);
		Ingredient ig3 = new Ingredient("ig03", "Teszt2", 10.0, 20.5, 4.05,
				5.54);

		rf.addIngredient(ig, 170);
		rf.addIngredient(ig2, 120);
		rf.addIngredient(ig3, 57.9);

		assertTrue(rf.removeIngredient(ig2));
		assertFalse(rf.removeIngredient(ig2));

		assertFalse(rf.getIngredientsTable().containsKey(ig2));
	}

	@Test
	public void testGetIngredientTable() {
		Ingredient ig2 = new Ingredient("ig02", "Teszt", 1.0, 2.5, 4.0, 5.5);
		Ingredient ig3 = new Ingredient("ig03", "Teszt2", 10.0, 20.5, 4.05,
				5.54);

		rf.addIngredient(ig, 170);
		rf.addIngredient(ig2, 120);
		rf.addIngredient(ig3, 57.9);

		Map<Ingredient, Double> map = new HashMap<Ingredient, Double>();
		map.put(ig, 170.0);
		map.put(ig2, 120.0);
		map.put(ig3, 57.9);

		Map<Ingredient, Double> map2 = rf.getIngredientsTable();
		assertEquals(map, map2);
	}

}
