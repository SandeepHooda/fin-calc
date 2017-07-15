package com.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.endpoint.xirr;
import com.google.gson.Gson;


/**
 * Servlet implementation class oauth2callback
 */
public class oauth2callback extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(xirr.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public oauth2callback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("oAuth called ");
		String code = request.getParameter("code");
		if ("signin".equalsIgnoreCase(request.getParameter("request"))){
			getAuthCode(request, response);
		}else if (null != code) {
			String accessToken = getAccesstoken(request, response, code);
			String email = getUserEmail(accessToken);
			addCookie(email,response );
			response.sendRedirect("web/index.html");
		}
		
	}
	
	private void addCookie(String email , HttpServletResponse res){
		Cookie cookie = new Cookie("email",email);
	      cookie.setMaxAge(60*60*24); 
	      res.addCookie(cookie);
	}
	private void getAuthCode(HttpServletRequest request, HttpServletResponse response){
		//Client id + redirect url + scope + response type
	
			String redirectUrl = "https://accounts.google.com/o/oauth2/auth?response_type=code&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile&client_id=876159984834-eraakoprdjn2qnld6vl7jknoc17rhece.apps.googleusercontent.com&redirect_uri=http%3A%2F%2Ffin-cal.appspot.com%2Foauth2callback";
			try {
				response.sendRedirect(redirectUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	private String getAccesstoken(HttpServletRequest request, HttpServletResponse res, String code) throws IOException{
		log.info("Got auth code , now try to get access token  "+code);
		//String client_id = "876159984834-eraakoprdjn2qnld6vl7jknoc17rhece.apps.googleusercontent.com";
		//String client_secret = "jRwLo8wBoVLZnfBMLKRWFcYS";
		String urlParameters  = "grant_type=authorization_code&client_id=876159984834-eraakoprdjn2qnld6vl7jknoc17rhece.apps.googleusercontent.com&client_secret=jRwLo8wBoVLZnfBMLKRWFcYS&redirect_uri=http%3A%2F%2Ffin-cal.appspot.com%2Foauth2callback&code="+code;
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		
		
	    URL url = new URL("https://accounts.google.com/o/oauth2/token" );
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded");
	    conn.setRequestProperty( "charset", "utf-8");
	    conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
	    conn.setUseCaches( false );
	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	       
	    DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
	    wr.write( postData );
	        	
	  
	    
	    int respCode = conn.getResponseCode();  // New items get NOT_FOUND on PUT
	    log.info("respCode "+respCode);
	    if (respCode == HttpURLConnection.HTTP_OK || respCode == HttpURLConnection.HTTP_NOT_FOUND || true) {
	    	request.setAttribute("error", "");
	      StringBuffer response = new StringBuffer();
	      String line;

	      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      while ((line = reader.readLine()) != null) {
	        response.append(line);
	      }
	      reader.close();
	      log.info("Got access_token response  "+response.toString());
	      Gson gson = new Gson(); 
	      String json = response.toString();
	      Map<String,Object> map = new HashMap<String,Object>();
	      map = (Map<String,Object>) gson.fromJson(json, map.getClass());
	      
	      log.info("Extract access token "+map.get("access_token"));
	     return (String)map.get("access_token");
	    }
	     return null;
	}

	private String getUserEmail(String accessToken) throws IOException{
		log.info("Will get getUserEmail for access token "+accessToken);
		URL url = new URL("https://people.googleapis.com/v1/people/me?personFields=emailAddresses,names" );
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Authorization",  "Bearer "+accessToken);
	    
	    BufferedReader in = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		Gson gson = new Gson(); 
	      
	      Map<String,Object> map = new HashMap<String,Object>();
	      map = (Map<String,Object>) gson.fromJson(response.toString(), map.getClass());
	      
	     Map emailMap = (Map) ((List<Object>)map.get("emailAddresses")).get(0);
	     
		log.info("response  "+emailMap.get("value"));
		return (String)emailMap.get("value");

	}
	 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
