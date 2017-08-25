package com.example.demo;

import java.math.BigDecimal;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This classes is responsible for the data of ONE product
 * @author femachado
 *
 */
@Entity
public class Produto {
	//@ManyToOne
	@Id
	private Integer cod;	
	private String name ;
	private BigDecimal price;
	
	/**
	 * Given a name, a code and a price, generates a product
	 * @param name
	 * @param cod
	 * @param price
	 */
	@Autowired
	public Produto(String name, int cod, Double price) {
		this.name = name;
		this.cod = cod;
		this.price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public	Produto() {
		
	}
	public Integer getCod() {
		return cod;
	}

	public String getName() {
		return name;
	}
	public void setPrice(double price) {
		this.price =  new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getPrice() {
		return price;
	}
	
	public String toString() {
		return "Produto nome: "+ this.name + "\tCodigo:" + this.cod + "\tpreço: "+ this.price;
	}
}
