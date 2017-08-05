package com.chart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;







public class ChartDAO implements Runnable {
	private static final Logger log = Logger.getLogger(ChartDAO.class.getName());
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	private String   houseCode;
	private int noOfSemesters = 6;
	
	
	private Set<String> schemeCodes;
	private Map<String, ChartVO>  schemCode_ChartVO_MAP = new HashMap<String, ChartVO>();
	
	@Override
	public void run() {
		try {
			getChartData();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	public Map<String, ChartVO> getResult(){
		return schemCode_ChartVO_MAP;
		
	}
	public ChartDAO (int noOfSemesters, String houseCode, Set<String> schemeCodes){
		
		this.noOfSemesters = noOfSemesters; 
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
	    	 List<String> historicalNavsForAHouse = getNavFromAmfiindia(url);
	    	
	 		log.info(" data url "+url);
	 		if (null!= historicalNavsForAHouse && historicalNavsForAHouse.size() > 100){
	 			removeHeaders(historicalNavsForAHouse);
		 		
		 		populateChartVOMap(historicalNavsForAHouse);
	 		}
	 		
	    	 
	    	 
	    	 toCal.add(Calendar.MONTH, 6);
	    	 fromCal.add(Calendar.MONTH, 6);
	    }
	   
		
		
		
		
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
					if (schemeCodes.contains(schemeCode)){//ppt has this scheme
						
						ChartVO chartVO =  schemCode_ChartVO_MAP.get(schemeCode);
						if (null == chartVO) {
							
							chartVO = new ChartVO();
							chartVO.setSchemeCode(schemeCode);
							schemCode_ChartVO_MAP.put(schemeCode, chartVO);
						}
						chartVO.getChartNAVS().add(parseRowToNAV(navLineItem));
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
		
		String[] dataArray = dataRow.split(";");
		nav.setNav(Double.parseDouble(dataArray[2]));
		try {
			nav.setNavDate(sdf.parse(dataArray[5]));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return nav;
	}

}
