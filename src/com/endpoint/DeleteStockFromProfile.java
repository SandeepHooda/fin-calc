package com.endpoint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.profile.ProfileService;

/**
 * Servlet implementation class DeleteStockFromProfile
 */
public class DeleteStockFromProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteStockFromProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		
		String profileID = request.getParameter("profileID");
		response.setContentType("application/json");
		if (null != profileID){
			ProfileService.deleteStockFromPortfolio(email, Long.parseLong(profileID));
			response.getWriter().append("{\"data\":\"SUCCESS\"}");
		}else {
			response.getWriter().append("{\"data\":\"Please pass a valid profile ID to be deleted.\"}");
		}
		
	}

}
