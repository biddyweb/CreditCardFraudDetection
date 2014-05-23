package com.un.creditcard.main;

import java.util.List;

import com.un.creditcard.service.AnalyseTransaction;
import com.un.creditcard.util.TransactionUtil;

public class StartFraudDetection {
	
	private static final String CHECK_FOR_DAY = "2014-05-23";
	private static final Double THRESHOLD_PRICE = new Double(1000.00);

	public static void main(String[] args) {
		
		List<String> fraudTransactionCardList = null;
		try{
			List<String> transactionList = TransactionUtil.getTransactions();
			if(transactionList != null){
				fraudTransactionCardList = AnalyseTransaction.checkTranasctionsForGivenDay(transactionList, CHECK_FOR_DAY, THRESHOLD_PRICE);
				
				if(fraudTransactionCardList != null && fraudTransactionCardList.size() > 0){
					System.out.println("Fraud transactions detected from the following CreditCards for " + CHECK_FOR_DAY);
					for(String hashedCreditCardNumber : fraudTransactionCardList){
						System.out.println("hashed Credit Card # " + hashedCreditCardNumber);
					}
				}else{
					System.out.println("No fraud CreditCard transactions detected for " + CHECK_FOR_DAY);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
