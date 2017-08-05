package com.chart;

import java.util.ArrayList;
import java.util.List;

public class ChartVO {
	private String schemeCode;
	private String schemeName;
	private List<ChartNAV> chartNAVS = new ArrayList<ChartNAV>();
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public List<ChartNAV> getChartNAVS() {
		return chartNAVS;
	}
	public void setChartNAVS(List<ChartNAV> chartNAV) {
		this.chartNAVS = chartNAV;
	}

}
