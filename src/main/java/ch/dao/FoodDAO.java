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
	void deleteIngredient(Ingredient ig) throws FoodDaoException;
	
	ReadyFood getReadyFoodByID(String id) throws FoodDaoException;
	void updateReadyFood(ReadyFood rf) throws FoodDaoException;
	void insertReadyFood(ReadyFood rf) throws FoodDaoException;
	void deleteReadyFood(ReadyFood rf) throws FoodDaoException;
}
