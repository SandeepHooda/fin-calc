package com.endpoint;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.profile.ProfileService;
import com.vo.Portfolio;
import com.vo.StockPortfolio;

/**
 * Servlet implementation class GetStockProfile
 */
public class GetStockProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GetStockProfile.class.getName());   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStockProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		if (null == email){
			email = "sonu.hooda@gmail.com";
		}
		
		StockPortfolio portfolio = ProfileService.getStockPortfolio(email);
		if(null == portfolio){
			portfolio = new StockPortfolio();
		}
		log.info("Total value"+portfolio.getTotalGain());
		log.info("Total XIRR"+portfolio.getTotalXirr());
		GsonBuilder builder = new GsonBuilder();
		builder.serializeSpecialFloatingPointValues();
		 Gson gson =builder	.create();
		String portfolioStr = gson.toJson(portfolio, StockPortfolio.class);
		portfolioStr = portfolioStr.replaceAll("NaN", "0.0");
		portfolioStr = portfolioStr.replaceAll("Infinity", "0.0");
		response.addHeader("Cache-Control", "max-age=900");//15 minutes
		response.getWriter().append(portfolioStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
