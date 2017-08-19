package com.chart;



public class ChartNAV {
	private double nav;
	private String dt;
	private double bpi;
	private double rollingRate;
	public ChartNAV(){
		
	}
	public ChartNAV clone(){
		ChartNAV obj = new ChartNAV();
		obj.nav = this.nav;
		obj.dt = this.dt;
		obj.bpi = this.bpi;
		//obj.bpiS = this.bpiS;
		return obj;
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
	
	public double getRollingRate() {
		return rollingRate;
	}
	public void setRollingRate(double rollingRate) {
		this.rollingRate = rollingRate;
	}
	


}
