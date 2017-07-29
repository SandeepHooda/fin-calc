package com.profile;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import com.google.gson.reflect.TypeToken;
import com.nav.CompanyVO;
import com.nav.NavTextDAO;
import com.nav.NavVO;
import com.google.gson.Gson;
import com.vo.Portfolio;
import com.vo.Profile;
import com.xirr.XirrCalculatorService;

public class ProfileService {
	private static final Logger log = Logger.getLogger(ProfileService.class.getName());
	
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
			profile.setProfileID(1);
			pf.getAllProfiles().add(profile);
			log.info("Adding first profile "+currentData);
			
		}else {
			currentData = currentData.trim();
			log.info("currentData ="+currentData+"#");
			pf = json.fromJson(currentData, Portfolio.class);
			
			profile.setProfileID(pf.getAllProfiles().size() +1);
			pf.getAllProfiles().add(profile);
			
		}
		
		
		dataToAdd = json.toJson(pf, new TypeToken<Portfolio>() {}.getType());
		ProfileDAO.insertData(collection, dataToAdd);
	}
	
	public static Portfolio getPortfolio(String collection){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String portfolioStr = "{ \"allProfiles\" : [ { \"profileID\" : 1 , \"investmentDate\" : \"12-Jul-2017\" , \"schemeName\" : \"Axis Children's Gift Fund - Lock in - Direct Dividend\" , \"schemeCode\" : \"135765\" , \"nav\" : 11.9223 , \"investmentAmount\" : 22.0 , \"units\" : 1.8452815312481652 , \"currentValue\" : 0.0 , \"currentNav\" : 0.0 , \"xirr\" : 0.0 , \"companyName\" : \"Axis Mutual Fund\"}]}";
		//portfolioStr = ProfileDAO.getUserPortfolio(collection, true, null);
		Gson  json = new Gson();
		Portfolio portfolio = json.fromJson(portfolioStr, Portfolio.class);
		try {
			Map<String, CompanyVO> map = NavTextDAO.getCurrentNav();
			
			for (Profile profile: portfolio.getAllProfiles()){
				CompanyVO company = map.get(profile.getCompanyName());
				for (NavVO navVo :company.getNavs()){
					if(navVo.getSchemeCode().equals(profile.getSchemeCode())){
						profile.setCurrentNav(Double.parseDouble(navVo.getNetAssetValue()));//Set nav and current value
						double[] payments = new  double[2];
						Date[] dates = new Date[2];
						payments[0] = profile.getInvestmentAmount() *-1;
						dates[0] = sdf.parse( profile.getInvestmentDate());
						payments[1] = profile.getCurrentValue();
						dates[1] = new Date(); 
						double xirr = XirrCalculatorService.Newtons_method(0.1, payments, dates);
						profile.setXirr(xirr);
						break;
					}
				}
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return portfolio;
	}
	

}
