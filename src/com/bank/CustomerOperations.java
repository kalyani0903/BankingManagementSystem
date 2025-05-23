package com.bank;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class CustomerOperations {

    public static void showCustomers() {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> collection = db.getCollection("customers");

        MongoCursor<Document> cursor = collection.find().iterator();
        System.out.println("\n--- Customer Records ---");
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            System.out.println("Customer No: " + doc.get("cust_no"));
            System.out.println("Name       : " + doc.get("name"));
            System.out.println("Phone No   : " + doc.get("phone"));
            System.out.println("City       : " + doc.get("city"));
            System.out.println("------------------------------");
        }
    }

    public static void addCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer Number: ");
        int custNo = scanner.nextInt();
        scanner.nextLine();  // consume newline

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter City: ");
        String city = scanner.nextLine();

        Document newCustomer = new Document("cust_no", custNo)
                .append("name", name)
                .append("phone", phone)
                .append("city", city);

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> collection = db.getCollection("customers");
        collection.insertOne(newCustomer);

        System.out.println("✅ Customer added successfully!");
    }

    public static void deleteCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer Number to delete: ");
        int custNo = scanner.nextInt();

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> collection = db.getCollection("customers");

        Document query = new Document("cust_no", custNo);
        long deletedCount = collection.deleteOne(query).getDeletedCount();

        if (deletedCount > 0) {
            System.out.println("✅ Customer with cust_no " + custNo + " deleted successfully!");
        } else {
            System.out.println("❌ Customer with cust_no " + custNo + " not found.");
        }

        // Show updated list
        showCustomers();
    }

    public static void updateCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer Number to update: ");
        int custNo = scanner.nextInt();
        scanner.nextLine(); // consume newline

        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> collection = db.getCollection("customers");

        Document query = new Document("cust_no", custNo);

        System.out.println("Choose field to update:");
        System.out.println("1. Name");
        System.out.println("2. Phone Number");
        System.out.println("3. City");
        System.out.print("Enter choice: ");
        int fieldChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Document update = new Document();
        switch (fieldChoice) {
            case 1:
                System.out.print("Enter new Name: ");
                String newName = scanner.nextLine();
                update.append("name", newName);
                break;
            case 2:
                System.out.print("Enter new Phone Number: ");
                String newPhone = scanner.nextLine();
                update.append("phone", newPhone);
                break;
            case 3:
                System.out.print("Enter new City: ");
                String newCity = scanner.nextLine();
                update.append("city", newCity);
                break;
            default:
                System.out.println("Invalid choice. Update aborted.");
                return;
        }

        Document updateQuery = new Document("$set", update);
        long updatedCount = collection.updateOne(query, updateQuery).getModifiedCount();

        if (updatedCount > 0) {
            System.out.println("✅ Customer record updated successfully!");
        } else {
            System.out.println("❌ Customer with cust_no " + custNo + " not found or no changes made.");
        }

        // Show updated list
        showCustomers();
    }
public static void showAccountDetails() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Customer Number to show account details: ");
    int custNo = scanner.nextInt();

    MongoDatabase db = MongoConnection.getDatabase();
    MongoCollection<Document> custCollection = db.getCollection("customers");
    MongoCollection<Document> accountCollection = db.getCollection("accounts");

    Document customer = custCollection.find(new Document("cust_no", custNo)).first();

    if (customer == null) {
        System.out.println("❌ Customer with cust_no " + custNo + " not found.");
        return;
    }

    System.out.println("\n--- Customer Details ---");
    System.out.println("Customer No: " + customer.get("cust_no"));
    System.out.println("Name       : " + customer.get("name"));
    System.out.println("Phone No   : " + customer.get("phone"));
    System.out.println("City       : " + customer.get("city"));

    System.out.println("\n--- Account Details ---");
    MongoCursor<Document> accounts = accountCollection.find(new Document("cust_no", custNo)).iterator();

    boolean hasAccount = false;
    while (accounts.hasNext()) {
        hasAccount = true;
        Document account = accounts.next();
        System.out.println("Account No  : " + account.get("account_no"));
        System.out.println("Type        : " + account.get("type"));
        System.out.println("Balance     : " + account.get("balance"));
        System.out.println("Branch Code : " + account.get("branch_code"));
        System.out.println("Branch Name : " + account.get("branch_name"));
        System.out.println("Branch City : " + account.get("branch_city"));
        System.out.println("-----------------------------");
    }
    if (!hasAccount) {
        System.out.println("No accounts found for this customer.");
    }
}

