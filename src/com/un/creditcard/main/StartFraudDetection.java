package com.un.creditcard.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.un.creditcard.pojo.Transaction;
import com.un.creditcard.service.CreditCardTransactionAnalyser;
import com.un.creditcard.service.TransactionAnalyser;
import com.un.creditcard.util.TransactionListFactory;

public class StartFraudDetection {

	private static final String CHECK_FOR_DAY = "2014-01-30";
	private static final Double THRESHOLD_PRICE = new Double(1000.00);
	private static TransactionListFactory transactionListFactory;
	private static TransactionAnalyser analyseTransaction;

	public static void main(String[] args) {

		String checkDay = null;
		String thresholdPrice = null;

		List<String> fraudTransactionCardList = null;

		System.out.println("Fraud Detection System running...");
		System.out.println("please enter a date(yyyy-mm-dd) to check transactions");
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String userEntry = scanner.next();
			if (userEntry.equalsIgnoreCase("exit")) {
				scanner.close();
				break;
			}

			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");               
			try {
				format.parse(userEntry);
				checkDay = userEntry;
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Using default date- " + CHECK_FOR_DAY);
				checkDay = CHECK_FOR_DAY;
			}		
			
			System.out.println("please enter the Threshold amount");
			thresholdPrice = scanner.next();

			try {
				transactionListFactory = new TransactionListFactory();
				
				List<Transaction> transactionList = transactionListFactory.populateTransactions("CSV");
				if (transactionList != null) {
					analyseTransaction = new CreditCardTransactionAnalyser(checkDay != null ? checkDay : CHECK_FOR_DAY, 
							thresholdPrice != null ? Double.valueOf(thresholdPrice) : THRESHOLD_PRICE);
					fraudTransactionCardList = analyseTransaction.analyse( transactionList	);

					if (fraudTransactionCardList != null && fraudTransactionCardList.size() > 0) {
						System.out.println("Fraud transactions detected from the following CreditCards for " + checkDay);
						for (String hashedCreditCardNumber : fraudTransactionCardList) {
							System.out.println("hashed Credit Card # "	+ hashedCreditCardNumber);
						}
					} else {
						System.out.println("No fraud CreditCard transactions detected for "	+ checkDay);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("please enter 'Exit' to terminate the system, or enter a date(yyyy-mm-dd) to continue");
		}

	}

}
