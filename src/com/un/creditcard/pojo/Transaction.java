package com.un.creditcard.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	
	private String credirCardNum;
	private String paymentDone;
	private Double price;
	
	public Transaction(){};
	
	public Transaction(String credirCardNum, Date paymentDone, Double price){
		this.credirCardNum =  credirCardNum;
		this.paymentDone = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(paymentDone);		
		this.price = price;
	}

	public String getCredirCardNum() {
		return credirCardNum;
	}

	public void setCredirCardNum(String credirCardNum) {
		this.credirCardNum = credirCardNum;
	}

	public String getPaymentDone() {
		return paymentDone;
	}

	public void setPaymentDone(String paymentDone) {
		this.paymentDone = paymentDone;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		
		return credirCardNum.hashCode()+ "," +
				paymentDone.toString() + "," +
				price.toString();
	}

}
