import java.util.*;

class BankAccount {
    int accountNumber;
    String name;
    double balance;

    BankAccount(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    void deposit(double amount) {
        balance = balance + amount;
        System.out.println("Amount deposited successfully!");
    }

    void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
            System.out.println("Amount withdrawn successfully!");
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    void display() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Name           : " + name);
        System.out.println("Balance        : ₹" + balance);
        System.out.println("----------------------------");
    }
}

public class BankingManagementWeek3{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

     
        LinkedHashMap<Integer, BankAccount> accounts = new LinkedHashMap<>();

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
                    System.out.print("Enter Account Number: ");
                    int accNo = sc.nextInt();

                    if (accounts.containsKey(accNo)) {
                        System.out.println("Account already exists!");
                    } else {
                        sc.nextLine();

                        System.out.print("Enter Account Holder Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Initial Deposit: ");
                        double balance = sc.nextDouble();

                        BankAccount account =
                                new BankAccount(accNo, name, balance);

                        accounts.put(accNo, account);

                        System.out.println("Account created successfully!");
                    }
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    if (accounts.containsKey(accNo)) {

                        System.out.print("Enter Deposit Amount: ");
                        double amount = sc.nextDouble();

                        if (amount > 0) {
                            accounts.get(accNo).deposit(amount);
                        } else {
                            System.out.println("Invalid amount!");
                        }

                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    if (accounts.containsKey(accNo)) {

                        System.out.print("Enter Withdrawal Amount: ");
                        double amount = sc.nextDouble();

                        if (amount > 0) {
                            accounts.get(accNo).withdraw(amount);
                        } else {
                            System.out.println("Invalid amount!");
                        }

                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    if (accounts.containsKey(accNo)) {
                        accounts.get(accNo).display();
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 5:
                    if (accounts.isEmpty()) {
                        System.out.println("No accounts available!");
                    } else {

                        System.out.println("\n===== ALL ACCOUNTS =====");

                        for (BankAccount account : accounts.values()) {
                            account.display();
                        }
                    }
                    break;

                case 6:
                    System.out.println("Thank you for using Banking Management System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}