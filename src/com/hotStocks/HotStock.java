package com.hotStocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Constants;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileDAO;


/**
 * Servlet implementation class HotStock
 */
public class HotStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotStock() {
        super();
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
			String completeData =  ProfileDAO.getArrayData("hotstocks","hotstocks", false,null,Constants.mlabKey);
			
			Gson  json = new Gson();
			List<HotStockVO>  hotStocks= json.fromJson(completeData, new TypeToken<List<HotStockVO>>() {}.getType());	
		    List<HotStockDailyVO> hotstockDailyList = new ArrayList<HotStockDailyVO>();
		    List<HotStockDailyVO> hotstockDailyListReverse = new ArrayList<HotStockDailyVO>();
		    for (HotStockVO hotStock: hotStocks){
		    	for (HotStockDailyVO hotStockDaily: hotStock.getHotStocks()){
		    		hotStockDaily.setDateStr(hotStock.get_id());
		    		hotstockDailyList.add(hotStockDaily);
		    	}
		    }
		    for (int i=hotstockDailyList.size() -1;i>=0;i--){
		    	hotstockDailyListReverse.add(hotstockDailyList.get(i));
		    }
		    completeData = json.toJson(hotstockDailyListReverse, new TypeToken<List<HotStockDailyVO>>() {}.getType());
		response.addHeader("Cache-Control", "max-age=43200");//12 hours
		response.getWriter().append(completeData);
	}

	
}
