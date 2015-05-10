package ch.dao;

import java.util.List;

import ch.model.Ingredient;

public interface FoodDAO {
	List<Ingredient> getIngredients();
	

	Ingredient getIngredientByID(String id);

	void updateIngredient(Ingredient ig);

	void insertIngredient(Ingredient ig);
}
