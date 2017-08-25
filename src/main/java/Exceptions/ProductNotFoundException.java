package Exceptions;

public class ProductNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ProductNotFoundException(){
		
	}
	public ProductNotFoundException(String message){
		super(message);
	}
	
}
