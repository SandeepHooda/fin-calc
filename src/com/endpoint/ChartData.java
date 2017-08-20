package com.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chart.ChartVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileDAO;
import com.profile.ProfileService;

import com.vo.Portfolio;

/**
 * Servlet implementation class ChartData
 */
public class ChartData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ProfileDAO.class.getName());  
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
		/*String email = (String)request.getSession().getAttribute("email");
		int noOfSemesters = 0;
		if (null != request.getParameter("noOfSemesters")){
			noOfSemesters = Integer.parseInt(request.getParameter("noOfSemesters"));
		}
		int noOfMonths = 0;
		if (null != request.getParameter("noOfMonths")){
			noOfMonths = Integer.parseInt(request.getParameter("noOfMonths"));
		}
		
		
		
		log.info(" Trying for noOfSemesters "+noOfSemesters+" noOfMonths ="+noOfMonths );
		if (noOfSemesters > 0){
			ProfileService.getAllHistoricalData( noOfSemesters);
		}
		
		
		if (noOfMonths > 0){
			ProfileService.getAllHistoricalMonthlyData( false);
		}
		
		
		noOfSemesters -=2;
		
	
		
		while(noOfSemesters > 0){//When some recors failed due to data size
			log.info(" Fail re-trying for noOfSemesters "+noOfSemesters );
			ProfileService.getAllHistoricalData( noOfSemesters);
			noOfSemesters -=2;
			
		}*/
		
		response.getWriter().append("Data not available");
		
	}

	

}
