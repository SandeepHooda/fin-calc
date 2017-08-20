package com.endpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chart.ChartVO;
import com.chart.ChartVOUI;
import com.chart.ChartVOUIComparator;
import com.common.FinConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;

/**
 * Servlet implementation class TopFundOfAllHouses
 */
public class TopFundOfAllHouses extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int schemeCountFrom = Integer.parseInt(request.getParameter("schemeCountFrom"));
		int schemeCountTo = Integer.parseInt(request.getParameter("schemeCountTo"));
		List<ChartVO> chartVOs = ProfileService.getAllHouseTopPerformers();
		
		Gson  json = new Gson();
		String chartDataStr = json.toJson(chartVOs, new TypeToken<List<ChartVO>>() {}.getType());
		response.addHeader("Cache-Control", "max-age=86400");
		response.getWriter().append(chartDataStr);
	}

	
}
