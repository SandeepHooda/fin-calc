package com.vo;

public class Profile {
	private long profileID;
	private String investmentDate, schemeName;
	private String exitDate;
	private String schemeCode;
	private double nav;
	private double investmentAmount;
	private double units;
	private double exitUnits;
	private double absoluteGain;
	private double currentValue;
	private double currentNav;
	private double lastKnownNav;
    private double xirr;
    private double percentGainAbsolute;
    private double percentGainAnual;
    private String companyName;
    private double companyXirr;
    private double companyAbsoluteGainPercent;
    private double companyTotalInvestment;
    private double companyCurrentValue;
    private double companyTotalGain;
    private String navDate = "";
    
   
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
		setAbsoluteGain(this.currentValue  - this.investmentAmount);
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public double getCompanyXirr() {
		return companyXirr;
	}
	public void setCompanyXirr(double companyXirr) {
		this.companyXirr = companyXirr;
	}
	public double getCompanyTotalInvestment() {
		return companyTotalInvestment;
	}
	public void setCompanyTotalInvestment(double companyTotalInvestment) {
		this.companyTotalInvestment = companyTotalInvestment;
	}
	public double getLastKnownNav() {
		return lastKnownNav;
	}
	public void setLastKnownNav(double lastKnownNav) {
		this.lastKnownNav = lastKnownNav;
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
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}
	public String getNavDate() {
		return navDate;
	}
	public void setNavDate(String navDate) {
		this.navDate = navDate;
	}
	public double getAbsoluteGain() {
		return absoluteGain;
	}
	public void setAbsoluteGain(double absoluteGain) {
		this.absoluteGain = absoluteGain;
	}
	public double getCompanyAbsoluteGainPercent() {
		return companyAbsoluteGainPercent;
	}
	public void setCompanyAbsoluteGainPercent(double companyAbsoluteGainPercent) {
		this.companyAbsoluteGainPercent = companyAbsoluteGainPercent;
	}
	public double getCompanyTotalGain() {
		return companyTotalGain;
	}
	public void setCompanyTotalGain(double companyTotalGain) {
		this.companyTotalGain = companyTotalGain;
	}
	public double getCompanyCurrentValue() {
		return companyCurrentValue;
	}
	public void setCompanyCurrentValue(double companyCurrentValue) {
		this.companyCurrentValue = companyCurrentValue;
	}
	public String getExitDate() {
		return exitDate;
	}
	public void setExitDate(String exitDate) {
		this.exitDate = exitDate;
	}
	public double getExitUnits() {
		return exitUnits;
	}
	public void setExitUnits(double exitUnits) {
		this.exitUnits = exitUnits;
	}
	


}
