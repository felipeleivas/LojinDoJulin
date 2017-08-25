package com.example.demo;

import java.math.BigDecimal;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * These Class is responsible for a sale of ONE product
 * @author flmachado
 *
 */
@Entity
public class ProductSale {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long saleId;
	
	private Integer amount;
	@JoinColumn(name = "prod")
	@ManyToOne(fetch = FetchType.EAGER)
	private Produto product;
	private BigDecimal subTotalprice;
	
	
	/** Given a Product and a amount, calculates the total price of the sale of this product
	 * @param product the product that was sold
	 * @param amount the amount of product that was sold
	 */
	@Autowired
	public ProductSale(Produto product, int amount) {
		this.amount = amount;
		this.product = product;
		BigDecimal amout_aux = new BigDecimal(this.amount);
		this.subTotalprice = product.getPrice().multiply(amout_aux) ; 
	}
	public ProductSale() {
		
	}

	public int getAmount() {
		return amount;
	}

	public Produto getProduct() {
		return product;
	}

	public BigDecimal getTotalprice() {
		return subTotalprice;
	}
	
	public String toString() {
		return product.toString() + "\tVendidas: " + amount.toString() + "\tPreço total: " + subTotalprice.toString();
	}

}
