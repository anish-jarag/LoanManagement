package com.java.loanmanagement.dao;

import java.util.List;

import com.java.loanmanagement.model.Loan;
import com.java.loanmanagement.myexception.InvalidLoanException;

public interface ILoanRepository {
	boolean applyLoan(Loan loan);
	List<Loan> getAllLoan();
    Loan getLoanById(int loanId) throws InvalidLoanException;
    List<Loan> getLoansByCustomerId(int customerId) throws InvalidLoanException;
    String loanStatus(int loanId) throws InvalidLoanException;

    double calculateInterest(int loanId) throws InvalidLoanException;
    double calculateEMI(int loanId) throws InvalidLoanException;

    String loanRepayment(int loanId, double amount) throws InvalidLoanException;

    
}
