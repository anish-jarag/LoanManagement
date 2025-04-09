# 💼 Loan Management System

A Java-based Loan Management System developed as part of the Hexaware Coding Challenge. The system simulates real-world loan operations including application, approval, EMI calculations, repayments, and tracking using a menu-driven console interface.

---

## 📁 Project Structure

```
├── resource
│   ├── db.properties
com.java.loanmanagement
├── dao
│   ├── ILoanRepository.java
│   └── ILoanRepositoryImpl.java
├── model
│   ├── Customer.java
│   ├── Loan.java
│   ├── HomeLoan.java
│   ├── CarLoan.java
│   ├── LoanType.java
│   └── LoanStatus.java
├── myexception
│   └── InvalidLoanException.java
├── util
│   └── ConnectionHelper.java
└── LoanManagementMain.java
```

---

## ✅ Features Implemented

- 📝 **Apply for Loan** (HomeLoan / CarLoan)
- 🔍 **View Loan by ID**
- 🔍 **Search Loans by Customer ID**
- 📋 **View All Loans**
- 📈 **Calculate Interest & EMI**
- 📊 **Check & Update Loan Status** (auto-approved if credit score > 650)
- 💸 **Loan Repayment** with overpayment handling & remaining amount display
- 📖 **Loan Repayment Logs** stored in a separate `loan_repayment` table


---

## 🧱 Database Tables Used

- `customer`
- `loan`
- `homeloan`
- `carloan`
- `loan_repayment`

---

## 🛠️ Technologies

- **Java** (OOP Principles)
- **MySQL**
- **JDBC**
- **Custom Exception Handling**
- **Console-based UI**

---

## ⚙️ Setup Instructions

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/loan-management-system.git
   ```

2. Create the MySQL database and tables using the provided schema. (SQL SCRIPT included)

3. Update your database credentials in `db.properties`.

4. Compile and run:
   ```bash
   javac com/java/loanmanagement/LoanManagementMain.java
   java com.java.loanmanagement.LoanManagementMain
   ```
   
---

## 👨‍💼 Author

**Anish Jarag**  
Hexaware Training - Coding Challenge  
[GitHub Profile](https://github.com/your-username)
