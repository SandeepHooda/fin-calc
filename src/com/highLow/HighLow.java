package com.highLow;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Constants;
import com.PriceChart.AScript;
import com.PriceChart.Scripts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nav.CurrentMarketPrice;
import com.nav.CurrentMarketPriceComparator;
import com.profile.ProfileDAO;

/**
 * Servlet implementation class HighLow
 */
public class HighLow extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DecimalFormat df2 = new DecimalFormat(".##");
	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HighLow() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String format = request.getParameter("format");
		List<CurrentMarketPrice> marketRequest = marketRequest = getTickerList();
		Map<String, CurrentMarketPrice> map =  null;
		if(!"static".equalsIgnoreCase(format)){
			map = ProfileDAO.getCurrentMarkerPrice(marketRequest,true);
		}
		 
		
		if("static".equalsIgnoreCase(format)){
			response.getWriter().append(staticResponse());
		}else {
			List<CurrentMarketPrice> list = new ArrayList<CurrentMarketPrice>();
			Set<String> scripts = map.keySet();
			for (String ticker : scripts){
				CurrentMarketPrice mp = map.get(ticker);
				mp.setHigh52Chg((mp.getL_fix() - mp.getHigh52())/mp.getHigh52()*100);
				list.add(mp);
			}
			Collections.sort(list,new CurrentMarketPriceComparator());
			if ("json".equalsIgnoreCase(format)){
				Gson  json = new Gson();
				String jsonData = json.toJson(list, new TypeToken<List<CurrentMarketPrice>>() {}.getType());
				response.getWriter().append(jsonData);
			}
			else {
				response.getWriter().append(buildHtml(list));
				
			}
		}
		
		
		
		
	}
	
	private String staticResponse(){
		String data= "[{\"t\":\"TITAN\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":871.0,\"previousClose\":840.1,\"totalTradedVolume\":3308280.0,\"high52\":871.0,\"low52\":307.1,\"open\":845.0,\"dayHigh\":871.0,\"dayLow\":843.1,\"deliveryToTradedQuantity\":32.01},{\"t\":\"GILLETTE\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":6845.0,\"previousClose\":6778.85,\"totalTradedVolume\":4656.0,\"high52\":7100.0,\"low52\":4022.2,\"open\":6778.9,\"dayHigh\":6950.15,\"dayLow\":6778.9,\"deliveryToTradedQuantity\":53.89},{\"t\":\"BRITANNIA\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":4770.0,\"previousClose\":4699.5,\"totalTradedVolume\":274186.0,\"high52\":4963.9,\"low52\":2772.85,\"open\":4715.0,\"dayHigh\":4779.45,\"dayLow\":4678.3,\"deliveryToTradedQuantity\":78.71},{\"t\":\"GAYAPROJ\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":211.1,\"previousClose\":212.85,\"totalTradedVolume\":57990.0,\"high52\":220.95,\"low52\":120.23,\"open\":212.85,\"dayHigh\":215.0,\"dayLow\":210.05,\"deliveryToTradedQuantity\":46.79},{\"t\":\"DBL\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":927.0,\"previousClose\":900.05,\"totalTradedVolume\":234039.0,\"high52\":1008.95,\"low52\":216.55,\"open\":910.0,\"dayHigh\":929.0,\"dayLow\":901.1,\"deliveryToTradedQuantity\":40.31},{\"t\":\"VGUARD\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":232.5,\"previousClose\":229.9,\"totalTradedVolume\":1299958.0,\"high52\":240.65,\"low52\":108.64,\"open\":230.3,\"dayHigh\":234.7,\"dayLow\":229.35,\"deliveryToTradedQuantity\":44.85},{\"t\":\"MARUTI\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":9846.0,\"previousClose\":9309.1,\"totalTradedVolume\":1275759.0,\"high52\":9858.0,\"low52\":5041.0,\"open\":9345.0,\"dayHigh\":9858.0,\"dayLow\":9326.0,\"deliveryToTradedQuantity\":28.96},{\"t\":\"BHARATFORG\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":728.05,\"previousClose\":706.35,\"totalTradedVolume\":1427071.0,\"high52\":749.95,\"low52\":435.15,\"open\":701.25,\"dayHigh\":729.9,\"dayLow\":701.25,\"deliveryToTradedQuantity\":35.13},{\"t\":\"CONTROLPR\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":482.9,\"previousClose\":470.95,\"totalTradedVolume\":13746.0,\"high52\":530.0,\"low52\":228.0,\"open\":474.0,\"dayHigh\":485.9,\"dayLow\":462.25,\"deliveryToTradedQuantity\":45.07},{\"t\":\"HAVELLS\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":553.05,\"previousClose\":551.25,\"totalTradedVolume\":838889.0,\"high52\":564.6,\"low52\":310.55,\"open\":551.6,\"dayHigh\":555.35,\"dayLow\":549.2,\"deliveryToTradedQuantity\":33.09},{\"t\":\"FINCABLES\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":712.05,\"previousClose\":688.25,\"totalTradedVolume\":536547.0,\"high52\":723.8,\"low52\":400.0,\"open\":694.0,\"dayHigh\":717.0,\"dayLow\":689.0,\"deliveryToTradedQuantity\":9.61},{\"t\":\"MOTHERSUMI\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":393.3,\"previousClose\":379.1,\"totalTradedVolume\":6586870.0,\"high52\":395.0,\"low52\":201.6,\"open\":380.65,\"dayHigh\":395.0,\"dayLow\":380.6,\"deliveryToTradedQuantity\":44.04},{\"t\":\"CERA\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":3790.0,\"previousClose\":3703.7,\"totalTradedVolume\":4934.0,\"high52\":3864.0,\"low52\":1880.45,\"open\":3724.0,\"dayHigh\":3817.95,\"dayLow\":3696.1,\"deliveryToTradedQuantity\":60.54},{\"t\":\"FILATEX\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":222.0,\"previousClose\":220.5,\"totalTradedVolume\":66220.0,\"high52\":238.0,\"low52\":62.0,\"open\":220.95,\"dayHigh\":223.95,\"dayLow\":219.0,\"deliveryToTradedQuantity\":61.03},{\"t\":\"PGHH\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":9425.0,\"previousClose\":9407.65,\"totalTradedVolume\":9671.0,\"high52\":9900.0,\"low52\":6500.0,\"open\":9482.45,\"dayHigh\":9485.05,\"dayLow\":9400.0,\"deliveryToTradedQuantity\":89.99},{\"t\":\"SOMANYCERA\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":913.0,\"previousClose\":876.35,\"totalTradedVolume\":28097.0,\"high52\":959.1,\"low52\":470.05,\"open\":888.95,\"dayHigh\":915.0,\"dayLow\":870.0,\"deliveryToTradedQuantity\":81.04},{\"t\":\"WHIRLPOOL\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":1547.0,\"previousClose\":1549.0,\"totalTradedVolume\":22786.0,\"high52\":1597.9,\"low52\":838.95,\"open\":1558.0,\"dayHigh\":1586.0,\"dayLow\":1511.0,\"deliveryToTradedQuantity\":58.79},{\"t\":\"ASHOKLEY\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":118.2,\"previousClose\":114.3,\"totalTradedVolume\":1.2707741E7,\"high52\":133.9,\"low52\":75.05,\"open\":115.25,\"dayHigh\":118.55,\"dayLow\":114.5,\"deliveryToTradedQuantity\":37.14},{\"t\":\"LUMAXIND\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":2110.0,\"previousClose\":2065.4,\"totalTradedVolume\":5753.0,\"high52\":2250.0,\"low52\":708.8,\"open\":2050.0,\"dayHigh\":2115.0,\"dayLow\":2050.0,\"deliveryToTradedQuantity\":64.44},{\"t\":\"KANSAINER\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":542.0,\"previousClose\":524.5,\"totalTradedVolume\":155857.0,\"high52\":544.0,\"low52\":313.75,\"open\":526.9,\"dayHigh\":544.0,\"dayLow\":522.55,\"deliveryToTradedQuantity\":63.82},{\"t\":\"IFBIND\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":1399.95,\"previousClose\":1349.4,\"totalTradedVolume\":43906.0,\"high52\":1500.0,\"low52\":418.05,\"open\":1358.0,\"dayHigh\":1400.0,\"dayLow\":1357.95,\"deliveryToTradedQuantity\":44.76},{\"t\":\"DCMSHRIRAM\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":617.0,\"previousClose\":610.25,\"totalTradedVolume\":153925.0,\"high52\":628.0,\"low52\":196.35,\"open\":611.1,\"dayHigh\":628.0,\"dayLow\":603.95,\"deliveryToTradedQuantity\":48.48},{\"t\":\"VIPIND\",\"e\":\"NSE\",\"lt_dts\":\"19-DEC-2017 16:00:00\",\"l_fix\":358.5,\"previousClose\":349.55,\"totalTradedVolume\":452611.0,\"high52\":393.0,\"low52\":112.2,\"open\":353.6,\"dayHigh\":362.0,\"dayLow\":350.75,\"deliveryToTradedQuantity\":40.57}]";
		return data;
	}
	private String buildHtml(List<CurrentMarketPrice> list){
		StringBuilder htmlData = new StringBuilder();
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		
		htmlData.append(sdf.format(new Date()));
		
		 
		htmlData.append(" <br/><br/>");
		htmlData.append("<table  border=\"1\" style=\"width:100%\"> <tr> <th>Ticker</th>  <th>CMP</th>   <th>Close</th>  <th>Open</th> <th>Vol</th> <th>Delivery</th> <th>52 High</th> <th>52 Low</th> <th>Day High</th>  <th>Day Low</th>   </tr> ");
	
		for (CurrentMarketPrice mp : list){
			
			htmlData.append("<tr>");
			htmlData.append("<td align=\"center\">"+mp.getT()+"</td>");
			htmlData.append("<td align=\"center\">"+mp.getL_fix()+"</td>");
			if ((mp.getL_fix() - mp.getPreviousClose()) >= 0){
				htmlData.append("<td align=\"center\" style=\"background-color:green\">");
			}else {
				htmlData.append("<td align=\"center\" style=\"background-color:red\">");
			}
			htmlData.append(mp.getPreviousClose() +"("+ df2.format((mp.getL_fix() - mp.getPreviousClose())/mp.getPreviousClose()*100)+ ")"+"</td>");
			if ((mp.getL_fix() - mp.getOpen()) >= 0){
				htmlData.append("<td align=\"center\" style=\"background-color:green\">");
			}else {
				htmlData.append("<td align=\"center\" style=\"background-color:red\">");
			}
			htmlData.append(mp.getOpen()+"("+ df2.format((mp.getL_fix() - mp.getOpen())/mp.getOpen()*100)+ ")"+"</td>");
			
			htmlData.append("<td align=\"center\">"+mp.getTotalTradedVolume()+"</td>");
			
			htmlData.append("<td align=\"center\">"+mp.getDeliveryToTradedQuantity()+"</td>");
			if ((mp.getL_fix() - mp.getHigh52()) >= 0){
				htmlData.append("<td align=\"center\" style=\"background-color:green\">");
			}else {
				htmlData.append("<td align=\"center\" style=\"background-color:red\">");
			}
			htmlData.append(mp.getHigh52()+"("+ df2.format((mp.getL_fix() - mp.getHigh52())/mp.getHigh52()*100)+ ")"+"</td>");
			if ((mp.getL_fix() - mp.getLow52()) >= 0){
				htmlData.append("<td align=\"center\" style=\"background-color:green\">");
			}else {
				htmlData.append("<td align=\"center\" style=\"background-color:red\">");
			}
			htmlData.append(mp.getLow52()+"("+ df2.format((mp.getL_fix() - mp.getLow52())/mp.getLow52()*100)+ ")"+"</td>");
			if ((mp.getL_fix() - mp.getDayHigh()) >= 0){
				htmlData.append("<td align=\"center\" style=\"background-color:green\">");
			}else {
				htmlData.append("<td align=\"center\" style=\"background-color:red\">");
			}
			htmlData.append(mp.getDayHigh()+"("+ df2.format((mp.getL_fix() - mp.getDayHigh())/mp.getDayHigh()*100)+ ")"+"</td>");
			if ((mp.getL_fix() - mp.getDayLow()) >= 0){
				htmlData.append("<td align=\"center\" style=\"background-color:green\">");
			}else {
				htmlData.append("<td align=\"center\" style=\"background-color:red\">");
			}
			htmlData.append(mp.getDayLow()+"("+ df2.format((mp.getL_fix() - mp.getDayLow())/mp.getDayLow()*100)+ ")"+"</td>");
			
			
			htmlData.append("</tr>");
		}
		htmlData.append("</table>");
		return htmlData.toString();
	}

	private List<CurrentMarketPrice> getTickerList(){
		List<CurrentMarketPrice> marketRequest = new ArrayList<CurrentMarketPrice>();
		CurrentMarketPrice req = new CurrentMarketPrice();
		
		Gson  json = new Gson();
		String scripts = ProfileDAO.getADocument("script-list","script-list","myscripts",Constants.mlabKey);
		scripts = scripts.replaceFirst("\\[", "").trim();
		 if (scripts.indexOf("]") >= 0){
			
			 scripts = scripts.substring(0, scripts.length()-1);
		 }
		Scripts  myScripts= json.fromJson(scripts, new TypeToken<Scripts>() {}.getType());
		
		for (AScript ascript : myScripts.getScripts()){
			req.setT(ascript.getId());
			req.setE("NSE");
			marketRequest.add(req);
		}
		
		
		
		return marketRequest;
	}
	
}
