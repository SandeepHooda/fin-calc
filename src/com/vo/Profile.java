package com.vo;

public class Profile {
	private int profileID;
	private String investmentDate, schemeName;
	private double nav;
	private double investmentAmount;
	private double units;
	public String toString(){
		return "{   \"investmentAmount\": "+investmentAmount+",    \"nav\": "+nav+",    \"investmentDate\": "+investmentDate+
				",    \"units\": "+units+",    \"schemeName\": "+schemeName+"  }";
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
	public int getProfileID() {
		return profileID;
	}
	public void setProfileID(int profileID) {
		this.profileID = profileID;
	}


}
