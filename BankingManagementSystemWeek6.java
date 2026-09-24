import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BankingManagementSystemWeek6{

    static final String CUSTOMER_FILE = "customers.csv";
    static final String TRANSACTION_FILE = "transactions.csv";

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        createFiles();

        while (true) {

            System.out.println("\n====================================");
            System.out.println("       BANKING MANAGEMENT SYSTEM");
            System.out.println("====================================");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("====================================");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    createAccount();
                    break;

                case "2":
                    login();
                    break;

                case "3":
                    System.out.println("Thank you for using the system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createFiles() {

        try {

            File customerFile = new File(CUSTOMER_FILE);

            if (!customerFile.exists()) {

                PrintWriter writer =
                        new PrintWriter(
                                new FileWriter(customerFile));

                writer.println(
                        "AccountNo,Name,Phone,PIN,Balance");

                writer.close();
            }

            File transactionFile =
                    new File(TRANSACTION_FILE);

            if (!transactionFile.exists()) {

                PrintWriter writer =
                        new PrintWriter(
                                new FileWriter(transactionFile));

                writer.println(
                        "TransactionID,AccountNo,Type,Amount,Date");

                writer.close();
            }

        } catch (IOException e) {

            System.out.println(
                    "File error: " + e.getMessage());
        }
    }

    private static void createAccount() {

        System.out.println("\n========== CREATE ACCOUNT ==========");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Create 4 Digit PIN: ");
        String pin = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        if (!phone.matches("\\d+")) {
            System.out.println(
                    "Phone number must contain digits only.");
            return;
        }

        if (!pin.matches("\\d{4}")) {
            System.out.println(
                    "PIN must contain exactly 4 digits.");
            return;
        }

        int accountNo = generateAccountNumber();

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(
                                    CUSTOMER_FILE, true));

            writer.println(
                    accountNo + "," +
                    escapeCSV(name) + "," +
                    phone + "," +
                    pin + ",0.00");

            writer.close();

            System.out.println(
                    "\nAccount created successfully.");

            System.out.println(
                    "Account Number : " + accountNo);

            System.out.println(
                    "Name           : " + name);

            System.out.println(
                    "Balance        : ₹0.00");

        } catch (IOException e) {

            System.out.println(
                    "Error saving account: "
                            + e.getMessage());
        }
    }

    private static void login() {

        System.out.println("\n========== LOGIN ==========");

        System.out.print("Enter Account Number: ");
        String accountNo =
                scanner.nextLine().trim();

        System.out.print("Enter PIN: ");
        String pin =
                scanner.nextLine().trim();

        String[] account =
                findAccount(accountNo);

        if (account == null) {

            System.out.println(
                    "Account not found.");
            return;
        }

        if (!account[3].equals(pin)) {

            System.out.println(
                    "Incorrect PIN.");
            return;
        }

        System.out.println(
                "\nLogin successful!");

        System.out.println(
                "Welcome " + account[1]);

        customerMenu(accountNo);
    }

    private static void customerMenu(
            String accountNo) {

        while (true) {

            System.out.println(
                    "\n========== CUSTOMER MENU ==========");

            System.out.println("1. Account Details");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");

            String choice =
                    scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    accountDetails(accountNo);
                    break;

                case "2":
                    checkBalance(accountNo);
                    break;

                case "3":
                    deposit(accountNo);
                    break;

                case "4":
                    withdraw(accountNo);
                    break;

                case "5":
                    transactionHistory(accountNo);
                    break;

                case "6":
                    System.out.println(
                            "Logged out successfully.");
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    private static void accountDetails(
            String accountNo) {

        String[] account =
                findAccount(accountNo);

        if (account == null) {
            return;
        }

        System.out.println(
                "\n========== ACCOUNT DETAILS ==========");

        System.out.println(
                "Account Number : " + account[0]);

        System.out.println(
                "Name           : " + account[1]);

        System.out.println(
                "Phone          : " + account[2]);

        System.out.println(
                "Balance        : ₹" + account[4]);
    }

    private static void checkBalance(
            String accountNo) {

        String[] account =
                findAccount(accountNo);

        if (account == null) {
            return;
        }

        System.out.println(
                "\n========== BALANCE ==========");

        System.out.println(
                "Account Number : " + account[0]);

        System.out.println(
                "Balance        : ₹" + account[4]);
    }

    private static void deposit(
            String accountNo) {

        System.out.println(
                "\n========== DEPOSIT ==========");

        System.out.print(
                "Enter amount: ₹");

        String input =
                scanner.nextLine().trim();

        double amount;

        try {

            amount =
                    Double.parseDouble(input);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid amount.");
            return;
        }

        if (amount <= 0) {

            System.out.println(
                    "Amount must be greater than zero.");
            return;
        }

        String[] account =
                findAccount(accountNo);

        double balance =
                Double.parseDouble(account[4]);

        double newBalance =
                balance + amount;

        updateBalance(
                accountNo,
                newBalance);

        saveTransaction(
                accountNo,
                "DEPOSIT",
                amount);

        System.out.printf(
                "\n₹%.2f deposited successfully.%n",
                amount);

        System.out.printf(
                "New Balance: ₹%.2f%n",
                newBalance);
    }

    private static void withdraw(
            String accountNo) {

        System.out.println(
                "\n========== WITHDRAW ==========");

        System.out.print(
                "Enter amount: ₹");

        String input =
                scanner.nextLine().trim();

        double amount;

        try {

            amount =
                    Double.parseDouble(input);

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid amount.");
            return;
        }

        if (amount <= 0) {

            System.out.println(
                    "Amount must be greater than zero.");
            return;
        }

        String[] account =
                findAccount(accountNo);

        double balance =
                Double.parseDouble(account[4]);

        if (amount > balance) {

            System.out.println(
                    "Insufficient balance.");

            System.out.printf(
                    "Available Balance: ₹%.2f%n",
                    balance);

            return;
        }

        double newBalance =
                balance - amount;

        updateBalance(
                accountNo,
                newBalance);

        saveTransaction(
                accountNo,
                "WITHDRAW",
                amount);

        System.out.printf(
                "\n₹%.2f withdrawn successfully.%n",
                amount);

        System.out.printf(
                "Remaining Balance: ₹%.2f%n",
                newBalance);
    }

    private static void transactionHistory(
            String accountNo) {

        System.out.println(
                "\n================ TRANSACTION HISTORY ================");

        System.out.printf(
                "%-8s %-15s %-15s %-20s%n",
                "ID",
                "TYPE",
                "AMOUNT",
                "DATE");

        System.out.println(
                "------------------------------------------------------");

        boolean found = false;

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(
                                    TRANSACTION_FILE));

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        parseCSVLine(line);

                if (data.length >= 5 &&
                        data[1].equals(accountNo)) {

                    found = true;

                    System.out.printf(
                            "%-8s %-15s ₹%-14s %-20s%n",
                            data[0],
                            data[2],
                            data[3],
                            data[4]);
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println(
                    "Error reading transaction file.");
        }

        if (!found) {

            System.out.println(
                    "No transactions found.");
        }
    }

    private static String[] findAccount(
            String accountNo) {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(
                                    CUSTOMER_FILE));

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        parseCSVLine(line);

                if (data.length >= 5 &&
                        data[0].equals(accountNo)) {

                    reader.close();

                    return data;
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println(
                    "Error reading customer file.");
        }

        return null;
    }

    private static int generateAccountNumber() {

        int max = 1000;

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(
                                    CUSTOMER_FILE));

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        parseCSVLine(line);

                if (data.length > 0) {

                    try {

                        int number =
                                Integer.parseInt(data[0]);

                        if (number > max) {
                            max = number;
                        }

                    } catch (
                            NumberFormatException ignored) {
                    }
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println(
                    "Error reading customer file.");
        }

        return max + 1;
    }

    private static void updateBalance(
            String accountNo,
            double newBalance) {

        List<String[]> accounts =
                new ArrayList<>();

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(
                                    CUSTOMER_FILE));

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        parseCSVLine(line);

                if (data.length >= 5) {

                    if (data[0].equals(accountNo)) {

                        data[4] =
                                String.format(
                                        "%.2f",
                                        newBalance);
                    }

                    accounts.add(data);
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println(
                    "Error updating balance.");
            return;
        }

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(
                                    CUSTOMER_FILE));

            writer.println(
                    "AccountNo,Name,Phone,PIN,Balance");

            for (String[] account : accounts) {

                writer.println(
                        toCSVLine(account));
            }

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving customer data.");
        }
    }

    private static void saveTransaction(
            String accountNo,
            String type,
            double amount) {

        int id =
                generateTransactionId();

        String date =
                new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss")
                        .format(new Date());

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(
                                    TRANSACTION_FILE,
                                    true));

            writer.println(
                    id + "," +
                    accountNo + "," +
                    type + "," +
                    String.format(
                            "%.2f",
                            amount) + "," +
                    date);

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving transaction.");
        }
    }

    private static int generateTransactionId() {

        int max = 0;

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(
                                    TRANSACTION_FILE));

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        parseCSVLine(line);

                if (data.length > 0) {

                    try {

                        int id =
                                Integer.parseInt(data[0]);

                        if (id > max) {
                            max = id;
                        }

                    } catch (
                            NumberFormatException ignored) {
                    }
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println(
                    "Error reading transaction file.");
        }

        return max + 1;
    }

    private static String[] parseCSVLine(
            String line) {

        List<String> values =
                new ArrayList<>();

        StringBuilder current =
                new StringBuilder();

        boolean insideQuotes = false;

        for (int i = 0;
             i < line.length();
             i++) {

            char c = line.charAt(i);

            if (c == '"') {

                if (insideQuotes &&
                        i + 1 < line.length() &&
                        line.charAt(i + 1) == '"') {

                    current.append('"');
                    i++;

                } else {

                    insideQuotes =
                            !insideQuotes;
                }

            } else if (
                    c == ',' &&
                    !insideQuotes) {

                values.add(
                        current.toString().trim());

                current.setLength(0);

            } else {

                current.append(c);
            }
        }

        values.add(
                current.toString().trim());

        return values.toArray(
                new String[0]);
    }

    private static String escapeCSV(
            String value) {

        if (value.contains(",") ||
                value.contains("\"") ||
                value.contains("\n")) {

            value =
                    value.replace(
                            "\"",
                            "\"\"");

            return "\"" + value + "\"";
        }

        return value;
    }

    private static String toCSVLine(
            String[] data) {

        StringBuilder result =
                new StringBuilder();

        for (int i = 0;
             i < data.length;
             i++) {

            if (i > 0) {
                result.append(",");
            }

            result.append(
                    escapeCSV(data[i]));
        }

        return result.toString();
    }
}
