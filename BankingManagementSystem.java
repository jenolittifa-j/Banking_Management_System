import java.io.*;
import java.util.Scanner;

public class BankingManagementSystem {

    static Scanner sc = new Scanner(System.in);

    static int accountNumber;
    static String name;
    static double balance;

    static final String FILE_NAME = "bank.json";

    public static void main(String[] args) {

        loadData();

        while (true) {

            System.out.println("\n===== BANKING MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    viewAccount();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    withdraw();
                    break;

                case 5:
                    checkBalance();
                    break;

                case 6:
                    saveData();
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void createAccount() {

        System.out.println("\n===== CREATE ACCOUNT =====");

        System.out.print("Enter Account Number: ");
        accountNumber = sc.nextInt();

        sc.nextLine();

        System.out.print("Enter Name: ");
        name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        balance = sc.nextDouble();

        saveData();

        System.out.println(
                "Account created successfully!"
        );
    }

    static void viewAccount() {

        if (accountNumber == 0) {

            System.out.println(
                    "No account found!"
            );

            return;
        }

        System.out.println("\n===== ACCOUNT DETAILS =====");

        System.out.println(
                "Account Number: " + accountNumber
        );

        System.out.println(
                "Name: " + name
        );

        System.out.println(
                "Balance: ₹" + balance
        );
    }

    static void deposit() {

        if (accountNumber == 0) {

            System.out.println(
                    "Please create an account first!"
            );

            return;
        }

        System.out.print("Enter deposit amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {

            System.out.println(
                    "Invalid amount!"
            );

            return;
        }

        balance = balance + amount;

        saveData();

        System.out.println(
                "Amount deposited successfully!"
        );

        System.out.println(
                "New Balance: ₹" + balance
        );
    }

    static void withdraw() {

        if (accountNumber == 0) {

            System.out.println(
                    "Please create an account first!"
            );

            return;
        }

        System.out.print("Enter withdrawal amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {

            System.out.println(
                    "Invalid amount!"
            );

            return;
        }

        if (amount > balance) {

            System.out.println(
                    "Insufficient balance!"
            );

            return;
        }

        balance = balance - amount;

        saveData();

        System.out.println(
                "Amount withdrawn successfully!"
        );

        System.out.println(
                "Remaining Balance: ₹" + balance
        );
    }

    static void checkBalance() {

        if (accountNumber == 0) {

            System.out.println(
                    "No account found!"
            );

            return;
        }

        System.out.println(
                "Current Balance: ₹" + balance
        );
    }

    static void saveData() {

        try {

            FileWriter writer =
                    new FileWriter(FILE_NAME);

            writer.write("{\n");

            writer.write(
                    "  \"accountNumber\": "
                            + accountNumber + ",\n"
            );

            writer.write(
                    "  \"name\": \""
                            + name + "\",\n"
            );

            writer.write(
                    "  \"balance\": "
                            + balance + "\n"
            );

            writer.write("}");

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving data!"
            );
        }
    }

    static void loadData() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.startsWith(
                        "\"accountNumber\"")) {

                    String value =
                            line.substring(
                                    line.indexOf(":") + 1
                            );

                    value =
                            value.replace(",", "").trim();

                    accountNumber =
                            Integer.parseInt(value);
                }

                else if (line.startsWith(
                        "\"name\"")) {

                    String value =
                            line.substring(
                                    line.indexOf(":") + 1
                            );

                    value =
                            value.replace(",", "").trim();

                    name =
                            value.replace("\"", "");
                }

                else if (line.startsWith(
                        "\"balance\"")) {

                    String value =
                            line.substring(
                                    line.indexOf(":") + 1
                            );

                    value =
                            value.replace(",", "").trim();

                    balance =
                            Double.parseDouble(value);
                }
            }

            reader.close();

        } catch (Exception e) {

            System.out.println(
                    "Error reading JSON file!"
            );
        }
    }
}
