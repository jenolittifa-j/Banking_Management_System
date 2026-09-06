import java.util.Scanner;
import java.util.TreeMap;

public class BankingManagementWeek4{

    static TreeMap<Integer, String> customers = new TreeMap<>();
    static TreeMap<Integer, Double> balances = new TreeMap<>();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== BANKING MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Exit");
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
                    displayAccounts();
                    break;

                case 6:
                    System.out.println("Thank you for using the Banking System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }

    // Create Account
    static void createAccount() {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        if (customers.containsKey(accountNumber)) {
            System.out.println("Account already exists!");
            return;
        }

        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double amount = sc.nextDouble();

        customers.put(accountNumber, name);
        balances.put(accountNumber, amount);

        System.out.println("Account created successfully!");
    }

    // Deposit
    static void depositMoney() {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        if (!customers.containsKey(accountNumber)) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Deposit Amount: ");
        double amount = sc.nextDouble();

        balances.put(
            accountNumber,
            balances.get(accountNumber) + amount
        );

        System.out.println("Money deposited successfully!");
    }

    // Withdraw
    static void withdrawMoney() {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        if (!customers.containsKey(accountNumber)) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Withdraw Amount: ");
        double amount = sc.nextDouble();

        if (amount > balances.get(accountNumber)) {
            System.out.println("Insufficient balance!");
        } else {
            balances.put(
                accountNumber,
                balances.get(accountNumber) - amount
            );

            System.out.println("Money withdrawn successfully!");
        }
    }

    // Check Balance
    static void checkBalance() {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        if (!customers.containsKey(accountNumber)) {
            System.out.println("Account not found!");
            return;
        }

        System.out.println("Customer Name: " +
                customers.get(accountNumber));

        System.out.println("Balance: ₹" +
                balances.get(accountNumber));
    }

    // Display All Accounts
    static void displayAccounts() {

        if (customers.isEmpty()) {
            System.out.println("No accounts available!");
            return;
        }

        System.out.println("\n===== ALL ACCOUNTS =====");

        for (Integer accountNumber : customers.keySet()) {

            System.out.println(
                "Account No: " + accountNumber +
                " | Name: " + customers.get(accountNumber) +
                " | Balance: ₹" + balances.get(accountNumber)
            );
        }
    }
}
