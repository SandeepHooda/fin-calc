package com.endpoint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Constants;
import com.profile.ProfileDAO;

/**
 * Servlet implementation class getStocksTrend
 */
public class getStocksTrend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String completeData = null;
		for(int i=10; i<=100;i+=10){
			String data =  ProfileDAO.getArrayData("nse-tickers-xirr","nse-tickers-xirr"+i, false,null,Constants.mlabKey);
			data = data.substring(1);
			data = data.substring(0,data.length()-1);
			if (null != completeData){
				completeData += "," +data;
			}else {
				completeData = data;
			}
			
		}
		response.addHeader("Cache-Control", "max-age=43200");//12 hours
		response.getWriter().append("["+completeData+"]");
	}

	

}
