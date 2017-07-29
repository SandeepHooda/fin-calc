package com.vo;

public class Profile {
	private long profileID;
	private String investmentDate, schemeName;
	private String schemeCode;
	private double nav;
	private double investmentAmount;
	private double units;
	private double currentValue;
	private double currentNav;
    private double xirr;
    private String companyName;
    
   
	public String toString(){
		return "{   \"investmentAmount\": "+investmentAmount+",    \"nav\": "+nav+",    \"investmentDate\": "+investmentDate+
				",    \"units\": "+units+",    \"schemeName\": "+schemeName+" \"currentValue\":"+currentValue+" \"xirr\":"+xirr+" \"companyName\":"+companyName+"schemeCode\":"+schemeCode+"}";
	}
	public String getInvestmentDate() {
		return investmentDate;
	}
	public void setInvestmentDate(String investmentDate) {
		this.investmentDate = investmentDate;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public double getNav() {
		return nav;
	}
	public void setNav(double nav) {
		this.nav = nav;
	}
	public double getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(double investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public double getUnits() {
		return units;
	}
	public void setUnits(double units) {
		this.units = units;
	}
	public long getProfileID() {
		return profileID;
	}
	public void setProfileID(long profileID) {
		this.profileID = profileID;
	}
	public double getCurrentValue() {
		return currentValue;
	}
	
	public double getXirr() {
		return xirr;
	}
	public void setXirr(double xirr) {
		this.xirr = xirr;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public double getCurrentNav() {
		return currentNav;
	}
	public void setCurrentNav(double currentNav) {
		this.currentValue = currentNav * units; 
		this.currentNav = currentNav;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}


}
