package ch.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IngredientTest {

	Ingredient i1;

	@Before
	public void setUp() throws Exception {
		i1 = new Ingredient("ig01", "Teszt", 1.0, 2.5, 4.0, 5.5);
	}

	@Test
	public void testConstructors() {
		assertTrue(i1 != null);
		assertTrue(new Ingredient("asd") != null);
	}
	
	@Test
	public void testGetters1() {
		assertEquals("ig01", i1.getId());
		assertEquals(4.0, i1.getCalories(), 0.0001);
		assertEquals(2.5, i1.getFat(), 0.0001);
		assertEquals(5.5, i1.getCarbons(), 0.0001);
		assertEquals(1.0, i1.getProteins(), 0.0001);
	}

	@Test
	public void testGetters2() {
		assertEquals(4.0, i1.getCalories(100.0), 0.0001);
		assertEquals(1.25, i1.getFat(50.0), 0.0001);
		assertEquals(0.55, i1.getCarbons(10.0), 0.0001);
		assertEquals(1.5, i1.getProteins(150.0), 0.0001);
	}
	
	@Test
	public void testWrongGetters() {
		try {
			i1.getCalories(-1);
			fail();
		} catch (FoodException e){}
		try {
			i1.getFat(-1);
			fail();
		} catch (FoodException e){}
		try {
			i1.getCarbons(-1);
			fail();
		} catch (FoodException e){}
		try {
			i1.getProteins(-1);
			fail();
		} catch (FoodException e){}
	}
	
	

	@Test
	public void testSetters() {
		i1.setName("Körte");
		i1.setCalories(140.0);
		i1.setCarbons(150.0);
		i1.setProteins(45.0);
		i1.setFat(46.0);
		
		assertEquals("Körte", i1.getName());
		assertEquals(140, i1.getCalories(), 0.0001);
		assertEquals(150, i1.getCarbons(), 0.0001);
		assertEquals(45, i1.getProteins(), 0.0001);
		assertEquals(46, i1.getFat(), 0.0001);
	}
	
	@Test
	public void testWrongSetters() {
		try {
			i1.setCalories(-1.0);
			fail();
		} catch (FoodException e){}
		try {
			i1.setFat(-1.0);
			fail();
		} catch (FoodException e){}
		try {
			i1.setCarbons(-1.0);
			fail();
		} catch (FoodException e){}
		try {
			i1.setProteins(-1.0);
			fail();
		} catch (FoodException e){}
	}
	
	@Test
	public void testEqualsAndHashCode() {
		assertTrue(i1.equals(i1));
		assertTrue(!i1.equals(1));
		assertTrue(!i1.equals(new Ingredient("asd")));
		
		assertTrue(i1.hashCode() == i1.hashCode());
		assertTrue(i1.hashCode() != new Ingredient("asd").hashCode());
	}
	
	
}
