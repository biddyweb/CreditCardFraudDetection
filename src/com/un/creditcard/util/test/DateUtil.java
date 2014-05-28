package com.un.creditcard.util.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class DateUtil {

	public static Date getNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	public static Date createDateFromString( String transactionDate){
		String dateFormat = "yyyy-MM-dd";
		try {
			return (new SimpleDateFormat(dateFormat).parse(transactionDate));
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return new Date();
	}
	

}
