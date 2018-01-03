package com.nav;

public class CurrentMarketPrice {
	private String t;
	private String e;
	private String lt_dts;
	private double l_fix;
	private double previousClose,totalTradedVolume,high52, low52, open, dayHigh,dayLow,deliveryToTradedQuantity;
	private double high52Chg;
	private double closeChange;
	
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
	public String getLt_dts() {
		return lt_dts;
	}
	public void setLt_dts(String lt_dts) {
		this.lt_dts = lt_dts;
	}
	public double getL_fix() {
		return l_fix;
	}
	public void setL_fix(double l_fix) {
		this.l_fix = l_fix;
	}
	public double getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}
	public double getTotalTradedVolume() {
		return totalTradedVolume;
	}
	public void setTotalTradedVolume(double totalTradedVolume) {
		this.totalTradedVolume = totalTradedVolume;
	}
	public double getHigh52() {
		return high52;
	}
	public void setHigh52(double high52) {
		this.high52 = high52;
	}
	public double getLow52() {
		return low52;
	}
	public void setLow52(double low52) {
		this.low52 = low52;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getDayHigh() {
		return dayHigh;
	}
	public void setDayHigh(double dayHigh) {
		this.dayHigh = dayHigh;
	}
	public double getDayLow() {
		return dayLow;
	}
	public void setDayLow(double dayLow) {
		this.dayLow = dayLow;
	}
	public double getDeliveryToTradedQuantity() {
		return deliveryToTradedQuantity;
	}
	public void setDeliveryToTradedQuantity(double deliveryToTradedQuantity) {
		this.deliveryToTradedQuantity = deliveryToTradedQuantity;
	}
	public double getHigh52Chg() {
		return high52Chg;
	}
	public void setHigh52Chg(double high52Chg) {
		this.high52Chg = high52Chg;
	}
	public double getCloseChange() {
		return closeChange;
	}
	public void setCloseChange(double closeChange) {
		this.closeChange = closeChange;
	}
	
	

}
