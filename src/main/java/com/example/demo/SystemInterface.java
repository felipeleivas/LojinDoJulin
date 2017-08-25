package com.example.demo;

import java.util.ArrayList;

import Exceptions.NotEnoughtInStockException;
import Exceptions.ProductCodeAlreadyExistsException;
import Exceptions.ProductNotFoundException;
import Exceptions.SaleNotFoundException;

public interface SystemInterface {
	public Integer addProduct (Produto newProduct, int amount) throws ProductNotFoundException, ProductCodeAlreadyExistsException;
	
	public SalesReceipt generateSalesReceipt(ArrayList<Sale> sales) throws NotEnoughtInStockException, ProductNotFoundException; 
	public SalesReceipt generateSalesReceipt(FinalSale productSales) throws NotEnoughtInStockException, ProductNotFoundException;	
	public FinalSale generateSale(ArrayList<Sale> sales) throws NotEnoughtInStockException, ProductNotFoundException;
	public String printStock() ;
	public void addProductSaleOnASale(Long id, Sale sale) throws ProductNotFoundException, SaleNotFoundException;
	public void updatePrice(int cod, Double newPrice) throws ProductNotFoundException;
	public Iterable<FinalSale> getSales();
	public Long createSale() ;
	public FinalSale getFinalSale(Long id) throws SaleNotFoundException;
	public Iterable<StorageProduct> getStock();
}
