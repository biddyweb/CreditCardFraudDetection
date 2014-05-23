package com.un.creditcard.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyseTransaction {

	private final static Logger LOGGER = Logger.getLogger(AnalyseTransaction.class.getName());

	/**
	 * Check a list of transactions to identify if any fraud activities were done on a given day
	 * @param transactions a list of transactions to check for fraud
	 * @param transactionDate the date to filter transactions
	 * @param priceThreshold max transaction amount for a day
	 * @return List<String> of hashed credit card numbers which exceeded the daily transaction threshold limit; or null if no records found
	 */
	public static List<String> checkTranasctionsForGivenDay(List<String> transactions, String checkDate, Double priceThreshold) {

		Map<String, Double> transactionTotalPerCard = returnAllTransactiondForGivenDate(transactions, checkDate);

		if(transactionTotalPerCard != null && transactionTotalPerCard.size() > 0){
			return applyPriceThreshold(transactionTotalPerCard, priceThreshold);
		}else{
			return null;
		}
		
	}

	/**
	 * Detect the fraud credit cards which has exceeded the allowable limit
	 * @param transactionTotalPerCard contains # CreditCard numbers with daily used amount
	 * @param priceThreshold  maximum amount allowed daily
	 * @return List<String> hashed credit card numbers
	 */
	private static List<String> applyPriceThreshold(Map<String, Double> transactionTotalPerCard, Double priceThreshold) {
		
		List<String> detectedAsFraud = new ArrayList<String>();

		for (Map.Entry<String, Double> entry : transactionTotalPerCard.entrySet()) {
			//check if total amount exceed threshold amount
			if (entry.getValue().compareTo(priceThreshold) >= 0) {
				LOGGER.log(Level.INFO, "Fraud detected for card # -" + entry.getKey() + " , tomal amount is  " + entry.getValue());
				detectedAsFraud.add(entry.getKey());
			}
		}

		return detectedAsFraud;
	}

	/**
	 * Filter transactions by the given date and return a list of hashed credit card numbers with daily spent amount 
	 * @param transactions a list of transactions
	 * @param checknDate check transactions for the particular date
	 * @return Map<String, Double>
	 */
	private static Map<String, Double> returnAllTransactiondForGivenDate( List<String> transactions, String checkDate) {

		boolean atleastOneMatchedTransaction = false;
		String creditCardNumber = null;
		String paymententDate = null;
		Double paymentAmount = null;
		String[] transactionDetails = null;
		Date checkForDate, tranactionDay;
		Map<String, Double> transactionTotalPerCard = new HashMap<String, Double>();

		for (String tranaction : transactions) {
			LOGGER.log(Level.INFO, tranaction);

			transactionDetails = tranaction.split(",");			
			
			if (transactionDetails != null && transactionDetails.length == 3) {
				//assuming the split string will always have 3 items
				creditCardNumber = transactionDetails[0];
				paymententDate = transactionDetails[1];
				paymentAmount = Double.valueOf(transactionDetails[2]);

				try {
					checkForDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkDate);					
					tranactionDay = new SimpleDateFormat("yyyy-MM-dd").parse(paymententDate);

					if (tranactionDay.equals(checkForDate)){ 
						LOGGER.log(Level.INFO, "transaction date matched.. proceed with verification");

						atleastOneMatchedTransaction = true;
						
						// keep adding amount if credit card number exists in Map
						Double existingValue = transactionTotalPerCard.get(creditCardNumber);
						transactionTotalPerCard.put(creditCardNumber, existingValue == null ? paymentAmount	: existingValue + paymentAmount);
					}
				} catch (ParseException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
			}else{
				LOGGER.log(Level.SEVERE, "Transaction record not in given format");
			}
		}
		
		if(!atleastOneMatchedTransaction){
			LOGGER.log(Level.INFO, "No Transactions found for given date");
		}

		return transactionTotalPerCard;
	}

}
