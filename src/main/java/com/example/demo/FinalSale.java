package com.example.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
//import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.persistence.Temporal;

@Entity
@Table(name ="finalsales")
public class FinalSale {
		@JoinColumn(name = "sales")
		@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
		private List<ProductSale> sales;
		//private BigDecimal totalPrice;

		//		@Temporal(javax.persistence.TemporalType.TIME)
		//		private LocalDate data;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long saleId;

		/**
		 * Creates a instance of SalesReceipt
		 * @param sales an Array with all sales that will be compute by this Receipt
		 */
		public FinalSale(List<ProductSale> sales) {
			this.sales = sales;
//			BigDecimal total = BigDecimal.ZERO;
//			//Calculates the total price 
//			for(ProductSale tempSale: sales) {
//				total = total.add( tempSale.getTotalprice());
//			}
//			this.totalPrice = total;
//			
		}
		public FinalSale() {
			this.sales = new ArrayList<ProductSale>();
		//	this.totalPrice = BigDecimal.ZERO;
		}
		
		public void addProductSale(ProductSale sale) {
			this.sales.add(sale);
		//	this.totalPrice.add(sale.getTotalprice());
		}
		
		public List<ProductSale> getSales() {
			return sales;
		}

		public BigDecimal getTotalPrice() {
			BigDecimal total = BigDecimal.ZERO;
			for(ProductSale tempSale: sales) {
				total = total.add( tempSale.getSubTotalPrice());
			}
			return total;
			
		}
		
		public Long getSaleId() {
			return this.saleId;
		}
		@Override
		public String toString() {
			String text = "";
			for(ProductSale sale: this.sales) {
				text = text + sale.toString() + "\n";
			}
			
			text = text + this.getTotalPrice().toString() ;
//			text = text +  "\n sold in: "+ data.toString();
			return text;
		}
		
	
}
