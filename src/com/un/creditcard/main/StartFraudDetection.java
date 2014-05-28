package com.un.creditcard.main;

import java.util.List;
import java.util.Scanner;

import com.un.creditcard.pojo.Transaction;
import com.un.creditcard.service.AnalyseTransaction;
import com.un.creditcard.util.TransactionUtil;

public class StartFraudDetection {

	private static final String CHECK_FOR_DAY = "2014-05-28";
	private static final Double THRESHOLD_PRICE = new Double(1000.00);

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

			checkDay = userEntry;
			System.out.println("please enter the Threshold amount");
			thresholdPrice = scanner.next();

			try {
				List<Transaction> transactionList = TransactionUtil.getTransactionsFromFile();
				if (transactionList != null) {
					fraudTransactionCardList = AnalyseTransaction.checkTranasctionsForGivenDay(	transactionList,
									checkDay != null ? checkDay : CHECK_FOR_DAY, thresholdPrice != null ? Double.valueOf(thresholdPrice) : THRESHOLD_PRICE);

					if (fraudTransactionCardList != null && fraudTransactionCardList.size() > 0) {
						System.out.println("Fraud transactions detected from the following CreditCards for " + CHECK_FOR_DAY);
						for (String hashedCreditCardNumber : fraudTransactionCardList) {
							System.out.println("hashed Credit Card # "	+ hashedCreditCardNumber);
						}
					} else {
						System.out.println("No fraud CreditCard transactions detected for "	+ CHECK_FOR_DAY);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("please enter Exit to terminate the system, or enter a date(yyyy-mm-dd) to continue");
		}

	}

}
