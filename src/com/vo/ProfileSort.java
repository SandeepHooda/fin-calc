package com.vo;

import java.util.Comparator;

public class ProfileSort implements Comparator<Profile>{

	@Override
	public int compare(Profile o1, Profile o2) {
		if (o1.getCompanyXirr() > o2.getCompanyXirr()){
			return -1;
		}else {
			return 1;
		}
	}

}
