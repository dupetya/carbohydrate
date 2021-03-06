package ch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadyFood implements Food {

	private String id;
	private String name;
	private Map<Ingredient, Double> ingredients;

	public ReadyFood(String id) {
		this.id = id;
		this.name = "";
		ingredients = new HashMap<Ingredient, Double>();
	}

	public ReadyFood(String id, String name, Map<Ingredient, Double> map) {
		this(id);
		this.name = name;
		this.ingredients = map;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalWeight() {
		double sum = 0.0;
		for (double d : ingredients.values()) {
			sum += d;
		}
		return sum;
	}

	public Map<Ingredient, Double> getIngredientsTable() {
		Map<Ingredient, Double> mRes = new HashMap<Ingredient, Double>();
		for (Ingredient ing : getIngredients()) {
			mRes.put(ing, getIngredientWeight(ing));
		}
		return mRes;
	}

	public List<Ingredient> getIngredients() {
		List<Ingredient> l = new ArrayList<Ingredient>();
		for (Ingredient ingredient : ingredients.keySet()) {
			l.add(ingredient);
		}
		return l;
	}

	public double getIngredientWeight(Ingredient ing) {
		if (ingredients.get(ing) == null)
			return 0;
		else
			return ingredients.get(ing);
	}

	public boolean addIngredient(Ingredient ing, double weight) {
		if (weight <= 0.0) {
			throw new FoodException("Invalid weight");
		}
		if (ingredients.get(ing) == null) {
			ingredients.put(ing, weight);
			return true;
		}
		return false;
	}

	public boolean removeIngredient(Ingredient ing) {
		if (ingredients.get(ing) != null) {
			ingredients.remove(ing);
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
		if (weight < 0.0)
			throw new FoodException("Weight can't be negative");
		double sum = 0.0;
		for (Ingredient ing : ingredients.keySet()) {
			sum += ing.getCarbons((ingredients.get(ing)));
		}

		double w = getTotalWeight();
		return (sum / w) * weight;
	}

	@Override
	public double getFat(double weight) {
		if (weight < 0.0)
			throw new FoodException("Weight can't be negative");
		double sum = 0.0;
		for (Ingredient ing : ingredients.keySet()) {
			sum += ing.getFat((ingredients.get(ing)));
		}

		double w = getTotalWeight();
		return (sum / w) * weight;
	}

	@Override
	public double getCalories(double weight) {
		if (weight < 0.0)
			throw new FoodException("Weight can't be negative");
		double sum = 0.0;
		for (Ingredient ing : ingredients.keySet()) {
			sum += ing.getCalories((ingredients.get(ing)));
		}

		double w = getTotalWeight();
		return (sum / w) * weight;
	}

	@Override
	public double getProteins(double weight) {
		if (weight < 0.0)
			throw new FoodException("Weight can't be negative");
		double sum = 0.0;
		for (Ingredient ing : ingredients.keySet()) {
			sum += ing.getProteins((ingredients.get(ing)));
		}

		double w = getTotalWeight();
		return (sum / w) * weight;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReadyFood) {
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
