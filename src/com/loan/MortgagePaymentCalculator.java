package com.loan;

public class MortgagePaymentCalculator {
	 
	   private static double calculateMonthlyPayment(
	      int loanAmount, int termInMonths, double interestRate) {
	       
	      // Convert interest rate into a decimal
	      // eg. 6.5% = 0.065
	       
	      interestRate /= 100.0;
	       
	      // Monthly interest rate 
	      // is the yearly rate divided by 12
	       
	      double monthlyRate = interestRate / 12.0;
	       
	      
	       
	      // Calculate the monthly payment
	      // Typically this formula is provided so 
	      // we won't go into the details
	       
	      // The Math.pow() method is used calculate values raised to a power
	       
	      double monthlyPayment =          (loanAmount*monthlyRate) /             (1-Math.pow(1+monthlyRate, -termInMonths));
	       
	      return monthlyPayment;
	   }
	   public static double calculateRate(double monthlyPayment, int termInMonths,  int loanAmount){
		   boolean rateFound = false;
		   double interestRate = 10;
		   double differenceDromTarget_previous = 0;
		  
		   int iterations = 0;
		   double boosterDose = .1;
		   
		   double gap = 0;
		   while(!rateFound){
			   iterations++;
			   double differenceDromTarget = monthlyPayment - calculateMonthlyPayment(  loanAmount,  termInMonths, interestRate);
			   if (differenceDromTarget < 10 && differenceDromTarget > -10){
				   rateFound = true;
				   return interestRate;
			   }else {
				   if (differenceDromTarget< 0){
					   boosterDose = -.1;
					   gap = (differenceDromTarget_previous *-1) - (differenceDromTarget*-1);
				   }else {
					   boosterDose = .1;
					   gap = (differenceDromTarget_previous ) - (differenceDromTarget);
				   }
				   if (iterations > 1){
					  
					   if (gap > 10){
						   interestRate +=boosterDose;
					   }
					  
					   differenceDromTarget_previous =   differenceDromTarget; 
						   
				   }else {
					  
					   interestRate +=boosterDose;
					   differenceDromTarget_previous =   differenceDromTarget; 
				   }
				   
			   }
		   }
		   return 0;
	   }
	   public static void main(String[] args) {
		   calculateRate(49919,240,5000000);
	 
	   }
	 
	}
