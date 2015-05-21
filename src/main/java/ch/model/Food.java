package ch.model;

public interface Food {
	public double getCarbons();

	public double getFat();

	public double getCalories();

	public double getProteins();

	public double getCarbons(double weight);

	public double getFat(double weight);

	public double getCalories(double weight);

	public double getProteins(double weight);
}
