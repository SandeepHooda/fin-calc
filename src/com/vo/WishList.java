package com.vo;

public class WishList {
	 private double  price;
	 private double  currentMarketPrice;
	 private double differencePercentage;
private String companyName ;
private String ticker;
private String buySell ;
private long profileID ;
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getBuySell() {
	return buySell;
}
public void setBuySell(String buySell) {
	this.buySell = buySell;
}
public long getProfileID() {
	return profileID;
}
public void setProfileID(long profileID) {
	this.profileID = profileID;
}
public double getCurrentMarketPrice() {
	return currentMarketPrice;
}
public void setCurrentMarketPrice(double currentMarketPrice) {
	this.currentMarketPrice = currentMarketPrice;
}
public double getDifferencePercentage() {
	return differencePercentage;
}
public void setDifferencePercentage(double differencePercentage) {
	this.differencePercentage = differencePercentage;
}
public String getTicker() {
	return ticker;
}
public void setTicker(String ticker) {
	this.ticker = ticker;
}
}
