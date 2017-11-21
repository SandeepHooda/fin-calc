package com.hotStocks;

import java.util.List;

public class HotStockVO {
	private String _id;
	private List<HotStockDailyVO> hotStocks;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<HotStockDailyVO> getHotStocks() {
		return hotStocks;
	}
	public void setHotStocks(List<HotStockDailyVO> hotStocks) {
		this.hotStocks = hotStocks;
	}

}
