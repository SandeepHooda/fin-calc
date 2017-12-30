package com.PriceChart;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
		List<PriceVO> volumeInfoList = new ArrayList<PriceVO>();
		String dbDataJson = null;
		
		Gson  json = new Gson();
		String scripts = ProfileDAO.getADocument("script-list","script-list","myscripts",Constants.mlabKey);
		scripts = scripts.replaceFirst("\\[", "").trim();
		 if (scripts.indexOf("]") >= 0){
			
			 scripts = scripts.substring(0, scripts.length()-1);
		 }
		System.out.println("scripts === "+scripts);
		Scripts  myScripts= json.fromJson(scripts, new TypeToken<Scripts>() {}.getType());
		for (AScript ascript : myScripts.getScripts()){
			dbDataJson =  ProfileDAO.getADocument("nse-tickers-xirr",ascript.getCollection(),ascript.getId(),Constants.mlabKey);
			if (null != dbDataJson) priceVOList.add(polulatePriceVO(dbDataJson,maxDays,fromDate,toDate,ascript.getId(),ascript.getColor(), volumeInfoList));
		}
		
		
		
		
		PriceAndVolume priceAndVolume = new PriceAndVolume();
		priceAndVolume.setPriceVOList(priceVOList);
		priceAndVolume.setVolumeVoList(volumeInfoList);
		response.addHeader("Cache-Control", "max-age=43200");//12 hours
		response.getWriter().append(json.toJson(priceAndVolume, new TypeToken<PriceAndVolume>() {}.getType()));
		
	}

	
	private PriceVO polulatePriceVO(String dbDataJson,int maxDays, Date fromDate, Date toDate,String ticker , String bordercolor, List<PriceVO> volumeInfoList){
		
		Gson  json = new Gson();
		List<DBPriceData>  dbStockPrice= json.fromJson(dbDataJson, new TypeToken<List<DBPriceData>>() {}.getType());	
		PriceVO priceVO = new PriceVO();
		PriceVO volumeVO = new PriceVO();
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
			sdf.setTimeZone(TimeZone.getTimeZone("IST"));
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
		List<DataSets> volumeAndDeliveryDataSets = new ArrayList<DataSets>();
		priceVO.setDatasets(dataSets);
		volumeVO.setDatasets(volumeAndDeliveryDataSets);
		volumeVO.setCompanyName(ticker);
		DataSets dataSet = new DataSets();
		dataSets.add(dataSet);
		dataSet.setLabel(ticker);
		dataSet.setBorderColor(bordercolor);
		dataSet.setBackgroundColor(bordercolor);
		
		DataSets volumeDataSet = new DataSets();
		volumeDataSet.setLabel("Volume");
		volumeDataSet.setBorderColor("#1E88E5");
		volumeDataSet.setBackgroundColor("#42A5F5");
		volumeAndDeliveryDataSets.add(volumeDataSet);
		
		DataSets deliveryDataSet = new DataSets();
		deliveryDataSet.setLabel("Devivery");
		deliveryDataSet.setBorderColor("#7CB342");
		deliveryDataSet.setBackgroundColor("#9CCC65");
		volumeAndDeliveryDataSets.add(deliveryDataSet);
		
		double percentageFactor =-1;
		for (int i=0;i<maxDays && i<dbStockPriceSize;i++){
			double price = stockPriceList.get(dbStockPriceSize-i-1).getPrice();
			if (percentageFactor == -1){
				percentageFactor = price;
				
			}
			String date = ""+stockPriceList.get(dbStockPriceSize-i-1).getDate();
			priceVO.getLabels().add(date.substring(4));
			volumeVO.getLabels().add(date.substring(4));
			dataSet.getData().add((price-percentageFactor)/percentageFactor*100);
			try{
				//if (stockPriceList.get(dbStockPriceSize-i-1).getDeliveryToTradedQuantity()> 0){
					volumeDataSet.getData().add(stockPriceList.get(dbStockPriceSize-i-1).getTotalTradedVolume());
					deliveryDataSet.getData().add(stockPriceList.get(dbStockPriceSize-i-1).getDeliveryToTradedQuantity());
				//}else {
					//volumeDataSet.getData().add(10 + Math.random()*10);
					//deliveryDataSet.getData().add(10+Math.random()*10);
				//}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			//dateSet.getData().add(price);
		}
		volumeInfoList.add(volumeVO);
		return priceVO;
		
	}

}
