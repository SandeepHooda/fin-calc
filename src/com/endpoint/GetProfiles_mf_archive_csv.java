package com.endpoint;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.profile.ProfileService;
import com.vo.Portfolio;
import com.vo.Profile;

/**
 * Servlet implementation class GetProfiles_mf_archive_csv
 */
public class GetProfiles_mf_archive_csv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProfiles_mf_archive_csv() {
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
		Portfolio portfolio = ProfileService.getPortfolio_mf_archive(email);
		StringBuilder outputResult = new StringBuilder();
		outputResult.append("Scheme name");
		outputResult.append(",");
		outputResult.append("Investment Amount");
		outputResult.append(",");
		outputResult.append("Investment Date");
		outputResult.append(",");
		outputResult.append("Units");
		outputResult.append(",");
		outputResult.append("Retun Amount");
		outputResult.append(",");
		outputResult.append("Exit Date");
		outputResult.append(",");
		outputResult.append("Units Sold");
		outputResult.append(",");
		outputResult.append("Gain Loss");
		outputResult.append(",");
		outputResult.append("Gain Loss %");
		outputResult.append(",");
		outputResult.append("Xirr");
		outputResult.append("\n");
		DecimalFormat df = new DecimalFormat("#.00");
		for (Profile profile : portfolio.getAllProfiles()){
			outputResult.append(profile.getSchemeName());
			outputResult.append(",");
			outputResult.append(df.format(profile.getInvestmentAmount()));
			outputResult.append(",");
			outputResult.append(profile.getInvestmentDate());
			outputResult.append(",");
			outputResult.append(df.format(profile.getUnits()));
			outputResult.append(",");
			outputResult.append(df.format(profile.getCurrentValue()));
			outputResult.append(",");
			outputResult.append(profile.getExitDate());
			outputResult.append(",");
			outputResult.append(df.format(profile.getExitUnits()));
			outputResult.append(",");
			outputResult.append(df.format(profile.getAbsoluteGain()));
			outputResult.append(",");
			outputResult.append(df.format(profile.getPercentGainAbsolute()));
			outputResult.append(",");
			outputResult.append(df.format(profile.getXirr()));
			outputResult.append("\n");
		}
		
		
		 
		  
		 response.setContentType("text/csv");
		 response.setHeader("Content-Disposition", "attachment; filename=\"Enterprise.csv\"");
		    try
		    {
		        OutputStream outputStream = response.getOutputStream();
		        
		        outputStream.write(outputResult.toString().getBytes());
		        outputStream.flush();
		        outputStream.close();
		    }
		    catch(Exception e)
		    {
		        System.out.println(e.toString());
		    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
