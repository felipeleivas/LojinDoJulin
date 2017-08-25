package Exceptions;

public class SaleNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public SaleNotFoundException(){
		
	}
	public SaleNotFoundException(String message){
		super(message);
	}
	
}
