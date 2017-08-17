package com.vo;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
	private List<Profile> allProfiles = new ArrayList<Profile>();
	private double totalGain; //absolute gain
	private double totalXirr;
	private double percentGainAbsolute;
    //private double percentGainAnual; //N.A.
	private double totalInvetment; //absolute value invested
	
	public String toString(){
		return " totalInvetment "+totalInvetment+" totalXirr "+totalXirr + " totalGain "+totalGain+ " allProfiles "+allProfiles.toString();
	}

	public List<Profile> getAllProfiles() {
		return allProfiles;
	}

	public void setAllProfiles(List<Profile> allProfiles) {
		this.allProfiles = allProfiles;
	}

	public double getTotalGain() {
		return totalGain;
	}

	public void setTotalGain(double totalGain) {
		this.totalGain = totalGain;
	}

	public double getTotalXirr() {
		return totalXirr;
	}

	public void setTotalXirr(double totalXirr) {
		this.totalXirr = totalXirr;
	}

	public double getTotalInvetment() {
		return totalInvetment;
	}

	public void setTotalInvetment(double totalInvetment) {
		this.totalInvetment = totalInvetment;
	}

	public double getPercentGainAbsolute() {
		return percentGainAbsolute;
	}

	public void setPercentGainAbsolute(double percentGainAbsolute) {
		this.percentGainAbsolute = percentGainAbsolute;
	}

}
