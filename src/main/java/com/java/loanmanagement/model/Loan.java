package com.java.loanmanagement.model;

public class Loan {
	private int loanId;
    private Customer customer;
    private double principalAmount;
    private double interestRate;
    private int LoanTenure;
    private LoanType loanType;
    private LoanStatus loanStatus;
    
	public int getLoanId() {
		return loanId;
	}
	
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public double getPrincipalAmount() {
		return principalAmount;
	}
	
	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public int getLoanTenure() {
		return LoanTenure;
	}
	
	public void setLoanTerm(int loanTerm) {
		this.LoanTenure = loanTerm;
	}
	
	public LoanType getLoanType() {
		return loanType;
	}
	
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	
	public LoanStatus getLoanStatus() {
		return loanStatus;
	}
	
	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", customer=" + customer + ", principalAmount=" + principalAmount
				+ ", interestRate=" + interestRate + ", loanTerm=" + LoanTenure + ", loanType=" + loanType
				+ ", loanStatus=" + loanStatus + "]";
	}
	
	public Loan() {
		
	}
	
	public Loan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm,
			LoanType loanType, LoanStatus loanStatus) {
		super();
		this.loanId = loanId;
		this.customer = customer;
		this.principalAmount = principalAmount;
		this.interestRate = interestRate;
		this.LoanTenure = loanTerm;
		this.loanType = loanType;
		this.loanStatus = loanStatus;
	}
    
}
