package com.profile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.tools.ant.util.CollectionUtils;

import com.google.gson.reflect.TypeToken;
import com.nav.CompanyVO;
import com.nav.CurrentMarketPrice;
import com.nav.NavTextDAO;
import com.nav.NavVO;
import com.sip.SipSchemeVO;
import com.Constants;
import com.chart.ChartDAO;
import com.chart.ChartNAV;
import com.chart.ChartVO;
import com.chart.ChartVOComparator;
import com.chart.ChartVOUI;
import com.chart.ChartVOUIComparator;
import com.chart.MonthlyData;
import com.chart.NavVoUI;
import com.common.FinConstants;
import com.google.appengine.api.ThreadManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vo.CurrentPrice;
import com.vo.CurrentPriceVO;
import com.vo.CurrentPriceVOSort;
import com.vo.Portfolio;
import com.vo.Profile;
import com.vo.ProfileSort;
import com.vo.StockPortfolio;
import com.vo.StockSort;
import com.vo.StockVO;
import com.vo.WishList;
import com.vo.chart.ChartDataSets;
import com.vo.chart.chartData;
import com.xirr.XirrCalculatorService;

public class ProfileService {
	private static final Logger log = Logger.getLogger(ProfileService.class.getName());
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	public static SimpleDateFormat stockQuoteDateTime = new SimpleDateFormat("dd-MMM-yyyy h:m:s");
	static {
		stockQuoteDateTime.setTimeZone(TimeZone.getTimeZone("IST"));
	}
	public static void deleteFromPortfolio(String collection, long profileID) {
		Gson json = new Gson();
		log.info("Deleting from   user profile " + collection + profileID);
		String currentData = ProfileDAO.getUserPortfolio(Constants.dbName, collection, false, null, Constants.mlabKey);
		String currentData_mf_archive = ProfileDAO.getUserPortfolio(Constants.dbName, collection+Constants.mf_archive, false, null, Constants.mlabKey);
																					
		if (null == currentData || "".equals(currentData.trim())) {
			log.info("Creating a new profile " + collection);
			ProfileDAO.createNewCollection(collection, Constants.dbName, Constants.mlabKey);
		} else {

			log.info("Profile already exists " + currentData);
		}
		Portfolio pf_mf_archive = null;
		if (null == currentData_mf_archive || "".equals(currentData_mf_archive.trim())) {
			pf_mf_archive = new Portfolio();
			log.info("Creating a new profile " + collection);
			ProfileDAO.createNewCollection(collection+Constants.mf_archive, Constants.dbName, Constants.mlabKey);
		}else {
			currentData_mf_archive = currentData_mf_archive.trim();
			pf_mf_archive = json.fromJson(currentData_mf_archive, Portfolio.class);
		}
		String dataToAdd = null;
		Portfolio pf = null;
		
		log.info("pf_mf_archive " + pf_mf_archive);
		currentData = ProfileDAO.getUserPortfolio(Constants.dbName, collection, false, null,Constants.mlabKey);
		

		currentData = currentData.trim();
		log.info("currentData =" + currentData + "#");
		pf = json.fromJson(currentData, Portfolio.class);
		
		Iterator<Profile> profileItr = pf.getAllProfiles().iterator();
		while (profileItr.hasNext()) {
			Profile profileToBeDelated = profileItr.next();
			if (profileToBeDelated.getProfileID() == profileID) {
				profileToBeDelated.setExitDate(sdf.format(new Date()));
				profileToBeDelated.setExitUnits(profileToBeDelated.getUnits());
				pf_mf_archive.getAllProfiles().add(profileToBeDelated);
				profileItr.remove();
			}
		}

		dataToAdd = json.toJson(pf, new TypeToken<Portfolio>() {}.getType());
		ProfileDAO.insertData(collection, dataToAdd);
		String dataToAdd_mf_archive = json.toJson(pf_mf_archive, new TypeToken<Portfolio>() {}.getType());
		ProfileDAO.insertData(collection+Constants.mf_archive, dataToAdd_mf_archive);
	}
	
	public static void deleteStockFromPortfolio(String collection, long profileID) {
		Gson json = new Gson();
		log.info("Deleting from   user profile " + collection + profileID);
		String currentData = ProfileDAO.getUserPortfolio(Constants.stockEquityDB, collection, false, null, Constants.mlabKey);
		String currentData_eq_archive = ProfileDAO.getUserPortfolio(Constants.stockEquityDB, collection+Constants.eq_archive, false, null, Constants.mlabKey);
																					
		if (null == currentData || "".equals(currentData.trim())) {
			log.info("Creating a new profile " + collection);
			ProfileDAO.createNewCollection(collection, Constants.stockEquityDB, Constants.mlabKey);
		} else {

			log.info("Profile already exists " + currentData);
		}
		StockPortfolio pf_eq_archive = null;
		if (null == currentData_eq_archive || "".equals(currentData_eq_archive.trim())) {
			pf_eq_archive = new StockPortfolio();
			ProfileDAO.createNewCollection(collection+Constants.eq_archive, Constants.stockEquityDB, Constants.mlabKey);
		}else {
			currentData_eq_archive = currentData_eq_archive.trim();
			pf_eq_archive = json.fromJson(currentData_eq_archive, StockPortfolio.class);
		}
		String dataToAdd = null;
		StockPortfolio pf = null;
		
		currentData = ProfileDAO.getUserPortfolio(Constants.stockEquityDB, collection, false, null,Constants.mlabKey);// get
																			
		currentData = currentData.trim();
		log.info("currentData =" + currentData + "#");
		pf = json.fromJson(currentData, StockPortfolio.class);
		Iterator<StockVO> profileItr = pf.getAllStocks().iterator();
		while (profileItr.hasNext()) {
			StockVO stocktoBeDeleted = profileItr.next();
			if (stocktoBeDeleted.getProfileID() == profileID) {
				stocktoBeDeleted.setExitDateStr(sdf.format(new Date()));
				stocktoBeDeleted.setExitQty(stocktoBeDeleted.getExitQty());
				pf_eq_archive.getAllStocks().add(stocktoBeDeleted);
				profileItr.remove();
			}
		}

		dataToAdd = json.toJson(pf, new TypeToken<StockPortfolio>() {}.getType());
		ProfileDAO.insertData(collection, dataToAdd, Constants.stockEquityDB,Constants.mlabKey);
		String dataToAdd_eq_eqarchive = json.toJson(pf_eq_archive, new TypeToken<StockPortfolio>() {}.getType());
		ProfileDAO.insertData(collection+Constants.eq_archive, dataToAdd_eq_eqarchive, Constants.stockEquityDB,Constants.mlabKey);
	}
	
	
	public static void EditProfiles_mf_archive(String collection, List<Profile> oldProfilesList) {
		Gson json = new Gson();
		String currentData_mf_archive = ProfileDAO.getUserPortfolio(Constants.dbName, collection+Constants.mf_archive, false, null, Constants.mlabKey);
		Portfolio pf_mf_archive = null;
		currentData_mf_archive = currentData_mf_archive.trim();
		pf_mf_archive = json.fromJson(currentData_mf_archive, Portfolio.class);
		pf_mf_archive.setAllProfiles(oldProfilesList);
		String dataToAdd_mf_archive = json.toJson(pf_mf_archive, new TypeToken<Portfolio>() {}.getType());
		ProfileDAO.insertData(collection+Constants.mf_archive, dataToAdd_mf_archive);
	}
	public static void EditProfiles_eq_archive(String collection, List<StockVO> oldProfilesList) {
		Gson json = new Gson();
		String currentData_eq_archive = ProfileDAO.getUserPortfolio(Constants.stockEquityDB, collection+Constants.eq_archive, false, null, Constants.mlabKey);
		StockPortfolio pf_eq_archive = null;
		currentData_eq_archive = currentData_eq_archive.trim();
		pf_eq_archive = json.fromJson(currentData_eq_archive, StockPortfolio.class);
		pf_eq_archive.setAllStocks(oldProfilesList);
		String dataToAdd_eq_archive = json.toJson(pf_eq_archive, new TypeToken<StockPortfolio>() {}.getType());
		ProfileDAO.insertData(collection+Constants.eq_archive, dataToAdd_eq_archive,Constants.stockEquityDB,Constants.mlabKey);
	}

	public static void addFundToPortfolio(String collection, Profile profile) {
		log.info("Adding to user profile " + collection + profile);
		String currentData = ProfileDAO.getUserPortfolio(Constants.dbName, collection, false, null, Constants.mlabKey);
		if (null == currentData || "".equals(currentData.trim())) {
			log.info("Creating a new profile " + collection);
			ProfileDAO.createNewCollection(collection, Constants.dbName, Constants.mlabKey);
		} else {

			log.info("Profile already exists " + currentData);
		}
		String dataToAdd = null;
		Portfolio pf = null;
		Gson json = new Gson();
		currentData = ProfileDAO.getUserPortfolio(Constants.dbName,collection, true, null, Constants.mlabKey);
		if (null == currentData || "".equals(currentData.trim())) {// empty
																	// commection
			pf = new Portfolio();
			profile.setProfileID(new Date().getTime());
			pf.getAllProfiles().add(profile);
			log.info("Adding first profile " + currentData);

		} else {
			currentData = currentData.trim();
			log.info("currentData =" + currentData + "#");
			pf = json.fromJson(currentData, Portfolio.class);

			profile.setProfileID(new Date().getTime());
			pf.getAllProfiles().add(profile);

		}

		dataToAdd = json.toJson(pf, new TypeToken<Portfolio>() {}.getType());
		ProfileDAO.insertData(collection, dataToAdd);
	}
	
