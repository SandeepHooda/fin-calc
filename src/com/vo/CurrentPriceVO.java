package com.vo;

import java.util.Date;
import java.util.List;

public class CurrentPriceVO {
	private List<CurrentPrice> currentPrices ;
	private String _id ;
	private long timeCreated = new Date().getTime();
	public List<CurrentPrice> getCurrentPrices() {
		return currentPrices;
	}
	public void setCurrentPrices(List<CurrentPrice> currentPrices) {
		this.currentPrices = currentPrices;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public long getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}

}
