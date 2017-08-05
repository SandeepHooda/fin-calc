package com.profile;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import com.google.gson.reflect.TypeToken;
import com.nav.CompanyVO;
import com.nav.NavTextDAO;
import com.nav.NavVO;
import com.sip.SipSchemeVO;
import com.chart.ChartDAO;
import com.chart.ChartVO;
import com.common.FinConstants;
import com.google.appengine.api.ThreadManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vo.Portfolio;
import com.vo.Profile;
import com.vo.ProfileSort;
import com.xirr.XirrCalculatorService;

public class ProfileService {
	private static final Logger log = Logger.getLogger(ProfileService.class.getName());
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	public static void deleteFromPortfolio(String collection, long profileID){
		log.info("Deleting from   user profile "+collection+profileID);
		String currentData = ProfileDAO.getUserPortfolio(collection, false, null);//get data along with default key
		if(null == currentData || "".equals(currentData.trim())){
			log.info("Creating a new profile "+collection);
			ProfileDAO.createNewCollection(collection);
		}else {
			
			log.info("Profile already exists "+currentData);
		}
		String dataToAdd = null;
		Portfolio pf = null;
		Gson  json = new Gson();
		currentData = ProfileDAO.getUserPortfolio(collection, true, null);//get data by suppressing default key
		
			currentData = currentData.trim();
			log.info("currentData ="+currentData+"#");
			pf = json.fromJson(currentData, Portfolio.class);
			Iterator<Profile> profileItr = pf.getAllProfiles().iterator();
			while(profileItr.hasNext()){
				if (profileItr.next().getProfileID() == profileID){
					profileItr.remove();
				}
			}
			
		
		dataToAdd = json.toJson(pf, new TypeToken<Portfolio>() {}.getType());
		ProfileDAO.insertData(collection, dataToAdd);
	}
	
	public static void addFundToPortfolio(String collection, Profile profile){
		log.info("Adding to user profile "+collection+profile);
		String currentData = ProfileDAO.getUserPortfolio(collection, false, null);//get data along with default key
		if(null == currentData || "".equals(currentData.trim())){
			log.info("Creating a new profile "+collection);
			ProfileDAO.createNewCollection(collection);
		}else {
			
			log.info("Profile already exists "+currentData);
		}
		String dataToAdd = null;
		Portfolio pf = null;
		Gson  json = new Gson();
		currentData = ProfileDAO.getUserPortfolio(collection, true, null);//get data by suppressing default key
		if(null == currentData || "".equals(currentData.trim())){//empty commection 
			pf = new Portfolio();
			profile.setProfileID( new Date().getTime());
			pf.getAllProfiles().add(profile);
			log.info("Adding first profile "+currentData);
			
		}else {
			currentData = currentData.trim();
			log.info("currentData ="+currentData+"#");
			pf = json.fromJson(currentData, Portfolio.class);
			
			profile.setProfileID(new Date().getTime());
			pf.getAllProfiles().add(profile);
			
		}
		
		
		dataToAdd = json.toJson(pf, new TypeToken<Portfolio>() {}.getType());
		ProfileDAO.insertData(collection, dataToAdd);
	}
	
	public static void saveSipList(String collection, List<SipSchemeVO> schemes){
		collection += "_sip";
		log.info("Adding to user profile "+collection);
		String currentData = ProfileDAO.getUserPortfolio(collection, false, null);//get data along with default key
		if(null == currentData || "".equals(currentData.trim())){
			log.info("Creating a new profile "+collection);
			ProfileDAO.createNewCollection(collection);
		}else {
			
			log.info("Profile already exists "+currentData);
		}
		
		
		Gson  json = new Gson();
		String dataToAdd = json.toJson(schemes, new TypeToken<List<SipSchemeVO>>() {}.getType());
		ProfileDAO.insertData(collection, dataToAdd);
	}
	
