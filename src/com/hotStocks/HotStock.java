package com.hotStocks;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Constants;
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
			
			
		
		response.addHeader("Cache-Control", "max-age=43200");//12 hours
		response.getWriter().append(completeData);
	}

	
}
