package com.corpAnalysis;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Constants;
import com.google.gson.Gson;
import com.profile.ProfileDAO;

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
		
		String jsonStr = new ProfileDAO(). getArrayData("corp-analysis","corp-analysis", false, null,Constants.mlabKey );
		response.getWriter().append(jsonStr);
	}

	
}
