package com.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartVO {
	private String _id;
	private Long tm  ;
	private List<ChartNAV> navs = new ArrayList<ChartNAV>();
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public List<ChartNAV> getNavs() {
		return navs;
	}
	public void setNavs(List<ChartNAV> navs) {
		this.navs = navs;
	}
	public Long getTm() {
		return tm;
	}
	public void setTm(Long tm) {
		this.tm = tm;
	}
	
}
