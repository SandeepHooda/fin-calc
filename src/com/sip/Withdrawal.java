package com.sip;

import java.util.Date;

public class Withdrawal {
	
   private Date date;
   private long dateLong;
   private double amount;
   
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public long getDateLong() {
	return dateLong;
}
public void setDateLong(long dateLong) {
	this.dateLong = dateLong;
}

}
