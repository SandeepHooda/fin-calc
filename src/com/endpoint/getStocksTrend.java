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
		
		response.getWriter().append(ProfileDAO.getArrayData("nse-tickers-xirr","nse-tickers-xirr", false,null,Constants.mlabKey));
	}

	

}
