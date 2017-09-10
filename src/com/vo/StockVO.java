package com.vo;

import java.util.Date;

public class StockVO {
	private long profileID;
	public String isin;
	private String companyName  ;
	private String ticker ;
	private String exchange ;
	private Date purchaseDate ;
	private String exitDateStr;
	private String purchaseDateStr  ;
	private double purchaseQty ;
	private double exitQty ;
	private double purchasePrice;
	private double investmentAmount;
	private double lastTradePrice ;
	private String asOfDate ;
	
	private double absoluteGain;
	private double currentValue;
	private double currentPrice;
	private double lastKnownPrice;
    private double xirr;
    private double percentGainAbsolute;
    private double percentGainAnual;
    private double companyXirr;
    private double companyAbsoluteGainPercent;
    private double companyTotalInvestment;
    private double companyCurrentValue;
    private double companyTotalGain;

    
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getPurchaseDateStr() {
		return purchaseDateStr;
	}
	public void setPurchaseDateStr(String purchaseDateStr) {
		this.purchaseDateStr = purchaseDateStr;
	}
	public double getPurchaseQty() {
		return purchaseQty;
	}
	public void setPurchaseQty(double purchaseQty) {
		this.purchaseQty = purchaseQty;
	}
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public double getLastTradePrice() {
		return lastTradePrice;
	}
	public void setLastTradePrice(double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
	public long getProfileID() {
		return profileID;
	}
	public void setProfileID(long profileID) {
		this.profileID = profileID;
	}
	public double getAbsoluteGain() {
		return absoluteGain;
	}
	public void setAbsoluteGain(double absoluteGain) {
		this.absoluteGain = absoluteGain;
	}
	public double getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public double getLastKnownPrice() {
		return lastKnownPrice;
	}
	public void setLastKnownPrice(double lastKnownPrice) {
		this.lastKnownPrice = lastKnownPrice;
	}
	public double getXirr() {
		return xirr;
	}
	public void setXirr(double xirr) {
		this.xirr = xirr;
	}
	public double getPercentGainAbsolute() {
		return percentGainAbsolute;
	}
	public void setPercentGainAbsolute(double percentGainAbsolute) {
		this.percentGainAbsolute = percentGainAbsolute;
	}
	public double getPercentGainAnual() {
		return percentGainAnual;
	}
	public void setPercentGainAnual(double percentGainAnual) {
		this.percentGainAnual = percentGainAnual;
	}
	public double getCompanyXirr() {
		return companyXirr;
	}
	public void setCompanyXirr(double companyXirr) {
		this.companyXirr = companyXirr;
	}
	public double getCompanyAbsoluteGainPercent() {
		return companyAbsoluteGainPercent;
	}
	public void setCompanyAbsoluteGainPercent(double companyAbsoluteGainPercent) {
		this.companyAbsoluteGainPercent = companyAbsoluteGainPercent;
	}
	public double getCompanyTotalInvestment() {
		return companyTotalInvestment;
	}
	public void setCompanyTotalInvestment(double companyTotalInvestment) {
		this.companyTotalInvestment = companyTotalInvestment;
	}
	public double getCompanyCurrentValue() {
		return companyCurrentValue;
	}
	public void setCompanyCurrentValue(double companyCurrentValue) {
		this.companyCurrentValue = companyCurrentValue;
	}
	public double getCompanyTotalGain() {
		return companyTotalGain;
	}
	public void setCompanyTotalGain(double companyTotalGain) {
		this.companyTotalGain = companyTotalGain;
	}
	public double getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(double investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getExitDateStr() {
		return exitDateStr;
	}
	public void setExitDateStr(String exitDateStr) {
		this.exitDateStr = exitDateStr;
	}
	public double getExitQty() {
		return exitQty;
	}
	public void setExitQty(double exitQty) {
		this.exitQty = exitQty;
	}

}
