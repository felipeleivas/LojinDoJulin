package com.example.demo;

import java.util.ArrayList;

import Exceptions.NotEnoughtInStockException;
import Exceptions.ProductNotFoundException;

public interface StorageManagement {

	public void addProductInStock(Produto prod, int amount);
	public void productWasSold(Sale sale) throws NotEnoughtInStockException, ProductNotFoundException;
	public void soldAListOfProduct(ArrayList<ProductSale> sales) throws NotEnoughtInStockException, ProductNotFoundException;
	public Boolean enoughInStock(ProductSale sale) throws ProductNotFoundException;
	public Boolean enoughInStock(Sale sale) throws ProductNotFoundException;
	public Produto getProductByCode(int code) throws ProductNotFoundException;
	void setPrice(Double price, int id) throws ProductNotFoundException;
	public String toString() ;
	public Boolean exitsProduct(Integer id);
}
