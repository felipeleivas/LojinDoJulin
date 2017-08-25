package Exceptions;


public enum Messages {
	NOT_ENOUGH_IN_STOCK("There is no enought product in stock"),STOCK_CANT_BE_NEGATIVE("The stock of a product can't be negative"),
	PRODUCT_NOT_FOUND("There is no product with this code");
	private String message;
	
	
	private Messages(String message) {
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}
	public String toString() {
		return this.message;
	}
}
