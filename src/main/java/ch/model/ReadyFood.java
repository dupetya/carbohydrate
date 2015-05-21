package ch.model;

import java.util.HashMap;
import java.util.Map;

public class ReadyFood implements Food {

	private String id;
	private String name;
	private Map<Ingredient, Double> ingredients;
	
	public ReadyFood(String id) {
		ingredients = new HashMap<Ingredient, Double>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setIngredients(Map<Ingredient, Double> ingredients) {
		this.ingredients = ingredients;
	}
	
	public Map<Ingredient, Double> getIngredients() {
		return ingredients;
	}
	
	public boolean addIngredient(Ingredient ing, double weight) {
		if(ingredients.get(ing) == null) {
			ingredients.put(ing, weight);
			return true;
		}
		return false;
	}
	
	
	@Override
	public double getCarbons() {
		return getCarbons(100.0);
	}

	@Override
	public double getFat() {
		return getFat(100.0);
	}

	@Override
	public double getCalories() {
		return getCalories(100.0);
	}

	@Override
	public double getProteins() {
		return getProteins(100.0);
	}

	@Override
	public double getCarbons(double weight) {
		if(weight < 0.0)
			throw new FoodException("Weight can't be negative");
		double sum = 0.0;
		for (Ingredient ing : ingredients.keySet()) {
			sum += ing.getCarbons((ingredients.get(ing)));
		}
		return (sum / 100.0) * weight;
	}

	@Override
	public double getFat(double weight) {
		if(weight < 0.0)
			throw new FoodException("Weight can't be negative");
		double sum = 0.0;
		for (Ingredient ing : ingredients.keySet()) {
			sum += ing.getFat((ingredients.get(ing)));
		}
		return (sum / 100.0) * weight;
	}

	@Override
	public double getCalories(double weight) {
		if(weight < 0.0)
			throw new FoodException("Weight can't be negative");
		double sum = 0.0;
		for (Ingredient ing : ingredients.keySet()) {
			sum += ing.getCalories((ingredients.get(ing)));
		}
		return (sum / 100.0) * weight;
	}

	@Override
	public double getProteins(double weight) {
		if(weight < 0.0)
			throw new FoodException("Weight can't be negative");
		double sum = 0.0;
		for (Ingredient ing : ingredients.keySet()) {
			sum += ing.getProteins((ingredients.get(ing)));
		}
		return (sum / 100.0) * weight;
	}
		
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ReadyFood) {
			ReadyFood o = (ReadyFood) obj;
			return this.id.equals(o.id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
