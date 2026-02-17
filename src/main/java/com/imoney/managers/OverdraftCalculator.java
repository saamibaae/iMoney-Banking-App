package com.imoney.managers;

import com.imoney.models.BankAccount;
import com.imoney.interfaces.Calculable;

public class OverdraftCalculator implements Calculable {

    private static final double DAILY_RATE_PER_100 = 0.09;

    private BankAccount account;

    public OverdraftCalculator(BankAccount account) {
        this.account = account;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public static double getDailyRatePer100() {
        return DAILY_RATE_PER_100;
    }

    public double getOverdraftAmount() {
        if (account.getBalance() < 0) {
            return Math.abs(account.getBalance());
        }
        return 0.0;
    }

    public double calculateDailyCharge() {
        double overdraftAmount = getOverdraftAmount();
        if (overdraftAmount == 0) {
            return 0.0;
        }

        return (overdraftAmount / 100.0) * DAILY_RATE_PER_100;
    }

    public double calculateChargeForPeriod(int days) {
        return calculateDailyCharge() * days;
    }

    public void applyOverdraftCharge(int days) {
        double charge = calculateChargeForPeriod(days);
        if (charge > 0) {
            double newBalance = account.getBalance() - charge;
            account.setBalance(newBalance);
            System.out.printf("⚠️  Overdraft charge applied: £%.2f for %d days%n", charge, days);
        }
    }

    @Override
    public double calculate() {
        return calculateDailyCharge();
    }

    public void displayOverdraftInfo() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              OVERDRAFT INFORMATION                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        double overdraftAmount = getOverdraftAmount();

        if (overdraftAmount == 0) {
            System.out.println("✅ No overdraft - Account balance is positive");
            return;
        }

        System.out.printf("Overdraft Amount      : £%.2f%n", overdraftAmount);
        System.out.printf("Daily Interest Rate   : £%.2f per £100 (9p)%n", DAILY_RATE_PER_100);
        System.out.printf("Daily Charge          : £%.4f%n", calculateDailyCharge());
        System.out.printf("Estimated Weekly      : £%.2f (7 days)%n", calculateChargeForPeriod(7));
        System.out.printf("Estimated Monthly     : £%.2f (30 days)%n", calculateChargeForPeriod(30));
        System.out.println();
    }
}
