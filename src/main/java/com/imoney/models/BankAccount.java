package com.imoney.models;

public class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private double overdraftLimit;

    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.overdraftLimit = 500.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("✓ Deposited £%.2f. New balance: £%.2f%n", amount, balance);
        } else {
            System.out.println("✗ Deposit amount must be positive");
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("✗ Withdrawal amount must be positive");
            return false;
        }

        double newBalance = balance - amount;

        if (newBalance < -overdraftLimit) {
            System.out.printf("✗ Transaction declined: Would exceed overdraft limit (£%.2f)%n",
                overdraftLimit);
            return false;
        }

        balance = newBalance;
        System.out.printf("✓ Withdrawn £%.2f. New balance: £%.2f%n", amount, balance);
        return true;
    }

    public boolean isOverdrawn() {
        return balance < 0;
    }

    public String getAccountStatus() {
        if (balance >= 0) {
            return "ACTIVE";
        } else if (balance > -overdraftLimit) {
            return "OVERDRAWN";
        } else {
            return "LIMIT_REACHED";
        }
    }

    @Override
    public String toString() {
        return String.format("Account: %s | Holder: %s | Balance: £%.2f | Status: %s",
            accountNumber, accountHolderName, balance, getAccountStatus());
    }
}
