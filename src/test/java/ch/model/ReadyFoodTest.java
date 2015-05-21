package ch.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ReadyFoodTest {

	ReadyFood rf01, rf02;
	Ingredient ig01;
	
	@Before
	public void setUp() throws Exception {
		rf01 = new ReadyFood("rf1");
		ig01 = new Ingredient("ig01", "alma", 1.0, 2.0, 3.0, 4.0);
		rf02 = new ReadyFood("rf2");
		rf02.addIngredient(ig01, 100);
	}
	
	@Test
	public void testCtor()	{
		assertTrue(rf01 != null);
	}

	@Test
	public void testOneAddIngredient() {
		boolean a = rf01.addIngredient(ig01, 100.0);
		assertTrue(a);
		assertEquals(3.0, rf01.getCalories(), 0.0001);
		
		a = rf01.addIngredient(ig01, 100.0);
		assertTrue(!a);
	}
	
	@Test
	public void testMultipleAddIngredient() {
		rf01.addIngredient(ig01, 100);
		rf01.addIngredient(new Ingredient("ig02", "Teszt", 1.0, 2.5, 4.0, 5.5) , 100);
		rf01.addIngredient(new Ingredient("ig03", "Teszt2", 10.0, 20.5, 4.05, 5.54) , 100);
		
		assertEquals(4, rf01.getProteins(), 0.0001);
	}
	

}
