package com.sip;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class SipSchemeVO {
private double schemeID;	
private String schemeName  ;
private  Date startDate  ;
private Date endDate  ;
private  long startDateLong  ;
private long endDateLong  ;
private double sipAmount  ; 
public Date pensionStartDate ;
public Date pensionEndDate ;
public long pensionStartDateLong ;
public long pensionEndDateLong ;
public long pensionAmount ;  
private double returnOnInsvement  ;//xirr
private double loanIntrestRate ; // Compound intrest rate
private  List<Withdrawal> withdrawlsRows = new ArrayList<Withdrawal>();

public String getSchemeName() {
	return schemeName;
}
public void setSchemeName(String schemeName) {
	this.schemeName = schemeName;
}
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
public double getSipAmount() {
	return sipAmount;
}
public void setSipAmount(double sipAmount) {
	this.sipAmount = sipAmount;
}
public double getReturnOnInsvement() {
	return returnOnInsvement;
}
public void setReturnOnInsvement(double returnOnInsvement) {
	this.returnOnInsvement = returnOnInsvement;
}
public List<Withdrawal> getWithdrawlsRows() {
	return withdrawlsRows;
}
public void setWithdrawlsRows(List<Withdrawal> withdrawlsRows) {
	this.withdrawlsRows = withdrawlsRows;
}
public long getStartDateLong() {
	return startDateLong;
}
public long getEndDateLong() {
	return endDateLong;
}
public void setStartDateLong(long startDateLong) {
	this.startDateLong = startDateLong;
}
public void setEndDateLong(long endDateLong) {
	this.endDateLong = endDateLong;
}
public double getSchemeID() {
	return schemeID;
}
public void setSchemeID(double schemeID) {
	this.schemeID = schemeID;
}
public Date getPensionStartDate() {
	return pensionStartDate;
}
public void setPensionStartDate(Date pensionStartDate) {
	this.pensionStartDate = pensionStartDate;
}
public Date getPensionEndDate() {
	return pensionEndDate;
}
public void setPensionEndDate(Date pensionEndDate) {
	this.pensionEndDate = pensionEndDate;
}
public long getPensionStartDateLong() {
	return pensionStartDateLong;
}
public void setPensionStartDateLong(long pensionStartDateLong) {
	this.pensionStartDateLong = pensionStartDateLong;
}
public long getPensionEndDateLong() {
	return pensionEndDateLong;
}
public void setPensionEndDateLong(long pensionEndDateLong) {
	this.pensionEndDateLong = pensionEndDateLong;
}
public long getPensionAmount() {
	return pensionAmount;
}
public void setPensionAmount(long pensionAmount) {
	this.pensionAmount = pensionAmount;
}
public double getLoanIntrestRate() {
	return loanIntrestRate;
}
public void setLoanIntrestRate(double loanIntrestRate) {
	this.loanIntrestRate = loanIntrestRate;
} 

}
