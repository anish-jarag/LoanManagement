package com.java.loanmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.loanmanagement.model.CarLoan;
import com.java.loanmanagement.model.Customer;
import com.java.loanmanagement.model.HomeLoan;
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
	    System.out.print("Do you want to proceed with the loan application? (yes/no): ");
	    String confirm = scanner.nextLine().trim().toLowerCase();

	    if (!confirm.equals("yes")) {
	        System.out.println("Loan application cancelled by user.");
	        return false;
	    }
	    
	    Connection connection = null;
	    PreparedStatement pst = null;
	    ResultSet generatedKeys = null;
	    
	    try {
	        connection = ConnectionHelper.getConnection();
	        
	        String loanInsertSQL = "insert into loan (customerid, principalamount, interestrate, " +
	                             "loanterm, loantype, loanstatus) values (?, ?, ?, ?, ?, ?)";
	        
	        pst = connection.prepareStatement(loanInsertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
	        
	        pst.setInt(1, loan.getCustomer().getCustomerId());
	        pst.setDouble(2, loan.getPrincipalAmount());
	        pst.setDouble(3, loan.getInterestRate());
	        pst.setInt(4, loan.getLoanTenure());
	        pst.setString(5, loan.getLoanType().toString());
	        pst.setString(6, loan.getLoanStatus().toString());
	        
	        int affectedRows = pst.executeUpdate();
	        
	        if (affectedRows == 0) {
	            throw new SQLException("Creating loan failed, no rows affected.");
	        }
	        
	        generatedKeys = pst.getGeneratedKeys();
	        if (!generatedKeys.next()) {
	            throw new SQLException("Creating loan failed, no ID obtained.");
	        }
	        
	        int loanId = generatedKeys.getInt(1);
	        loan.setLoanId(loanId); 
	        
	        if (loan instanceof HomeLoan) {
	            HomeLoan homeLoan = (HomeLoan) loan;
	            String homeLoanSQL = "insert into homeloan (loanid, propertyaddress, propertyvalue) " +
	                               "values (?, ?, ?)";
	            
	            pst = connection.prepareStatement(homeLoanSQL);
	            pst.setInt(1, loanId);
	            pst.setString(2, homeLoan.getPropertyAddress());
	            pst.setDouble(3, homeLoan.getPropertyValue());
	            pst.executeUpdate();
	        } 
	        else if (loan instanceof CarLoan) {
	            CarLoan carLoan = (CarLoan) loan;
	            String carLoanSQL = "insert into carloan (loanid, carmodel, carvalue) " +
	                              "values (?, ?, ?)";
	            
	            pst = connection.prepareStatement(carLoanSQL);
	            pst.setInt(1, loanId);
	            pst.setString(2, carLoan.getCarModel());
	            pst.setDouble(3, carLoan.getCarValue());
	            pst.executeUpdate();
	        }
	        
	        return true;
	        
	    } catch (Exception e) {
	        System.out.println("❌ Error applying for loan: " + e.getMessage());
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
	        
	        // Get basic loan info
	        String cmd = "select * from loan where loanid = ?";
	        pst = connection.prepareStatement(cmd);
	        pst.setInt(1, loanId);
	        ResultSet rs = pst.executeQuery();

	        if (!rs.next()) {
	            throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	        }

	        Loan loan;
	        LoanType loanType = LoanType.valueOf(rs.getString("loantype").toUpperCase());
	        
	        if (loanType == LoanType.HOMELOAN) {
	            HomeLoan homeLoan = new HomeLoan();
	            String homeLoanQuery = "select * from homeloan where loanid = ?";
	            pst = connection.prepareStatement(homeLoanQuery);
	            pst.setInt(1, loanId);
	            ResultSet homeLoanRs = pst.executeQuery();
	            
	            if (homeLoanRs.next()) {
	                homeLoan.setPropertyAddress(homeLoanRs.getString("propertyaddress"));
	                homeLoan.setPropertyValue(homeLoanRs.getDouble("propertyvalue"));
	            }
	            loan = homeLoan;
	        } 
	        else if (loanType == LoanType.CARLOAN) {
	            CarLoan carLoan = new CarLoan();
	            String carLoanQuery = "select * from carloan where loanid = ?";
	            pst = connection.prepareStatement(carLoanQuery);
	            pst.setInt(1, loanId);
	            ResultSet carLoanRs = pst.executeQuery();
	            
	            if (carLoanRs.next()) {
	                carLoan.setCarModel(carLoanRs.getString("carmodel"));
	                carLoan.setCarValue(carLoanRs.getDouble("carvalue"));
	            }
	            loan = carLoan;
	        } 
	        else {
	            loan = new Loan();
	        }

	        loan.setLoanId(rs.getInt("loanid"));
	        Customer customer = new Customer();
	        customer.setCustomerId(rs.getInt("customerid"));
	        loan.setCustomer(customer);
	        loan.setPrincipalAmount(rs.getDouble("principalamount"));
	        loan.setInterestRate(rs.getDouble("interestrate"));
	        loan.setLoanTerm(rs.getInt("loanterm"));
	        loan.setLoanType(loanType);
	        loan.setLoanStatus(LoanStatus.valueOf(rs.getString("loanstatus").toUpperCase()));

	        return loan;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InvalidLoanException("Unable to retrieve loan with ID " + loanId);
	    }
	}

	@Override
	public double calculateInterest(int loanId) throws InvalidLoanException {
		try {
	        Loan loan = getLoanById(loanId);

	        if (loan == null) {
	            throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	        }

	        double principal = loan.getPrincipalAmount();
	        double rate = loan.getInterestRate();
	        int tenure = loan.getLoanTenure();

	        double interest = (principal * rate * tenure) / (100 * 12);

	        return interest;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InvalidLoanException("Failed to calculate interest.");
	    }
	}


	@Override
	public double calculateEMI(int loanId) throws InvalidLoanException {
	    try {
	        Loan loan = getLoanById(loanId);  

	        if (loan == null) {
	            throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	        }

	        double principal = loan.getPrincipalAmount();
	        double annualRate = loan.getInterestRate();
	        int termMonths = loan.getLoanTenure();

	        double monthlyRate = annualRate / 12 / 100;

	        double emi;
	        if (monthlyRate == 0) {
	            emi = principal / termMonths;
	        } else {
	            emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, termMonths)) /
	                  (Math.pow(1 + monthlyRate, termMonths) - 1);
	        }

	        return Math.round(emi * 100.0) / 100.0; 

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InvalidLoanException("An error occurred while calculating EMI.");
	    }
	}

	@Override
	public String loanRepayment(int loanId, double amount) throws InvalidLoanException {
	    try {
	        Loan loan = getLoanById(loanId);

	        if (loan == null) {
	            throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
	        }

	        if (loan.getLoanStatus() == LoanStatus.PAID) {
	            return "✅ Loan is already fully paid. No payment required.";
	        }

	        double emi = calculateEMI(loanId);
	        if (amount < emi) {
	            return "❌ Amount is less than one EMI (₹" + emi + "). Minimum required: ₹" + emi;
	        }

	        int remainingTerm = loan.getLoanTenure();
	        double totalDue = emi * remainingTerm;

	        double actualPayment = Math.min(amount, totalDue);
	        int paidEmis = (int) (actualPayment / emi);
	        double usedAmount = paidEmis * emi;
	        double refundAmount = amount - usedAmount;
	        double remainingAmount = totalDue - usedAmount;

	        if (paidEmis >= remainingTerm) {
	            loan.setLoanStatus(LoanStatus.PAID);
	            updateLoanStatusIfFullyPaid(loan);
	            remainingAmount = 0;
	        }

	        logRepayment(loanId, usedAmount, paidEmis, remainingAmount);

	        return String.format(
	            "💸 Payment of ₹%.2f accepted.\n" +
	            "✅ %d EMI(s) paid @ ₹%.2f each.\n" +
	            "📊 Remaining Amount: ₹%.2f\n" +
	            "📌 Loan Status: %s\n" +
	            (refundAmount > 0 ? "🔁 ₹%.2f has been refunded (excess payment)." : ""),
	            usedAmount, paidEmis, emi,
	            remainingAmount,
	            (loan.getLoanStatus() == LoanStatus.PAID ? "✅ PAID" : "⏳ " + loan.getLoanStatus()),
	            refundAmount
	        );

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InvalidLoanException("An error occurred while processing repayment.");
	    }
	}

