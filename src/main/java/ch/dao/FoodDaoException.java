package ch.dao;

public class FoodDaoException extends Exception {

	public FoodDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public FoodDaoException(String message) {
		super(message);
	}

	public FoodDaoException(Throwable cause) {
		super(cause);
	}
	

}
