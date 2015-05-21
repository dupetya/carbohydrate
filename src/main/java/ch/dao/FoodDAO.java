package ch.dao;

import java.util.List;

import ch.model.Ingredient;
import ch.model.ReadyFood;

public interface FoodDAO {
	List<Ingredient> getIngredients();
	List<ReadyFood> getReadyFoods();
	
	

	Ingredient getIngredientByID(String id) throws FoodDaoException;
	void updateIngredient(Ingredient ig) throws FoodDaoException;
	void insertIngredient(Ingredient ig) throws FoodDaoException;
	
	Ingredient getReadyFoodByID(String id) throws FoodDaoException;
	void updateReadyFood(ReadyFood rf) throws FoodDaoException;
	void insertReadyFood(ReadyFood rf) throws FoodDaoException;
}
