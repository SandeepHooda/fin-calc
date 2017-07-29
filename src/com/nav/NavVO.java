package com.nav;

public class NavVO {
	private String companyName, SchemeCode,SchemeName,NetAssetValue,Date;
	
	public String toString(){
		return SchemeCode+":"+SchemeName+":"+NetAssetValue+":"+Date;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSchemeCode() {
		return SchemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		SchemeCode = schemeCode;
	}

	public String getSchemeName() {
		return SchemeName;
	}

	public void setSchemeName(String schemeName) {
		SchemeName = schemeName;
	}

	public String getNetAssetValue() {
		return NetAssetValue;
	}

	public void setNetAssetValue(String netAssetValue) {
		NetAssetValue = netAssetValue;
	}

	

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	
}
