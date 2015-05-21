package ch.dao;

import java.util.List;

import ch.model.Ingredient;

public interface FoodDAO {
	List<Ingredient> getIngredients();
	
	

	Ingredient getIngredientByID(String id) throws FoodDaoException;
	void updateIngredient(Ingredient ig) throws FoodDaoException;
	void insertIngredient(Ingredient ig) throws FoodDaoException;
}
