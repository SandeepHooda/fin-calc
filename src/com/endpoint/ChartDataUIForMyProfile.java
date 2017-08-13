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
 * Servlet implementation class ChartDataUIForMyProfile
 */
public class ChartDataUIForMyProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChartDataUIForMyProfile() {
        super();
   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		//email = "sonu.hooda@gmail.com";		
		Portfolio myPortFolio = ProfileService.getPortfolioDromDB(email);
		
		List<ChartVO> chartVO = ProfileService.getHistoricalDataForMyProfile(myPortFolio, 6);
		
		Gson  json = new Gson();
		String chartDataStr = json.toJson(chartVO, new TypeToken<List<ChartVO>>() {}.getType());
		response.addHeader("Cache-Control", "max-age=86400");
		response.getWriter().append(chartDataStr);
	}

	
}
