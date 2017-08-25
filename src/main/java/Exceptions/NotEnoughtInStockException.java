package Exceptions;

public class NotEnoughtInStockException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughtInStockException(){
		
	}
	public NotEnoughtInStockException(String message){
		super(message);
	}
}
