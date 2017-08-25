package com.example.demo;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Exceptions.Messages;
import Exceptions.NotEnoughtInStockException;
import Exceptions.ProductCodeAlreadyExistsException;
import Exceptions.ProductNotFoundException;
import Exceptions.SaleNotFoundException;


/**
 * @author femachado
 * These Class is has a list of products and handle it's sales
 */
@Component
public class Sistema implements SystemInterface{
	StorageManagement stock;
	SalesInterface sales;
	/**
	 * Creates a instance of Sistema 
	 */
	@Autowired
	public Sistema(StorageManagement stock, SalesInterface sales) {
		this.stock = stock;
		this.sales = sales;
	}
	public Sistema() {
		
	}
	/**
	 * Adds the newProduct to the Sistema list of products
	 * @param newProduct
	 * @throws ProductNotFoundException 
	 * @throws ProductCodeAlreadyExistsException 
	 */
	public Integer addProduct (Produto newProduct, int amount) throws ProductNotFoundException, ProductCodeAlreadyExistsException {
		if(this.stock.exitsProduct(newProduct.getCod())) {
			throw new ProductCodeAlreadyExistsException();
		}
		
		this.stock.addProductInStock(newProduct, amount);
		return this.stock.getProductByCode(newProduct.getCod()).getCod();
	}
	/**
	 * Generates the SalesReceipt of a incoming List of Sale
	 * @param sales An ArrayList of Sale, that contains all sales
	 * @return a SaleReceipt that contains all the sales and the total price
	 * @throws ProductNotFoundException 
	 */
	public SalesReceipt generateSalesReceipt(ArrayList<Sale> sales) throws NotEnoughtInStockException, ProductNotFoundException {
		FinalSale productSales = this.generateSale(sales);
		SalesReceipt salesReceipt = this.generateSalesReceipt(productSales);
		return salesReceipt;
	}
	/**
	 * @param productSales
	 * @return
	 * @throws NotEnoughtInStockException
	 * @throws ProductNotFoundException
	 */
	public SalesReceipt generateSalesReceipt(FinalSale productSales) throws NotEnoughtInStockException, ProductNotFoundException {
		SalesReceipt salesReceipt = new SalesReceipt(productSales);
		return salesReceipt;
	}
	public FinalSale generateSale(ArrayList<Sale> sales) throws NotEnoughtInStockException, ProductNotFoundException {
		ArrayList<ProductSale> productSales = new ArrayList<ProductSale>();
		// Generates the sale of each Product and adds into an array
		ProductSale tempProductSale;	
		for(Sale actualSale: sales) {
			if(this.enoughtInStock(actualSale)) { //tests if the product can be sold
				tempProductSale = new ProductSale (this.getProductByCode(actualSale.getProdCode()), actualSale.getAmount());
				productSales.add(tempProductSale);								
			}
			else {
				throw new NotEnoughtInStockException(Messages.NOT_ENOUGH_IN_STOCK.getMessage() + ": " + this.getProductByCode(actualSale.getProdCode()).getName());
			}
			
		}
		// If all products can be sold update the data base
		this.updateStockAfterSale(productSales);
		// Do the sale
		FinalSale finalSale = new FinalSale(productSales);
		// Save the sale in the DB
		this.updateSale(finalSale);
		return finalSale;
	}	
	/**
	 * @param code The code of a saved product
	 * @return the product with that code
	 * @throws ProductNotFoundException 
	 */
	private Produto getProductByCode(int code) throws ProductNotFoundException{
		return this.stock.getProductByCode(code);

	}	
	public String printStock() {
		return this.stock.toString();
	}
	@Override
	public void updatePrice(int cod, Double newPrice) throws ProductNotFoundException {
		this.stock.setPrice(newPrice, cod);
		
	}
	@Override
	public void addProductSaleOnASale(Long id, Sale sale) throws ProductNotFoundException, SaleNotFoundException {
		FinalSale oldSale = this.findSaleById(id);
		if (oldSale != null) {			
			Produto prod = this.getProductByCode(sale.getProdCode());
			ProductSale newSale = new ProductSale(prod,sale.getAmount());
			oldSale.addProductSale(newSale);
			this.updateSale(oldSale);
		}
		else
			throw new SaleNotFoundException();
	}
	@Override
	public Iterable<FinalSale> getSales() {
		
		return this.sales.findAll();
	}
	@Override
	public Long createSale() {
		FinalSale newSale = new FinalSale();
		return this.sales.save(newSale).getSaleId();
	}
	@Override
	public FinalSale getFinalSale(Long id) throws SaleNotFoundException {
		return this.findSaleById(id);
	}
	
	private boolean enoughtInStock(Sale sale) throws ProductNotFoundException {
		return this.stock.enoughInStock(sale);
	}
	private void updateStockAfterSale(ArrayList<ProductSale> sales) throws NotEnoughtInStockException, ProductNotFoundException {
		this.stock.soldAListOfProduct(sales);
	}
	private void updateSale(FinalSale sale) {
		this.sales.save(sale);
	}
	private FinalSale findSaleById(Long id) throws SaleNotFoundException {
		if(sales.exists(id)) {
			return this.sales.findBySaleId(id);			
		}
		else {
			throw new SaleNotFoundException();
		}
	}
}
