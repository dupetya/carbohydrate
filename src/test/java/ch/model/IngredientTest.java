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
	public void testGetters1() {
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
	public void testFFF() {
		try {
			i1.getCalories(-2);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
