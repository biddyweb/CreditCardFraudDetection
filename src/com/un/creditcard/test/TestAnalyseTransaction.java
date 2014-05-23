package com.un.creditcard.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.un.creditcard.service.AnalyseTransaction;
import com.un.creditcard.util.TransactionUtil;

public class TestAnalyseTransaction {
	
	String checkForDay = null;
	Double thresholdPrice = null;
	List<String> transactionList = null;
	
	@Before
	public void setUp() throws Exception{
		 checkForDay = "2014-05-23";
		 thresholdPrice = new Double(1000.00);
		 
		 transactionList = TransactionUtil.getTransactions();
	}
	
	@After
	public void tearDown() throws Exception{
		transactionList = null;
		checkForDay = null;
		thresholdPrice = null;
	}

	
	@Test
	public void checkTranasctionsForGivenDay_WrongDateFormat(){		
		 checkForDay = "23-05-2014";
				
		 List<String> fraudCreditCardList = AnalyseTransaction.checkTranasctionsForGivenDay(transactionList, checkForDay, thresholdPrice);
		 
		 assertNull( fraudCreditCardList);
	}
	
	@Test
	public void checkTranasctionsForGivenDay_ThresholdPriceMatchesTransactionAmount(){
		// Boundary condition (Transaction t5 = new Transaction("1234567891011121", new Date(), 1000.50);)		
		 thresholdPrice = new Double(1000.50);
		
		 List<String> fraudCreditCardList = AnalyseTransaction.checkTranasctionsForGivenDay(transactionList, checkForDay, thresholdPrice);
		 
		 assertEquals(2,  fraudCreditCardList.size());
	}
	
	
	@Test
	public void checkTranasctionsForGivenDay_NoTransactionsFoundForCheckDate(){		
		 checkForDay = "2014-01-01";
					
		 List<String> fraudCreditCardList = AnalyseTransaction.checkTranasctionsForGivenDay(transactionList, checkForDay, thresholdPrice);
		 
		 assertNull( fraudCreditCardList );
	}
	
	@Test
	public void checkTranasctionsForGivenDay_ValidData_FraudDetected(){		 		 
		
		 List<String> fraudCreditCardList = AnalyseTransaction.checkTranasctionsForGivenDay(transactionList, checkForDay, thresholdPrice);
		 
		 assertNotNull(fraudCreditCardList);
		 assertEquals(2,  fraudCreditCardList.size());
	}
	
	@Test
	public void checkTranasctionsForGivenDay_ValidData_NoFraudDetected(){
		//Transaction amount always Less than Threshold amount
		 thresholdPrice = new Double(3000.00);		
		 	
		 List<String> fraudCreditCardList = AnalyseTransaction.checkTranasctionsForGivenDay(transactionList, checkForDay, thresholdPrice);
		 
		 assertNotNull(fraudCreditCardList);		 
		 assertEquals(0,  fraudCreditCardList.size());
	}

}
