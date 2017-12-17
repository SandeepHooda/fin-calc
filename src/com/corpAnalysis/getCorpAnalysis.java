package com.corpAnalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Constants;
import com.chart.ChartVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileDAO;
import com.vo.chart.chartData;

/**
 * Servlet implementation class getCorpAnalysis
 */
public class getCorpAnalysis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getCorpAnalysis() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Gson  json = new Gson();
		 int maxYears = 5;
		 if (request.getParameter("maxYears") != null){
			 maxYears = Integer.parseInt(request.getParameter("maxYears"));
		 }
		String jsonStr = new ProfileDAO(). getArrayData("corp-analysis","corp-analysis", false, null,Constants.mlabKey );
		//List<StockAnalysisVO> corpAnalysis = json.fromJson(jsonStr, new TypeToken<List<StockAnalysisVO>>() {}.getType());
		//calculateYOYPercentage(corpAnalysis,maxYears);
		//jsonStr = json.toJson(corpAnalysis, new TypeToken<List<StockAnalysisVO>>() {}.getType());
		response.addHeader("Cache-Control", "max-age=43200");//12 hours
		response.getWriter().append(jsonStr);
	}

	private void calculateYOYPercentage(List<StockAnalysisVO> corpAnalysis, int maxYears){
		  
		  for (int i=0;i< corpAnalysis.size();i++){
		    if (corpAnalysis.get(i).getPBDITMargin().size() > maxYears){
		    	corpAnalysis.get(i).getPBDITMargin().subList(corpAnalysis.get(i).getPBDITMargin().size()-maxYears,corpAnalysis.get(i).getPBDITMargin().size());
		    	corpAnalysis.get(i).setPBDITMarginYOY(new ArrayList<Double>());
		      for (int j=0; j<corpAnalysis.get(i).getPBDITMargin().size();j++){
		        double baseVal  = corpAnalysis.get(i).getPBDITMargin().get(0);
		        double val = corpAnalysis.get(i).getPBDITMargin().get(j);
		        corpAnalysis.get(i).getPBDITMarginYOY().add((val-baseVal)/baseVal*100);
		      }
		    }
		    
		    if (corpAnalysis.get(i).getPBDITPerShare().size() > maxYears){
		    	corpAnalysis.get(i).getPBDITPerShare().subList(corpAnalysis.get(i).getPBDITPerShare().size()-maxYears,corpAnalysis.get(i).getPBDITPerShare().size());
		    	corpAnalysis.get(i).setPBDITPerShareYOY(new ArrayList<Double>());
		      for (int j=0; j<corpAnalysis.get(i).getPBDITPerShare().size();j++){
		        double baseVal  = corpAnalysis.get(i).getPBDITPerShare().get(0);
		        double val = corpAnalysis.get(i).getPBDITPerShare().get(j);
		        corpAnalysis.get(i).getPBDITPerShareYOY().add((val-baseVal)/baseVal*100);
		      }
		    }
		    if (corpAnalysis.get(i).getRevenueOperationPerShare().size() > maxYears){
		    	corpAnalysis.get(i).getRevenueOperationPerShare().subList(corpAnalysis.get(i).getRevenueOperationPerShare().size()-maxYears,corpAnalysis.get(i).getRevenueOperationPerShare().size());
		    	corpAnalysis.get(i).setRevenueOperationPerShareYOY(new ArrayList<Double>());
		      for (int j=0; i<corpAnalysis.get(i).getRevenueOperationPerShare().size();j++){
		        double baseVal  = corpAnalysis.get(i).getRevenueOperationPerShare().get(0);
		        double val = corpAnalysis.get(i).getRevenueOperationPerShare().get(j);
		        corpAnalysis.get(i).getRevenueOperationPerShareYOY().add((val-baseVal)/baseVal*100);
		      }
		    }
		   
		   
		    
		    
		  }
	}
	
}
