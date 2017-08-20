package com.chart;

import java.util.ArrayList;
import java.util.List;

public class MonthlyData {
	private String _id ;
	private List<String> navs = new ArrayList<String>();
	
	
	
	public List<String> getNavs() {
		return navs;
	}
	public void setNavs(List<String> navs) {
		this.navs = navs;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}

}
