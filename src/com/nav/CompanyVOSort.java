package com.nav;

import java.util.Comparator;


public class CompanyVOSort implements Comparator<CompanyVO>{

	@Override
	public int compare(CompanyVO o1, CompanyVO o2) {
		
		return o1.getCompanyName().compareTo(o2.getCompanyName());
	}

}