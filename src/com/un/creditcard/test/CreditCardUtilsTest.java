package com.un.creditcard.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.un.creditcard.pojo.Transaction;
import com.un.creditcard.util.TransactionListFactory;
import com.un.creditcard.util.test.DateUtil;

public class CreditCardUtilsTest {	

	private TransactionListFactory transactionListFactory;

	@Test
	public void checkTransactionsListNotNullOrEmpty() {
		transactionListFactory = new TransactionListFactory();
		
		assertNotNull( transactionListFactory.populateTransactions("CSV"));		
		assertTrue(transactionListFactory.populateTransactions("CSV").size() > 0); 
	}
	
	@Test
	public void checkTransactionDetailsNotNull(){
		transactionListFactory = new TransactionListFactory();
		
		List<Transaction> transactions = transactionListFactory.populateTransactions("CSV");
		for (Transaction tranaction : transactions) {
			assertNotNull(tranaction.getCredirCardNum());			
			assertNotNull(tranaction.getPaymentDone());	
			assertNotNull(tranaction.getPrice());	
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
	
	@Test
	public void test1() throws Exception {
		
	}

}
