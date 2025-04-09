package com.java.loanmanagement.testmodel;

import com.java.loanmanagement.model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoanTest {

    @Test
    public void testLoanConstructorAndGetters() {
        Customer customer = new Customer(1, "Anish Jarag", "anish@example.com", "9876543210", "Pune", 720.0);
        Loan loan = new Loan(5001, customer, 1000000.0, 7.5, 10, LoanType.HOMELOAN, LoanStatus.PENDING);

        assertEquals(5001, loan.getLoanId());
        assertEquals("Anish Jarag", loan.getCustomer().getName());
        assertEquals(1000000.0, loan.getPrincipalAmount(), 0.0001);
        assertEquals(7.5, loan.getInterestRate(), 0.0001);
        assertEquals(10, loan.getLoanTenure());
        assertEquals(LoanType.HOMELOAN, loan.getLoanType());
        assertEquals(LoanStatus.PENDING, loan.getLoanStatus());
    }

    @Test
    public void testLoanSetters() {
        Customer customer = new Customer(2, "Anish Jarag", "anish@example.com", "9876543210", "Pune", 710.0);
        Loan loan = new Loan();

        loan.setLoanId(6001);
        loan.setCustomer(customer);
        loan.setPrincipalAmount(850000.0);
        loan.setInterestRate(6.9);
        loan.setLoanTerm(5);
        loan.setLoanType(LoanType.CARLOAN);
        loan.setLoanStatus(LoanStatus.APPROVED);

        assertEquals(6001, loan.getLoanId());
        assertEquals("Anish Jarag", loan.getCustomer().getName());
        assertEquals(850000.0, loan.getPrincipalAmount(), 0.0001);
        assertEquals(6.9, loan.getInterestRate(), 0.0001);
        assertEquals(5, loan.getLoanTenure());
        assertEquals(LoanType.CARLOAN, loan.getLoanType());
        assertEquals(LoanStatus.APPROVED, loan.getLoanStatus());
    }

    @Test
    public void testToStringContainsFields() {
        Customer customer = new Customer(3, "Anish Jarag", "anish@example.com", "9876543210", "Kolhapur", 700.0);
        Loan loan = new Loan(7001, customer, 1500000.0, 8.0, 15, LoanType.HOMELOAN, LoanStatus.REJECTED);

        String output = loan.toString();
        assertTrue(output.contains("Anish Jarag"));
        assertTrue(output.contains("principalAmount=1500000.0"));
        assertTrue(output.contains("loanStatus=REJECTED"));
    }
}
