package gui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import entity.ValidationResult;

public class GUIValidation {
	
	/***********************************************************
	 *  					General 
	 ******************************************************/
	
	// is the string contains only characters a-z A-Z
	
	public boolean isLengthLongerThan(String str,int len) {
	    if(str.length() > len)
	    	return true;
	    return false;
	}
	
	public boolean isString(String str) {
	    return str.matches("[a-zA-Z]+");
	}
	
	// is string Contains only digits
	public boolean isOnlyDigits(String str) {
	    if(str == null || str.trim().isEmpty()) {
	        return false;
	    }
	    for (int i = 0; i < str.length(); i++) {
	        if(!Character.isDigit(str.charAt(i))) {
	            return false;
	        } 
	    }
	    return true;
	}
	
	// return true if string is FLOAT
	public boolean isFloat(String numberFloat)
	{
		try
		{
		  Float.parseFloat(numberFloat);
		}
		catch(NumberFormatException e)
		{
		  //not a float
			return false;
		}
		return true;
	}
	
	/***********************************************************
	 *  		update my data - Credit Card 
	 ******************************************************/
	
	public ValidationResult isValidMonth(String month)
	{
		boolean bool = true;
		ArrayList<String> msg=null;
		
		// not only digits
		if(this.isOnlyDigits(month) == false)
		{
			bool = false;
			msg.add("Month must contain only numbers");
		}
		
		// check for valid month value
		try {
			int monthInteger = Integer.parseInt(month);
			if(monthInteger>12 || monthInteger<1)
			{
				bool = false;
				msg.add("Month value must be between 1-12");
			}
		}
		catch(NumberFormatException e) {
			// not int
			bool = false;
		}
		
		// passed all checkings
		return new ValidationResult(bool,msg);
	}
	
	public ValidationResult isValidYear(String year)
	{
		boolean bool = true;
		ArrayList<String> msg=null;
		
		// not only digits
		if(this.isOnlyDigits(year) == false)
		{
			bool = false;
			msg.add("Year can contain only digits");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	public ValidationResult isDateExpired(int year,int month)
	{
		boolean bool = true;
		ArrayList<String> msg=null;
		
		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH);
		
		if(year < curYear || (year==curYear && month>curMonth))
		{
			bool = false;
			msg.add("The date already passed");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	public ValidationResult CreditCard(String card)
	{
		boolean bool = true;
		ArrayList<String> msg=null;
		
		// not only digits
		if(isLengthLongerThan(card, 19))
		{
			bool = false;
			msg.add("Credit Card can't be longer than 19 digits");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	public ValidationResult CVV(String cvv)
	{
		boolean bool = true;
		ArrayList<String> msg=null;
		
		if(this.isOnlyDigits(cvv) == false)
		{
			bool = false;
			msg.add("CVV must contain only digits");
		}
		
		// not only digits
		if(cvv.length()!=3)
		{
			bool = false;
			msg.add("CVV must contain 3 digits");
		}
		
		return new ValidationResult(bool,msg);
	}
	
	/***********************************************************
	 *  			update my data - User Data 
	 ******************************************************/
	
	
	/***********************************************************
	 *  		update my data - Address 
	 ******************************************************/
	
	
	/***********************************************************
	 *  		custom made order - prices 
	 ******************************************************/
	
	/***********************************************************
	 *  		update my data - Address 
	 ******************************************************/
	
	/***********************************************************
	 *  		update catalog : id,name,type,price 
	 ******************************************************/
	
	/***********************************************************
	 *  		service - complains: text, compensation 
	 ******************************************************/
	
	/***********************************************************
	 *  		service - surveys : questions text 
	 ******************************************************/
	
	
	
	
	
	


}
