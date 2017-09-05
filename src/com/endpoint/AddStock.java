package com.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.profile.ProfileService;
import com.vo.Profile;
import com.vo.StockVO;

/**
 * Servlet implementation class AddStock
 */
public class AddStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AddStock.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStock() {
        super();

    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		if (null == email){
			email = "sonu.hooda@gmail.com";
		}
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

		log.info("requestData"+data);
		data = data.replace("{  \"selectedStock\":", "").trim();
		data = data.substring(0, data.length()-1);
		Gson  json = new Gson();
		StockVO  selectedStock= json.fromJson(data, StockVO.class);
		log.info("parsed to java object"+selectedStock);
		ProfileService.addStockToPortfolio(email, selectedStock);
		response.getWriter().append("{\"data\":\"SUCCESS\"}");
	}

}
