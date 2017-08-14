package com.chart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.Constants;
import com.common.FinConstants;
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileDAO;







public class ChartDAO implements Runnable {
	private static final Logger log = Logger.getLogger(ChartDAO.class.getName());
	private static FetchOptions lFetchOptions = FetchOptions.Builder.doNotValidateCertificate();
	private static URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	private String   houseCode;
	private int noOfSemesters = 6;
	
	
	private Set<String> schemeCodes;
	private Map<String, ChartVO>  schemCode_ChartVO_MAP = new HashMap<String, ChartVO>();
	
	@Override
	public void run() {
		try {
			getDataOrCreateNewCollection(houseCode, false, null);
			getChartData();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	public Map<String, ChartVO> getResult(){
		return schemCode_ChartVO_MAP;
		
	}
	public ChartDAO (int noOfSemesters, String houseCode){
		
		this.noOfSemesters = noOfSemesters; 
		this.houseCode = houseCode;
	}
	public ChartDAO (int noOfSemesters, String houseCode, Set<String> schemeCodes){
		
		this.noOfSemesters = noOfSemesters; 
		this.houseCode = houseCode;
		this.schemeCodes = schemeCodes;
	}
	public ChartDAO ( String houseCode, Set<String> schemeCodes){
	
		this.houseCode = houseCode;
		this.schemeCodes = schemeCodes;
	}
	public   void getChartData() throws IOException{
		//fromDate = "04-Aug-2014";
		String url = "";
		Calendar toCal = new GregorianCalendar();
		 toCal.add(Calendar.MONTH, ( -6 * (noOfSemesters -1)));
		 Calendar fromCal = new GregorianCalendar();
		 fromCal.add(Calendar.MONTH,( -6 * (noOfSemesters -1) - 6));
		
	    
	   for (int i= 0 ;  i< noOfSemesters ;  i++){
		   String fromDate = sdf.format(fromCal.getTime());
			 String toDate = sdf.format(toCal.getTime());
	    	 url = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf="+houseCode+"&tp=1&frmdt="+fromDate+"&todt="+toDate;
	    	 log.info(url);
	    	 List<String> historicalNavsForAHouse = getNavFromAmfiindia(url);
	    	
	 		
	 		if (null!= historicalNavsForAHouse && historicalNavsForAHouse.size() > 100){
	 			removeHeaders(historicalNavsForAHouse);
		 		
		 		populateChartVOMap(historicalNavsForAHouse);
	 		}
	 		
	 		log.info("Got result for above url");
	    	 
	    	 toCal.add(Calendar.MONTH, 6);
	    	 fromCal.add(Calendar.MONTH, 6);
	    }
	}
	
	public   void getChartDataForACompleteMonth(int triMesters ) throws IOException{
		//fromDate = "04-Aug-2014";
		String url = "";
		Calendar toCal = new GregorianCalendar();
		 toCal.add(Calendar.MONTH,  -9); 
		 Calendar fromCal = new GregorianCalendar();
		 fromCal.add(Calendar.MONTH, -12);
		
	    
	   for (int i= 0 ;  i< triMesters ;  i++){
		   String fromDate = sdf.format(fromCal.getTime());
			 String toDate = sdf.format(toCal.getTime());
	    	 url = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf="+houseCode+"&tp=1&frmdt="+fromDate+"&todt="+toDate;
	    	 log.info(url);
	    	 List<String> historicalNavsForAHouse = getNavFromAmfiindia(url);
	    	
	 		
	 		if (null!= historicalNavsForAHouse && historicalNavsForAHouse.size() > 100){
	 			removeHeaders(historicalNavsForAHouse);
		 		
		 		populateChartVOMap(historicalNavsForAHouse);
	 		}
	 		
	 		log.info("Got result for above url");
	    	 
	    	 toCal.add(Calendar.MONTH, 3);
	    	 fromCal.add(Calendar.MONTH, 3);
	    }
	}
	public   void getChartMonthly( int noOfMonths) throws IOException{
		Calendar toMonth = new GregorianCalendar();
		toMonth.setTime(new Date());
		toMonth.set(Calendar.DATE, 1);
		
		Calendar fromMonthBegin = new GregorianCalendar();
		fromMonthBegin.setTime(new Date());
		fromMonthBegin.set(Calendar.DATE, 1);
		fromMonthBegin.add(Calendar.MONTH, -noOfMonths);
		Calendar fromMonthEnd = new GregorianCalendar();
		fromMonthEnd.setTime(new Date());
		fromMonthEnd.set(Calendar.DATE, 3);
		fromMonthEnd.add(Calendar.MONTH, -noOfMonths);
		
		
		String url = "";
		
		
	    
	   while (fromMonthBegin.before(toMonth)){
		   
		   String fromDate = sdf.format(fromMonthBegin.getTime());
			 String toDate = sdf.format(fromMonthEnd.getTime() );
	    	 url = "http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?mf="+houseCode+"&tp=1&frmdt="+fromDate+"&todt="+toDate;
	    	 log.info(url);
	    	 List<String> historicalNavsForAHouse = getNavFromAmfiindia(url);
	    	
	 		
	 		if (null!= historicalNavsForAHouse && historicalNavsForAHouse.size() > 100){
	 			removeHeaders(historicalNavsForAHouse);
		 		
		 		populateChartVOMap(historicalNavsForAHouse);
	 		}
	 		
	 		log.info("Got result for above url");
	    	 
	    	
	 		fromMonthBegin.add(Calendar.MONTH, 1);
	 		fromMonthEnd.add(Calendar.MONTH, 1);
	    }
	   
		
		
		
		
	}
	public static List<ChartVO> getHouseDataFromMDB(String houseCode){
		houseCode = "_"+houseCode;
		String httpsURL = "https://api.mlab.com/api/1/databases/"+Constants.dbName_mutualFunfs+"/collections/"+houseCode+"?apiKey="+Constants.mlabKey_mutualFunfs;
		
		String data = "";
		 try {
			
		        URL url = new URL(httpsURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
	            HTTPResponse res = fetcher.fetch(req);
	            data =(new String(res.getContent()));
	            
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		
		 Gson  json = new Gson();
		 List<ChartVO> chartVOUIs= json.fromJson(data, new TypeToken<List<ChartVO>>() {}.getType());
			
		 return chartVOUIs;
	}
	private void removeHeaders(List<String> historicalNavsForAHouse ){
		
				historicalNavsForAHouse.remove(0);
				Iterator<String> itr = historicalNavsForAHouse.iterator();
				while(itr.hasNext()){
					String dataLine = itr.next();
					if (dataLine.contains(";")){
						break;
					}else {
						itr.remove();
					}
				}
	}
	private void populateChartVOMap(List<String> historicalNavsForAHouse){
		String schemeCode = "";

		try{
			for (String navLineItem : historicalNavsForAHouse){
				if (navLineItem.indexOf(";") > 0){
					schemeCode = navLineItem.substring(0, navLineItem.indexOf(";"));
					if (schemeCode.matches("\\d+")){
						if ( null == schemeCodes || schemeCodes.contains(schemeCode) ){//ppt has this scheme or we want all schemes
							
							ChartVO chartVO =  schemCode_ChartVO_MAP.get(schemeCode);
							if (null == chartVO) {
								
								chartVO = new ChartVO();
								chartVO.set_id(schemeCode);
								schemCode_ChartVO_MAP.put(schemeCode, chartVO);
							}
							ChartNAV charNav = parseRowToNAV(navLineItem);
							if (null != charNav){
								chartVO.getNavs().add(charNav);
							}
							
						}
					}
					
				}
				
				
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}
	private  List<String> getNavFromAmfiindia(String endpoint) throws IOException{
		List<String> navData = new ArrayList<String>();
		
		
		try{
			URL url = new URL( endpoint);
			
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
			conn.setConnectTimeout(1600000);
			conn.setReadTimeout(1600000);
		    conn.setRequestMethod("GET");
		    BufferedReader in = new BufferedReader(    new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				navData.add(inputLine);
			}
			in.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return navData;
   }
	
	
	private  ChartNAV parseRowToNAV(String dataRow){
		ChartNAV nav = new ChartNAV();
		//Scheme Code;Scheme Name;Net Asset Value;Repurchase Price;Sale Price;Date #### Historocal NAV
		try{
				String[] dataArray = dataRow.split(";");
				nav.setNav(Double.parseDouble(dataArray[2]));
				nav.setDt(dataArray[5]);
				return nav;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
	
	public static boolean isUpdateNeeded(String houseID){
		houseID = "_"+houseID;
			String httpsURL = "https://api.mlab.com/api/1/databases/"+Constants.dbName_mutualFunfs+"/collections/"+houseID+"/"+Constants.timeOfUpdateKey+"?apiKey="+Constants.mlabKey_mutualFunfs;
			
			String respo = "";
			 try {
				
			        URL url = new URL(httpsURL);
		            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
		            HTTPResponse res = fetcher.fetch(req);
		            respo =(new String(res.getContent()));
		            
		        } catch (IOException e) {
		        	respo = e.getMessage();
		        	return true;
		        }
			
			 
			 log.info("is update needed "+respo);
			
			 if (respo.indexOf("Document not found") < 0){
				 Gson  json = new Gson();
				 ChartVO chartVO = json.fromJson(respo,ChartVO.class);
				 Date today = new Date();
				 if ((today.getTime() - chartVO.getTm() ) < (FinConstants.aDay * 15) ){
					 return false;
				 }
			 }
			 
			 
		
		return true;
	}
	
	public static String getDataOrCreateNewCollection(String houseID, boolean suppressDefaultKey, String datakey ){
		houseID = "_"+houseID;
		
		String httpsURL = "https://api.mlab.com/api/1/databases/"+Constants.dbName_mutualFunfs+"/collections/"+houseID+"?apiKey="+Constants.mlabKey_mutualFunfs;
		if (datakey != null && datakey.trim().length() > 0){
			httpsURL += "&f={\""+datakey+"\":1,\"_id\":0}";
		}else{
			if (suppressDefaultKey){
				httpsURL += "&f={\"_id\":0}";
			}
			
		}
		String respo = "";
		 try {
			
		        URL url = new URL(httpsURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
	            HTTPResponse res = fetcher.fetch(req);
	            respo =(new String(res.getContent()));
	            
	        } catch (IOException e) {
	        	respo = e.getMessage();
	        }
		
		 respo = respo.replaceFirst("\\[", "").trim();
		 if (respo.indexOf("]") >= 0){
			
			 respo = respo.substring(0, respo.length()-1);
		 }
		 
		 if(null == respo || "".equals(respo.trim())){
				
				ProfileDAO.createNewCollection(houseID,Constants.dbName_mutualFunfs,Constants.mlabKey_mutualFunfs);
			}
		
		 return respo;
		
		
		
		
	}
	public String getHouseCode() {
		return houseCode;
	}

}
