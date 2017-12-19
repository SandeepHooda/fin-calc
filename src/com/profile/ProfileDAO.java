package com.profile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	final static Pattern nse_pattern = Pattern.compile("\"lastPrice\":\"(.+?)\",");
	
	final static Pattern nse_pattern_previousClose = Pattern.compile("\"previousClose\":\"(.+?)\",");
	final static Pattern nse_pattern_totalTradedVolume = Pattern.compile("\"totalTradedVolume\":\"(.+?)\"}");
	final static Pattern nse_pattern_high52 = Pattern.compile("\"high52\":\"(.+?)\",");
	final static Pattern nse_pattern_low52 = Pattern.compile("\"low52\":\"(.+?)\",");
	final static Pattern nse_pattern_open = Pattern.compile("\"open\":\"(.+?)\",");
	final static Pattern nse_pattern_dayHigh = Pattern.compile("\"dayHigh\":\"(.+?)\",");
	final static Pattern nse_pattern_dayLow = Pattern.compile("\"dayLow\":\"(.+?)\",");
	final static Pattern nse_pattern_deliveryToTradedQuantity = Pattern.compile("\"deliveryToTradedQuantity\":\"(.+?)\",");

	final static Pattern nse_patternDate = Pattern.compile("\"lastUpdateTime\":\"(.+?)\",");
	final static Pattern bse_pattern = Pattern.compile("<td.*>(.+?)</td><td><img");
	
	public static Map<String, CurrentMarketPrice> getCurrentMarkerPrice(List<CurrentMarketPrice> request){
		return getCurrentMarkerPrice(request, false);
	}
	public static Map<String, CurrentMarketPrice> getCurrentMarkerPrice(List<CurrentMarketPrice> request, boolean getDetailValues){
		Map<String , CurrentMarketPrice> markerResponse = new HashMap<String , CurrentMarketPrice>();
		
		String nseURL = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=";
		String nsePostFix = "&illiquid=0&smeFlag=0&itpFlag=0";
		String bseURL = "http://www.bseindia.com/stock-share-price/SiteCache/IrBackupStockReach.aspx?scripcode=";


		 
	
		for (CurrentMarketPrice ticker: request){
			if ("NSE".equals(ticker.getE())){
				try {
					log.info("Getting Quote from NSE "+ticker.getT());
			        URL url = new URL(nseURL+ticker.getT()+nsePostFix);
		            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
		            HTTPResponse res = fetcher.fetch(req);
		            String respoNse =(new String(res.getContent()));
		             Matcher quote = nse_pattern.matcher(respoNse);
					 Matcher quoteDate = nse_patternDate.matcher(respoNse);
					quote.find();
					quoteDate.find();
					CurrentMarketPrice nseQuote = new CurrentMarketPrice();
					nseQuote.setT(ticker.getT());
					nseQuote.setE("NSE");
					nseQuote.setLt_dts(quoteDate.group(1));
					String price = quote.group(1).replaceAll(",", "");
					nseQuote.setL_fix(Double.parseDouble(price));
					markerResponse.put(nseQuote.getT(), nseQuote);
					
					if (getDetailValues){
						//Previous close 
						Matcher previousCloseMatcher = nse_pattern_previousClose.matcher(respoNse);
						previousCloseMatcher.find();
						price = previousCloseMatcher.group(1).replaceAll(",", "");
						nseQuote.setPreviousClose(Double.parseDouble(price));
						
						//nse_pattern_totalTradedVolume
						Matcher nse_pattern_totalTradedVolumeMatcher = nse_pattern_totalTradedVolume.matcher(respoNse);
						nse_pattern_totalTradedVolumeMatcher.find();
						price = nse_pattern_totalTradedVolumeMatcher.group(1).replaceAll(",", "");
						nseQuote.setTotalTradedVolume(Double.parseDouble(price));
						
						//nse_pattern_totalTradedVolume
						Matcher nse_pattern_high52Matcher = nse_pattern_high52.matcher(respoNse);
						nse_pattern_high52Matcher.find();
						price = nse_pattern_high52Matcher.group(1).replaceAll(",", "");
						nseQuote.setHigh52(Double.parseDouble(price));
						
						
						Matcher nse_pattern_low52Matcher = nse_pattern_low52.matcher(respoNse);
						nse_pattern_low52Matcher.find();
						price = nse_pattern_low52Matcher.group(1).replaceAll(",", "");
						nseQuote.setLow52(Double.parseDouble(price));
						
						Matcher nse_pattern_openMatcher = nse_pattern_open.matcher(respoNse);
						nse_pattern_openMatcher.find();
						price = nse_pattern_openMatcher.group(1).replaceAll(",", "");
						nseQuote.setOpen(Double.parseDouble(price));
						
						Matcher nse_pattern_dayHighMatcher = nse_pattern_dayHigh.matcher(respoNse);
						nse_pattern_dayHighMatcher.find();
						price = nse_pattern_dayHighMatcher.group(1).replaceAll(",", "");
						nseQuote.setDayHigh(Double.parseDouble(price));
						
						Matcher nse_pattern_dayLowMatcher = nse_pattern_dayLow.matcher(respoNse);
						nse_pattern_dayLowMatcher.find();
						price = nse_pattern_dayLowMatcher.group(1).replaceAll(",", "");
						nseQuote.setDayLow(Double.parseDouble(price));
						
						Matcher nse_pattern_deliveryToTradedQuantityMatcher = nse_pattern_deliveryToTradedQuantity.matcher(respoNse);
						nse_pattern_deliveryToTradedQuantityMatcher.find();
						price = nse_pattern_deliveryToTradedQuantityMatcher.group(1).replaceAll(",", "");
						nseQuote.setDeliveryToTradedQuantity(Double.parseDouble(price));
					}
					
					
					
					log.info("Quote from NSE "+nseQuote.getT() +" = "+nseQuote.getL_fix());
		        } catch (Exception e) {
		        	log.warning("Error while getting quote from NSE "+nseURL+ticker.getT()+nsePostFix+" "+e.getMessage());
		        	e.printStackTrace();
		        }
			}else {
					try {
					log.info("Getting Quote from BSE "+ticker.getT());
			        URL url = new URL(bseURL+ticker.getT());
		            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
		            HTTPResponse res = fetcher.fetch(req);
		            String respoBse =(new String(res.getContent()));
		            final Matcher quote = bse_pattern.matcher(respoBse);
					quote.find();
					CurrentMarketPrice bseQuote = new CurrentMarketPrice();
					bseQuote.setT(ticker.getT());
					bseQuote.setE("BSE");
					bseQuote.setLt_dts(ProfileService.stockQuoteDateTime.format(new Date()));
					String price = quote.group(1).replaceAll(",", "");
					bseQuote.setL_fix(Double.parseDouble(price));
					markerResponse.put(bseQuote.getT(), bseQuote);
					log.info("Quote from BSE "+bseQuote.getT() +" = "+bseQuote.getL_fix());
		        } catch (Exception e) {
		        	log.warning("Error while getting quote from BSE "+bseURL+ticker.getT() +" "+e.getMessage());
		        	e.printStackTrace();
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
	public static String getADocument(String dbName, String collection,  String documentKey,String mlabApiKey){
		String httpsURL = "https://api.mlab.com/api/1/databases/"+dbName+"/collections/"+collection+"?apiKey="+mlabApiKey+"&q=%7B%22_id%22:%22"+documentKey+"%22%7D";
		
		String data = "";
		 try {
			
		        URL url = new URL(httpsURL);
	            HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
	            HTTPResponse res = fetcher.fetch(req);
	            data =(new String(res.getContent()));
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	
	        	data = "[ { \"_id\" : \"ALCHEM\" , \"xirr5\" : 0.0 , \"xirr10\" : 0.0 , \"xirr30\" : 0.0 , \"xirr182\" : 0.0 , \"xirr365\" : 0.0 , \"currentMarketPrice\" : 14.7 , \"stockPriceList\" : [ { \"price\" : 14.7 , \"date\" : 20171205} , { \"price\" : 15.3 , \"date\" : 20171204} , { \"price\" : 15.3 , \"date\" : 20171202}]} ]";
	        	data = data.replace("ALCHEM", documentKey);
	        	return null;
	        }
		
		 
		
		 return data;
		
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
