package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;

/**
 * These class is responsible 
 * @author femachado
 *
 */

public class Sale {
	//@OneToOne
	private int prodCode;
	private int amount;
	@Autowired
	public Sale(int prodCode, int amount){
		this.prodCode = prodCode;
		this.amount = amount;
	}
	
	
	public int getProdCode() {
		return prodCode;
	}

	public int getAmount() {
		return amount;
	}
}
