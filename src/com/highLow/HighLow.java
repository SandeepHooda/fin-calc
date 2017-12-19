package com.highLow;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nav.CurrentMarketPrice;
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
		
		List<CurrentMarketPrice> marketRequest =getTickerList()	;
		
		Map<String, CurrentMarketPrice> map = ProfileDAO.getCurrentMarkerPrice(marketRequest,true);
		/*Gson  json = new Gson();
		String jsonData = json.toJson(map, new TypeToken<Map<String, CurrentMarketPrice>>() {}.getType());*/
		
		StringBuilder htmlData = new StringBuilder();
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		
		htmlData.append(sdf.format(new Date()));
		
		 
		htmlData.append(" <br/><br/>");
		htmlData.append("<table  border=\"1\" style=\"width:100%\"> <tr> <th>Ticker</th>  <th>CMP</th>   <th>Close</th>  <th>Open</th> <th>Vol</th> <th>Delivery</th> <th>52 High</th> <th>52 Low</th> <th>Day High</th>  <th>Day Low</th>   </tr> ");
		Set<String> scripts = map.keySet();
		for (String ticker : scripts){
			CurrentMarketPrice mp = map.get(ticker);
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
		response.getWriter().append(htmlData.toString());
	}

	private List<CurrentMarketPrice> getTickerList(){
		List<CurrentMarketPrice> marketRequest = new ArrayList<CurrentMarketPrice>();
		CurrentMarketPrice req = new CurrentMarketPrice();
		req.setT("ASHOKLEY");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("BHARATFORG");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("BRITANNIA");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("CERA");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("CONTROLPR");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("DBL");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("DCMSHRIRAM");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("FILATEX");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("FINCABLES");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("GAYAPROJ");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("GILLETTE");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("HAVELLS");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("IFBIND");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("KANSAINER");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("LUMAXIND");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("MARUTI");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("MOTHERSUMI");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("PGHH");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("SOMANYCERA");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("TITAN");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("VGUARD");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("VIPIND");
		req.setE("NSE");
		marketRequest.add(req);
		
		req = new CurrentMarketPrice();
		req.setT("WHIRLPOOL");
		req.setE("NSE");
		marketRequest.add(req);
		
		return marketRequest;
	}
	
}
