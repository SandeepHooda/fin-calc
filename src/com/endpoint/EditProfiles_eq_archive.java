package com.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;
import com.vo.Profile;
import com.vo.StockVO;

/**
 * Servlet implementation class EditProfiles_eq_archive
 */
public class EditProfiles_eq_archive extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfiles_eq_archive() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		if (null == email ){
			email = "sonu.hooda@gmail.com";
		}
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println(" data "+data);
		
		data = data.replace("{  \"allProfiles_eq_archive\":", "").trim();
		data = data.substring(0, data.length()-1);
		Gson  json = new Gson();
		
		List<StockVO> oldProfilesList = json.fromJson(data, new TypeToken<List<StockVO>>() {}.getType());
		
		ProfileService.EditProfiles_eq_archive(email, oldProfilesList);
		response.getWriter().append("{\"data\":\"SUCCESS\"}");
	}

}
