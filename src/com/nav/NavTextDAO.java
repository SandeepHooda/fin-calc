package com.nav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.common.FinConstants;
import com.profile.ProfileDAO;


public class NavTextDAO {
	private static final Logger log = Logger.getLogger(NavTextDAO.class.getName());
	private static Map<String, CompanyVO> currentNAV;
	private static Date navDate ;
	
	public static Map<String, CompanyVO> getNavForDate(String ddMmmyyyy) throws IOException{
		return parseNav(getNavFromAmfiindia("http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?frmdt="+ddMmmyyyy+"&todt="+ddMmmyyyy), true);
	}
	public static Map<String, CompanyVO> getCurrentNav() throws IOException{
		if (null == navDate || ((new Date().getTime() - navDate.getTime() ) > FinConstants.aHour  )) {
			log.info("Will fetch current dated nav  ");
			currentNAV = parseNav(getNavFromAmfiindia("http://www.amfiindia.com/spages/NAVAll.txt"), false);
			log.info("Tata NAV"+currentNAV.get("Tata Mutual Fund").getNavs().get(0).getNetAssetValue());
			navDate = new Date();
		}
		return currentNAV;
	}
	
	private static List<String> getNavFromAmfiindia(String endpoint) throws IOException{
		List<String> navData = new ArrayList<String>();
		//http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?frmdt=03-Jul-2017&todt=03-Jul-2017
		URL url = new URL( endpoint);
		
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(1000 *60);
		conn.setReadTimeout(1000 *60);
	    conn.setRequestMethod("GET");
	    BufferedReader in = new BufferedReader(    new InputStreamReader(conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			navData.add(inputLine);
		}
		in.close();
		return navData;
   }
	
	public static Map<String, CompanyVO> parseNav(List<String> navData, boolean historicalNav){
		//List<NavVO> navVOList = new ArrayList<NavVO>();
		String currentCompanyName = null;
		CompanyVO currentCompanyVO = null;
		Map<String, CompanyVO> navMap = new HashMap<String, CompanyVO>();
		navData.remove(0);
		for (String aRow:navData){
			if(aRow.indexOf("Schemes") > 0 && aRow.indexOf("(")  > 0 && aRow.indexOf(")")  > 0 && aRow.indexOf(";")  <0){ 
				//This is a scheme type row 
				//Open Ended Schemes ( Income )
				//Ignore this
				
			}else if (aRow.trim().length() > 0) {
				if (aRow.indexOf(";") > 0){//data row
					currentCompanyVO.getNavs().add(parseRowToNAV(aRow, historicalNav));
					//navVOList.add(parseRowToNAV(aRow));
				}else { //Company Name row
					if (null != currentCompanyName && null != currentCompanyVO){ //A new company name or the first one
						navMap.put(currentCompanyName, currentCompanyVO); //Save data to map for previous company
					}
					
					currentCompanyName = aRow;
					currentCompanyVO = navMap.get(aRow);
					if (null == currentCompanyVO){
						currentCompanyVO = new CompanyVO(currentCompanyName);
						
					}
				}
				
			}
		}
		navMap.put(currentCompanyName, currentCompanyVO); //Save last company data
		return navMap;
		//return navVOList;
		
	}
	
	private static NavVO parseRowToNAV(String data, boolean historicalNav){
		NavVO nav = new NavVO();
		//Scheme Code;Scheme Name;Net Asset Value;Repurchase Price;Sale Price;Date #### Historocal NAV
		//Scheme Code;ISIN Div Payout/ ISIN Growth;ISIN Div Reinvestment;Scheme Name;Net Asset Value;Repurchase Price;Sale Price;Date #### current NAV
		String[] dataArray = data.split(";");
		int dataIndex = 0;
		nav.setSchemeCode(dataArray[dataIndex++]);
		if (!historicalNav){
			dataIndex +=2;
		}
		nav.setSchemeName(applyCorrectionOnName(dataArray[dataIndex++]));
		//nav.setSchemeName(nav.getSchemeName().replaceAll(" ", "_"));
		nav.setNetAssetValue(dataArray[dataIndex]);
		dataIndex +=3;
		nav.setDate(dataArray[dataIndex]);
		return nav;
		
	}
	
	private static String applyCorrectionOnName(String schhemeName){
		schhemeName = schhemeName.replaceAll("&#39;", "'");
		schhemeName = schhemeName.replaceAll("\u0027", "'");
		schhemeName = schhemeName.replaceAll("&gt;", ">");
		schhemeName = schhemeName.replaceAll("\u003e", ">");
		schhemeName = schhemeName.replaceAll("&lt;", "<");
		schhemeName = schhemeName.replaceAll("\u003c", "<");
		schhemeName = schhemeName.replaceAll("&amp;", "&");
		schhemeName = schhemeName.replaceAll("\u0026", "&");
		schhemeName = schhemeName.replaceAll("\u003d", "=");
		schhemeName = schhemeName.replaceAll("-", " ");
		schhemeName = schhemeName.replaceAll("_", " ");
		schhemeName = schhemeName.replaceAll(",", " ");
		schhemeName = schhemeName.trim().replaceAll(" +", " ");
		schhemeName = schhemeName.toUpperCase();
		return schhemeName;
	}

}
