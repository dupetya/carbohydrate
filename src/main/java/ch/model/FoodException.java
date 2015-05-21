package ch.model;

public class FoodException extends RuntimeException {

	public FoodException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FoodException(String arg0) {
		super(arg0);
	}

	public FoodException(Throwable cause) {
		super(cause);
	}
	
}