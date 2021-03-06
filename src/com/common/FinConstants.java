package com.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FinConstants {
	
	public static Map<String, String> houseIDMap ;
	public static Map<String, String> houseNameMap ;
	public static long aDay = 86400000;
	public static long aHour = 3600000;
	
	
	static {
		 houseIDMap = new HashMap<String, String>();
		 houseIDMap.put("ABN" , "39");
		 houseIDMap.put("AEGON" , "50");
		 houseIDMap.put("Alliance" , "1");
		 houseIDMap.put("Axis" , "53");
		 houseIDMap.put("Baroda" , "4");
		 houseIDMap.put("Benchmark" , "36");
		 houseIDMap.put("Birla" , "3");
		 houseIDMap.put("BNP" , "59");
		 houseIDMap.put("BOI" , "46");
		 houseIDMap.put("Canara" , "32");
		 houseIDMap.put("Daiwa" , "60");
		 houseIDMap.put("DBS" , "31");
		 houseIDMap.put("Deutsche" , "38");
		 houseIDMap.put("DHFL" , "58");
		 houseIDMap.put("DSP" , "6");
		 houseIDMap.put("Edelweiss" , "47");
		 houseIDMap.put("Escorts" , "13");
		 houseIDMap.put("Fidelity" , "40");
		 houseIDMap.put("Fortis" , "51");
		 houseIDMap.put("Franklin" , "27");
		 houseIDMap.put("GIC" , "8");
		 houseIDMap.put("Goldman" , "49");
		 houseIDMap.put("HDFC" , "9");
		 houseIDMap.put("HSBC" , "37");
		 houseIDMap.put("ICICI" , "20");
		 houseIDMap.put("IDBI" , "57");
		 houseIDMap.put("IDFC" , "48");
		 houseIDMap.put("IIFCL" , "68");
		 houseIDMap.put("IIFL" , "62");
		 houseIDMap.put("IL&F" , "11");
		 houseIDMap.put("IL&FS" , "65");
		 houseIDMap.put("Indiabulls" , "63");
		 houseIDMap.put("ING" , "14");
		 houseIDMap.put("Invesco" , "42");
		 houseIDMap.put("JM" , "16");
		 houseIDMap.put("JPMorgan" , "43");
		 houseIDMap.put("Kotak" , "17");
		 houseIDMap.put("L&T" , "56");
		 houseIDMap.put("LIC" , "18");
		 houseIDMap.put("Mahindra" , "69");
		 houseIDMap.put("Mirae" , "45");
		 houseIDMap.put("Morgan" , "19");
		 houseIDMap.put("Motilal" , "55");
		 houseIDMap.put("Peerless" , "54");
		 houseIDMap.put("PineBridge" , "44");
		 houseIDMap.put("PNB" , "34");
		 houseIDMap.put("PPFAS" , "64");
		 houseIDMap.put("PRINCIPAL" , "10");
		 houseIDMap.put("Quantum" , "41");
		 houseIDMap.put("Reliance" , "21");
		 houseIDMap.put("Sahara" , "35");
		 houseIDMap.put("SBI" , "22");
		 houseIDMap.put("Shinsei" , "52");
		 houseIDMap.put("Shriram" , "67");
		 houseIDMap.put("SREI" , "66");
		 houseIDMap.put("Standard" , "2");
		 houseIDMap.put("SUN" , "24");
		 houseIDMap.put("Sundaram" , "33");
		 houseIDMap.put("Tata" , "25");
		 houseIDMap.put("Taurus" , "26");
		 houseIDMap.put("Union" , "61");
		 houseIDMap.put("UTI" , "28");
		 houseIDMap.put("Zurich" , "29");
		 
		 
		 houseNameMap = new HashMap<String, String>();
		 houseNameMap.put("39","ABN  AMRO Mutual Fund");
		 houseNameMap.put("50","AEGON Mutual Fund");
		 houseNameMap.put("1","Alliance Capital Mutual Fund");
		 houseNameMap.put("53","Axis Mutual Fund");
		 houseNameMap.put("4","Baroda Pioneer Mutual Fund");
		 houseNameMap.put("36","Benchmark Mutual Fund");
		 houseNameMap.put("3","Birla Sun Life Mutual Fund");
		 houseNameMap.put("59","BNP Paribas Mutual Fund");
		 houseNameMap.put("46","BOI AXA Mutual Fund");
		 houseNameMap.put("32","Canara Robeco Mutual Fund");
		 houseNameMap.put("60","Daiwa Mutual Fund");
		 houseNameMap.put("31","DBS Chola Mutual Fund");
		 houseNameMap.put("38","Deutsche Mutual Fund");
		 houseNameMap.put("58","DHFL Pramerica Mutual Fund");
		 houseNameMap.put("6","DSP BlackRock Mutual Fund");
		 houseNameMap.put("47","Edelweiss Mutual Fund");
		 houseNameMap.put("13","Escorts Mutual Fund");
		 houseNameMap.put("40","Fidelity Mutual Fund");
		 houseNameMap.put("51","Fortis Mutual Fund");
		 houseNameMap.put("27","Franklin Templeton Mutual Fund");
		 houseNameMap.put("8","GIC Mutual Fund");
		 houseNameMap.put("49","Goldman Sachs Mutual Fund");
		 houseNameMap.put("9","HDFC Mutual Fund");
		 houseNameMap.put("37","HSBC Mutual Fund");
		 houseNameMap.put("20","ICICI Prudential Mutual Fund");
		 houseNameMap.put("57","IDBI Mutual Fund");
		 houseNameMap.put("48","IDFC Mutual Fund");
		 houseNameMap.put("68","IIFCL Mutual Fund (IDF)");
		 houseNameMap.put("62","IIFL Mutual Fund");
		 houseNameMap.put("11","IL&F S Mutual Fund");
		 houseNameMap.put("65","IL&FS Mutual Fund (IDF)");
		 houseNameMap.put("63","Indiabulls Mutual Fund");
		 houseNameMap.put("14","ING Mutual Fund");
		 
		 houseNameMap.put("16","JM Financial Mutual Fund");
		 houseNameMap.put("43","JPMorgan Mutual Fund");
		 houseNameMap.put("17","Kotak Mahindra Mutual Fund");
		 houseNameMap.put("56","L&T Mutual Fund");
		 houseNameMap.put("18","LIC Mutual Fund");
		 houseNameMap.put("69","Mahindra Mutual Fund");
		 houseNameMap.put("45","Mirae Asset Mutual Fund");
		 houseNameMap.put("19","Morgan Stanley Mutual Fund");
		 houseNameMap.put("55","Motilal Oswal Mutual Fund");
		 houseNameMap.put("54","Peerless Mutual Fund");
		 houseNameMap.put("44","PineBridge Mutual Fund");
		 houseNameMap.put("34","PNB Mutual Fund");
		 houseNameMap.put("64","PPFAS Mutual Fund");
		 houseNameMap.put("10","PRINCIPAL Mutual Fund");
		 houseNameMap.put("41","Quantum Mutual Fund");	 
		 houseNameMap.put("35","Sahara Mutual Fund");
		 houseNameMap.put("22","SBI Mutual Fund");
		 houseNameMap.put("52","Shinsei Mutual Fund");
		 houseNameMap.put("67","Shriram Mutual Fund");
		 houseNameMap.put("66","SREI Mutual Fund (IDF)");
		 houseNameMap.put("2","Standard Chartered Mutual Fund");
		 houseNameMap.put("24","SUN F&C Mutual Fund");
		 houseNameMap.put("33","Sundaram Mutual Fund");
		 houseNameMap.put("25","Tata Mutual Fund");
		 houseNameMap.put("26","Taurus Mutual Fund");
		 houseNameMap.put("61","Union Mutual Fund");
		 houseNameMap.put("28","UTI Mutual Fund");
		 houseNameMap.put("29","Zurich India Mutual Fund");
		houseNameMap.put("21","Reliance Mutual Fund");
		houseNameMap.put("42","Invesco Mutual Fund");
 

	}

}
