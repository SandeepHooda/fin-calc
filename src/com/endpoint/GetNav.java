package com.endpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nav.CompanyVO;
import com.nav.CompanyVOSort;
import com.nav.NavTextDAO;
import com.nav.NavVO;


/**
 * Servlet implementation class GetCurrentNav
 */
public class GetNav extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GetNav.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNav() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date");
		String companyName = request.getParameter("companyName");
		String schemeCode = request.getParameter("schemeCode");
		log.info("date "+date+" companyName "+companyName+" schemeCode"+schemeCode);
		double nav = 0;
		Map<String, CompanyVO> map = null;
		if (null != date){
			map = NavTextDAO.getNavForDate(date);
		}else {
			map = NavTextDAO.getCurrentNav();
		}
		 
		List<CompanyVO> companyVOList = new ArrayList<CompanyVO>();
		Iterator<String> itr = map.keySet().iterator();
		while(itr.hasNext()){
			String name = itr.next();
			
			if (name.equalsIgnoreCase(companyName)){
					nav = getNav(map.get(name),schemeCode);
			}
			
			
			companyVOList.add(map.get(name));
		}
		Collections.sort(companyVOList, new CompanyVOSort());
		Gson gson = new GsonBuilder().create();
		
		String json = null;
		if (null != schemeCode) {
			json = ""+nav;
		}else {
			json= gson.toJson(companyVOList);
		}
		response.getWriter().println(json);
	}
	private double getNav(CompanyVO companyVO, String schemeCode ){
		for (NavVO navVO: companyVO.getNavs()){
			if (navVO.getSchemeCode().equalsIgnoreCase(schemeCode)){
				return Double.parseDouble(navVO.getNetAssetValue());
			}
		}
		
			return -9999.9999;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
