package com.java.loanmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.loanmanagement.model.Customer;
import com.java.loanmanagement.model.Loan;
import com.java.loanmanagement.model.LoanStatus;
import com.java.loanmanagement.model.LoanType;
import com.java.loanmanagement.myexception.InvalidLoanException;
import com.java.loanmanagement.util.ConnectionHelper;

public class ILoanRepositoryImpl implements ILoanRepository {
	Connection connection;
	PreparedStatement pst;
	Scanner scanner = new Scanner(System.in);

	@Override
	public boolean applyLoan(Loan loan) {
		Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to proceed with the loan application? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("Loan application cancelled by user.");
            return false;
        }
        
        try {
			connection = ConnectionHelper.getConnection();
			String cmd = "insert into loan (customerid, principalamount, interestrate, loanterm, loantype, loanstatus) "
					+ "values (?, ?, ?, ?, ?, ?)";
			
			pst = connection.prepareStatement(cmd);
			
			pst.setInt(1, loan.getCustomer().getCustomerId());
            pst.setDouble(2, loan.getPrincipalAmount());
            pst.setDouble(3, loan.getInterestRate());
            pst.setInt(4, loan.getLoanTerm());
            pst.setString(5, loan.getLoanType().toString());
            pst.setString(6, loan.getLoanStatus().toString().toUpperCase());
			
			int rows = pst.executeUpdate();
			
			if (rows > 0) {
                System.out.println("Loan application submitted successfully.");
                System.out.println("Your Loan Id is " + loan.getLoanId());
                return true;
            } else {
                System.out.println("Failed to apply for loan.");
                return false;
            }
			
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
			return false;
		}
        
	}
	
	@Override
	public String loanStatus(int loanId) throws InvalidLoanException {
		try {
			connection = ConnectionHelper.getConnection();
			String cmd = "select customerid, loanstatus from loan where loanid = ?";
			pst = connection.prepareStatement(cmd);
			
			pst.setInt(1, loanId);
			
			ResultSet rs = pst.executeQuery();
			
			if(!rs.next()) {
	            throw new InvalidLoanException("Loan with ID " + loanId + " not found.");	
			}
			
			int customerId = rs.getInt("customerid");
	        String currentStatus = rs.getString("loanstatus");
	        
	        if (!currentStatus.equalsIgnoreCase("Pending")) {
	            return "Loan is already " + currentStatus + ". No action needed.";
	        }
	        
	        cmd = "select creditscore from customer where customerid = ?";
	        pst = connection.prepareStatement(cmd);
	        pst.setInt(1, customerId);
	        rs = pst.executeQuery();

	        if (!rs.next()) {
	            throw new InvalidLoanException("Customer for loan ID " + loanId + " not found.");
	        }
	        
	        double creditScore = rs.getDouble("creditscore");
	        String updatedStatus = creditScore > 650.0 ? "APPROVED" : "REJECTED";
	        
	        cmd = "update loan set loanstatus = ? where loanid = ?";
	        pst = connection.prepareStatement(cmd);
	        pst.setString(1, updatedStatus);
	        pst.setInt(2, loanId);
	        pst.executeUpdate();

	        return "Loan status updated to " + updatedStatus;
			
		} catch (Exception e) {
			 e.printStackTrace();
		     throw new InvalidLoanException("An error occurred while updating loan status.");
		}
	}

	@Override
	public List<Loan> getAllLoan() {
	    List<Loan> loans = new ArrayList<>();

	    try {
	        connection = ConnectionHelper.getConnection();
	        String cmd = "select * from loan";
	        pst = connection.prepareStatement(cmd);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            Loan loan = new Loan();
	            loan.setLoanId(rs.getInt("loanid"));

	            Customer customer = new Customer();
	            customer.setCustomerId(rs.getInt("customerid"));
	            loan.setCustomer(customer);

	            loan.setPrincipalAmount(rs.getDouble("principalamount"));
	            loan.setInterestRate(rs.getDouble("interestrate"));
	            loan.setLoanTerm(rs.getInt("loanterm"));
	            loan.setLoanType(LoanType.valueOf(rs.getString("loantype").toUpperCase()));
	            loan.setLoanStatus(LoanStatus.valueOf(rs.getString("loanstatus").toUpperCase()));

	            loans.add(loan);
	        }

	    } catch (Exception e) {
	        System.out.println("Error while fetching loan list: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return loans;
	}


	@Override
	public Loan getLoanById(int loanId) throws InvalidLoanException {
	    try {
	        connection = ConnectionHelper.getConnection();
	        String cmd = "select * from loan where loanid = ?";
	        pst = connection.prepareStatement(cmd);
	        pst.setInt(1, loanId);

	        ResultSet rs = pst.executeQuery();

	        if (!rs.next()) {
	            throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	        }

	        Loan loan = new Loan();
	        loan.setLoanId(rs.getInt("loanid"));
	        
	        Customer customer = new Customer();
	        customer.setCustomerId(rs.getInt("customerid"));
	        loan.setCustomer(customer);

	        loan.setPrincipalAmount(rs.getDouble("principalamount"));
	        loan.setInterestRate(rs.getDouble("interestrate"));
	        loan.setLoanTerm(rs.getInt("loanterm"));
	        loan.setLoanType(LoanType.valueOf(rs.getString("loantype").toUpperCase()));
	        loan.setLoanStatus(LoanStatus.valueOf(rs.getString("loanstatus").toUpperCase()));

	        return loan;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InvalidLoanException("Unable to retrieve loan with ID " + loanId);
	    }
	}

	@Override
	public double calculateInterest(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double calculateEMI(int loanId) throws InvalidLoanException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String loanRepayment(int loanId, double amount) throws InvalidLoanException {
		// TODO Auto-generated method stub
		return null;
	}

}
