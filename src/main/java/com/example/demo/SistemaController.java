package com.example.demo;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import Exceptions.NotEnoughtInStockException;
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
	@PutMapping("/addProduct/{saleID}")
	public ResponseEntity<Void>  addProductSale(@PathVariable("saleID") Long id, int prodCod, int amount){
		try {
			this.systemMng.addProductSaleOnASale(id, new Sale(prodCod, amount));
		} 
		catch (ProductNotFoundException  | SaleNotFoundException e2) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}			
		return new ResponseEntity<>(HttpStatus.OK);
	}
	//Create a empty sale;
	@PutMapping("/createSale")
	public ResponseEntity<Long> createSale(){
		Long id=  this.systemMng.createSale();
		return new ResponseEntity<>(id,HttpStatus.CREATED);
	}
	//Show all sales
	@GetMapping("/showsales")
	public Iterable<FinalSale> showSales(){
		return this.systemMng.getSales();
	}
	
	@GetMapping("/getsale/{id}")
	public ResponseEntity<FinalSale> getSale(@PathVariable("id") Long id) {
		try {
			FinalSale sale = this.systemMng.getFinalSale(id);
			return new ResponseEntity<FinalSale>(sale,HttpStatus.OK);
		} catch (SaleNotFoundException e) {
			return new ResponseEntity<FinalSale>(HttpStatus.NOT_FOUND);
		}
	}
}
