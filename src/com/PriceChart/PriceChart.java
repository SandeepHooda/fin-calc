package com.PriceChart;

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
 * Servlet implementation class PriceChart
 */
public class PriceChart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PriceChart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<PriceVO> priceVOList = new ArrayList<PriceVO>();
		String[] tickers = {"ALCHEM"};
		for (String ticker : tickers){
			String dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr10",ticker,Constants.mlabKey);
			priceVOList.add(polulatePriceVO(dbDataJson,ticker));
		}
		
		 
		Gson  json = new Gson();
		
		response.getWriter().append(json.toJson(priceVOList, new TypeToken<List<PriceVO>>() {}.getType()));
	}

	
	private PriceVO polulatePriceVO(String dbDataJson, String ticker){
		int maxDays = 30;
		Gson  json = new Gson();
		List<DBPriceData>  dbStockPrice= json.fromJson(dbDataJson, new TypeToken<List<DBPriceData>>() {}.getType());	
		PriceVO priceVO = new PriceVO();
		
		List<StockPrice> stockPriceList = dbStockPrice.get(0).getStockPriceList();
		int dbStockPriceSize  = stockPriceList.size();
		List<DataSets> dataSets = new ArrayList<DataSets>();
		priceVO.setDatasets(dataSets);
		DataSets dateSet = new DataSets();
		dataSets.add(dateSet);
		dateSet.setLabel(ticker);
		for (int i=0;i<maxDays && i<dbStockPriceSize;i++){
			String date = ""+stockPriceList.get(dbStockPriceSize-i-1).getDate();
			priceVO.getLabels().add(date);
			dateSet.getData().add(stockPriceList.get(dbStockPriceSize-i-1).getPrice());
		}
		return priceVO;
		
	}

}
