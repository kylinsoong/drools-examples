package com.sample;


public class ShoppingCart implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6698406262844403388L;
	private Double amount;
	private Double discount;
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
	
	
}