//	Was updating the loan table(Data Integrity issue original data lost)
//	public void updateLoanAfterRepayment(Loan loan) throws Exception {
//		try {
//	        connection = ConnectionHelper.getConnection();
//	        String cmd = "update loan set principalamount = ?, loanterm = ?, loanstatus = ? where loanid = ?";
//	        pst = connection.prepareStatement(cmd);
//	        pst.setDouble(1, loan.getPrincipalAmount());
//	        pst.setInt(2, loan.getLoanTenure()); 
//	        pst.setString(3, loan.getLoanStatus().toString());
//	        pst.setInt(4, loan.getLoanId());
//	        pst.executeUpdate();
//	    } catch (Exception e) {
//	        System.out.println("❌ Failed to update loan: " + e.getMessage());
//	        e.printStackTrace();
//	    }
//	}
	
	public void logRepayment(int loanId, double paymentAmount, int emisPaid, double remainingAmount) throws Exception {
	    connection = ConnectionHelper.getConnection();
	    String cmd = "insert into loan_repayment (loanid, payment_amount, emis_paid, remaining_amount) values (?, ?, ?, ?)";
	    pst = connection.prepareStatement(cmd);
	    pst.setInt(1, loanId);
	    pst.setDouble(2, paymentAmount);
	    pst.setInt(3, emisPaid);
	    pst.setDouble(4, remainingAmount);
	    pst.executeUpdate();
	}

	
	public void updateLoanStatusIfFullyPaid(Loan loan) throws Exception {
	    if (loan.getLoanStatus() == LoanStatus.PAID) {
	        connection = ConnectionHelper.getConnection();
	        String cmd = "update loan set loanstatus = ? where loanid = ?";
	        pst = connection.prepareStatement(cmd);
	        pst.setString(1, "PAID");
	        pst.setInt(2, loan.getLoanId());
	        pst.executeUpdate();
	    }
	}


	@Override
	public List<Loan> getLoansByCustomerId(int customerId) throws InvalidLoanException {
	    List<Loan> loans = new ArrayList<>();
	    try {
	        connection = ConnectionHelper.getConnection();
	        String cmd = "select * from loan where customerid = ?";
	        pst = connection.prepareStatement(cmd);
	        pst.setInt(1, customerId);
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

	        if (loans.isEmpty()) {
	            throw new InvalidLoanException("No loans found for Customer ID: " + customerId);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new InvalidLoanException("Failed to fetch loans for Customer ID: " + customerId);
	    }

	    return loans;
	}


}
