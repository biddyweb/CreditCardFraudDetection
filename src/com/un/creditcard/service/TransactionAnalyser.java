package com.un.creditcard.service;

import java.util.List;

import com.un.creditcard.pojo.Transaction;

public interface TransactionAnalyser {
	
	public List<String> analyse(List<Transaction> transactions);

}
