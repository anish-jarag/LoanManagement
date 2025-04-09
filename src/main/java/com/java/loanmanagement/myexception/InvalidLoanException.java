package com.java.loanmanagement.myexception;

public class InvalidLoanException extends Exception{
	public InvalidLoanException() {
		super("Invalid Loan ");
	}

	public InvalidLoanException(String msg) {
		super(msg);
	}
}
