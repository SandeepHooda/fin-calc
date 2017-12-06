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
		String maxDaysStr = request.getParameter("maxDays");
		int maxDays = 30;
		if (null != maxDaysStr){
			maxDays = Integer.parseInt(maxDaysStr);
		}
		List<PriceVO> priceVOList = new ArrayList<PriceVO>();
		String dbDataJson = null;
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr10","ACE",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"ACE", "#4bc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr10","ALPHAGEO",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"ALPHAGEO", "#cbc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr20","ASHOKLEY",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"ASHOKLEY", "#4bc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr20","BHARATFORG",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"BHARATFORG", "#cbc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr20","BRITANNIA",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"BRITANNIA", "#4bc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","CERA",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"CERA", "#cbc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","CONTROLPR",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"CONTROLPR", "#4bc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","DCMSHRIRAM",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"DCMSHRIRAM", "#cbc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","DBL",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"DBL", "#4bc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","FILATEX",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"FILATEX", "#cbc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","FINCABLES",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"FINCABLES", "#4bc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","GAYAPROJ",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"GAYAPROJ", "#cbc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","GILLETTE",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"GILLETTE", "#4bc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr60","MARUTI",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,"MARUTI", "#cbc0c0"));
		
		
		
		 
		Gson  json = new Gson();
		
		response.getWriter().append(json.toJson(priceVOList, new TypeToken<List<PriceVO>>() {}.getType()));
	}

	
	private PriceVO polulatePriceVO(String dbDataJson,int maxDays, String ticker , String bordercolor){
		
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
		dateSet.setBorderColor(bordercolor);
		double percentageFactor =-1;
		for (int i=0;i<maxDays && i<dbStockPriceSize;i++){
			double price = stockPriceList.get(dbStockPriceSize-i-1).getPrice();
			if (percentageFactor == -1){
				percentageFactor = price;
				
			}
			String date = ""+stockPriceList.get(dbStockPriceSize-i-1).getDate();
			priceVO.getLabels().add(date.substring(4));
			dateSet.getData().add((price-percentageFactor)/percentageFactor*100);
			//dateSet.getData().add(price);
		}
		return priceVO;
		
	}

}
