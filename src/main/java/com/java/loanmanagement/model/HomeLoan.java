package com.java.loanmanagement.model;

public class HomeLoan extends Loan{
	private String propertyAddress;
    private double propertyValue;
	
    public String getPropertyAddress() {
		return propertyAddress;
	}
	
	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}
	
	public double getPropertyValue() {
		return propertyValue;
	}
	
	public void setPropertyValue(double propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	@Override
	public String toString() {
		return "HomeLoan [propertyAddress=" + propertyAddress + ", propertyValue=" + propertyValue + "]";
	}
	
	public HomeLoan() {
		
	}

	
    public HomeLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm, LoanStatus loanStatus, String propertyAddress, double propertyValue) {
        super(loanId, customer, principalAmount, interestRate, loanTerm, LoanType.HOMELOAN, loanStatus);
        this.propertyAddress = propertyAddress;
        this.propertyValue = propertyValue;
    }
    
}
