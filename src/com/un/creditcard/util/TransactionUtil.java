package com.un.creditcard.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.un.creditcard.pojo.Transaction;

public class TransactionUtil {
	
	/**
	 * Create a List of transactions 
	 * @return List<String> Transaction details (hashed credit card number, transaction date, transaction amount) in the form of (String,String,String)
	 */
	public static List<String> getTransactions() {
		List<String> transactionList = new ArrayList<String>();

		Transaction t1 = new Transaction("123456789", DateUtil.createDateFromString("2014-05-23"), 100.20);
		Transaction t2 = new Transaction("1234567891011", new Date(), 400.00);
		Transaction t3 = new Transaction("123456789", DateUtil.createDateFromString("2014-05-23"), 500.75);
		Transaction t4 = new Transaction("123456789", DateUtil.createDateFromString("2014-05-23"), 600.20);
		Transaction t5 = new Transaction("1234567891011121", new Date(), 1000.50);
		Transaction t6 = new Transaction("123456789", DateUtil.createDateFromString("2014-05-23"), 900.75);

		transactionList.add(t1.toString());
		transactionList.add(t2.toString());
		transactionList.add(t3.toString());
		transactionList.add(t4.toString());
		transactionList.add(t5.toString());
		transactionList.add(t6.toString());

		return transactionList;
	}
	
	

}