	public static List<ChartVO> getHistoricalData(Portfolio portFolio){
		Set<String> houseIds = new HashSet<String>();
		Set<String> schemeCodes = new HashSet<String>();
		for (Profile profile : portFolio.getAllProfiles()){
			schemeCodes.add(profile.getSchemeCode());
			String schemaName = profile.getSchemeName();
			schemaName = schemaName.substring(0, schemaName.indexOf(" "));
			String houseCode = FinConstants.houseIDMap.get(schemaName);
			if (null != houseCode){
				houseIds.add(houseCode);
				log.info("schemaName  "+schemaName +" house code "+houseCode);
			}
			
			
		}
	
		
		List<ChartVO>  chartVos = new ArrayList<ChartVO>();
		
		
		ThreadFactory factory = ThreadManager.currentRequestThreadFactory();
		ExecutorService service = Executors.newCachedThreadPool(factory);
		
		List<ChartDAO> workers = new ArrayList<ChartDAO>();
		
		
	
		
		 
		for (String houseID :houseIds ){
			log.info("Creating a woorker  "+houseID);
		
				 ChartDAO worker = new ChartDAO(6, houseID, schemeCodes);
					service.execute( worker);
					workers.add(worker);
			
			
			
		}
		service.shutdown();
		boolean finshed  = false;
		try {
			finshed = service.awaitTermination(15, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Will get resukt from workesr now ");
		
		
		for (ChartDAO worker : workers){
			
			log.info("Will get resukt from workesr  a woorker  ");
			Map<String, ChartVO> results =  worker.getResult();
			for (String schemeCode : results.keySet() ) {
				chartVos.add(results.get(schemeCode));
				log.info("Resukt  "+results.get(schemeCode).getSchemeCode());
			}
			
		}
		return chartVos;
	}
	public static Portfolio getPortfolioDromDB(String collection){
		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1501383477625 , \"investmentDate\" : \"7-Jun-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.216 , \"investmentAmount\" : 20000.0 , \"units\" : 1233.3497779970398 , \"currentValue\" : 21931.79575727676 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 76.901310434256 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383508987 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.708 , \"investmentAmount\" : 10000.0 , \"units\" : 598.5156811108452 , \"currentValue\" : 10642.985396217382 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 113.43821019391093 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501643072554 , \"investmentDate\" : \"1-Aug-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 17.9165 , \"investmentAmount\" : 10000.0 , \"units\" : 558.1447269276924 , \"currentValue\" : 9925.096977646304 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : -49.64451913397262 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383546880 , \"investmentDate\" : \"12-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Banking and Financial Services Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120244\" , \"nav\" : 59.71 , \"investmentAmount\" : 20000.0 , \"units\" : 334.95226930162454 , \"currentValue\" : 21279.51766873221 , \"currentNav\" : 63.53 , \"lastKnownNav\" : 63.53 , \"xirr\" : 52.068377206971164 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 52.068377206971164 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383200382 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"DSP BlackRock Natural Resources and New Energy Fund - Direct Plan - Growth\" , \"schemeCode\" : \"119028\" , \"nav\" : 31.25 , \"investmentAmount\" : 20000.0 , \"units\" : 640.0 , \"currentValue\" : 21260.8 , \"currentNav\" : 33.22 , \"lastKnownNav\" : 33.22 , \"xirr\" : 41.714939715327034 , \"companyName\" : \"DSP BlackRock Mutual Fund\" , \"companyXirr\" : 41.714939715327034 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501379186922 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 131.08 , \"investmentAmount\" : 25000.0 , \"units\" : 190.72322245956667 , \"currentValue\" : 26417.07354287458 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 36.288325205709135 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501382919277 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 136.51 , \"investmentAmount\" : 10000.0 , \"units\" : 73.25470661490002 , \"currentValue\" : 10146.5094132298 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 19.358328546622104 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501383156577 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"L&T Infrastructure Fund -Direct Plan-Growth Option\" , \"schemeCode\" : \"119413\" , \"nav\" : 15.79 , \"investmentAmount\" : 25000.0 , \"units\" : 1583.2805573147562 , \"currentValue\" : 26139.962001266627 , \"currentNav\" : 16.51 , \"lastKnownNav\" : 16.51 , \"xirr\" : 28.955743356765133 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 28.955743356765133 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383093995 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Kotak Select Focus Fund - Growth - Direct\" , \"schemeCode\" : \"120166\" , \"nav\" : 32.109 , \"investmentAmount\" : 25000.0 , \"units\" : 778.5979009000591 , \"currentValue\" : 25928.08869787287 , \"currentNav\" : 33.301 , \"lastKnownNav\" : 33.301 , \"xirr\" : 23.107154129383588 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 23.107154129383588 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383259924 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Birla Sun Life Midcap Fund - Growth - Direct Plan\" , \"schemeCode\" : \"119620\" , \"nav\" : 303.95 , \"investmentAmount\" : 25000.0 , \"units\" : 82.25037012666557 , \"currentValue\" : 25880.07896035532 , \"currentNav\" : 314.65 , \"lastKnownNav\" : 314.65 , \"xirr\" : 21.812767042941566 , \"companyName\" : \"Birla Sun Life Mutual Fund\" , \"companyXirr\" : 21.812767042941566 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383350497 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Top 100 Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120596\" , \"nav\" : 312.28 , \"investmentAmount\" : 20000.0 , \"units\" : 64.04508774177022 , \"currentValue\" : 20664.14755988216 , \"currentNav\" : 322.65 , \"lastKnownNav\" : 322.65 , \"xirr\" : 21.588474731024075 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 21.588474731024075 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383395107 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Value Discovery Fund - Direct Plan - Growth\" , \"schemeCode\" : \"120323\" , \"nav\" : 141.1 , \"investmentAmount\" : 20000.0 , \"units\" : 141.74344436569808 , \"currentValue\" : 20412.47342310418 , \"currentNav\" : 144.01 , \"lastKnownNav\" : 144.01 , \"xirr\" : 12.992193185683728 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 12.992193185683728 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383315183 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Multicap Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120599\" , \"nav\" : 265.47 , \"investmentAmount\" : 20000.0 , \"units\" : 75.33807963235016 , \"currentValue\" : 20327.720646400718 , \"currentNav\" : 269.82 , \"lastKnownNav\" : 269.82 , \"xirr\" : 10.213910680254138 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 10.213910680254138 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383023193 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"Kotak-Mid-Cap-Growth - Direct\" , \"schemeCode\" : \"120164\" , \"nav\" : 76.772 , \"investmentAmount\" : 25000.0 , \"units\" : 325.6395560881571 , \"currentValue\" : 25317.49856718595 , \"currentNav\" : 77.747 , \"lastKnownNav\" : 77.747 , \"xirr\" : 7.343730747059469 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 7.343730747059469 , \"companyTotalInvestment\" : 25000.0}] , \"totalGain\" : 11273.74861204486 , \"totalXirr\" : 28.854294303825007 , \"totalInvetment\" : 1.375E7} ";
		portfolioStr = ProfileDAO.getUserPortfolio(collection, true, null);
		Gson  json = new Gson();
		Portfolio portfolio = json.fromJson(portfolioStr, Portfolio.class);
		return portfolio;
		
	}
	public static Portfolio getPortfolio(String collection){
		
		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1501383477625 , \"investmentDate\" : \"7-Jun-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.216 , \"investmentAmount\" : 20000.0 , \"units\" : 1233.3497779970398 , \"currentValue\" : 21931.79575727676 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 76.901310434256 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383508987 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.708 , \"investmentAmount\" : 10000.0 , \"units\" : 598.5156811108452 , \"currentValue\" : 10642.985396217382 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 113.43821019391093 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501643072554 , \"investmentDate\" : \"1-Aug-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 17.9165 , \"investmentAmount\" : 10000.0 , \"units\" : 558.1447269276924 , \"currentValue\" : 9925.096977646304 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : -49.64451913397262 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383546880 , \"investmentDate\" : \"12-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Banking and Financial Services Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120244\" , \"nav\" : 59.71 , \"investmentAmount\" : 20000.0 , \"units\" : 334.95226930162454 , \"currentValue\" : 21279.51766873221 , \"currentNav\" : 63.53 , \"lastKnownNav\" : 63.53 , \"xirr\" : 52.068377206971164 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 52.068377206971164 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383200382 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"DSP BlackRock Natural Resources and New Energy Fund - Direct Plan - Growth\" , \"schemeCode\" : \"119028\" , \"nav\" : 31.25 , \"investmentAmount\" : 20000.0 , \"units\" : 640.0 , \"currentValue\" : 21260.8 , \"currentNav\" : 33.22 , \"lastKnownNav\" : 33.22 , \"xirr\" : 41.714939715327034 , \"companyName\" : \"DSP BlackRock Mutual Fund\" , \"companyXirr\" : 41.714939715327034 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501379186922 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 131.08 , \"investmentAmount\" : 25000.0 , \"units\" : 190.72322245956667 , \"currentValue\" : 26417.07354287458 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 36.288325205709135 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501382919277 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 136.51 , \"investmentAmount\" : 10000.0 , \"units\" : 73.25470661490002 , \"currentValue\" : 10146.5094132298 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 19.358328546622104 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501383156577 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"L&T Infrastructure Fund -Direct Plan-Growth Option\" , \"schemeCode\" : \"119413\" , \"nav\" : 15.79 , \"investmentAmount\" : 25000.0 , \"units\" : 1583.2805573147562 , \"currentValue\" : 26139.962001266627 , \"currentNav\" : 16.51 , \"lastKnownNav\" : 16.51 , \"xirr\" : 28.955743356765133 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 28.955743356765133 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383093995 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Kotak Select Focus Fund - Growth - Direct\" , \"schemeCode\" : \"120166\" , \"nav\" : 32.109 , \"investmentAmount\" : 25000.0 , \"units\" : 778.5979009000591 , \"currentValue\" : 25928.08869787287 , \"currentNav\" : 33.301 , \"lastKnownNav\" : 33.301 , \"xirr\" : 23.107154129383588 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 23.107154129383588 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383259924 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Birla Sun Life Midcap Fund - Growth - Direct Plan\" , \"schemeCode\" : \"119620\" , \"nav\" : 303.95 , \"investmentAmount\" : 25000.0 , \"units\" : 82.25037012666557 , \"currentValue\" : 25880.07896035532 , \"currentNav\" : 314.65 , \"lastKnownNav\" : 314.65 , \"xirr\" : 21.812767042941566 , \"companyName\" : \"Birla Sun Life Mutual Fund\" , \"companyXirr\" : 21.812767042941566 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383350497 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Top 100 Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120596\" , \"nav\" : 312.28 , \"investmentAmount\" : 20000.0 , \"units\" : 64.04508774177022 , \"currentValue\" : 20664.14755988216 , \"currentNav\" : 322.65 , \"lastKnownNav\" : 322.65 , \"xirr\" : 21.588474731024075 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 21.588474731024075 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383395107 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Value Discovery Fund - Direct Plan - Growth\" , \"schemeCode\" : \"120323\" , \"nav\" : 141.1 , \"investmentAmount\" : 20000.0 , \"units\" : 141.74344436569808 , \"currentValue\" : 20412.47342310418 , \"currentNav\" : 144.01 , \"lastKnownNav\" : 144.01 , \"xirr\" : 12.992193185683728 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 12.992193185683728 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383315183 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Multicap Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120599\" , \"nav\" : 265.47 , \"investmentAmount\" : 20000.0 , \"units\" : 75.33807963235016 , \"currentValue\" : 20327.720646400718 , \"currentNav\" : 269.82 , \"lastKnownNav\" : 269.82 , \"xirr\" : 10.213910680254138 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 10.213910680254138 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383023193 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"Kotak-Mid-Cap-Growth - Direct\" , \"schemeCode\" : \"120164\" , \"nav\" : 76.772 , \"investmentAmount\" : 25000.0 , \"units\" : 325.6395560881571 , \"currentValue\" : 25317.49856718595 , \"currentNav\" : 77.747 , \"lastKnownNav\" : 77.747 , \"xirr\" : 7.343730747059469 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 7.343730747059469 , \"companyTotalInvestment\" : 25000.0}] , \"totalGain\" : 11273.74861204486 , \"totalXirr\" : 28.854294303825007 , \"totalInvetment\" : 1.375E7} ";
		portfolioStr = ProfileDAO.getUserPortfolio(collection, true, null);
		Gson  json = new Gson();
		Portfolio portfolio = json.fromJson(portfolioStr, Portfolio.class);
		try {
			Map<String, CompanyVO> map = NavTextDAO.getCurrentNav();
			
			for (Profile profile: portfolio.getAllProfiles()){
				CompanyVO company = map.get(profile.getCompanyName());
				for (NavVO navVo :company.getNavs()){
					if(navVo.getSchemeCode().equals(profile.getSchemeCode())){
						double currentNav = Double.parseDouble(navVo.getNetAssetValue());
						//log.info("currentNav == "+currentNav);
						if (currentNav == 0){
							currentNav = profile.getLastKnownNav();
							//log.info("Using last known nav == "+currentNav);
						}else {
							profile.setLastKnownNav(currentNav);
						}
						profile.setCurrentNav(currentNav);//Set nav and current value
						double[] payments = new  double[2];
						Date[] dates = new Date[2];
						payments[0] = profile.getInvestmentAmount() *-1;
						dates[0] = sdf.parse( profile.getInvestmentDate());
						payments[1] = profile.getCurrentValue();
						//log.info("current value == "+ profile.getCurrentValue());
						dates[1] = new Date(); 
						double xirr = XirrCalculatorService.Newtons_method(0.1, payments, dates);
						profile.setXirr(xirr);
						break;
					}
				}
			}
			
			calculateTotalGain( portfolio);
			calculateCompanyGain(portfolio);
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		Collections.sort(portfolio.getAllProfiles(), new ProfileSort());
		GsonBuilder builder = new GsonBuilder();
		builder.serializeSpecialFloatingPointValues();
		 Gson gson =builder	.create();
		String data = gson.toJson(portfolio, Portfolio.class);
		ProfileDAO.insertData(collection, data);
		return portfolio;
	}
	
public static List<SipSchemeVO> getSipList(String collection){
		collection += "_sip";
		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1501379186922 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 131.08 , \"investmentAmount\" : 25000.0 , \"units\" : 190.72322245956667 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501382919277 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 136.51 , \"investmentAmount\" : 10000.0 , \"units\" : 73.25470661490002 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501383023193 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"Kotak-Mid-Cap-Growth - Direct\" , \"schemeCode\" : \"120164\" , \"nav\" : 76.772 , \"investmentAmount\" : 25000.0 , \"units\" : 325.6395560881571 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Kotak Mahindra Mutual Fund\"} , { \"profileID\" : 1501383093995 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Kotak Select Focus Fund - Growth - Direct\" , \"schemeCode\" : \"120166\" , \"nav\" : 32.109 , \"investmentAmount\" : 25000.0 , \"units\" : 778.5979009000591 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Kotak Mahindra Mutual Fund\"} , { \"profileID\" : 1501383156577 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"L&T Infrastructure Fund -Direct Plan-Growth Option\" , \"schemeCode\" : \"119413\" , \"nav\" : 15.79 , \"investmentAmount\" : 25000.0 , \"units\" : 1583.2805573147562 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501383200382 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"DSP BlackRock Natural Resources and New Energy Fund - Direct Plan - Growth\" , \"schemeCode\" : \"119028\" , \"nav\" : 31.25 , \"investmentAmount\" : 20000.0 , \"units\" : 640.0 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"DSP BlackRock Mutual Fund\"} , { \"profileID\" : 1501383259924 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Birla Sun Life Midcap Fund - Growth - Direct Plan\" , \"schemeCode\" : \"119620\" , \"nav\" : 303.95 , \"investmentAmount\" : 25000.0 , \"units\" : 82.25037012666557 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Birla Sun Life Mutual Fund\"} , { \"profileID\" : 1501383315183 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Multicap Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120599\" , \"nav\" : 265.47 , \"investmentAmount\" : 20000.0 , \"units\" : 75.33807963235016 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383350497 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Top 100 Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120596\" , \"nav\" : 312.28 , \"investmentAmount\" : 20000.0 , \"units\" : 64.04508774177022 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383395107 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Value Discovery Fund - Direct Plan - Growth\" , \"schemeCode\" : \"120323\" , \"nav\" : 141.1 , \"investmentAmount\" : 20000.0 , \"units\" : 141.74344436569808 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383477625 , \"investmentDate\" : \"7-Jun-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.216 , \"investmentAmount\" : 20000.0 , \"units\" : 1233.3497779970398 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Tata Mutual Fund\"} , { \"profileID\" : 1501383508987 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.708 , \"investmentAmount\" : 10000.0 , \"units\" : 598.5156811108452 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Tata Mutual Fund\"} , { \"profileID\" : 1501383546880 , \"investmentDate\" : \"12-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Banking and Financial Services Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120244\" , \"nav\" : 59.71 , \"investmentAmount\" : 20000.0 , \"units\" : 334.95226930162454 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"}]}";
		portfolioStr = ProfileDAO.getArrayData(collection, true, null);
		log.info(" Sip result" +portfolioStr);
		Gson  json = new Gson();
		List<SipSchemeVO> portfolio = null;
		if ("".equalsIgnoreCase(portfolioStr.trim())){
			portfolio = new ArrayList<SipSchemeVO>();
			log.info(" Creating new blank Sip vo" +portfolioStr);
		}else {
			portfolio = json.fromJson(portfolioStr, new TypeToken<List<SipSchemeVO>>() {}.getType());
		}
		
		return portfolio;
	}
	
