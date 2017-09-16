package com.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;
import com.sip.SipSchemeVO;
import com.vo.WishList;

/**
 * Servlet implementation class SaveWishList
 */
public class SaveWishList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveWishList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		if (null == email) {
			email = "sonu.hooda@gmail.com";
		}
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

		
		data = data.replace("{  \"wishListEquity\":", "").trim();
		data = data.substring(0, data.length()-1);
		Gson json=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		List<WishList> wishList = json.fromJson(data, new TypeToken<List<WishList>>(){}.getType());
		
		ProfileService.saveWishList(email+"_wishList", wishList);
		response.getWriter().append("Done ");
	}

}
