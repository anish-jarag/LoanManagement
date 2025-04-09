package com.java.loanmanagement;

import java.util.Scanner;

import com.java.loanmanagement.dao.ILoanRepository;
import com.java.loanmanagement.dao.ILoanRepositoryImpl;
import com.java.loanmanagement.model.Customer;
import com.java.loanmanagement.model.Loan;
import com.java.loanmanagement.myexception.InvalidLoanException;
import com.java.loanmanagement.model.LoanStatus;

public class LoanManagementMain {
    static Scanner scanner;
    static ILoanRepository loanRepo;

    static {
        loanRepo = new ILoanRepositoryImpl();
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
            System.out.println("5. Calculate EMI (Custom)");
            System.out.println("6. Calculate Interest by Loan ID");
            System.out.println("7. Calculate Interest (Custom)");

            System.out.println("\n🔄 Loan Status & Repayment:");
            System.out.println("8. Check/Update Loan Status");
            System.out.println("9. Repay Loan");

            System.out.println("\n🚪 10. Exit");

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
                    calculateEmiCustom();
                    break;
                case 6:
                    calculateInterestByLoanId();
                    break;
                case 7:
                    calculateInterestCustom();
                    break;
                case 8:
                    checkLoanStatus();
                    break;
                case 9:
                    repayLoan();
                    break;
                case 10:
                    System.out.println("👋 Thank you for using Loan Management System!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        } while (choice != 10);
    }

    // Stub methods to be implemented next
    private static void applyForLoan() {
        // TODO: Implement
    }

    private static void viewLoanById() {
        // TODO: Implement
    }

    private static void viewAllLoans() {
        // TODO: Implement
    }

    private static void calculateEmiByLoanId() {
        // TODO: Implement
    }

    private static void calculateEmiCustom() {
        // TODO: Implement
    }

    private static void calculateInterestByLoanId() {
        // TODO: Implement
    }

    private static void calculateInterestCustom() {
        // TODO: Implement
    }

    private static void checkLoanStatus() {
        System.out.print("Enter Loan ID to check status: ");
        int loanId = scanner.nextInt();
        try {
            String result = loanRepo.loanStatus(loanId);
            System.out.println("📋 " + result);
        } catch (InvalidLoanException e) {
            System.out.println("❗ " + e.getMessage());
        }
    }

    private static void repayLoan() {
        // TODO: Implement
    }
}
