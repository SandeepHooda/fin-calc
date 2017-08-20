package com.endpoint;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chart.ChartVO;
import com.chart.ChartVOUI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;

/**
 * Servlet implementation class ChartDataUI
 */
public class ChartDataUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChartDataUI() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String houseCode = (String)request.getParameter("houseCode");
		int schemeCountFrom = Integer.parseInt(request.getParameter("schemeCountFrom"));
		int schemeCountTo = Integer.parseInt(request.getParameter("schemeCountTo"));
		List<ChartVOUI> chartVOUIs = ProfileService.getHouseChartData(houseCode, schemeCountFrom, schemeCountTo);
		
		Gson  json = new Gson();
		String chartDataStr = json.toJson(chartVOUIs, new TypeToken<List<ChartVOUI>>() {}.getType());
		response.addHeader("Cache-Control", "max-age=86400");*/
		response.getWriter().append("Data Not availabe");
		
		
	}

	

}
