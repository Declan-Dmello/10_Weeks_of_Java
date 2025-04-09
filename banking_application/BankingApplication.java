package banking_application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;



class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}



//recording the users transactions
class Transaction {
    private Date date;
    private String type;
    private double amount;
    private String description;

    public Transaction(String type, double amount, String description) {
        this.date = new Date();
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s  --> %s: ₹%.2f - %s", date, type, amount, description);
    }
}

interface Account {
    String getId();
    String getOwnerName();
    double getBalance();
    void deposit(double amount);
    void withdraw(double amount) throws InsufficientFundsException;
    List<Transaction> getTransactionHistory();
}





abstract class BankAccount implements Account {
    protected String id;
    protected String ownerName;
    protected double balance;
    protected List<Transaction> transactions;

    public BankAccount(String id, String ownerName, double initialDeposit) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();

        if (initialDeposit > 0) {
            this.transactions.add(new Transaction("DEPOSIT", initialDeposit, "Initial deposit"));
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("DEPOSIT", amount, "Deposit"));
        }
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            return;
        }

        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }

        balance -= amount;
        transactions.add(new Transaction("WITHDRAWAL", amount, "Withdrawal"));
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactions);
    }

    @Override
    public String toString() {
        return String.format("%s - %s - Balance: ₹%.2f", id, ownerName, balance);
    }
}

// Checking account implementation
class CheckingAccount extends BankAccount {
    public CheckingAccount(String id, String ownerName, double initialDeposit) {
        super(id, ownerName, initialDeposit);
    }

    @Override
    public String toString() {
        return "Checking: " + super.toString();
    }
}

// Savings account implementation
class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String id, String ownerName, double initialDeposit, double interestRate) {
        super(id, ownerName, initialDeposit);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        transactions.add(new Transaction("INTEREST", interest, "Interest applied"));
    }

    @Override
    public String toString() {
        return "Savings: " + super.toString() + String.format(" (Rate: %.1f%%)", interestRate * 100);
    }
}

// Bank class to manage accounts
class Bank {
    private String name;
    private List<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account findAccount(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    public void transfer(String fromId, String toId, double amount) throws InsufficientFundsException {
        Account fromAccount = findAccount(fromId);
        Account toAccount = findAccount(toId);

        if (fromAccount == null || toAccount == null) {
            System.out.println("One or both accounts not found");
            return;
        }

        // Execute the transfer
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }

    public String getName() {
        return name;
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
}

// Main class to demonstrate the banking application
public class BankingApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank("The PG Bank");

    public static void main(String[] args) {
        System.out.println("Welcome to " + bank.getName() + "!");
        System.out.println("--------------------------------");

        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getUserChoice(0, 6);

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    listAccounts();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    transfer();
                    break;
                case 6:
                    viewTransactions();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using " + bank.getName() + "!");
                    break;
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("1. Create a new account");
        System.out.println("2. List all accounts");
        System.out.println("3. Deposit money");
        System.out.println("4. Withdraw money");
        System.out.println("5. Transfer money");
        System.out.println("6. View transaction history");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");
    }

    private static int getUserChoice(int min, int max) {
        int choice = -1;

        while (choice < min || choice > max) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < min || choice > max) {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }

        return choice;
    }

    private static void createAccount() {
        System.out.println("\nCreate an account");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Account type:");
        System.out.println("1. Checking Acc");
        System.out.println("2. Savings Acc");
        System.out.print("Your choice: ");
        int accountType = getUserChoice(1, 2);

        System.out.print("Enter initial deposit amount: ₹");
        double initialDeposit = getAmount();

        String accountId;
        if (accountType == 1) {
            accountId = "C" + (bank.getAllAccounts().size() + 1);
            CheckingAccount account = new CheckingAccount(accountId, name, initialDeposit);
            bank.addAccount(account);
            System.out.println("Checking account created successfully!");
        } else {
            accountId = "S" + (bank.getAllAccounts().size() + 1);
            double interestRate = 0.05; // 5% interest rate
            SavingsAccount account = new SavingsAccount(accountId, name, initialDeposit, interestRate);
            bank.addAccount(account);
            System.out.println("Savings account created successfully!");
        }

        System.out.println("Your account ID is: " + accountId);
    }

    private static void listAccounts() {
        System.out.println("\nAll Available Accounts");
        List<Account> accounts = bank.getAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    private static void deposit() {
        System.out.println("\n--- Deposit Money ---");
        String accountId = getAccountId();
        if (accountId == null) return;

        Account account = bank.findAccount(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount to deposit: ₹");
        double amount = getAmount();

        account.deposit(amount);
        System.out.printf("Successfully deposited ₹%.2f to account %s\n", amount, accountId);
        System.out.printf("New balance: ₹%.2f\n", account.getBalance());
    }

    private static void withdraw() {
        System.out.println("\n--- Withdraw Money ---");
        String accountId = getAccountId();
        if (accountId == null) return;

        Account account = bank.findAccount(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount to withdraw: ₹");
        double amount = getAmount();

        try {
            account.withdraw(amount);
            System.out.printf("Successfully withdrew ₹%.2f from acc %s\n", amount, accountId);
            System.out.printf("New balance: ₹%.2f\n", account.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.printf("The Current balance is : ₹%.2f\n", account.getBalance());
        }
    }

    private static void transfer() {
        System.out.println("\nTransfer money (from acc -> to acc)");
        System.out.println("From account:");
        String fromAccountId = getAccountId();
        if (fromAccountId == null) return;

        System.out.println("To account:");
        String toAccountId = getAccountId();
        if (toAccountId == null) return;

        if (fromAccountId.equals(toAccountId)) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }

        Account fromAccount = bank.findAccount(fromAccountId);
        Account toAccount = bank.findAccount(toAccountId);

        if (fromAccount == null || toAccount == null) {
            System.out.println("One or both accounts not found.");
            return;
        }

        System.out.print("Enter amount to transfer: ₹");
        double amount = getAmount();

        try {
            bank.transfer(fromAccountId, toAccountId, amount);
            System.out.printf("Successfully transferred ₹%.2f from account %s to account %s\n",
                    amount, fromAccountId, toAccountId);
            System.out.printf("Source account balance: ₹%.2f\n", fromAccount.getBalance());
            System.out.printf("Destination account balance: ₹%.2f\n", toAccount.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.printf("Current balance: ₹%.2f\n", fromAccount.getBalance());
        }
    }

    private static void viewTransactions() {
        System.out.println("\nTransaction History");
        String accountId = getAccountId();
        if (accountId == null) return;

        Account account = bank.findAccount(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        List<Transaction> transactions = account.getTransactionHistory();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }

        System.out.println("Transaction history for account " + accountId + ":");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static String getAccountId() {
        System.out.print("Enter account ID (or 'c' to cancel): ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("c")) {
            return null;
        }

        return input;
    }

    private static double getAmount() {
        double amount = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) {
                    System.out.print("Enter an amount greater than 0: ₹");

                }

                else {
                    validInput = true;
                }



            } catch (NumberFormatException e) {
                System.out.print("Enter a valid amount: ₹");
                System.out.println( "Error Message" + e.getMessage());
            }
        }

        return amount;
    }
}