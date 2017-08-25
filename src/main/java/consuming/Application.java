package consuming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.FinalSale;

import java.util.Locale;
import java.util.Scanner;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        int choise = 0;
    	do {
    		Scanner keyboard = new Scanner(System.in);
    		keyboard.useLocale(Locale.US);
    		System.out.println("Enter the number that represents what you wanna do: "
    				+ 		   "0 - Exit\n"
    				+ 		   "1 - Show the Storage\n"
    				+ 		   "2 - Update a price of a product\n"
    				+ 		   "3 - Add a product on the storage\n"
    				+ 		   "4 - Add a product sale on a sale\n"
    				+ 		   "5 - Create a sale\n"
    				+ 		   "6 - Show all sales\n"
    				+ 		   "7 - Get a sale\n"
    				);
    		choise = keyboard.nextInt();
    		RestTemplate restTemplate = new RestTemplate();
    		
    		switch(choise) {
    		case 0: break;
    		
    		case 1: {
        			String x = restTemplate.getForObject("http://localhost:8080/show-storage", String.class);
        			System.out.println(x);
        			break;
    		}
    		case 2:{
    			
    				Double newPrice;
    				Integer cod;
    				System.out.print("Put the product code: ");
    				cod = keyboard.nextInt();
    				System.out.print("\nPut the product new price: ");
    				newPrice = keyboard.nextDouble();
    				String URI = "http://localhost:8080/update-price/" + cod+"?newPrice="+newPrice;
    				ResponseEntity<Void> x = restTemplate.postForEntity(URI,null,Void.class);
    				if( x.getStatusCode() == HttpStatus.OK)
    					System.out.println("Success changing the product price");
    				else
    					System.out.println("Error changing price, not found a product with that code doens't exist");
    				break;
	    		}
    				
    		case 3:{
    				Integer cod = 80, amount = 0;
    				Double price;
    				String name;
    				System.out.print("Put the product code: ");
    				cod = keyboard.nextInt();
    				System.out.print("\nPut the amount of product that is storaged: ");
    				amount = keyboard.nextInt();
    				System.out.print("\nPut the product price: ");
    				price = keyboard.nextDouble();
    				System.out.print("\nPut the product name: ");
    				name = keyboard.next();
    				
    				String URI = "http://localhost:8080/add-product?cod=" + cod.toString() +"&name=" + name +"&price=" + price.toString() +"&amount=" + amount.toString();
    				ResponseEntity<Integer> x2 = restTemplate.postForEntity(URI,null,Integer.class);
    				System.out.println("The product code is: " + x2.getBody().toString());
    				break;
    		}
    		
    		case 4:{
    			Integer prodCod = 0, saleCod = 0, amount = 0;
				System.out.print("Put the sale code: ");
				saleCod = keyboard.nextInt();
				System.out.print("Put the product code: ");
				prodCod = keyboard.nextInt();
				System.out.print("\nPut the amount of product that was sold: ");
				amount = keyboard.nextInt();
				
				String URI = "http://localhost:8080/addProduct/" + saleCod+"/?prodCod=" + prodCod + "&amount=" + amount;
				ResponseEntity<Void> x2 = restTemplate.postForEntity(URI,null,Void.class);
				if(x2.getStatusCode() == HttpStatus.OK) {
					System.out.println("The product sale was add at the sale");
				}
				else {
					System.out.println("Error at adding the product sale");
				}
    		}
    		case 5:{
    			Long saleCod;
				ResponseEntity<Long> x2 = restTemplate.postForEntity("http://localhost:8080/createSale",null,Long.class);
				if(x2.getStatusCode() == HttpStatus.CREATED) {
					System.out.println("The sale was created, and the sale code is: "+x2.getBody());
				}
				else {
					System.out.println("The sale was not created");
				}
    			
    		}
    		
    		case 6:{
    			Iterable<FinalSale> x = restTemplate.getForObject("http://localhost:8080/show-storage", null );
    			System.out.println(x);
    			break;
    		}
    			
    		}
        }while(choise != 0);
  
    }

}