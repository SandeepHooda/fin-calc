package com.endpoint;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.profile.ProfileService;
import com.vo.Portfolio;

/**
 * Servlet implementation class GetProfiles
 */
public class GetProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GetProfiles.class.getName());   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProfiles() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		email = "sonu.hooda@gmail.com";
		Portfolio portfolio = ProfileService.getPortfolio(email);
		log.info("Total value"+portfolio.getTotalGain());
		log.info("Total XIRR"+portfolio.getTotalXirr());
		Gson  json = new Gson();
		String portfolioStr = json.toJson(portfolio, Portfolio.class);
		response.getWriter().append(portfolioStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
