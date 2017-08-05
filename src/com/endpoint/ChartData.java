package com.endpoint;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chart.ChartVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;

import com.vo.Portfolio;

/**
 * Servlet implementation class ChartData
 */
public class ChartData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChartData() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		int noOfSemesters = Integer.parseInt(request.getParameter("noOfSemesters"));
		//email = "sonu.hooda@gmail.com";
		Portfolio portfolio = ProfileService.getPortfolioDromDB(email);
		List<ChartVO> chartData = ProfileService.getHistoricalData(portfolio, noOfSemesters);
		ProfileService.calculatePercentage(chartData,  portfolio);
		Gson  json = new Gson();
		String chartDataStr = json.toJson(chartData, new TypeToken<List<ChartVO>>() {}.getType());
		response.getWriter().append(chartDataStr);
		
	}

	

}
