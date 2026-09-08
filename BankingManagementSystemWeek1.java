import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    int accountNumber;
    String name;
    String phone;
    double balance;

    BankAccount(int accountNumber, String name, String phone, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.phone = phone;
        this.balance = balance;
    }

    void display() {
        System.out.println("----------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Name           : " + name);
        System.out.println("Phone          : " + phone);
        System.out.println("Balance        : " + balance);
    }
}

public class BankingManagementSystem {

    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // Find account using account number
    static BankAccount findAccount(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.accountNumber == accountNumber) {
                return account;
            }
        }
        return null;
    }

    static void createAccount() {
        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();
        sc.nextLine();

        if (findAccount(accountNumber) != null) {
            System.out.println("Account already exists!");
            return;
        }

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        BankAccount account =
                new BankAccount(accountNumber, name, phone, balance);

        accounts.add(account);

        System.out.println("Account created successfully!");
    }
    static void depositMoney() {
        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Deposit Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        account.balance += amount;

        System.out.println("Money deposited successfully!");
        System.out.println("New Balance: " + account.balance);
    }

    
    static void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Withdrawal Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
        } else if (amount > account.balance) {
            System.out.println("Insufficient balance!");
        } else {
            account.balance -= amount;

            System.out.println("Money withdrawn successfully!");
            System.out.println("Remaining Balance: " + account.balance);
        }
    }

    static void checkBalance() {
        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
        } else {
            System.out.println("Account Holder: " + account.name);
            System.out.println("Current Balance: " + account.balance);
        }
    }

    static void searchAccount() {
        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
        } else {
            account.display();
        }
    }

    static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available!");
            return;
        }

        System.out.println("\n===== ALL ACCOUNTS =====");

        for (BankAccount account : accounts) {
            account.display();
        }
    }

    static void updateAccount() {
        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();
        sc.nextLine();

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter New Name: ");
        account.name = sc.nextLine();

        System.out.print("Enter New Phone Number: ");
        account.phone = sc.nextLine();

        System.out.println("Account updated successfully!");
    }


    static void deleteAccount() {
        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
        } else {
            accounts.remove(account);
            System.out.println("Account deleted successfully!");
        }
    }

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("   BANKING MANAGEMENT SYSTEM");
            System.out.println("================================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Search Account");
            System.out.println("6. Display All Accounts");
            System.out.println("7. Update Account");
            System.out.println("8. Delete Account");
            System.out.println("9. Exit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    depositMoney();
                    break;

                case 3:
                    withdrawMoney();
                    break;

                case 4:
                    checkBalance();
                    break;

                case 5:
                    searchAccount();
                    break;

                case 6:
                    displayAllAccounts();
                    break;

                case 7:
                    updateAccount();
                    break;

                case 8:
                    deleteAccount();
                    break;

                case 9:
                    System.out.println("Thank you for using Banking Management System!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 9);

        sc.close();
    }
}
