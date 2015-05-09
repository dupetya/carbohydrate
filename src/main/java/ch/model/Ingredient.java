package ch.model;

public class Ingredient implements Food {
	private String id;
	private String name;
	private double proteins;
	private double fat;
	private double calories;
	private double carbons;

	public Ingredient(String id, String name, double proteins, double fat,
			double calories, double carbons) {
		super();
		this.id = id;
		this.name = name;
		this.proteins = proteins;
		this.fat = fat;
		this.calories = calories;
		this.carbons = carbons;
	}

	public Ingredient(String id, String name) {
		this(id, name, 0.0, 0.0, 0.0, 0.0);
	}

	public double getCarbons() {
		return carbons;
	}

	public double getFat() {
		return fat;
	}

	public double getCalories() {
		return calories;
	}

	public double getProteins() {
		return proteins;
	}

	public double getCarbons(double weight) {
		if (weight < 0.0)
			throw new IllegalArgumentException(
					"Weight can't be negative value!");
		return carbons / 100 * weight;
	}

	public double getFat(double weight) {
		if (weight < 0.0)
			throw new IllegalArgumentException(
					"Weight can't be negative value!");
		return fat / 100 * weight;
	}

	public double getCalories(double weight) {
		if (weight < 0.0)
			throw new IllegalArgumentException(
					"Weight can't be negative value!");
		return calories / 100 * weight;
	}

	public double getProteins(double weight) {
		if (weight < 0.0)
			throw new IllegalArgumentException(
					"Weight can't be negative value!");
		return proteins / 100 * weight;
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

	public void setProteins(double proteins) {
		this.proteins = proteins;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public void setCarbons(double carbons) {
		this.carbons = carbons;
	}

	@Override
	public int hashCode() {
		return 31 * this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ingredient) {
			Ingredient ig = (Ingredient) obj;
			return this.id.equals(ig.id);
		}
		return false;
	}

}
