package com.endpoint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.profile.ProfileService;
import com.vo.Portfolio;

/**
 * Servlet implementation class SaveTodaysPortfilioPrice
 */
public class SaveTodaysPortfilioPrice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveTodaysPortfilioPrice() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		if (null == email){
			email = "sonu.hooda@gmail.com";
		}
		ProfileService.saveTodaysPortfilioPrice(email);
		
		response.getWriter().append("Done");
	}

	

}
