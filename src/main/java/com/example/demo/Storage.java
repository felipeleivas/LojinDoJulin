package com.example.demo;

import java.math.BigDecimal;
import java.util.ArrayList;



import org.springframework.stereotype.Component;

import Exceptions.Messages;
import Exceptions.NotEnoughtInStockException;
import Exceptions.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author femachado
 *
 */
@Component
public class Storage implements StorageManagement {
	private StorageProductRepository stock;
	private ProductRepository productsRegisters;
	

	public Storage() {
		
	}
	@Autowired
	public Storage(StorageProductRepository stock, ProductRepository productRepository) {
		this.productsRegisters = productRepository;
		this.stock = stock;
	}
	@Override
	public void addProductInStock(Produto prod, int amount) {
		this.productsRegisters.save(prod);
		Produto registredProduct = this.productsRegisters.findByCod(prod.getCod());
		this.stock.save(new StorageProduct(registredProduct, amount));
		
	}
	@Override
	public void productWasSold(Sale sale) throws NotEnoughtInStockException, ProductNotFoundException {
		if(this.enoughInStock(sale)) {
			this.updateStockAfterSale(sale);
		}	
	}
	@Override
	public void soldAListOfProduct(ArrayList<ProductSale> sales) throws NotEnoughtInStockException, ProductNotFoundException {
		for (ProductSale sale: sales) {
			if(!this.enoughInStock(sale)) {
				throw new NotEnoughtInStockException(Messages.NOT_ENOUGH_IN_STOCK.getMessage() + sale.getProduct().getName() + "!");
			}				
		}
		this.updateStockAfterSale(sales);
	}
	@Override
	public Boolean enoughInStock(ProductSale sale) throws ProductNotFoundException {
		StorageProduct prod = this.getStorageProductByCode(sale.getProduct().getCod());
		if(prod != null) {
			if(prod.getAmountStorage() >= sale.getAmount()) {
				return true;
			}
		}
		else {
			throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND.getMessage());
		}
		return false;
	}
	@Override
	public Boolean enoughInStock(Sale sale) throws ProductNotFoundException{
		StorageProduct prod = this.getStorageProductByCode(sale.getProdCode());
		if(prod != null) {
			if(prod.getAmountStorage() >= sale.getAmount()) {
				return true;
			}
		}
		else {
			throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND.getMessage());
		}
		return false;
	}	
	public Produto getProductByCode(int code) throws ProductNotFoundException {
		Produto prod =  this.getStorageProductByCode(code).getProd();
		return prod;
	}
	@Override
	public String toString() {
		String str = "";
		BigDecimal total = BigDecimal.ZERO;
		for(StorageProduct product: this.stock.findAll()) {
			str += product.toString() + "\n";
			 total = total.add(product.getProd().getPrice().multiply(new BigDecimal(product.getAmountStorage( ) ) ) ); 
		}
		str += "Total stocked price: " + total.toString()+"\n";
		return str;
	}
	
	private StorageProduct getStorageProductByCode(int cod) throws ProductNotFoundException {
		if(this.stock.exists(cod)) {
			return this.stock.findById(cod);			
		}
		else {
			throw new ProductNotFoundException();
		}
	}	
	private void updateProductOnStorage(StorageProduct prod) {
		this.stock.delete(prod);
		this.stock.save(prod);
	}
	private void updateStockAfterSale(ArrayList<ProductSale> sales) throws NotEnoughtInStockException, ProductNotFoundException {
		for (ProductSale sale: sales) {
			updateStockAfterSale(sale);
		}
	}
	private void updateStockAfterSale(ProductSale sale) throws NotEnoughtInStockException, ProductNotFoundException {
		StorageProduct product = this.stock.findById(sale.getProduct().getCod());
		if(product != null) {
			int difference = sale.getAmount();
			product.decreaseAmount(difference);
			this.updateProductOnStorage(product);
		}
	}	
	private void updateStockAfterSale(Sale sale) throws NotEnoughtInStockException, ProductNotFoundException {
		StorageProduct product = this.stock.findById(sale.getProdCode());
		this.updateProductOnStorage(product);
	}
	@Override
	public void setPrice(Double price, int id) throws ProductNotFoundException {
		StorageProduct prod = this.getStorageProductByCode(id);
		prod.UpdatePrice(price);
		this.stock.save(prod);
		
	}
	@Override
	public Boolean exitsProduct(Integer id) {
		return this.stock.exists(id);	}
	
	
}
