package com.un.creditcard.util;

import java.util.List;

import com.un.creditcard.pojo.Transaction;

public class TransactionListFactory {
	
	public List<Transaction> populateTransactions(String fileType){
		
		List<Transaction> transactionList = null;
		
		if(fileType.equalsIgnoreCase("CSV")){
			transactionList = TransactionListUtil.getTransactionsFromCSVFile();
		}else if(fileType.equalsIgnoreCase("XML")){
			transactionList = TransactionListUtil.getTransactionsFromXMLFile();
		}else{
			transactionList = TransactionListUtil.createTransactionsManually();
		}
		
		return  transactionList;
	}

}
