package com.un.creditcard.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.un.creditcard.util.DateUtil;
import com.un.creditcard.util.TransactionUtil;

public class TestCreditCardUtils {

	@Test
	public void checkTransactionsListNotNullOrEmpty() {
		assertNotNull( TransactionUtil.getTransactions());
		
		assertTrue(TransactionUtil.getTransactions().size() > 0); 
	}
	
	@Test
	public void checkTransactionStringSizeIsThree(){
		
		String[] transactionDetails = null;
		List<String> transactions = TransactionUtil.getTransactions();
		for (String tranaction : transactions) {
			transactionDetails = tranaction.split(",");				
			assertTrue(transactionDetails.length == 3);
		}
	}
	
	@Test
	public void checkNextDayNotNull() {
		assertNotNull( DateUtil.getNextDay(new Date()));
	}
	
	@Test
	public void checkCreationOfDateFromString() {
		assertNotNull( DateUtil.createDateFromString("2014-05-22"));
		
		assertTrue((DateUtil.createDateFromString("2014-05-22")) instanceof Date );
	}
	
	@Test
	public void checkCreationOfDateFromStringWithTimestamp() {
		assertNotNull( DateUtil.createDateFromString("2014-05-22T23:12:15"));
		
		assertTrue((DateUtil.createDateFromString("2014-05-22T23:12:15")) instanceof Date );
	}
	
	@Test
	public void checkNextDayIsCorrect() {
		Date date = DateUtil.getNextDay(DateUtil.createDateFromString("2014-05-22"));
		assertEquals("2014-05-23",  new SimpleDateFormat("yyyy-MM-dd").format(date));
	}
	
	

}
