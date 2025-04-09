package com.java.loanmanagement;

import java.util.List;
import java.util.Scanner;

import com.java.loanmanagement.dao.ILoanRepository;
import com.java.loanmanagement.dao.ILoanRepositoryImpl;
import com.java.loanmanagement.model.Customer;
import com.java.loanmanagement.model.Loan;
import com.java.loanmanagement.model.LoanStatus;
import com.java.loanmanagement.model.LoanType;
import com.java.loanmanagement.myexception.InvalidLoanException;

public class LoanManagementMain {
    static Scanner scanner;
    static ILoanRepository loanService ;

    static {
    	loanService = new ILoanRepositoryImpl();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n========================");
            System.out.println("   💼 Loan Management");
            System.out.println("========================");

            System.out.println("\n📝 Loan Operations:");
            System.out.println("1. Apply for Loan");
            System.out.println("2. View Loan By ID");
            System.out.println("3. View All Loans");

            System.out.println("\n📈 Calculations:");
            System.out.println("4. Calculate EMI by Loan ID");
            System.out.println("5. Calculate Interest by Loan ID");

            System.out.println("\n🔄 Loan Status & Repayment:");
            System.out.println("6. Check/Update Loan Status");
            System.out.println("7. Repay Loan");

            System.out.println("\n🚪 8. Exit");

            System.out.print("\nEnter Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    applyForLoan();
                    break;
                case 2:
                    viewLoanById();
                    break;
                case 3:
                    viewAllLoans();
                    break;
                case 4:
                    calculateEmiByLoanId();
                    break;
                case 5:
                    calculateInterestByLoanId();
                    break;
                case 6:
                    checkLoanStatus();
                    break;
                case 7:
                    repayLoan();
                    break;
                case 8:
                    System.out.println("👋 Thank you for using Loan Management System!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    private static void applyForLoan() {
    	try {
            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();

            System.out.print("Enter Principal Amount: ");
            double principal = scanner.nextDouble();

            System.out.print("Enter Interest Rate (%): ");
            double interestRate = scanner.nextDouble();

            System.out.print("Enter Loan Term (in months): ");
            int loanTerm = scanner.nextInt();

            scanner.nextLine(); 
            System.out.print("Enter Loan Type (e.g., HOMELOAN, CARLOAN): ");
            String loanType = scanner.nextLine().toUpperCase();

            Loan loan = new Loan();
            Customer customer = new Customer();
            customer.setCustomerId(customerId);
            loan.setCustomer(customer);
            loan.setPrincipalAmount(principal);
            loan.setInterestRate(interestRate);
            loan.setLoanTerm(loanTerm);
            loan.setLoanType(LoanType.valueOf(loanType));
            loan.setLoanStatus(LoanStatus.PENDING);

            boolean result = loanService.applyLoan(loan);
            if (result) {
                System.out.println("Loan application submitted.");
            } else {
                System.out.println("Loan application failed.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewLoanById() {
        try {
            System.out.print("🔍 Enter Loan ID: ");
            int loanId = scanner.nextInt();

            Loan loan = loanService.getLoanById(loanId);

            if (loan == null) {
                System.out.println("⚠️  Loan not found for ID: " + loanId);
                return;
            }

            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t📄 Loan Details");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-12s %-15s %-15s %-12s %-12s %-12s\n",
                    "| Loan ID", "| Cust ID", "| Principal (₹)", "| Interest (%)", "| Term (mo)", "| Type", "| Status   |");
            System.out.println("--------------------------------------------------------------------------------------------");

            System.out.printf("| %-8d | %-9d | %-13.2f | %-13.2f | %-10d | %-10s | %-9s |\n",
                    loan.getLoanId(),
                    loan.getCustomer().getCustomerId(),
                    loan.getPrincipalAmount(),
                    loan.getInterestRate(),
                    loan.getLoanTerm(),
                    loan.getLoanType(),
                    loan.getLoanStatus());

            System.out.println("--------------------------------------------------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }


    private static void viewAllLoans() {
        try {
            List<Loan> loans = loanService.getAllLoan();

            if (loans.isEmpty()) {
                System.out.println("⚠️  No loan records found.");
                return;
            }

            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t📋 All Loan Records");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-12s %-15s %-15s %-12s %-12s %-12s\n",
                    "| Loan ID", "| Cust ID", "| Principal (₹)", "| Interest (%)", "| Term (mo)", "| Type", "| Status   |");
            System.out.println("--------------------------------------------------------------------------------------------");

            for (Loan loan : loans) {
                System.out.printf("| %-8d | %-9d | %-13.2f | %-13.2f | %-10d | %-10s | %-9s |\n",
                        loan.getLoanId(),
                        loan.getCustomer().getCustomerId(),
                        loan.getPrincipalAmount(),
                        loan.getInterestRate(),
                        loan.getLoanTerm(),
                        loan.getLoanType(),
                        loan.getLoanStatus());
            }

            System.out.println("--------------------------------------------------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println("❌ Error fetching loan records: " + e.getMessage());
        }
    }


    private static void calculateEmiByLoanId() {
        // TODO: Implement
    }

    private static void calculateInterestByLoanId() {
        // TODO: Implement
    }
    
    private static void checkLoanStatus() {
        System.out.print("Enter Loan ID to check status: ");
        int loanId = scanner.nextInt();
        try {
            String result = loanService.loanStatus(loanId);
            System.out.println("📋 " + result);
        } catch (InvalidLoanException e) {
            System.out.println("❗ " + e.getMessage());
        }
    }

    private static void repayLoan() {
        // TODO: Implement
    }
}
