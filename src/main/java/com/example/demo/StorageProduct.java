package com.example.demo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import Exceptions.Messages;
import Exceptions.NotEnoughtInStockException;


@Entity
@Table(name = "products")
public class StorageProduct {
	
	@OneToOne
	@JoinColumn(name = "prod")
	private Produto prod;
	@Column(name = "amount")
	private Integer amountStorage;
	@Id
	private int id;
	@Autowired
	public StorageProduct(Produto prod, Integer amount){
		this.amountStorage = amount;
		this.prod = prod;
		this.id = prod.getCod();
	}
	
	public StorageProduct() {
		
	}
	
	public Produto getProd() {
		return prod;
	}

	public Integer getAmountStorage() {
		return this.amountStorage;
	}
	
	public void UpdatePrice(double newprice) {
		this.prod.setPrice(newprice);
	}
	//subtracts the new value from the old one, if results in a negative value, doesn't modify the original value
	public void decreaseAmount(Integer difference) throws NotEnoughtInStockException {
		int newValue = this.amountStorage - difference;
		if(newValue >= 0) {
			this.amountStorage = newValue;
		}
		else {
			throw new NotEnoughtInStockException(Messages.NOT_ENOUGH_IN_STOCK.getMessage()); 
		}
		
	}
	
	public String toString() {
		return this.prod.toString() + "\t" + "Quantidade estocada: " + this.amountStorage.toString();
	}
}