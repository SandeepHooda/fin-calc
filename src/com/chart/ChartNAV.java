package com.chart;



public class ChartNAV {
	private double nav;
	private String dt;
	private double bpi;
	public ChartNAV(){
		
	}
	public ChartNAV(String dt){
		this.dt = dt;
	}
	public double getNav() {
		return nav;
	}
	public void setNav(double nav) {
		this.nav = nav;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public double getBpi() {
		return bpi;
	}
	public void setBpi(double bpi) {
		this.bpi = bpi;
	}


}
