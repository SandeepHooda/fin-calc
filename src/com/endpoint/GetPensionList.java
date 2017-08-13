package com.endpoint;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.profile.ProfileService;
import com.sip.SipSchemeVO;
import com.sip.Withdrawal;

/**
 * Servlet implementation class GetPensionList
 */
public class GetPensionList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPensionList() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		List<SipSchemeVO> userSipList = ProfileService.getSipList(email+"_pension");
		for (SipSchemeVO sip: userSipList){
			sip.setStartDateLong(sip.getStartDate().getTime());
			sip.setEndDateLong(sip.getEndDate().getTime());
			sip.setPensionStartDateLong(sip.getPensionStartDate().getTime());
			sip.setPensionEndDateLong(sip.getPensionEndDate().getTime());
			for (Withdrawal withdraw :sip.getWithdrawlsRows() ){
				withdraw.setDateLong(withdraw.getDate().getTime());
			}
		}
		Gson  json = new Gson();
		String userSipListJson = json.toJson(userSipList, new TypeToken<List<SipSchemeVO>>() {}.getType());
		response.getWriter().append(userSipListJson);
	}

	
}
