package com;

import java.util.HashSet;
import java.util.Set;

public class Constants {
	public static final String mlabKey = "soblgT7uxiAE6RsBOGwI9ZuLmcCgcvh_";
	public static final String dbName = "stockdb";//This is for MF
	public static final String stockEquityDB = "stockequity";//This is for stocks
	
	public static final String mlabKey_mutualFunfs = "cn4MkheHSQkbbgdYJrPpQ0asyNsqOBdS";
	public static final String dbName_mutualFunfs = "mutual_funds_history";//mutual_funds_history_ext
	public static final String timestamp = "timestamp";
	public static final String timeOfUpdateKey = "timeOfUpdateKey";
	public static final Set<String> monthsDB = new HashSet<>();
	public static final String allHouses =  "all_houses";
	
	static{
		monthsDB.add("jan");monthsDB.add("feb");monthsDB.add("mar");monthsDB.add("apr");monthsDB.add("may");monthsDB.add("jun");
		monthsDB.add("jul");monthsDB.add("aug");monthsDB.add("sep");monthsDB.add("oct");monthsDB.add("nov");monthsDB.add("dec");
		
	}

}
