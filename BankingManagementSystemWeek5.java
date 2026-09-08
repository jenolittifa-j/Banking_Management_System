import java.io.*;
import java.util.*;

class Account {
    int accountNo;
    String name;
    double balance;

    Account(int accountNo, String name, double balance) {
        this.accountNo = accountNo;
        this.name = name;
        this.balance = balance;
    }
}

public class BankingManagementSystemWeek5{

    static Scanner sc = new Scanner(System.in);
    static String fileName = "accounts.txt";

    // Create Account
    static void createAccount() throws IOException {

        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        if (findAccount(accNo) != null) {
            System.out.println("Account already exists!");
            return;
        }

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Deposit: ");
        double balance = sc.nextDouble();

        BufferedWriter writer =
                new BufferedWriter(new FileWriter(fileName, true));

        writer.write(accNo + "," + name + "," + balance);
        writer.newLine();

        writer.close();

        System.out.println("Account created successfully!");
    }

  
    static Account findAccount(int accNo) throws IOException {

        File file = new File(fileName);

        if (!file.exists()) {
            return null;
        }

        BufferedReader reader =
                new BufferedReader(new FileReader(file));

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            int number = Integer.parseInt(data[0]);

            if (number == accNo) {

                String name = data[1];
                double balance = Double.parseDouble(data[2]);

                reader.close();

                return new Account(number, name, balance);
            }
        }

        reader.close();

        return null;
    }


    static void deposit() throws IOException {

        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account account = findAccount(accNo);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Deposit Amount: ");
        double amount = sc.nextDouble();

        account.balance = account.balance + amount;

        updateAccount(account);

        System.out.println("Money deposited successfully!");
        System.out.println("Current Balance: " + account.balance);
    }


    static void withdraw() throws IOException {

        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account account = findAccount(accNo);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Withdrawal Amount: ");
        double amount = sc.nextDouble();

        if (amount > account.balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        account.balance = account.balance - amount;

        updateAccount(account);

        System.out.println("Money withdrawn successfully!");
        System.out.println("Current Balance: " + account.balance);
    }

   
    static void checkBalance() throws IOException {

        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account account = findAccount(accNo);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.println("Account Number : " + account.accountNo);
        System.out.println("Account Name   : " + account.name);
        System.out.println("Balance        : " + account.balance);
    }

   
    static void updateAccount(Account updated) throws IOException {

        File file = new File(fileName);

        BufferedReader reader =
                new BufferedReader(new FileReader(file));

        ArrayList<String> list = new ArrayList<>();

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            int accNo = Integer.parseInt(data[0]);

            if (accNo == updated.accountNo) {

                list.add(updated.accountNo + "," +
                         updated.name + "," +
                         updated.balance);

            } else {

                list.add(line);
            }
        }

        reader.close();

        BufferedWriter writer =
                new BufferedWriter(new FileWriter(file));

        for (String data : list) {
            writer.write(data);
            writer.newLine();
        }

        writer.close();
    }

   
    public static void main(String[] args) throws IOException {

        while (true) {

            System.out.println("\n==============================");
            System.out.println(" BANKING MANAGEMENT SYSTEM");
            System.out.println("==============================");

            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    deposit();
                    break;

                case 3:
                    withdraw();
                    break;

                case 4:
                    checkBalance();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}