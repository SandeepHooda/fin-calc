package com.profile;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;


import com.Constants;
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class ProfileDAO {
	private static final Logger log = Logger.getLogger(ProfileDAO.class.getName());
	private static FetchOptions lFetchOptions = FetchOptions.Builder.doNotValidateCertificate();
	private static URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();

	public static String getUserPortfolio(String userID, boolean suppressDefaultKey, String datakey ){
	
		String httpsURL = "https://api.mlab.com/api/1/databases/"+Constants.dbName+"/collections/"+userID+"?apiKey="+Constants.mlabKey;
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
		
		 return respo;
		
		
		
		
	}
	
	
	
	public static void createNewCollection(String collectionToCreate){
		
		String httpsURL = "https://api.mlab.com/api/1/databases/"+Constants.dbName+"/collections/"+collectionToCreate+"?apiKey="+Constants.mlabKey;
		
		 try {
			
		        URL url = new URL(httpsURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.POST, lFetchOptions);
	            HTTPHeader header = new HTTPHeader("Content-type", "application/json");
	            
	            req.setHeader(header);
	           
	            req.setPayload("{ \"_id\" : 1 }".getBytes());
	            fetcher.fetch(req);
	            
	 
	        } catch (IOException e) {
	        	
	        }
	}
	
	public static void insertData(String collection, String data){
		String httpsURL = "https://api.mlab.com/api/1/databases/"+Constants.dbName+"/collections/"+collection+"?apiKey="+Constants.mlabKey;
		
		 try {
			
		        URL url = new URL(httpsURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.PUT, lFetchOptions);
	            HTTPHeader header = new HTTPHeader("Content-type", "application/json");
	            
	            req.setHeader(header);
	           
	            req.setPayload(data.getBytes());
	            fetcher.fetch(req);
	            
	            log.info("Updated the DB  collection "+collection+data);
	 
	        } catch (IOException e) {
	        	 log.info("Error while  upfdating DB  collection "+collection+data+" Message "+e.getMessage());
	        	e.printStackTrace();
	        }
	}
	
	
}
