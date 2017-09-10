package com.endpoint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.profile.ProfileService;
import com.vo.StockPortfolio;

/**
 * Servlet implementation class GetStockProfile_eq_archive
 */
public class GetStockProfile_eq_archive extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStockProfile_eq_archive() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String email = (String)request.getSession().getAttribute("email");
		
		
		StockPortfolio portfolio = ProfileService.getStockPortfolio_eq_archive(email);
		if(null == portfolio){
			portfolio = new StockPortfolio();
		}
		
		GsonBuilder builder = new GsonBuilder();
		builder.serializeSpecialFloatingPointValues();
		 Gson gson =builder	.create();
		String portfolioStr = gson.toJson(portfolio, StockPortfolio.class);
		portfolioStr = portfolioStr.replaceAll("NaN", "0.0");
		
		response.getWriter().append(portfolioStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
