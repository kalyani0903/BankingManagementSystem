package com.bank;

import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Banking Management System ===");
            System.out.println("1. Show Customer Records");
            System.out.println("2. Add Customer Record");
            System.out.println("3. Delete Customer Record");
            System.out.println("4. Update Customer Information");
            System.out.println("5. Show Account Details of a Customer");
            System.out.println("6. Show Loan Details of a Customer");
            System.out.println("7. Deposit Money to an Account");
            System.out.println("8. Withdraw Money from an Account");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();


switch (choice) {
    case 1:
        CustomerOperations.showCustomers();
        break;
    case 2:
        CustomerOperations.addCustomer();
        break;
    case 3:
        CustomerOperations.deleteCustomer();
        break;
    case 4:
        CustomerOperations.updateCustomer();
        break;
    case 5:
        CustomerOperations.showAccountDetails();
        break;
    case 6:
        CustomerOperations.showLoanDetails();
        break;
    case 7:
        CustomerOperations.depositMoney();
        break;
    case 8:
        CustomerOperations.withdrawMoney();
        break;
    case 9:
        System.out.println("Exiting the program...");
        break;
    default:
        System.out.println("Invalid choice.");
}
        } while (choice != 9);

        scanner.close();
    }
}
