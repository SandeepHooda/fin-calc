package com.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;
import com.sip.SipSchemeVO;



/**
 * Servlet implementation class GetSipList
 */
public class GetSipList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GetSipList.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSipList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = (String)request.getSession().getAttribute("email");
		List<SipSchemeVO> userSipList = ProfileService.getSipList(email);
		
		Gson  json = new Gson();
		String userSipListJson = json.toJson(userSipList, new TypeToken<List<SipSchemeVO>>() {}.getType());
		response.getWriter().append(userSipListJson);
	}

	

}
