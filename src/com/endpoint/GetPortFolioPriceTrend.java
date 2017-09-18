package com.endpoint;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;
import com.vo.CurrentPriceVO;

/**
 * Servlet implementation class GetPortFolioPriceTrend
 */
public class GetPortFolioPriceTrend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPortFolioPriceTrend() {
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
		List<CurrentPriceVO> currentPriceVOList = ProfileService.getPortFolioPriceTrend(email);
		Gson  json = new Gson();
		String chartData = json.toJson(currentPriceVOList, new TypeToken<List<CurrentPriceVO>>() {}.getType());
		response.getWriter().append(chartData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
