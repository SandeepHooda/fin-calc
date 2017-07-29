package com.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xirr.XirrCalculatorService;
import com.vo.Request;




/**
 * Servlet implementation class xirr
 */
public class xirr extends HttpServlet {
	private static final Logger log = Logger.getLogger(xirr.class.getName());
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public xirr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Use post method ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

		log.info("requestData"+data);
		Gson  json = new Gson();
		Request xirrData = json.fromJson(data, Request.class);
		double xirr = XirrCalculatorService.Newtons_method(0.1, xirrData.getRequest().getPayments(), xirrData.getRequest().getDates());
		response.getWriter().append(" "+xirr);
		
	}

}
