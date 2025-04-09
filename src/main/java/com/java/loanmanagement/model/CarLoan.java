package com.java.loanmanagement.model;

public class CarLoan extends Loan{
	private String carModel;
    private double carValue;
	
    public String getCarModel() {
		return carModel;
	}
	
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	
	public double getCarValue() {
		return carValue;
	}
	
	public void setCarValue(double carValue) {
		this.carValue = carValue;
	}
	
	@Override
	public String toString() {
		return "CarLoan [carModel=" + carModel + ", carValue=" + carValue + "]";
	}
	
	public CarLoan() {
		
	}
	
    public CarLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm, LoanStatus loanStatus, String carModel, double carValue) {
        super(loanId, customer, principalAmount, interestRate, loanTerm, LoanType.CARLOAN, loanStatus);
        this.carModel = carModel;
        this.carValue = carValue;
    }
  
}
