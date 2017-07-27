package com.profile;


import java.util.logging.Logger;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.vo.Portfolio;
import com.vo.Profile;

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
	
	public static String getPortfolio(String collection){
		return ProfileDAO.getUserPortfolio(collection, true, null);
	}
	

}