	private static void calculateTotalGain(Portfolio portfolio) throws ParseException{
		double totalGain = 0;
		Date today = new Date(); 
		List<Double> payments = new ArrayList<Double>();
		List<Date> dates = new ArrayList<Date>();
		for (Profile aprofile: portfolio.getAllProfiles()){
			payments.add(aprofile.getInvestmentAmount() *-1);
			dates.add(sdf.parse(aprofile.getInvestmentDate()));
			payments.add(aprofile.getCurrentValue());
			dates.add(today);
			totalGain += ( aprofile.getCurrentValue() - aprofile.getInvestmentAmount());
			portfolio.setTotalInvetment(portfolio.getTotalInvetment()+aprofile.getInvestmentAmount() );
		}
		portfolio.setTotalGain(totalGain);
		double[] allPayments = new double[payments.size()];
		Date[] alldates = new Date[dates.size()];
		for (int i=0; i< allPayments.length;i++){
			allPayments[i] = payments.get(i);
			alldates[i] = dates.get(i);
		}
		
		
		portfolio.setTotalXirr(XirrCalculatorService.Newtons_method(0.1,allPayments , alldates));
	}
	
	
	private static void calculateCompanyGain(Portfolio portfolio) throws ParseException{
		Date today = new Date(); 
		Map<String, List<Profile>> companyProfilesMap = new HashMap<String, List<Profile>>();
		for (Profile aprofile: portfolio.getAllProfiles()){
			if (null == companyProfilesMap.get(aprofile.getSchemeCode())){
				List<Profile> compantyProfilesList = new ArrayList<Profile>();
				compantyProfilesList.add(aprofile);
				companyProfilesMap.put(aprofile.getSchemeCode(), compantyProfilesList);
			}else {
				List<Profile> compantyProfilesList = companyProfilesMap.get(aprofile.getSchemeCode());
				compantyProfilesList.add(aprofile);
			}
		}
		
		for (String schemeCode : companyProfilesMap.keySet()){
			List<Profile> compantyProfilesList = companyProfilesMap.get(schemeCode);
			List<Double> payments = new ArrayList<Double>();
			List<Date> dates = new ArrayList<Date>();
			double companyTotalInvestment = 0;
			for (Profile aprofile: compantyProfilesList){
				payments.add(aprofile.getInvestmentAmount() *-1);
				dates.add(sdf.parse(aprofile.getInvestmentDate()));
				payments.add(aprofile.getCurrentValue());
				dates.add(today);
				companyTotalInvestment += aprofile.getInvestmentAmount();
			}
			
			double[] allPayments = new double[payments.size()];
			Date[] alldates = new Date[dates.size()];
			for (int i=0; i< allPayments.length;i++){
				allPayments[i] = payments.get(i);
				alldates[i] = dates.get(i);
			}
			
			double xirr = XirrCalculatorService.Newtons_method(0.1,allPayments , alldates);
			for (Profile aprofile: compantyProfilesList){
				aprofile.setCompanyXirr(xirr);
				aprofile.setCompanyTotalInvestment(companyTotalInvestment);
			}
		}
		
		
	}

}
