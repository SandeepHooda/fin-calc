package amfiindia;

public class NavVO {
	private String SchemeCode,SchemeName,NetAssetValue,Date;

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

	public String toString(){
		return SchemeCode+":"+SchemeName+":"+NetAssetValue+":"+Date;
	}
}