	public static void addStockToPortfolio(String collection, StockVO selectedStock) {
		
		String currentData = ProfileDAO.getUserPortfolio(Constants.stockEquityDB,collection, false, null,Constants.mlabKey);
		if (null == currentData || "".equals(currentData.trim())) {
			log.info("Creating a new profile " + collection);
			ProfileDAO.createNewCollection(collection, Constants.stockEquityDB, Constants.mlabKey);
		} else {

			log.info("Profile already exists " + currentData);
		}
		String dataToAdd = null;
		StockPortfolio pf = null;
		GsonBuilder builder = new GsonBuilder();
		builder.serializeSpecialFloatingPointValues();
		 Gson json =builder.create();
		currentData = ProfileDAO.getUserPortfolio(Constants.stockEquityDB,collection, false, null, Constants.mlabKey);
		if (null == currentData || "".equals(currentData.trim())) {// empty
																	// commection
			pf = new StockPortfolio();
			selectedStock.setProfileID(new Date().getTime());
			pf.getAllStocks().add(selectedStock);
			log.info("Adding first profile " + currentData);

		} else {
			currentData = currentData.trim();
			log.info("currentData =" + currentData + "#");
			pf = json.fromJson(currentData, StockPortfolio.class);

			selectedStock.setProfileID(new Date().getTime());
			pf.getAllStocks().add(selectedStock);

		}

		dataToAdd = json.toJson(pf, new TypeToken<StockPortfolio>() {}.getType());
		ProfileDAO.insertData(collection, dataToAdd, Constants.stockEquityDB,Constants.mlabKey);
		
	}

