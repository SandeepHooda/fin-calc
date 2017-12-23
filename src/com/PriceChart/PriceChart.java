package com.PriceChart;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		String fromDateStr = request.getParameter("fromDate");
		String toDateStr = request.getParameter("toDate");
		Date fromDate = null, toDate = null;
		if (null != fromDateStr && null != toDateStr){
			fromDate = new Date(Long.parseLong(fromDateStr));
			toDate = new Date(Long.parseLong(toDateStr));
		}
		int maxDays = 30;
		if (null != maxDaysStr){
			maxDays = Integer.parseInt(maxDaysStr);
		}
		List<PriceVO> priceVOList = new ArrayList<PriceVO>();
		String dbDataJson = null;
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr10","ACE",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"ACE", "#000080"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr10","ALPHAGEO",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"ALPHAGEO", "#cbc0c0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr20","APLAPOLLO",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"APLAPOLLO", "#cb5040"));
		
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr20","ASHOKLEY",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"ASHOKLEY", "#800080"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr20","BHARATFORG",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"BHARATFORG", "#FF00FF"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr20","BRITANNIA",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"BRITANNIA", "#00FF00"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","CERA",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"CERA", "#008080"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","CONTROLPR",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"CONTROLPR", "#00FFFF"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","DCMSHRIRAM",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"DCMSHRIRAM", "#0000FF"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","DBL",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"DBL", "#008000"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","FILATEX",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"FILATEX", "#808000"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","FINCABLES",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"FINCABLES", "#FFFF00"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","GAYAPROJ",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"GAYAPROJ", "#FFA500"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","GILLETTE",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"GILLETTE", "#FF0000"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr30","GOACARBON",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"GOACARBON", "#DEB887"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr40","GODREJIND",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"GODREJIND", "#5F9EA0"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr40","HAVELLS",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"HAVELLS", "#D2691E"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr50","HEROMOTOCO",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"HEROMOTOCO", "#FF7F50"));
		
		
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr50","IFBIND",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"IFBIND", "#006400"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr50","ITDCEM",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"ITDCEM", "#9932CC"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr60","MARUTI",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"MARUTI", "#FFD700"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr60","KANSAINER",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"KANSAINER", "#DAA520"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr60","LUMAXIND",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"LUMAXIND", "#CD5C5C"));
		
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr60","MEGH",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"MEGH", "#F0E68C"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr60","MOTHERSUMI",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"MOTHERSUMI", "#E6E6FA"));

		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr70","PGHH",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"PGHH", "#FFF0F5"));
		
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr90","SOMANYCERA",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"SOMANYCERA", "#FFFACD"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr100","TITAN",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"TITAN", "#6B8E23"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr100","VGUARD",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"VGUARD", "#CD853F"));
		 
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr100","VIPIND",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"VIPIND", "#FA8072"));
		
		dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr","nse-tickers-xirr110","WHIRLPOOL",Constants.mlabKey);
		if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,"WHIRLPOOL", "#F5DEB3"));
		
		Gson  json = new Gson();
		
		response.getWriter().append(json.toJson(priceVOList, new TypeToken<List<PriceVO>>() {}.getType()));
	}

	
	private PriceVO polulatePriceVO(String dbDataJson,int maxDays, Date fromDate, Date toDate,String ticker , String bordercolor){
		
		Gson  json = new Gson();
		List<DBPriceData>  dbStockPrice= json.fromJson(dbDataJson, new TypeToken<List<DBPriceData>>() {}.getType());	
		PriceVO priceVO = new PriceVO();
		if (dbStockPrice.size() ==0){
			System.out.println(" some problem "+ticker);
		}
		List<StockPrice> stockPriceList = dbStockPrice.get(0).getStockPriceList();
		int dbStockPriceSize  = stockPriceList.size();
		//Sublist based on max days
		if (dbStockPriceSize > maxDays){
			 
			stockPriceList = stockPriceList.subList(0,maxDays);
			dbStockPriceSize = maxDays;
		}
		
		if (null != fromDate && null != toDate){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int fromDateInt = Integer.parseInt(sdf.format(fromDate));
			int toDateInt = Integer.parseInt(sdf.format(toDate));
			List<StockPrice> stockPriceListTemp = new ArrayList<StockPrice>();
			for (StockPrice aPrice: stockPriceList){
				if (aPrice.getDate() >= fromDateInt &&  aPrice.getDate() <= toDateInt){
					stockPriceListTemp.add(aPrice);
				}
			}
			stockPriceList = stockPriceListTemp;
			dbStockPriceSize = stockPriceListTemp.size();
		}
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
