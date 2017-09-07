package com.profile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


import com.Constants;
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.nav.CurrentMarketPrice;
import com.vo.Portfolio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProfileDAO {
	private static final Logger log = Logger.getLogger(ProfileDAO.class.getName());
	private static FetchOptions lFetchOptions = FetchOptions.Builder.doNotValidateCertificate().setDeadline(300d);
	private static URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();

	public static Map<String, CurrentMarketPrice> getCurrentMarkerPrice(List<CurrentMarketPrice> request){
		Map<String , CurrentMarketPrice> markerResponse = new HashMap<String , CurrentMarketPrice>();
		String nseURLOrg = "https://www.google.com/finance/info?q=NSE:";
		String bseURLOrg = "https://www.google.com/finance/info?q=BSE:";
		String nseURL = "https://www.google.com/finance/info?q=NSE:";
		String bseURL = "https://www.google.com/finance/info?q=BSE:";
		String respoNse  = null, respoBse = null;
		boolean hasNSEReq = false;
		boolean hasBSEReq = false;
		 List<CurrentMarketPrice> bseData = null;
		 List<CurrentMarketPrice> nseData = null;
		Gson  json = new Gson();
		for (CurrentMarketPrice ticker: request){
			if ("NSE".equals(ticker.getE())){
				if (hasNSEReq){
					nseURL +=","+ticker.getT();
				}else {
					nseURL +=ticker.getT();
				}
				
				hasNSEReq = true;
			}else {
				if (hasBSEReq){
					bseURL +=","+ticker.getT();
				}else {
					bseURL +=ticker.getT();
				}
				
				hasBSEReq = true;
			}
		}
		if (hasNSEReq){
			try {
				
		        URL url = new URL(nseURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
	            HTTPResponse res = fetcher.fetch(req);
	            respoNse =(new String(res.getContent()));
	            respoNse = respoNse.replaceAll("/", "");
	            nseData = json.fromJson(respoNse, new TypeToken<List<CurrentMarketPrice>>() {}.getType());
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
			
		}
		if(hasBSEReq){
			try {
				
		        URL url = new URL(bseURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
	            HTTPResponse res = fetcher.fetch(req);
	            respoBse =(new String(res.getContent()));
	            respoBse = respoBse.replaceAll("/", "");
	            bseData = json.fromJson(respoBse, new TypeToken<List<CurrentMarketPrice>>() {}.getType());
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
			
		}
		
		
		
		
		
		if (null != nseData){
			for (CurrentMarketPrice nseResp : nseData){
				if ("NSE".equals(nseResp.getE())){
					markerResponse.put(nseResp.getT(), nseResp);
				}else {
					try {
						
				        URL url = new URL(nseURLOrg+nseResp.getT());
			            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
			            HTTPResponse res = fetcher.fetch(req);
			           String respoNseSingle =(new String(res.getContent()));
			           respoNseSingle = respoNseSingle.replaceAll("/", "");
			            List<CurrentMarketPrice> nseDataSingle = json.fromJson(respoNseSingle, new TypeToken<List<CurrentMarketPrice>>() {}.getType());
			            if(null != nseDataSingle && nseDataSingle.size() > 0){
			            	markerResponse.put(nseDataSingle.get(0).getT(), nseDataSingle.get(0));
			            }
			            
			        } catch (IOException e) {
			        	e.printStackTrace();
			        }
				}
			}
		}
		
		if (null != bseData){
			for (CurrentMarketPrice bseResp : bseData){
				if ("BOM".equals(bseResp.getE())){
					markerResponse.put(bseResp.getT(), bseResp);
				}else {
					try {
						
				        URL url = new URL(bseURLOrg+bseResp.getT());
			            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
			            HTTPResponse res = fetcher.fetch(req);
			           String respoBseSingle =(new String(res.getContent()));
			           respoBseSingle = respoBseSingle.replaceAll("/", "");
			            List<CurrentMarketPrice> bseDataSingle = json.fromJson(respoBseSingle, new TypeToken<List<CurrentMarketPrice>>() {}.getType());
			            if(null != bseDataSingle && bseDataSingle.size() > 0){
			            	markerResponse.put(bseDataSingle.get(0).getT(), bseDataSingle.get(0));
			            }
			            
			        } catch (IOException e) {
			        	e.printStackTrace();
			        }
				}
			}
		}
		
		return markerResponse;
	}
	public static String getUserPortfolio(String dbName, String userID, boolean suppressDefaultKey, String datakey, String apiKey ){
	
		String httpsURL = "https://api.mlab.com/api/1/databases/"+dbName+"/collections/"+userID+"?apiKey="+apiKey;
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
	
	public static String getArrayData (String dbName, String collection, boolean suppressDefaultKey, String datakey, String mlabApiKey){
		String httpsURL = "https://api.mlab.com/api/1/databases/"+dbName+"/collections/"+collection+"?apiKey="+mlabApiKey;
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
		
		 
		
		 return respo;
		
	}
	public static String getArrayData(String userID, boolean suppressDefaultKey, String datakey ){
		return getArrayData(Constants.dbName,userID,  suppressDefaultKey,  datakey, Constants.mlabKey);
		
	}
	

	public static void createNewCollection(String collectionToCreate, String dbName, String key){
		
		String httpsURL = "https://api.mlab.com/api/1/databases/"+dbName+"/collections/"+collectionToCreate+"?apiKey="+key;
		
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
	
public static void createNewCollectionWithData(String collectionToCreate, String dbName, String data, String key){
		
		String httpsURL = "https://api.mlab.com/api/1/databases/"+dbName+"/collections/"+collectionToCreate+"?apiKey="+key;
		
		 try {
			
		        URL url = new URL(httpsURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.POST, lFetchOptions);
	            HTTPHeader header = new HTTPHeader("Content-type", "application/json");
	            
	            req.setHeader(header);
	           
	            req.setPayload(data.getBytes());
	            fetcher.fetch(req);
	            
	 
	        } catch (IOException e) {
	        	
	        }
	}
	
	public static void insertData(String collection, String data, String dbName, String apiKey){
		String httpsURL = "https://api.mlab.com/api/1/databases/"+dbName+"/collections/"+collection+"?apiKey="+apiKey;
		
		 try {
			
		        URL url = new URL(httpsURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.PUT, lFetchOptions);
	            HTTPHeader header = new HTTPHeader("Content-type", "application/json");
	            
	            req.setHeader(header);
	           
	            req.setPayload(data.getBytes());
	            fetcher.fetch(req);
	            
	           // log.info("Updated the DB  collection "+collection+data);
	 
	        } catch (IOException e) {
	        	 log.info("Error while  upfdating DB  collection "+collection+" Message "+e.getMessage());
	        	e.printStackTrace();
	        	
	        }
		
	}
	public static void insertData(String collection, String data){
		insertData(collection,data,Constants.dbName,Constants.mlabKey );
	}
	
	
}
