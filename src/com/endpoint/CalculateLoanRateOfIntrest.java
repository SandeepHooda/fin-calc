package com.endpoint;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loan.MortgagePaymentCalculator;

/**
 * Servlet implementation class CalculateLoanRateOfIntrest
 */
public class CalculateLoanRateOfIntrest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalculateLoanRateOfIntrest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double monthlyPayment = Double.parseDouble(request.getParameter("monthlyPayment"));
		int termInMonths = Integer.parseInt(request.getParameter("termInMonths"));
		if (termInMonths < 0){
			termInMonths *=-1;
		}
		int loanAmount = Integer.parseInt(request.getParameter("loanAmount"));
		
		response.getWriter().append(""+MortgagePaymentCalculator.calculateRate(monthlyPayment, termInMonths, loanAmount));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