public static void showLoanDetails() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Customer Number to show loan details: ");
    int custNo = scanner.nextInt();

    MongoDatabase db = MongoConnection.getDatabase();
    MongoCollection<Document> custCollection = db.getCollection("customers");
    MongoCollection<Document> loanCollection = db.getCollection("loans");

    Document customer = custCollection.find(new Document("cust_no", custNo)).first();

    if (customer == null) {
        System.out.println("❌ Customer with cust_no " + custNo + " not found.");
        return;
    }

    System.out.println("\n--- Customer Details ---");
    System.out.println("Customer No: " + customer.get("cust_no"));
    System.out.println("Name       : " + customer.get("name"));
    System.out.println("Phone No   : " + customer.get("phone"));
    System.out.println("City       : " + customer.get("city"));

    System.out.println("\n--- Loan Details ---");
    MongoCursor<Document> loans = loanCollection.find(new Document("cust_no", custNo)).iterator();

    boolean hasLoan = false;
    while (loans.hasNext()) {
        hasLoan = true;
        Document loan = loans.next();
        System.out.println("Loan No    : " + loan.get("loan_no"));
        System.out.println("Amount     : " + loan.get("amount"));
        System.out.println("Branch Code: " + loan.get("branch_code"));
        System.out.println("Branch Name: " + loan.get("branch_name"));
        System.out.println("Branch City: " + loan.get("branch_city"));
        System.out.println("-----------------------------");
    }
    if (!hasLoan) {
        System.out.println("No loans found for this customer.");
    }
}

public static void depositMoney() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Account Number to deposit money: ");
    int accountNo = scanner.nextInt();
    System.out.print("Enter amount to deposit: ");
    double amount = scanner.nextDouble();

    if (amount <= 0) {
        System.out.println("❌ Deposit amount must be positive.");
        return;
    }

    MongoDatabase db = MongoConnection.getDatabase();
    MongoCollection<Document> accountCollection = db.getCollection("accounts");

    Document query = new Document("account_no", accountNo);
    Document account = accountCollection.find(query).first();

    if (account == null) {
        System.out.println("❌ Account number " + accountNo + " not found.");
        return;
    }

    double currentBalance = account.getDouble("balance");
    double newBalance = currentBalance + amount;

    Document updateQuery = new Document("$set", new Document("balance", newBalance));
    accountCollection.updateOne(query, updateQuery);

    System.out.println("✅ Deposit successful. New balance: " + newBalance);

    // Show updated account details of customer
    int custNo = account.getInteger("cust_no");
    showAccountDetailsByCustNo(custNo);
}

public static void withdrawMoney() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Account Number to withdraw money: ");
    int accountNo = scanner.nextInt();
    System.out.print("Enter amount to withdraw: ");
    double amount = scanner.nextDouble();

    if (amount <= 0) {
        System.out.println("❌ Withdrawal amount must be positive.");
        return;
    }

    MongoDatabase db = MongoConnection.getDatabase();
    MongoCollection<Document> accountCollection = db.getCollection("accounts");

    Document query = new Document("account_no", accountNo);
    Document account = accountCollection.find(query).first();

    if (account == null) {
        System.out.println("❌ Account number " + accountNo + " not found.");
        return;
    }

    double currentBalance = account.getDouble("balance");
    if (amount > currentBalance) {
        System.out.println("❌ Insufficient balance. Withdrawal denied.");
        return;
    }

    double newBalance = currentBalance - amount;

    Document updateQuery = new Document("$set", new Document("balance", newBalance));
    accountCollection.updateOne(query, updateQuery);

    System.out.println("✅ Withdrawal successful. New balance: " + newBalance);

    // Show updated account details of customer
    int custNo = account.getInteger("cust_no");
    showAccountDetailsByCustNo(custNo);
}

// Helper to show account details by cust_no, used internally after deposit/withdraw
private static void showAccountDetailsByCustNo(int custNo) {
    MongoDatabase db = MongoConnection.getDatabase();
    MongoCollection<Document> accountCollection = db.getCollection("accounts");

    System.out.println("\n--- Updated Account Details ---");
    MongoCursor<Document> accounts = accountCollection.find(new Document("cust_no", custNo)).iterator();

    while (accounts.hasNext()) {
        Document account = accounts.next();
        System.out.println("Account No  : " + account.get("account_no"));
        System.out.println("Type        : " + account.get("type"));
        System.out.println("Balance     : " + account.get("balance"));
        System.out.println("Branch Code : " + account.get("branch_code"));
        System.out.println("Branch Name : " + account.get("branch_name"));
        System.out.println("Branch City : " + account.get("branch_city"));
        System.out.println("-----------------------------");
    }
}
}