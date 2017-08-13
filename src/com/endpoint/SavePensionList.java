package com.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;
import com.sip.SipSchemeVO;

/**
 * Servlet implementation class SavePensionList
 */
public class SavePensionList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SavePensionList.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SavePensionList() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

		
		data = data.replace("{  \"sipList\":", "").trim();
		data = data.substring(0, data.length()-1);
		Gson json=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		List<SipSchemeVO> profile = json.fromJson(data, new TypeToken<List<SipSchemeVO>>(){}.getType());
		log.info("parsed to java object"+profile);
		ProfileService.saveSipList(email+"_pension", profile);
		doGet(request, response);
	}

	 /**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.getWriter().append("Use post method ");
		}

}
