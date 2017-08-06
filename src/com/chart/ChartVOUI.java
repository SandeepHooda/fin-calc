package com.chart;

import java.util.ArrayList;
import java.util.List;

public class ChartVOUI {
	private String _id;
	private String nm  ;
	private List<NavVoUI> navs = new ArrayList<NavVoUI>();
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public List<NavVoUI> getNavs() {
		return navs;
	}
	public void setNavs(List<NavVoUI> navs) {
		this.navs = navs;
	}
	

}
