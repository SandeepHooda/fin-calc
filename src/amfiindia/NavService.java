package amfiindia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NavService {
	
	public static Map<String, CompanyVO> getNavForDate(String ddMmmyyyy) throws IOException{
		return parseNav(getNavFromAmfiindia(ddMmmyyyy));
	}
	
	private static List<String> getNavFromAmfiindia(String ddMmmyyyy) throws IOException{
		List<String> navData = new ArrayList<String>();
		//http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?frmdt=03-Jul-2017&todt=03-Jul-2017
		URL url = new URL("http://portal.amfiindia.com/DownloadNAVHistoryReport_Po.aspx?frmdt="+ddMmmyyyy+"&todt="+ddMmmyyyy );
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    BufferedReader in = new BufferedReader(    new InputStreamReader(conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			navData.add(inputLine);
		}
		in.close();
		return navData;
   }
	
	public static Map<String, CompanyVO> parseNav(List<String> navData){
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
					currentCompanyVO.getNavs().add(parseRowToCompany(aRow));
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
		
	}
	
	private static NavVO parseRowToCompany(String data){
		NavVO nav = new NavVO();
		//Scheme Code;Scheme Name;Net Asset Value;Repurchase Price;Sale Price;Date
		String[] dataArray = data.split(";");
		nav.setSchemeCode(dataArray[0]);
		nav.setSchemeName(dataArray[1]);
		nav.setNetAssetValue(dataArray[2]);
		nav.setDate(dataArray[5]);
		return nav;
		
	}

}
