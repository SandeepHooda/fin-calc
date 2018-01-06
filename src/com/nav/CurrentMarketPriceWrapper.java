package com.nav;

import java.util.List;

public class CurrentMarketPriceWrapper {
	private String _id;
	private List<CurrentMarketPrice> marketPices;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<CurrentMarketPrice> getMarketPices() {
		return marketPices;
	}
	public void setMarketPices(List<CurrentMarketPrice> marketPices) {
		this.marketPices = marketPices;
	}

}
