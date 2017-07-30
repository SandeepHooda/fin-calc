package com.vo;

import java.util.Comparator;

public class ProfileSort implements Comparator<Profile>{

	@Override
	public int compare(Profile o1, Profile o2) {
		return o1.getSchemeName().compareTo(o2.getSchemeName());
	}

}
