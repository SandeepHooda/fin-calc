package htmlParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import amfiindia.CompanyVO;
import amfiindia.CompanyVOSort;
import amfiindia.NavVO;

public class HTMLParser {

	public static List<CompanyVO> main(String[] args) throws IOException {
	
		Map<String, CompanyVO> navMap = new HashMap<String, CompanyVO>();
		List<NavVO> allnavs = exractAllNavsFromHTML();
		
		if (allnavs != null){
			for (NavVO aNav:allnavs){
				CompanyVO companyVO = navMap.get(aNav.getCompanyName());
				if(null == companyVO){
					companyVO = new CompanyVO(aNav.getCompanyName());
					navMap.put(aNav.getCompanyName(), companyVO);
				}
				companyVO.getNavs().add(aNav);
			}
		}
		
		List<CompanyVO> companyVOList = new ArrayList<CompanyVO>();
		Iterator<String> itr = navMap.keySet().iterator();
		while(itr.hasNext()){
			companyVOList.add(navMap.get(itr.next()));
		}
		Collections.sort(companyVOList, new CompanyVOSort());
		
		return companyVOList;
		
		
	}
	
	private static boolean isAlphaNumeric(String s){
		s = s.replaceAll("&#39;", "");
		s = s.replaceAll("&gt;", "");
		s = s.replaceAll("&amp;", "");
	    String pattern= "^[a-zA-Z0-9-(),/+\\._ \\[\\]/%]*$";
	    return s.matches(pattern);
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
	private static List<NavVO> exractAllNavsFromHTML() throws IOException{
		List<NavVO> allnavs = new ArrayList<NavVO>();
		//Map<String, NavVO> map = new HashMap<String, NavVO>();
		File file = new File("C:\\Users\\shaurya\\Desktop\\NAV.txt");
		BufferedReader reader = new BufferedReader(new FileReader(file));	
		String line = "";
		NavVO nav = null;
		while( (line = reader.readLine() )!= null){
			if (line.indexOf("<td") >=0 || line.indexOf("</table>") >=0 ){
				if (line.indexOf("<td ") >=0){
					if (null !=nav ){
						/*if (!isAlphaNumeric(nav.getSchemeName())){
							System.out.println(nav.getSchemeName()+" <br/>");
						}*/
						allnavs.add(nav);
						//map.put(nav.getSchemeCode(), nav);
					}
					nav = new NavVO();
					String schhemeName = line.substring(line.indexOf(">")+1, line.indexOf("</td>"));
					
					
					schhemeName = applyCorrectionOnName(schhemeName);
					nav.setSchemeName(schhemeName);
					int index = schhemeName.indexOf(" ");
					
					nav.setCompanyName(schhemeName.substring(0, index));
					nav.setSchemeCode(schhemeName); // Temporarly because some securites don't have code 
					
					
				}else if(line.indexOf("<td>INF") >=0 ) {
					nav.setSchemeCode(line.substring(line.indexOf("<td>")+4, line.indexOf("</td>")));
				}else if ((line.indexOf("<td></td>") >=0)){
					
				}else if ((line.indexOf(".") >=0)){
					if (null == nav.getNetAssetValue()){
						nav.setNetAssetValue(line.substring(line.indexOf("<td>")+4, line.indexOf("</td>")));
					}
					
				}
				else if (line.indexOf("</table>") >=0){
					allnavs.add(nav);
					//map.put(nav.getSchemeCode(), nav);
				}else {
					nav.setDate(line.substring(line.indexOf("<td>")+4, line.indexOf("</td>")));
				}
				
				
			}
		}
		reader.close();
		return allnavs;
		
	}
	

}
