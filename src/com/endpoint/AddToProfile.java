package com.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.vo.Profile;
import com.vo.Request;

/**
 * Servlet implementation class AddToProfile
 */
public class AddToProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AddToProfile.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToProfile() {
        super();
        // TODO Auto-generated constructor stub
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
		Profile profile = json.fromJson(data, Profile.class);
		response.getWriter().append("{\"data\":\"SUCCESS\"}");
	}

}