	public static void saveSipList(String collection, List<SipSchemeVO> schemes) {
		//collection += "_sip";
		log.info("Adding to user profile " + collection);
		String currentData = ProfileDAO.getUserPortfolio(Constants.dbName, collection, false, null,Constants.mlabKey);
		if (null == currentData || "".equals(currentData.trim())) {
			log.info("Creating a new profile " + collection);
			ProfileDAO.createNewCollection(collection, Constants.dbName, Constants.mlabKey);
		} else {

			log.info("Profile already exists " + currentData);
		}

		Gson json = new Gson();
		String dataToAdd = json.toJson(schemes, new TypeToken<List<SipSchemeVO>>() {
		}.getType());
		ProfileDAO.insertData(collection, dataToAdd);
	}
	public static void saveWishList(String collection, List<WishList> wishList) {
		//collection += "_sip";
		log.info("Adding to user profile " + collection);
		String currentData = ProfileDAO.getUserPortfolio(Constants.stockEquityDB, collection, false, null,Constants.mlabKey);
		if (null == currentData || "".equals(currentData.trim())) {
			log.info("Creating a new profile " + collection);
			ProfileDAO.createNewCollection(collection, Constants.stockEquityDB, Constants.mlabKey);
		} else {

			log.info("Profile already exists " + currentData);
		}

		Gson json = new Gson();
		String dataToAdd = json.toJson(wishList, new TypeToken<List<WishList>>() {
		}.getType());
		ProfileDAO.insertData(collection, dataToAdd,Constants.stockEquityDB,Constants.mlabKey);
	}

/*	public static void calculatePercentage(List<ChartVO> chartData, Portfolio portfolio) {
		for (ChartVO chartVO : chartData) {
			String schemeCode = chartVO.get_id();
			for (Profile profile : portfolio.getAllProfiles()) {
				if (profile.getSchemeCode().equals(schemeCode)) {
					chartVO.set_id(profile.getSchemeName());
					List<ChartNAV> charNavs = chartVO.getNavs();
					double baseValue = charNavs.get(0).getNav();
					double previousValue = baseValue;

					int rowCount = 0;
					for (ChartNAV chartNav : charNavs) {
						rowCount++;
						if (rowCount > 1) {
							chartNav.setBasePercentageChange(((chartNav.getNav() - baseValue) / baseValue) * 100);
							chartNav.setRollingPercentageChange(
									((chartNav.getNav() - previousValue) / previousValue) * 100);
							previousValue = chartNav.getNav();
						}
					}
				}
			}
		}

	}*/
	
public static List<ChartVO> getAllHouseTopPerformers(int schemeCountFrom, int schemeCountTo){
	List<ChartVO> chartVos = new ArrayList<ChartVO>();
	Gson  json = new Gson();
	
	Calendar cal = new GregorianCalendar();
	cal.set(Calendar.DATE, 1);
	cal.add(Calendar.MONTH, 1);
	
	List<String> months = new ArrayList<>();
	for (int i=0;i<12;i++){
		months.add(ChartDAO.monthArray[cal.get(Calendar.MONTH)]);
		cal.add(Calendar.MONTH, 1);
	}
	
	if (ChartDAO.isUpdateNeeded("all_house")){
	//if (true){
		Iterator<String> itr = months.iterator();
		List<MonthlyData> allHousesForAllYear = new ArrayList<>();
		while(itr.hasNext()){
			String month = itr.next();
			
			String allHousesForAMonth = ProfileDAO.getArrayData(month+"_all", Constants.allHouses, false, null,Constants.mlabKey_mutualFunfs);
			//log.info(month);
			//log.info(allHousesForAMonth);
			allHousesForAllYear.addAll((List<MonthlyData> )json.fromJson(allHousesForAMonth, new TypeToken<List<MonthlyData>>() {}.getType()));

			
		}
		Map<String, ChartVO>  schemCode_ChartVO_MAP = new HashMap<>();
		ChartDAO dao = new ChartDAO(null,null);
		for (MonthlyData data: allHousesForAllYear){
			dao.populateChartVOMap(data.getNavs() ,  schemCode_ChartVO_MAP);
		}
		
		
		
		for (String schemeCode : schemCode_ChartVO_MAP.keySet()) {
			chartVos.add(schemCode_ChartVO_MAP.get(schemeCode));
			
		}
		
		completeASchemeNavs(chartVos, 3);
		chartVos.sort(new ChartVOComparator());
		
		String chartDataStr = json.toJson(chartVos, new TypeToken<List<ChartVO>>() {}.getType());
		ProfileDAO.createNewCollectionWithData("_all_house", "historical_data_sorted", chartDataStr, Constants.mlabKey_mutualFunfs);
		
		ChartVO timeOfUpdate = new ChartVO();
		timeOfUpdate.set_id(Constants.timeOfUpdateKey);
		timeOfUpdate.setTm(new Date().getTime());
		String timestamp = json.toJson(timeOfUpdate, ChartVO.class);
		ProfileDAO.createNewCollectionWithData("_all_house", Constants.timestamp,timestamp, Constants.mlabKey_mutualFunfs);
		
	}else {
		String allHousesData = ProfileDAO.getArrayData("historical_data_sorted", "all_house", false, null,Constants.mlabKey_mutualFunfs);
		chartVos = json.fromJson(allHousesData, new TypeToken<List<ChartVO>>() {}.getType());
		chartVos.sort(new ChartVOComparator());
	}
	
	
	return chartVos.subList(schemeCountFrom -1, schemeCountTo);
}
public static List<ChartVOUI> getHouseChartData(String houseCode, int schemeCountFrom, int schemeCountTo){
	List<ChartVO> listOfSchemes= new ArrayList<>();ChartDAO.getHouseDataFromMDB(houseCode);
	List<ChartVOUI> listOfSchemeUI = new ArrayList<ChartVOUI>();

	for (ChartVO aScheme:listOfSchemes ){
		
		
			if (aScheme.getNavs().size() > 0){
				ChartVOUI aSchemeUI = new ChartVOUI();
				aSchemeUI.set_id(aScheme.get_id());
				
				List<NavVoUI> uiNAvs = new ArrayList<NavVoUI>();
				double baseNav = -1;
				int navCount = 0;
				double previousNav = 0;
				Long nextDateToUse = null;
				boolean validGroth = true;
				List<ChartNAV> usedNavs = new ArrayList<ChartNAV>();
				for (ChartNAV nav: aScheme.getNavs()){
					
					if (navCount == 0){
						baseNav = nav.getNav();
					
					}
					Date navDate = null;
					String navDateStr = "01"+nav.getDt().substring(2);
					try {
						navDate = sdf.parse(navDateStr);//first of that month
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
					if (nextDateToUse == null || (null != navDate &&  (navDate.getTime() >= nextDateToUse) )){
						usedNavs.add(nav);
						navCount++;
						Calendar cal = new GregorianCalendar();
						cal.setTime(navDate);
						cal.add(Calendar.MONTH, 1);
						cal.set(Calendar.DAY_OF_MONTH, 1);
						nextDateToUse = cal.getTime().getTime();
						NavVoUI uiNav = new NavVoUI();
						uiNav.setDt(navDateStr);
						
						
						if ( navCount >= 12){
							try {
								long timeDiff = sdf.parse(nav.getDt()).getTime() - sdf.parse(usedNavs.get(navCount-12).getDt()).getTime();
								
								timeDiff = timeDiff/FinConstants.aDay; //no of days;
								baseNav = usedNavs.get(navCount-12).getNav();
								uiNav.setBpi(((nav.getNav() - baseNav)/baseNav *100 ) * (365/timeDiff));
								
							} catch (ParseException e) {
								
								e.printStackTrace();
							}
							
							
						}else {
							
							uiNav.setBpi( ( ((nav.getNav() - baseNav)/baseNav *100)  /navCount)*12);
						}
						
						
							if (navCount > 1 ){
								if (( (nav.getNav() - previousNav ) /previousNav *1200) > 200) {
									validGroth = false;
								}
							}
							
							
							
						
						uiNAvs.add(uiNav);
						previousNav = nav.getNav();
					}
					
				}
				
				aSchemeUI.setNavs(uiNAvs);
				if (usedNavs.size() == 0  ){
					validGroth = false;
				}else {
					try {
						Date latestNav = sdf.parse(usedNavs.get(usedNavs.size()-1).getDt());
						if (  (new Date().getTime() - latestNav.getTime() ) > ( FinConstants.aDay * 90)){ //very old nav
							validGroth = false;
							//System.out.println("validGroth =="+usedNavs.get(usedNavs.size()-1).getDt());
						}
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
				}
				if (uiNAvs.size() > 6 && validGroth) {
					uiNAvs.get(0).setBpi(uiNAvs.get(1).getBpi());
					listOfSchemeUI.add(aSchemeUI);
				}
				
				
			}
		
		
		
		
	}
	
	Collections.sort(listOfSchemeUI, new ChartVOUIComparator());
	List<ChartVOUI> chartVOUIsFiltered = null;
	if (schemeCountTo < listOfSchemeUI.size()){
		chartVOUIsFiltered = listOfSchemeUI.subList(schemeCountFrom -1, schemeCountTo);
	}else if (listOfSchemeUI.size() > 20) {
		chartVOUIsFiltered = listOfSchemeUI.subList(0, 20);
	}else {
		chartVOUIsFiltered = listOfSchemeUI;
	}
	 
	return chartVOUIsFiltered;
}

/*
private static boolean calculateMonthlyRollingReturn(List<NavVoUI> uiNAvs){
	boolean aValidGroth = true;
	int count = 0;
	double lastBpi = 0;
	for (NavVoUI aNav : uiNAvs){
		
		if (count>=1){
			aNav.setBpi( (aNav.getNav() - uiNAvs.get(count-1).getNav() ) /uiNAvs.get(count-1).getNav() *1200);
			if (aNav.getBpi() > 200) {
				aValidGroth = false;
			}
		}
		count++;
		
	}
	return aValidGroth;
}*/
	public static void getAllHistoricalData(int noOfSemesters) {
		Set<String> houseIds = FinConstants.houseNameMap.keySet();

		List<ChartVO> chartVos = new ArrayList<ChartVO>();
		
		List<ChartDAO> workers = new ArrayList<ChartDAO>();
		
		for (String houseID : houseIds) {
			if (ChartDAO.isUpdateNeeded(houseID)) {
				log.info("Creating a worker for house id  " + houseID);
				boolean insertInfoAllHouseDb = false;
				ChartDAO worker = new ChartDAO(noOfSemesters, houseID, insertInfoAllHouseDb);
				// service.execute( worker);
				workers.add(worker);
				try {
					worker.getChartData();
					updateResultinDB(worker, chartVos);
					log.info("we have cached data for future use for house id  " + houseID);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				log.info("we already have cached data for house id  " + houseID);
			}
			

		}
		
		log.info("Will get all results from all workers now ");

	}
	
	public static void getAllHistoricalMonthlyData(boolean allDates) {
		Set<String> houseIds = FinConstants.houseNameMap.keySet();

		List<ChartVO> chartVos = new ArrayList<ChartVO>();
		
		List<ChartDAO> workers = new ArrayList<ChartDAO>();
		 Gson  json = new Gson();
		 
		for (String houseID : houseIds) {
			if (ChartDAO.isUpdateNeeded(houseID)) {
				log.info("Creating a worker for house id  " + houseID);
				boolean insertInfoAllHouseDb = true;
				ChartDAO worker = new ChartDAO(0, houseID, insertInfoAllHouseDb);
				// service.execute( worker);
				workers.add(worker);
				try {
					Calendar fromCal =  new GregorianCalendar(), toCal =  new GregorianCalendar();
					 
					if (allDates){
						 toCal.add(Calendar.MONTH,  -10); 
						 toCal.set(Calendar.DATE, 1);
						 fromCal.add(Calendar.MONTH, -11);
						 fromCal.set(Calendar.DATE, 1);
					}else {
						
						 toCal.add(Calendar.MONTH,  -11); 
						 toCal.set(Calendar.DATE, 3);
						fromCal.add(Calendar.MONTH, -11);
						 fromCal.set(Calendar.DATE, 1);
					}
					if (worker.getChartDataForAllDaysOfYear(fromCal, toCal)) {
						updateTimeStampIn(houseID);
					}
					
					/*Map<String, ChartVO> result = worker.getResult();
					 String dataToStore = json.toJson(result, new TypeToken<Map<String, ChartVO>>() {}.getType() );
					ProfileDAO.createNewCollectionWithData("_"+houseID,Constants.dbName_mutualFunfs,dataToStore,Constants.mlabKey_mutualFunfs);*/
					log.info("we have cached data for future use for house id  " + houseID);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				log.info("we already have cached data for house id  " + houseID);
			}
			

		}
		
		log.info("Will get all results from all workers now ");

	}
	private static void updateTimeStampIn(String houseID){
	
		List<ChartVO> houseData = new ArrayList<ChartVO>();
		ChartVO timeOfUpdate = new ChartVO();
		timeOfUpdate.set_id(Constants.timeOfUpdateKey);
		timeOfUpdate.setTm(new Date().getTime());
		houseData.add(timeOfUpdate);
		
		Gson json = new Gson();

		String houseData_Str = json.toJson(houseData, new TypeToken<List<ChartVO>>() {}.getType());
		
		 ProfileDAO.insertData("_" + houseID, houseData_Str, Constants.timestamp,	Constants.mlabKey_mutualFunfs);
	}

	private static void updateResultinDB(ChartDAO worker, List<ChartVO> chartVos) {
		log.info("Will get resukt from workesr  a worker  " + worker.getHouseCode());
		Map<String, ChartVO> results = worker.getResult();
		List<ChartVO> houseData = new ArrayList<ChartVO>();
		ChartVO timeOfUpdate = new ChartVO();
		timeOfUpdate.set_id(Constants.timeOfUpdateKey);
		timeOfUpdate.setTm(new Date().getTime());
		houseData.add(timeOfUpdate);
		for (String schemeCode : results.keySet()) {
			houseData.add(results.get(schemeCode));
			chartVos.add(results.get(schemeCode));

		}
		Gson json = new Gson();

		String houseData_Str = json.toJson(houseData, new TypeToken<List<ChartVO>>() {}.getType());
		
		 ProfileDAO.insertData("_" + worker.getHouseCode(), houseData_Str, Constants.dbName_mutualFunfs,
				Constants.mlabKey_mutualFunfs);
	}

	public static List<ChartVO> getHistoricalDataForMyProfile(Portfolio portFolio) {
		
		boolean allDates = true;
		
		Set<String> houseIds = new HashSet<String>();
		Set<String> schemeCodes = new HashSet<String>();
		for (Profile profile : portFolio.getAllProfiles()) {
			schemeCodes.add(profile.getSchemeCode());
			String hounsename = profile.getSchemeName();
			hounsename = hounsename.substring(0, hounsename.indexOf(" "));
			String houseCode = FinConstants.houseIDMap.get(hounsename);
			if (null != houseCode) {
				houseIds.add(houseCode);
				log.info("schemaName  " + hounsename + " house code " + houseCode);
			}

		}

		List<ChartVO> chartVos = new ArrayList<ChartVO>();
		List<ChartDAO> workers = new ArrayList<ChartDAO>();

		for (String houseID : houseIds) {
			
			ChartDAO worker = new ChartDAO( houseID, schemeCodes);
			workers.add(worker);
			try {
				Calendar fromCal =  new GregorianCalendar(), toCal =  new GregorianCalendar();
				 
				if (allDates){
					 toCal.add(Calendar.MONTH,  -10); 
					 toCal.set(Calendar.DATE, 1);
					 fromCal.add(Calendar.MONTH, -11);
					 fromCal.set(Calendar.DATE, 1);
				}else {
					
					 toCal.add(Calendar.MONTH,  -11); 
					 toCal.set(Calendar.DATE, 3);
					fromCal.add(Calendar.MONTH, -11);
					 fromCal.set(Calendar.DATE, 1);
				}
				worker.getChartDataForAllDaysOfYear(fromCal, toCal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		

		for (ChartDAO worker : workers) {

			
			Map<String, ChartVO> results = worker.getResult();
			for (String schemeCode : results.keySet()) {
				chartVos.add(results.get(schemeCode));
				
			}

		}
		
		    
		completeASchemeNavs(chartVos, 31);
		
		
		return chartVos;
	}
	
	private static void completeASchemeNavs(List<ChartVO> chartVos, int maxDaysInMonth){
		double zoomFactor = 10;
	    double chartMaxValue = 10;
		
	
	
			
			Calendar chartEndDate = new GregorianCalendar();
			chartEndDate.set(Calendar.HOUR_OF_DAY, 0);
			chartEndDate.set(Calendar.MINUTE, 0);
			chartEndDate.set(Calendar.SECOND, 0);
			chartEndDate.set(Calendar.MILLISECOND, 0);
			//Now fill default value if any  data if data not available due to system errors - add to last
			for (ChartVO schemeChart: chartVos){
				List<ChartNAV> completeNav = new ArrayList<ChartNAV>();
				Calendar chartStartDate = new GregorianCalendar();
				chartStartDate.set(Calendar.HOUR_OF_DAY, 0);
				chartStartDate.set(Calendar.MINUTE, 0);
				chartStartDate.set(Calendar.SECOND, 0);
				chartStartDate.set(Calendar.MILLISECOND, 0);
				chartStartDate.set(Calendar.DATE, 1);
				chartStartDate.add(Calendar.MONTH, -11);
				int chartNavePointer = 0;
				
				int settlePeriodDays = 0;
				int nextNavStep = 1;
				boolean allNavDone = false;
				int daysIncluded = 0;
				
				try {
					
					ChartNAV nav = schemeChart.getNavs().get(0);
					ChartNAV lastKnownNav = nav;
					boolean zoomNeeded = false;
					//if (nav.getNav() > chartMaxValue ) {
						zoomNeeded = true;
						zoomFactor = nav.getNav();// / chartMaxValue;
					//}
					//log.info("zoomNeeded  " + nav.getNav() + " ? " + zoomNeeded);
					while(chartStartDate.before(chartEndDate) && !allNavDone){
						
						if (sdf.parse(nav.getDt()).getTime() == chartStartDate.getTime().getTime()){
							
						
								if (chartNavePointer > settlePeriodDays){//Let it settle
									
									
									if (zoomNeeded){
										nav.setScaled(nav.getNav()/zoomFactor);
									}else {
										nav.setScaled(nav.getNav());
									}
									lastKnownNav = nav;
									completeNav.add(nav);
									
								}
								
									if(schemeChart.getNavs().size() > (chartNavePointer +nextNavStep) ){
										chartStartDate.add(Calendar.DAY_OF_MONTH, 1);//Move to next day
										if (maxDaysInMonth < 25){
											daysIncluded ++;
											if (daysIncluded >= maxDaysInMonth){
												chartStartDate.add(Calendar.MONTH, 1);
												chartStartDate.set(Calendar.DATE, 1);
												daysIncluded =0;
											}
										}
										
										chartNavePointer +=nextNavStep;
										nav = schemeChart.getNavs().get(chartNavePointer);//and get next nav from list
									}else {
										allNavDone = true;
										
									}
								
						
							
						}else if (sdf.parse(nav.getDt()).getTime() < chartStartDate.getTime().getTime()) {//This Date Nav is already added in the list ignore this
							   
								if(schemeChart.getNavs().size() > (chartNavePointer +nextNavStep) ){
									chartNavePointer +=nextNavStep;
									nav = schemeChart.getNavs().get(chartNavePointer);
								}else {
									allNavDone = true;
									
								}
							
						}else {//Some navs are missing add dummy nav to complete the list
							ChartNAV navToAdd = lastKnownNav.clone();
							if (zoomNeeded){
								navToAdd.setScaled(navToAdd.getNav()/zoomFactor);
							}else {
								navToAdd.setScaled(navToAdd.getNav());
							}
							navToAdd.setDt(sdf.format(chartStartDate.getTime()));
							completeNav.add(navToAdd);
							chartStartDate.add(Calendar.DAY_OF_MONTH, 1);//Move to next day
							if (maxDaysInMonth < 25){
								daysIncluded ++;
								if (daysIncluded >= maxDaysInMonth){
									chartStartDate.add(Calendar.MONTH, 1);
									chartStartDate.set(Calendar.DATE, 1);
									daysIncluded =0;
								}
							}
							
						}
						
							
							
						
						
						
						
					}
					schemeChart.setNavs(completeNav);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

	
	private static int daysBetweenDates (Date date1 , Date date2){
		  return Math.round((date2.getTime() -date1.getTime() )/FinConstants.aDay); 
		}
	public static Portfolio getPortfolioDromDB(String collection) {
		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1501383477625 , \"investmentDate\" : \"7-Jun-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.216 , \"investmentAmount\" : 20000.0 , \"units\" : 1233.3497779970398 , \"currentValue\" : 21931.79575727676 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 76.901310434256 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383508987 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.708 , \"investmentAmount\" : 10000.0 , \"units\" : 598.5156811108452 , \"currentValue\" : 10642.985396217382 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 113.43821019391093 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501643072554 , \"investmentDate\" : \"1-Aug-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 17.9165 , \"investmentAmount\" : 10000.0 , \"units\" : 558.1447269276924 , \"currentValue\" : 9925.096977646304 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : -49.64451913397262 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383546880 , \"investmentDate\" : \"12-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Banking and Financial Services Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120244\" , \"nav\" : 59.71 , \"investmentAmount\" : 20000.0 , \"units\" : 334.95226930162454 , \"currentValue\" : 21279.51766873221 , \"currentNav\" : 63.53 , \"lastKnownNav\" : 63.53 , \"xirr\" : 52.068377206971164 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 52.068377206971164 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383200382 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"DSP BlackRock Natural Resources and New Energy Fund - Direct Plan - Growth\" , \"schemeCode\" : \"119028\" , \"nav\" : 31.25 , \"investmentAmount\" : 20000.0 , \"units\" : 640.0 , \"currentValue\" : 21260.8 , \"currentNav\" : 33.22 , \"lastKnownNav\" : 33.22 , \"xirr\" : 41.714939715327034 , \"companyName\" : \"DSP BlackRock Mutual Fund\" , \"companyXirr\" : 41.714939715327034 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501379186922 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 131.08 , \"investmentAmount\" : 25000.0 , \"units\" : 190.72322245956667 , \"currentValue\" : 26417.07354287458 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 36.288325205709135 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501382919277 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 136.51 , \"investmentAmount\" : 10000.0 , \"units\" : 73.25470661490002 , \"currentValue\" : 10146.5094132298 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 19.358328546622104 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501383156577 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"L&T Infrastructure Fund -Direct Plan-Growth Option\" , \"schemeCode\" : \"119413\" , \"nav\" : 15.79 , \"investmentAmount\" : 25000.0 , \"units\" : 1583.2805573147562 , \"currentValue\" : 26139.962001266627 , \"currentNav\" : 16.51 , \"lastKnownNav\" : 16.51 , \"xirr\" : 28.955743356765133 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 28.955743356765133 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383093995 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Kotak Select Focus Fund - Growth - Direct\" , \"schemeCode\" : \"120166\" , \"nav\" : 32.109 , \"investmentAmount\" : 25000.0 , \"units\" : 778.5979009000591 , \"currentValue\" : 25928.08869787287 , \"currentNav\" : 33.301 , \"lastKnownNav\" : 33.301 , \"xirr\" : 23.107154129383588 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 23.107154129383588 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383259924 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Birla Sun Life Midcap Fund - Growth - Direct Plan\" , \"schemeCode\" : \"119620\" , \"nav\" : 303.95 , \"investmentAmount\" : 25000.0 , \"units\" : 82.25037012666557 , \"currentValue\" : 25880.07896035532 , \"currentNav\" : 314.65 , \"lastKnownNav\" : 314.65 , \"xirr\" : 21.812767042941566 , \"companyName\" : \"Birla Sun Life Mutual Fund\" , \"companyXirr\" : 21.812767042941566 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383350497 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Top 100 Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120596\" , \"nav\" : 312.28 , \"investmentAmount\" : 20000.0 , \"units\" : 64.04508774177022 , \"currentValue\" : 20664.14755988216 , \"currentNav\" : 322.65 , \"lastKnownNav\" : 322.65 , \"xirr\" : 21.588474731024075 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 21.588474731024075 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383395107 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Value Discovery Fund - Direct Plan - Growth\" , \"schemeCode\" : \"120323\" , \"nav\" : 141.1 , \"investmentAmount\" : 20000.0 , \"units\" : 141.74344436569808 , \"currentValue\" : 20412.47342310418 , \"currentNav\" : 144.01 , \"lastKnownNav\" : 144.01 , \"xirr\" : 12.992193185683728 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 12.992193185683728 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383315183 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Multicap Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120599\" , \"nav\" : 265.47 , \"investmentAmount\" : 20000.0 , \"units\" : 75.33807963235016 , \"currentValue\" : 20327.720646400718 , \"currentNav\" : 269.82 , \"lastKnownNav\" : 269.82 , \"xirr\" : 10.213910680254138 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 10.213910680254138 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383023193 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"Kotak-Mid-Cap-Growth - Direct\" , \"schemeCode\" : \"120164\" , \"nav\" : 76.772 , \"investmentAmount\" : 25000.0 , \"units\" : 325.6395560881571 , \"currentValue\" : 25317.49856718595 , \"currentNav\" : 77.747 , \"lastKnownNav\" : 77.747 , \"xirr\" : 7.343730747059469 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 7.343730747059469 , \"companyTotalInvestment\" : 25000.0}] , \"totalGain\" : 11273.74861204486 , \"totalXirr\" : 28.854294303825007 , \"totalInvetment\" : 1.375E7} ";
		portfolioStr = ProfileDAO.getUserPortfolio(Constants.dbName, collection, false, null, Constants.mlabKey);
		Gson json = new Gson();
		Portfolio portfolio = json.fromJson(portfolioStr, Portfolio.class);
		return portfolio;

	}
	
	public static StockPortfolio getStockPortfolio(String collection) {

		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1501383477625 , \"investmentDate\" : \"7-Jun-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.216 , \"investmentAmount\" : 20000.0 , \"units\" : 1233.3497779970398 , \"currentValue\" : 21931.79575727676 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 76.901310434256 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383508987 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.708 , \"investmentAmount\" : 10000.0 , \"units\" : 598.5156811108452 , \"currentValue\" : 10642.985396217382 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 113.43821019391093 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501643072554 , \"investmentDate\" : \"1-Aug-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 17.9165 , \"investmentAmount\" : 10000.0 , \"units\" : 558.1447269276924 , \"currentValue\" : 9925.096977646304 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : -49.64451913397262 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383546880 , \"investmentDate\" : \"12-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Banking and Financial Services Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120244\" , \"nav\" : 59.71 , \"investmentAmount\" : 20000.0 , \"units\" : 334.95226930162454 , \"currentValue\" : 21279.51766873221 , \"currentNav\" : 63.53 , \"lastKnownNav\" : 63.53 , \"xirr\" : 52.068377206971164 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 52.068377206971164 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383200382 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"DSP BlackRock Natural Resources and New Energy Fund - Direct Plan - Growth\" , \"schemeCode\" : \"119028\" , \"nav\" : 31.25 , \"investmentAmount\" : 20000.0 , \"units\" : 640.0 , \"currentValue\" : 21260.8 , \"currentNav\" : 33.22 , \"lastKnownNav\" : 33.22 , \"xirr\" : 41.714939715327034 , \"companyName\" : \"DSP BlackRock Mutual Fund\" , \"companyXirr\" : 41.714939715327034 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501379186922 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 131.08 , \"investmentAmount\" : 25000.0 , \"units\" : 190.72322245956667 , \"currentValue\" : 26417.07354287458 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 36.288325205709135 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501382919277 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 136.51 , \"investmentAmount\" : 10000.0 , \"units\" : 73.25470661490002 , \"currentValue\" : 10146.5094132298 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 19.358328546622104 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501383156577 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"L&T Infrastructure Fund -Direct Plan-Growth Option\" , \"schemeCode\" : \"119413\" , \"nav\" : 15.79 , \"investmentAmount\" : 25000.0 , \"units\" : 1583.2805573147562 , \"currentValue\" : 26139.962001266627 , \"currentNav\" : 16.51 , \"lastKnownNav\" : 16.51 , \"xirr\" : 28.955743356765133 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 28.955743356765133 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383093995 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Kotak Select Focus Fund - Growth - Direct\" , \"schemeCode\" : \"120166\" , \"nav\" : 32.109 , \"investmentAmount\" : 25000.0 , \"units\" : 778.5979009000591 , \"currentValue\" : 25928.08869787287 , \"currentNav\" : 33.301 , \"lastKnownNav\" : 33.301 , \"xirr\" : 23.107154129383588 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 23.107154129383588 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383259924 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Birla Sun Life Midcap Fund - Growth - Direct Plan\" , \"schemeCode\" : \"119620\" , \"nav\" : 303.95 , \"investmentAmount\" : 25000.0 , \"units\" : 82.25037012666557 , \"currentValue\" : 25880.07896035532 , \"currentNav\" : 314.65 , \"lastKnownNav\" : 314.65 , \"xirr\" : 21.812767042941566 , \"companyName\" : \"Birla Sun Life Mutual Fund\" , \"companyXirr\" : 21.812767042941566 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383350497 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Top 100 Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120596\" , \"nav\" : 312.28 , \"investmentAmount\" : 20000.0 , \"units\" : 64.04508774177022 , \"currentValue\" : 20664.14755988216 , \"currentNav\" : 322.65 , \"lastKnownNav\" : 322.65 , \"xirr\" : 21.588474731024075 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 21.588474731024075 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383395107 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Value Discovery Fund - Direct Plan - Growth\" , \"schemeCode\" : \"120323\" , \"nav\" : 141.1 , \"investmentAmount\" : 20000.0 , \"units\" : 141.74344436569808 , \"currentValue\" : 20412.47342310418 , \"currentNav\" : 144.01 , \"lastKnownNav\" : 144.01 , \"xirr\" : 12.992193185683728 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 12.992193185683728 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383315183 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Multicap Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120599\" , \"nav\" : 265.47 , \"investmentAmount\" : 20000.0 , \"units\" : 75.33807963235016 , \"currentValue\" : 20327.720646400718 , \"currentNav\" : 269.82 , \"lastKnownNav\" : 269.82 , \"xirr\" : 10.213910680254138 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 10.213910680254138 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383023193 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"Kotak-Mid-Cap-Growth - Direct\" , \"schemeCode\" : \"120164\" , \"nav\" : 76.772 , \"investmentAmount\" : 25000.0 , \"units\" : 325.6395560881571 , \"currentValue\" : 25317.49856718595 , \"currentNav\" : 77.747 , \"lastKnownNav\" : 77.747 , \"xirr\" : 7.343730747059469 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 7.343730747059469 , \"companyTotalInvestment\" : 25000.0}] , \"totalGain\" : 11273.74861204486 , \"totalXirr\" : 28.854294303825007 , \"totalInvetment\" : 1.375E7} ";
		portfolioStr = ProfileDAO.getUserPortfolio(Constants.stockEquityDB, collection, false, null,Constants.mlabKey);
		log.info(" Stock data "+portfolioStr);
		Gson json = new Gson();
		StockPortfolio portfolio = json.fromJson(portfolioStr, StockPortfolio.class);
		try {
			List<CurrentMarketPrice> req = new ArrayList<CurrentMarketPrice>();
			Set<String> ticketrs = new HashSet<>();
			for (StockVO aStock : portfolio.getAllStocks()) {
				String uniqKey = aStock.getExchange()+aStock.getTicker();
				if (!ticketrs.contains(uniqKey)){
					CurrentMarketPrice markerReq = new CurrentMarketPrice();
					markerReq.setE(aStock.getExchange());
					markerReq.setT(aStock.getTicker());
					req.add(markerReq);
					ticketrs.add(uniqKey);
				}
				
			}
			Map<String , CurrentMarketPrice> markerResponse = ProfileDAO.getCurrentMarkerPrice(req);
			
			for (StockVO aStock : portfolio.getAllStocks()) {
				CurrentMarketPrice currentMarketPrice = markerResponse.get(aStock.getTicker());
				
							if (currentMarketPrice.getL_fix() != 0) {
								aStock.setLastKnownPrice(currentMarketPrice.getL_fix());
								aStock.setAsOfDate(currentMarketPrice.getLt_dts());
							}
						
							double[] payments = new double[2];
							Date[] dates = new Date[2];
							aStock.setInvestmentAmount(aStock.getPurchasePrice() * aStock.getPurchaseQty());
							payments[0] = aStock.getInvestmentAmount() * -1;
							dates[0] = sdf.parse(aStock.getPurchaseDateStr());
							payments[1] = aStock.getLastKnownPrice() * aStock.getPurchaseQty();
							aStock.setCurrentValue(payments[1]);
							
							dates[1] = new Date();
							double xirr = XirrCalculatorService.Newtons_method(0.1, payments, dates);
							aStock.setXirr(xirr);
							aStock.setPercentGainAbsolute((aStock.getCurrentValue() - aStock.getInvestmentAmount())/ aStock.getInvestmentAmount() * 100);
							aStock.setAbsoluteGain(aStock.getCurrentValue() - aStock.getInvestmentAmount());
						
							
							
						
	
			}

			calculateTotalGainForStock(portfolio);
			calculateCompanyGainForStock(portfolio);
		} catch (Exception e) {

			e.printStackTrace();
		} 

		Collections.sort(portfolio.getAllStocks(), new StockSort());
		GsonBuilder builder = new GsonBuilder();
		builder.serializeSpecialFloatingPointValues();
		Gson gson = builder.create();
		String data = gson.toJson(portfolio, StockPortfolio.class);
		ProfileDAO.insertData(collection, data,Constants.stockEquityDB,Constants.mlabKey);
		return portfolio;
	}
	public static StockPortfolio getStockPortfolio_eq_archive(String collection) {
		StockPortfolio portfolio = new StockPortfolio();
		String portfolioStr = ProfileDAO.getUserPortfolio(Constants.stockEquityDB, collection+Constants.eq_archive, false, null,Constants.mlabKey);
		log.info("portfolioStr ="+portfolioStr);
		if (null != portfolioStr && !"".equals(portfolioStr)){
			Gson json = new Gson();
			 portfolio = json.fromJson(portfolioStr, StockPortfolio.class);
		} 
		
		return portfolio;
	}

	public static chartData[] getPortFolioPriceTrend(String collection){
		Gson json = new Gson();
		String currentData = ProfileDAO.getArrayData(Constants.marketpriceDB, collection, false, null, Constants.mlabKey);
		List<CurrentPriceVO > currentPriceVOList = json.fromJson(currentData, new TypeToken<List<CurrentPriceVO>>() {}.getType());
		
		Collections.sort(currentPriceVOList , new CurrentPriceVOSort());
		
		Map<String, List<Double>> chartMapEQ = new HashMap<String, List<Double>>();
		Map<String, List<Double>> chartMapMF = new HashMap<String, List<Double>>();
		List<String> dates = new ArrayList<String>();
		String[] borderColor  = {"#000000","#c0c0c0","#800000", "#ff0000","#800080","#ff00ff", "#008000","#00ff00","#808000", "#ffff00","#000080","#0000ff", "#00ffff","#ffa500","#006400"};
        int colorID  = 0;
		for (CurrentPriceVO aDate: currentPriceVOList){
			dates.add(aDate.get_id());
			for (CurrentPrice aCompanyPriceOnADate: aDate.getCurrentPrices()){
				if (aCompanyPriceOnADate.getType().equals("EQ")){
					List<Double>  pricesOfCompany = chartMapEQ.get(aCompanyPriceOnADate.getCompanyName());
					if (null == pricesOfCompany){
						pricesOfCompany = new ArrayList<Double>();
					}
					pricesOfCompany.add(aCompanyPriceOnADate.getPrice());
					chartMapEQ.put(aCompanyPriceOnADate.getCompanyName(), pricesOfCompany);
				}else {
					List<Double>  pricesOfCompany = chartMapMF.get(aCompanyPriceOnADate.getCompanyName());
					if (null == pricesOfCompany){
						pricesOfCompany = new ArrayList<Double>();
					}
					pricesOfCompany.add(aCompanyPriceOnADate.getPrice());
					chartMapMF.put(aCompanyPriceOnADate.getCompanyName(), pricesOfCompany);
				}
				
			}
		}
		chartData[] chartDataArray = new chartData[2];
		Set<String> companyNamesEQ = chartMapEQ.keySet();
		Set<String> companyNamesMF = chartMapMF.keySet();
		for (String companyName : companyNamesEQ){
			ChartDataSets chartDataSets = new ChartDataSets();
			String companyNameSmall = companyName;
			if (companyNameSmall.length() > 35){
				companyNameSmall = companyNameSmall.substring(0,35);
			}
			chartDataSets.setLabel(companyNameSmall);
			List<Double> pricesInPercentage = chartMapEQ.get(companyName);
			Double basePrice = pricesInPercentage.get(0);
			for (int i=0;i<pricesInPercentage.size();i++){
				Double percentIncrease = (pricesInPercentage.get(i) -basePrice)/basePrice*100;
				
				pricesInPercentage.set(i, percentIncrease);
			}
			chartDataSets.setData(pricesInPercentage);
			chartDataSets.setBorderColor(borderColor[colorID]);
			colorID++;
			if (colorID> 14){
                colorID = 0;
            }
			chartData data = chartDataArray[0];
			if (null == data ){
				data = new chartData();
				data.setLabels(dates);
			}
			data.getDatasets().add(chartDataSets);
			chartDataArray[0] = data;
			
		}
		
		for (String companyName : companyNamesMF){
			ChartDataSets chartDataSets = new ChartDataSets();
			String companyNameSmall = companyName;
			if (companyNameSmall.length() > 35){
				companyNameSmall = companyNameSmall.substring(0,35);
			}
			chartDataSets.setLabel(companyNameSmall);
			List<Double> pricesInPercentage = chartMapMF.get(companyName);
			Double basePrice = pricesInPercentage.get(0);
			for (int i=0;i<pricesInPercentage.size();i++){
				Double percentIncrease = (pricesInPercentage.get(i) -basePrice)/basePrice*100;
				pricesInPercentage.set(i, percentIncrease);
			}
			chartDataSets.setData(pricesInPercentage);
			chartDataSets.setBorderColor(borderColor[colorID]);
			colorID++;
			if (colorID> 14){
                colorID = 0;
            }
			chartData data = chartDataArray[1];
			if (null == data ){
				data = new chartData();
				data.setLabels(dates);
			}
			data.getDatasets().add(chartDataSets);
			chartDataArray[1] = data;
		}
		return chartDataArray;
	}
	public static void saveTodaysPortfilioPrice(String collection){
		StockPortfolio portfolioEq = ProfileService.getStockPortfolio(collection);//companyName, ticker, currentPrice 
		Portfolio portfolioMf = ProfileService.getPortfolio(collection); //schemeCode, companyName, lastKnownNav
		List<CurrentPrice> currentMarketList = new ArrayList<CurrentPrice>();
		
		for (StockVO aStock : portfolioEq.getAllStocks() ){
			CurrentPrice currentMarketPrice = new CurrentPrice();
			currentMarketPrice.setCompanyName(aStock.getCompanyName());
			currentMarketPrice.setDate(aStock.getAsOfDate());
			currentMarketPrice.setPrice(aStock.getLastKnownPrice());
			currentMarketPrice.setType("EQ");
			currentMarketList.add(currentMarketPrice);
			
		}
		for (Profile aFund : portfolioMf.getAllProfiles() ){
			CurrentPrice currentMarketPrice = new CurrentPrice();
			currentMarketPrice.setCompanyName(aFund.getSchemeName());
			currentMarketPrice.setDate(aFund.getNavDate());
			currentMarketPrice.setPrice(aFund.getCurrentNav());
			currentMarketPrice.setType("MF");
			currentMarketList.add(currentMarketPrice);
		}
		CurrentPriceVO currentPriceVO = new CurrentPriceVO();
		currentPriceVO.set_id(sdf.format(new Date()).substring(0,2));
		
		currentPriceVO.setCurrentPrices(currentMarketList);
		
		Gson json = new Gson();
		List<CurrentPriceVO > currentPriceVOList = null;
		String currentData = ProfileDAO.getArrayData(Constants.marketpriceDB, collection, false, null, Constants.mlabKey);
		if (null == currentData || "".equals(currentData.trim())) {
			log.info("Creating a collection " + collection);
			ProfileDAO.createNewCollection(collection, Constants.marketpriceDB, Constants.mlabKey);
			currentPriceVOList = new ArrayList<CurrentPriceVO>();
			currentPriceVOList.add(currentPriceVO);
		} else {
			
			currentPriceVOList = json.fromJson(currentData, new TypeToken<List<CurrentPriceVO>>() {}.getType());
			Iterator<CurrentPriceVO> itr = currentPriceVOList.iterator();
			while(itr.hasNext()){
				CurrentPriceVO vo = itr.next();
				if (vo.get_id().equals(currentPriceVO.get_id())){
					itr.remove();
					break;
				}
			}
			currentPriceVOList.add(currentPriceVO);
		}
		currentData = json.toJson(currentPriceVOList, new TypeToken<List<CurrentPriceVO>>() {}.getType());
		ProfileDAO.insertData(collection, currentData, Constants.marketpriceDB,Constants.mlabKey);
		
	}
	public static Portfolio getPortfolio(String collection) {

		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1501383477625 , \"investmentDate\" : \"7-Jun-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.216 , \"investmentAmount\" : 20000.0 , \"units\" : 1233.3497779970398 , \"currentValue\" : 21931.79575727676 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 76.901310434256 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383508987 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.708 , \"investmentAmount\" : 10000.0 , \"units\" : 598.5156811108452 , \"currentValue\" : 10642.985396217382 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : 113.43821019391093 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501643072554 , \"investmentDate\" : \"1-Aug-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 17.9165 , \"investmentAmount\" : 10000.0 , \"units\" : 558.1447269276924 , \"currentValue\" : 9925.096977646304 , \"currentNav\" : 17.7823 , \"lastKnownNav\" : 17.7823 , \"xirr\" : -49.64451913397262 , \"companyName\" : \"Tata Mutual Fund\" , \"companyXirr\" : 77.89734563263954 , \"companyTotalInvestment\" : 40000.0} , { \"profileID\" : 1501383546880 , \"investmentDate\" : \"12-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Banking and Financial Services Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120244\" , \"nav\" : 59.71 , \"investmentAmount\" : 20000.0 , \"units\" : 334.95226930162454 , \"currentValue\" : 21279.51766873221 , \"currentNav\" : 63.53 , \"lastKnownNav\" : 63.53 , \"xirr\" : 52.068377206971164 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 52.068377206971164 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383200382 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"DSP BlackRock Natural Resources and New Energy Fund - Direct Plan - Growth\" , \"schemeCode\" : \"119028\" , \"nav\" : 31.25 , \"investmentAmount\" : 20000.0 , \"units\" : 640.0 , \"currentValue\" : 21260.8 , \"currentNav\" : 33.22 , \"lastKnownNav\" : 33.22 , \"xirr\" : 41.714939715327034 , \"companyName\" : \"DSP BlackRock Mutual Fund\" , \"companyXirr\" : 41.714939715327034 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501379186922 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 131.08 , \"investmentAmount\" : 25000.0 , \"units\" : 190.72322245956667 , \"currentValue\" : 26417.07354287458 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 36.288325205709135 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501382919277 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 136.51 , \"investmentAmount\" : 10000.0 , \"units\" : 73.25470661490002 , \"currentValue\" : 10146.5094132298 , \"currentNav\" : 138.51 , \"lastKnownNav\" : 138.51 , \"xirr\" : 19.358328546622104 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 33.578656053147505 , \"companyTotalInvestment\" : 35000.0} , { \"profileID\" : 1501383156577 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"L&T Infrastructure Fund -Direct Plan-Growth Option\" , \"schemeCode\" : \"119413\" , \"nav\" : 15.79 , \"investmentAmount\" : 25000.0 , \"units\" : 1583.2805573147562 , \"currentValue\" : 26139.962001266627 , \"currentNav\" : 16.51 , \"lastKnownNav\" : 16.51 , \"xirr\" : 28.955743356765133 , \"companyName\" : \"L&T Mutual Fund\" , \"companyXirr\" : 28.955743356765133 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383093995 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Kotak Select Focus Fund - Growth - Direct\" , \"schemeCode\" : \"120166\" , \"nav\" : 32.109 , \"investmentAmount\" : 25000.0 , \"units\" : 778.5979009000591 , \"currentValue\" : 25928.08869787287 , \"currentNav\" : 33.301 , \"lastKnownNav\" : 33.301 , \"xirr\" : 23.107154129383588 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 23.107154129383588 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383259924 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Birla Sun Life Midcap Fund - Growth - Direct Plan\" , \"schemeCode\" : \"119620\" , \"nav\" : 303.95 , \"investmentAmount\" : 25000.0 , \"units\" : 82.25037012666557 , \"currentValue\" : 25880.07896035532 , \"currentNav\" : 314.65 , \"lastKnownNav\" : 314.65 , \"xirr\" : 21.812767042941566 , \"companyName\" : \"Birla Sun Life Mutual Fund\" , \"companyXirr\" : 21.812767042941566 , \"companyTotalInvestment\" : 25000.0} , { \"profileID\" : 1501383350497 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Top 100 Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120596\" , \"nav\" : 312.28 , \"investmentAmount\" : 20000.0 , \"units\" : 64.04508774177022 , \"currentValue\" : 20664.14755988216 , \"currentNav\" : 322.65 , \"lastKnownNav\" : 322.65 , \"xirr\" : 21.588474731024075 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 21.588474731024075 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383395107 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Value Discovery Fund - Direct Plan - Growth\" , \"schemeCode\" : \"120323\" , \"nav\" : 141.1 , \"investmentAmount\" : 20000.0 , \"units\" : 141.74344436569808 , \"currentValue\" : 20412.47342310418 , \"currentNav\" : 144.01 , \"lastKnownNav\" : 144.01 , \"xirr\" : 12.992193185683728 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 12.992193185683728 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383315183 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Multicap Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120599\" , \"nav\" : 265.47 , \"investmentAmount\" : 20000.0 , \"units\" : 75.33807963235016 , \"currentValue\" : 20327.720646400718 , \"currentNav\" : 269.82 , \"lastKnownNav\" : 269.82 , \"xirr\" : 10.213910680254138 , \"companyName\" : \"ICICI Prudential Mutual Fund\" , \"companyXirr\" : 10.213910680254138 , \"companyTotalInvestment\" : 20000.0} , { \"profileID\" : 1501383023193 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"Kotak-Mid-Cap-Growth - Direct\" , \"schemeCode\" : \"120164\" , \"nav\" : 76.772 , \"investmentAmount\" : 25000.0 , \"units\" : 325.6395560881571 , \"currentValue\" : 25317.49856718595 , \"currentNav\" : 77.747 , \"lastKnownNav\" : 77.747 , \"xirr\" : 7.343730747059469 , \"companyName\" : \"Kotak Mahindra Mutual Fund\" , \"companyXirr\" : 7.343730747059469 , \"companyTotalInvestment\" : 25000.0}] , \"totalGain\" : 11273.74861204486 , \"totalXirr\" : 28.854294303825007 , \"totalInvetment\" : 1.375E7} ";
		portfolioStr = ProfileDAO.getUserPortfolio(Constants.dbName, collection, false, null,Constants.mlabKey);
		Gson json = new Gson();
		Portfolio portfolio = json.fromJson(portfolioStr, Portfolio.class);
		try {
			Map<String, CompanyVO> map = NavTextDAO.getCurrentNav();
			Set<String> allHousesNames = map.keySet();
			
			Map<String, NavVO> schemeCodeMap = new HashMap<String, NavVO>();
			for (String houseCode : allHousesNames ){
				CompanyVO companyVo = map.get(houseCode);
				for (NavVO navVo : companyVo.getNavs()) {
					schemeCodeMap.put(navVo.getSchemeCode(), navVo);
				}
				
			}

			for (Profile profile : portfolio.getAllProfiles()) {
				NavVO navVo = schemeCodeMap.get(profile.getSchemeCode());
				
							double currentNav = Double.parseDouble(navVo.getNetAssetValue());
							profile.setNavDate(navVo.getDate());
							
							if (currentNav == 0) {
								currentNav = profile.getLastKnownNav();
							} else {
								profile.setLastKnownNav(currentNav);
							}
							profile.setCurrentNav(currentNav);// Set nav and current value
							double[] payments = new double[2];
							Date[] dates = new Date[2];
							payments[0] = profile.getInvestmentAmount() * -1;
							dates[0] = sdf.parse(profile.getInvestmentDate());
							payments[1] = profile.getCurrentValue();
							
							dates[1] = new Date();
							double xirr = XirrCalculatorService.Newtons_method(0.1, payments, dates);
							profile.setXirr(xirr);
							profile.setPercentGainAbsolute((profile.getCurrentValue() - profile.getInvestmentAmount())/ profile.getInvestmentAmount() *100);
							profile.setPercentGainAnual(profile.getPercentGainAbsolute() *365/daysBetweenDates(dates[0],dates[1]));
							
						
	
			}

			calculateTotalGain(portfolio);
			calculateCompanyGain(portfolio);
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ParseException e) {

			e.printStackTrace();
		}

		Collections.sort(portfolio.getAllProfiles(), new ProfileSort());
		GsonBuilder builder = new GsonBuilder();
		builder.serializeSpecialFloatingPointValues();
		Gson gson = builder.create();
		String data = gson.toJson(portfolio, Portfolio.class);
		ProfileDAO.insertData(collection, data);
		return portfolio;
	}
	
	public static Portfolio getPortfolio_mf_archive(String collection) {
	Portfolio portfolio = new Portfolio();
	String portfolioStr = ProfileDAO.getUserPortfolio(Constants.dbName, collection+Constants.mf_archive, false, null,Constants.mlabKey);
	if (null != portfolioStr && !"".equals(portfolioStr)){
		Gson json = new Gson();
		 portfolio = json.fromJson(portfolioStr, Portfolio.class);
	}
	return portfolio;
	}

	public static List<SipSchemeVO> getSipList(String collection) {
		//collection += "_sip";
		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1501379186922 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 131.08 , \"investmentAmount\" : 25000.0 , \"units\" : 190.72322245956667 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501382919277 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 136.51 , \"investmentAmount\" : 10000.0 , \"units\" : 73.25470661490002 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501383023193 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"Kotak-Mid-Cap-Growth - Direct\" , \"schemeCode\" : \"120164\" , \"nav\" : 76.772 , \"investmentAmount\" : 25000.0 , \"units\" : 325.6395560881571 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Kotak Mahindra Mutual Fund\"} , { \"profileID\" : 1501383093995 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Kotak Select Focus Fund - Growth - Direct\" , \"schemeCode\" : \"120166\" , \"nav\" : 32.109 , \"investmentAmount\" : 25000.0 , \"units\" : 778.5979009000591 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Kotak Mahindra Mutual Fund\"} , { \"profileID\" : 1501383156577 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"L&T Infrastructure Fund -Direct Plan-Growth Option\" , \"schemeCode\" : \"119413\" , \"nav\" : 15.79 , \"investmentAmount\" : 25000.0 , \"units\" : 1583.2805573147562 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501383200382 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"DSP BlackRock Natural Resources and New Energy Fund - Direct Plan - Growth\" , \"schemeCode\" : \"119028\" , \"nav\" : 31.25 , \"investmentAmount\" : 20000.0 , \"units\" : 640.0 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"DSP BlackRock Mutual Fund\"} , { \"profileID\" : 1501383259924 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Birla Sun Life Midcap Fund - Growth - Direct Plan\" , \"schemeCode\" : \"119620\" , \"nav\" : 303.95 , \"investmentAmount\" : 25000.0 , \"units\" : 82.25037012666557 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Birla Sun Life Mutual Fund\"} , { \"profileID\" : 1501383315183 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Multicap Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120599\" , \"nav\" : 265.47 , \"investmentAmount\" : 20000.0 , \"units\" : 75.33807963235016 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383350497 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Top 100 Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120596\" , \"nav\" : 312.28 , \"investmentAmount\" : 20000.0 , \"units\" : 64.04508774177022 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383395107 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Value Discovery Fund - Direct Plan - Growth\" , \"schemeCode\" : \"120323\" , \"nav\" : 141.1 , \"investmentAmount\" : 20000.0 , \"units\" : 141.74344436569808 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383477625 , \"investmentDate\" : \"7-Jun-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.216 , \"investmentAmount\" : 20000.0 , \"units\" : 1233.3497779970398 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Tata Mutual Fund\"} , { \"profileID\" : 1501383508987 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.708 , \"investmentAmount\" : 10000.0 , \"units\" : 598.5156811108452 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Tata Mutual Fund\"} , { \"profileID\" : 1501383546880 , \"investmentDate\" : \"12-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Banking and Financial Services Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120244\" , \"nav\" : 59.71 , \"investmentAmount\" : 20000.0 , \"units\" : 334.95226930162454 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"}]}";
		portfolioStr = ProfileDAO.getArrayData(collection, true, null);
		log.info(" Sip result" + portfolioStr);
		Gson json = new Gson();
		List<SipSchemeVO> portfolio = null;
		if ("".equalsIgnoreCase(portfolioStr.trim())) {
			portfolio = new ArrayList<SipSchemeVO>();
			log.info(" Creating new blank Sip vo" + portfolioStr);
		} else {
			portfolio = json.fromJson(portfolioStr, new TypeToken<List<SipSchemeVO>>() {
			}.getType());
		}

		return portfolio;
	}
	public static List<WishList> getWishList(String collection) {
		//collection += "_sip";
		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1501379186922 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 131.08 , \"investmentAmount\" : 25000.0 , \"units\" : 190.72322245956667 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501382919277 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"L&T Mid Cap Fund-Direct Plan-Growth Plan\" , \"schemeCode\" : \"119807\" , \"nav\" : 136.51 , \"investmentAmount\" : 10000.0 , \"units\" : 73.25470661490002 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501383023193 , \"investmentDate\" : \"1-Jun-2017\" , \"schemeName\" : \"Kotak-Mid-Cap-Growth - Direct\" , \"schemeCode\" : \"120164\" , \"nav\" : 76.772 , \"investmentAmount\" : 25000.0 , \"units\" : 325.6395560881571 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Kotak Mahindra Mutual Fund\"} , { \"profileID\" : 1501383093995 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Kotak Select Focus Fund - Growth - Direct\" , \"schemeCode\" : \"120166\" , \"nav\" : 32.109 , \"investmentAmount\" : 25000.0 , \"units\" : 778.5979009000591 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Kotak Mahindra Mutual Fund\"} , { \"profileID\" : 1501383156577 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"L&T Infrastructure Fund -Direct Plan-Growth Option\" , \"schemeCode\" : \"119413\" , \"nav\" : 15.79 , \"investmentAmount\" : 25000.0 , \"units\" : 1583.2805573147562 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"L&T Mutual Fund\"} , { \"profileID\" : 1501383200382 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"DSP BlackRock Natural Resources and New Energy Fund - Direct Plan - Growth\" , \"schemeCode\" : \"119028\" , \"nav\" : 31.25 , \"investmentAmount\" : 20000.0 , \"units\" : 640.0 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"DSP BlackRock Mutual Fund\"} , { \"profileID\" : 1501383259924 , \"investmentDate\" : \"2-Jun-2017\" , \"schemeName\" : \"Birla Sun Life Midcap Fund - Growth - Direct Plan\" , \"schemeCode\" : \"119620\" , \"nav\" : 303.95 , \"investmentAmount\" : 25000.0 , \"units\" : 82.25037012666557 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Birla Sun Life Mutual Fund\"} , { \"profileID\" : 1501383315183 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Multicap Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120599\" , \"nav\" : 265.47 , \"investmentAmount\" : 20000.0 , \"units\" : 75.33807963235016 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383350497 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Top 100 Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120596\" , \"nav\" : 312.28 , \"investmentAmount\" : 20000.0 , \"units\" : 64.04508774177022 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383395107 , \"investmentDate\" : \"5-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Value Discovery Fund - Direct Plan - Growth\" , \"schemeCode\" : \"120323\" , \"nav\" : 141.1 , \"investmentAmount\" : 20000.0 , \"units\" : 141.74344436569808 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"} , { \"profileID\" : 1501383477625 , \"investmentDate\" : \"7-Jun-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.216 , \"investmentAmount\" : 20000.0 , \"units\" : 1233.3497779970398 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Tata Mutual Fund\"} , { \"profileID\" : 1501383508987 , \"investmentDate\" : \"6-Jul-2017\" , \"schemeName\" : \"Tata Banking And Financial Services Fund-Direct Plan-Growth\" , \"schemeCode\" : \"135793\" , \"nav\" : 16.708 , \"investmentAmount\" : 10000.0 , \"units\" : 598.5156811108452 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Tata Mutual Fund\"} , { \"profileID\" : 1501383546880 , \"investmentDate\" : \"12-Jun-2017\" , \"schemeName\" : \"ICICI Prudential Banking and Financial Services Fund - Direct Plan -  Growth\" , \"schemeCode\" : \"120244\" , \"nav\" : 59.71 , \"investmentAmount\" : 20000.0 , \"units\" : 334.95226930162454 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"ICICI Prudential Mutual Fund\"}]}";
		portfolioStr = ProfileDAO.getArrayData(Constants.stockEquityDB,collection,  false,  null, Constants.mlabKey);
		log.info(" Sip result" + portfolioStr);
		Gson json = new Gson();
		List<WishList> portfolio = null;
		if ("".equalsIgnoreCase(portfolioStr.trim())) {
			portfolio = new ArrayList<WishList>();
			log.info(" Creating new blank Sip vo" + portfolioStr);
		} else {
			portfolio = json.fromJson(portfolioStr, new TypeToken<List<WishList>>() {
			}.getType());
		}

		return portfolio;
	}

	private static void calculateTotalGainForStock(StockPortfolio portfolio) throws ParseException {
		double totalGain = 0;
		Date today = new Date();
		List<Double> payments = new ArrayList<Double>();
		List<Date> dates = new ArrayList<Date>();
		portfolio.setTotalInvetment(0);
		for (StockVO aprofile : portfolio.getAllStocks()) {
			payments.add(aprofile.getInvestmentAmount() * -1);
			dates.add(sdf.parse(aprofile.getPurchaseDateStr()));
			payments.add(aprofile.getCurrentValue());
			dates.add(today);
			totalGain += (aprofile.getCurrentValue() - aprofile.getInvestmentAmount());
			
			portfolio.setTotalInvetment(portfolio.getTotalInvetment() + aprofile.getInvestmentAmount());
		}
		portfolio.setTotalGain(totalGain);
		double[] allPayments = new double[payments.size()];
		Date[] alldates = new Date[dates.size()];
		for (int i = 0; i < allPayments.length; i++) {
			allPayments[i] = payments.get(i);
			alldates[i] = dates.get(i);
		}

		portfolio.setTotalXirr(XirrCalculatorService.Newtons_method(0.1, allPayments, alldates));
		portfolio.setPercentGainAbsolute(totalGain/portfolio.getTotalInvetment()*100);
	}
	private static void calculateTotalGain(Portfolio portfolio) throws ParseException {
		double totalGain = 0;
		Date today = new Date();
		List<Double> payments = new ArrayList<Double>();
		List<Date> dates = new ArrayList<Date>();
		portfolio.setTotalInvetment(0);
		for (Profile aprofile : portfolio.getAllProfiles()) {
			payments.add(aprofile.getInvestmentAmount() * -1);
			dates.add(sdf.parse(aprofile.getInvestmentDate()));
			payments.add(aprofile.getCurrentValue());
			dates.add(today);
			totalGain += (aprofile.getCurrentValue() - aprofile.getInvestmentAmount());
			
			portfolio.setTotalInvetment(portfolio.getTotalInvetment() + aprofile.getInvestmentAmount());
		}
		portfolio.setTotalGain(totalGain);
		double[] allPayments = new double[payments.size()];
		Date[] alldates = new Date[dates.size()];
		for (int i = 0; i < allPayments.length; i++) {
			allPayments[i] = payments.get(i);
			alldates[i] = dates.get(i);
		}

		portfolio.setTotalXirr(XirrCalculatorService.Newtons_method(0.1, allPayments, alldates));
		portfolio.setPercentGainAbsolute(totalGain/portfolio.getTotalInvetment()*100);
	}

	private static void calculateCompanyGain(Portfolio portfolio) throws ParseException {
		Date today = new Date();
		Map<String, List<Profile>> companyProfilesMap = new HashMap<String, List<Profile>>();
		for (Profile aprofile : portfolio.getAllProfiles()) {
			if (null == companyProfilesMap.get(aprofile.getSchemeCode())) {
				List<Profile> compantyProfilesList = new ArrayList<Profile>();
				compantyProfilesList.add(aprofile);
				companyProfilesMap.put(aprofile.getSchemeCode(), compantyProfilesList);
			} else {
				List<Profile> compantyProfilesList = companyProfilesMap.get(aprofile.getSchemeCode());
				compantyProfilesList.add(aprofile);
			}
		}

		for (String schemeCode : companyProfilesMap.keySet()) {
			List<Profile> compantyProfilesList = companyProfilesMap.get(schemeCode);
			List<Double> payments = new ArrayList<Double>();
			List<Date> dates = new ArrayList<Date>();
			double companyTotalInvestment = 0;
			double companyCurrentValue = 0;
			double companyTotalGain = 0;
			for (Profile aprofile : compantyProfilesList) {
				payments.add(aprofile.getInvestmentAmount() * -1);
				dates.add(sdf.parse(aprofile.getInvestmentDate()));
				payments.add(aprofile.getCurrentValue());
				dates.add(today);
				companyTotalInvestment += aprofile.getInvestmentAmount();
				companyTotalGain += aprofile.getAbsoluteGain();
				companyCurrentValue  += aprofile.getCurrentValue();
			}

			double[] allPayments = new double[payments.size()];
			Date[] alldates = new Date[dates.size()];
			for (int i = 0; i < allPayments.length; i++) {
				allPayments[i] = payments.get(i);
				alldates[i] = dates.get(i);
			}

			double xirr = XirrCalculatorService.Newtons_method(0.1, allPayments, alldates);
			for (Profile aprofile : compantyProfilesList) {
				aprofile.setCompanyXirr(xirr);
				aprofile.setCompanyTotalInvestment(companyTotalInvestment);
				aprofile.setCompanyTotalGain(companyTotalGain);
				aprofile.setCompanyCurrentValue(companyCurrentValue);
				aprofile.setCompanyAbsoluteGainPercent(companyTotalGain/companyTotalInvestment *100);
			}
		}

	}
	private static void calculateCompanyGainForStock(StockPortfolio portfolio) throws ParseException {
		Date today = new Date();
		Map<String, List<StockVO>> companyProfilesMap = new HashMap<String, List<StockVO>>();
		for (StockVO aprofile : portfolio.getAllStocks()) {
			String key = aprofile.getExchange()+aprofile.getTicker();
			if (null == companyProfilesMap.get(key)) {
				List<StockVO> compantyProfilesList = new ArrayList<StockVO>();
				compantyProfilesList.add(aprofile);
				companyProfilesMap.put(key, compantyProfilesList);
			} else {
				List<StockVO> compantyProfilesList = companyProfilesMap.get(key);
				compantyProfilesList.add(aprofile);
			}
		}

		for (String schemeCode : companyProfilesMap.keySet()) {
			List<StockVO> compantyProfilesList = companyProfilesMap.get(schemeCode);
			List<Double> payments = new ArrayList<Double>();
			List<Date> dates = new ArrayList<Date>();
			double companyTotalInvestment = 0;
			double companyCurrentValue = 0;
			double companyTotalGain = 0;
			for (StockVO aprofile : compantyProfilesList) {
				payments.add(aprofile.getInvestmentAmount() * -1);
				dates.add(sdf.parse(aprofile.getPurchaseDateStr()));
				payments.add(aprofile.getCurrentValue());
				dates.add(today);
				companyTotalInvestment += aprofile.getInvestmentAmount();
				companyTotalGain += aprofile.getAbsoluteGain();
				companyCurrentValue  += aprofile.getCurrentValue();
			}

			double[] allPayments = new double[payments.size()];
			Date[] alldates = new Date[dates.size()];
			for (int i = 0; i < allPayments.length; i++) {
				allPayments[i] = payments.get(i);
				alldates[i] = dates.get(i);
			}

			double xirr = XirrCalculatorService.Newtons_method(0.1, allPayments, alldates);
			for (StockVO aprofile : compantyProfilesList) {
				aprofile.setCompanyXirr(xirr);
				aprofile.setCompanyTotalInvestment(companyTotalInvestment);
				aprofile.setCompanyTotalGain(companyTotalGain);
				aprofile.setCompanyCurrentValue(companyCurrentValue);
				aprofile.setCompanyAbsoluteGainPercent(companyTotalGain/companyTotalInvestment *100);
			}
		}

	}

}
