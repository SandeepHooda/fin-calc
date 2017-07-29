package com.nav;

import java.util.ArrayList;
import java.util.List;

public class CompanyVO {
	
	private String companyName;
	private List<NavVO> navs = new ArrayList<NavVO>();
	
	public CompanyVO(String companyName) {
		this.companyName = companyName;
	}

	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<NavVO> getNavs() {
		return navs;
	}
	public void setNavs(List<NavVO> navs) {
		this.navs = navs;
	}
}

