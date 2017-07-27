package com.vo;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
	private List<Profile> allProfiles = new ArrayList<Profile>();

	public List<Profile> getAllProfiles() {
		return allProfiles;
	}

	public void setAllProfiles(List<Profile> allProfiles) {
		this.allProfiles = allProfiles;
	}

}
