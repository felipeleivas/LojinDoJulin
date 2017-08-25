package Exceptions;

public class ProductCodeAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductCodeAlreadyExistsException(){
		
	}
	public ProductCodeAlreadyExistsException(String message){
		super(message);
	}
}
