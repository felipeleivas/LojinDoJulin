package com.example.demo;


import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author femachado
 *
 */


public class SalesReceipt {
	@OneToMany
	@JoinColumn(name = "sales")
	private FinalSale sales;
	
	@SuppressWarnings("unused")
	private Long receiptId;
	
	/**
	 * Creates a instance of SalesReceipt
	 * @param The final sale that will generate the SalesReceipt
	 */
	@Autowired
	public SalesReceipt(FinalSale sales) {
		this.sales = sales;
		this.receiptId = sales.getSaleId();
	}

	public FinalSale getSales() {
		return sales;
	}
	
	@Override
	public String toString() {
		return this.sales.toString();
	}
	
}
