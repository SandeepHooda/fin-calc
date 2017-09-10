package com.endpoint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.profile.ProfileService;
import com.vo.Portfolio;

/**
 * Servlet implementation class GetProfiles_mf_archive
 */
public class GetProfiles_mf_archive extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProfiles_mf_archive() {
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
		Portfolio portfolio = ProfileService.getPortfolio_mf_archive(email);
		GsonBuilder builder = new GsonBuilder();
		builder.serializeSpecialFloatingPointValues();
		 Gson gson =builder	.create();
		String portfolioStr = gson.toJson(portfolio, Portfolio.class);
		portfolioStr = portfolioStr.replaceAll("NaN", "0.0");
		response.getWriter().append(portfolioStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
