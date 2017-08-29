package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Exceptions.ProductCodeAlreadyExistsException;
import Exceptions.ProductNotFoundException;
import Exceptions.SaleNotFoundException;

@Controller
public class SistemaController {
	
	SystemInterface systemMng;
	
	@Autowired
	public SistemaController( SystemInterface sisMg) {
		this.systemMng = sisMg;
	}
	//localhost:8080/show-storage
	@GetMapping("/show-storage")
	public	String showStorage(Model m1) {
		m1.addAttribute("storage",  this.systemMng.getStock());
		return "show-storage";
	}
	//Update a price of a product
	@PostMapping("/update-price")
	public String  updatePrice(@RequestParam("Product Code") int id, @RequestParam("New Price") Double newPrice, Model m1){
		try {
			this.systemMng.updatePrice(id, newPrice);
		} catch (ProductNotFoundException e) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String x = "The price was updated";
		m1.addAttribute("message",x);
//		return new ResponseEntity<>("update-price",HttpStatus.OK);
		return "update-price-answer";
	}
	@GetMapping("/update-price")
	public String  updatePrice(Model m1){
		return "update-price";
	}
	//add product on the stock
	
	@PostMapping("/add-product")
	public ResponseEntity<Integer> addProduct(@RequestParam("Product Code") int cod,@RequestParam("Name") String name,
											  @RequestParam("Price")Double price,   @RequestParam("Amount") int amount){
		Integer prodCode = null;
		try {
			prodCode = this.systemMng.addProduct(new Produto(name, cod, price),amount);
		} catch (ProductNotFoundException | ProductCodeAlreadyExistsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(prodCode,HttpStatus.OK);
	}
	@GetMapping("/add-product")
	public String addProduct(){
		return  "add-product";
	}
	//adds a product on a sale
	@PostMapping("/addProduct-on-sale")
	public ResponseEntity<Void>  addProductSale(@RequestParam("Sale Code") Long id,@RequestParam("Product Code") int prodCod,@RequestParam("Amount") int amount){
		try {
			this.systemMng.addProductSaleOnASale(id, new Sale(prodCod, amount));
		} 
		catch (ProductNotFoundException  | SaleNotFoundException e2) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}			
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/addProduct-on-sale")
	public String  addProductSale(){	
		return "addProduct-on-sale";
	}
	//Create a empty sale;
	@PutMapping("/createSale")
	public String createSale(Model m1){
		Long id =  this.systemMng.createSale();
		m1.addAttribute(id);
		return "createSale";
	}
	
	//Show all sales
	@GetMapping("/showsales")
	public String showSales(Model m1){
		m1.addAttribute("allSales",this.systemMng.getSales());
		return "show-sales";
	}
	
	@GetMapping("/getsale")
	public String getSale(@RequestParam("id") Long id,Model m1) {
		try {
			FinalSale sale = this.systemMng.getFinalSale(id);
			m1.addAttribute("sale",sale);
		} catch (SaleNotFoundException e) {
			return "Error";
		}
		return "show-sales-id";
	}
	@GetMapping("/getsale2")
	public String getSale2(Model m1) {
		return "show-sales2-id";
	}
}
