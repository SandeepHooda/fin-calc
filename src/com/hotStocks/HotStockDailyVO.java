package com.hotStocks;

import java.util.Date;

public class HotStockDailyVO {
	private String _id;
	private String script;
	private String exchange;
	private Date date;
	private String reason;
	private String cmp;
	private String cmpPercentChange;
	private boolean isHighProfile;
	private String tradeType;
	private String qtyTraded;
	private String investorName;
	private String dateStr;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCmp() {
		return cmp;
	}
	public void setCmp(String cmp) {
		this.cmp = cmp;
	}
	public String getCmpPercentChange() {
		return cmpPercentChange;
	}
	public void setCmpPercentChange(String cmpPercentChange) {
		this.cmpPercentChange = cmpPercentChange;
	}
	public boolean isHighProfile() {
		return isHighProfile;
	}
	public void setHighProfile(boolean isHighProfile) {
		this.isHighProfile = isHighProfile;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getQtyTraded() {
		return qtyTraded;
	}
	public void setQtyTraded(String qtyTraded) {
		this.qtyTraded = qtyTraded;
	}
	public String getInvestorName() {
		return investorName;
	}
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	

}
