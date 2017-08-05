package com.chart;

import java.util.Date;

public class ChartNAV {
	private double nav;
	private Date navDate;
	private double basePercentageChange;
	private double rollingPercentageChange;
	public double getNav() {
		return nav;
	}
	public void setNav(double nav) {
		this.nav = nav;
	}
	public Date getNavDate() {
		return navDate;
	}
	public void setNavDate(Date navDate) {
		this.navDate = navDate;
	}
	public double getBasePercentageChange() {
		return basePercentageChange;
	}
	public void setBasePercentageChange(double basePercentageChange) {
		this.basePercentageChange = basePercentageChange;
	}
	public double getRollingPercentageChange() {
		return rollingPercentageChange;
	}
	public void setRollingPercentageChange(double rollingPercentageChange) {
		this.rollingPercentageChange = rollingPercentageChange;
	}

}
