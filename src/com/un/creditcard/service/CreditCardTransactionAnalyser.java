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

import com.un.creditcard.pojo.Transaction;

public class CreditCardTransactionAnalyser implements TransactionAnalyser {
	
	private String checkDate;
	private Double priceThreshold;

	private final static Logger LOGGER = Logger.getLogger(CreditCardTransactionAnalyser.class.getName());
	
	public CreditCardTransactionAnalyser(String checkDate, Double priceThreshold){
		this.checkDate = checkDate;
		this.priceThreshold = priceThreshold;
	}
	
	@Override
	public List<String> analyse(List<Transaction> transactions) {
		return CreditCardTransactionAnalyser.checkTranasctionsForGivenDay(transactions, checkDate, priceThreshold);
	}

	/**
	 * Check a list of transactions to identify if any fraud activities were done on a given day
	 * @param transactions a list of transactions to check for fraud
	 * @param transactionDate the date to filter transactions
	 * @param priceThreshold max transaction amount for a day
	 * @return List<String> of hashed credit card numbers which exceeded the daily transaction threshold limit; or null if no records found
	 */
	public static List<String> checkTranasctionsForGivenDay(List<Transaction> transactions, String checkDate, Double priceThreshold) {

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
		
		List<String> fraudDetectedList = new ArrayList<String>();

		for (Map.Entry<String, Double> entry : transactionTotalPerCard.entrySet()) {
			//check if total amount exceed threshold amount
			if (entry.getValue().compareTo(priceThreshold) >= 0) {
				//LOGGER.log(Level.INFO, "Fraud detected for card # -" + entry.getKey() + " , tomal amount is  " + entry.getValue());
				fraudDetectedList.add(entry.getKey());
			}
		}

		return fraudDetectedList;
	}

	/**
	 * Filter transactions by the given date and return a list of hashed credit card numbers with daily spent amount 
	 * @param transactions a list of transactions
	 * @param checknDate check transactions for the particular date
	 * @return Map<String, Double>
	 */
	private static Map<String, Double> returnAllTransactiondForGivenDate( List<Transaction> transactions, String checkDate) {

		boolean atleastOneMatchedTransaction = false;
		String creditCardNumber = null;
		String paymententDate = null;
		Double paymentAmount = null;
		Date checkForDate, tranactionDay;
		Map<String, Double> transactionTotalPerCard = new HashMap<String, Double>();

		for (Transaction tranaction : transactions) {
			//LOGGER.log(Level.INFO, tranaction.toString());

			// assuming the split string will always have 3 items
			creditCardNumber = tranaction.getCredirCardNum();
			paymententDate = tranaction.getPaymentDone();
			paymentAmount = tranaction.getPrice();

			try {
				checkForDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkDate);
				tranactionDay = new SimpleDateFormat("yyyy-MM-dd").parse(paymententDate);

				if (tranactionDay.equals(checkForDate)) {
					//LOGGER.log(Level.INFO,	"transaction date matched.. proceed with verification");

					atleastOneMatchedTransaction = true;

					// keep adding amount if credit card number exists in Map
					Double existingValue = transactionTotalPerCard.get(creditCardNumber);
					transactionTotalPerCard.put(creditCardNumber, existingValue == null ? paymentAmount : existingValue + paymentAmount);
				}
			} catch (ParseException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
		
		if(!atleastOneMatchedTransaction){
			LOGGER.log(Level.INFO, "No Transactions found for given date");
		}

		return transactionTotalPerCard;
	}

	

}
