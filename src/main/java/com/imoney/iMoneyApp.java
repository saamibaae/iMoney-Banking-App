package com.imoney;

import com.imoney.models.*;
import com.imoney.managers.*;
import com.imoney.enums.*;
import java.util.Calendar;
import java.util.Date;

public class iMoneyApp {

    private BankAccount bankAccount;
    private ExpenseTracker expenseTracker;
    private NotificationManager notificationManager;
    private SavingsManager savingsManager;
    private OverdraftCalculator overdraftCalculator;

    public iMoneyApp(BankAccount account) {
        this.bankAccount = account;
        this.expenseTracker = new ExpenseTracker();
        this.notificationManager = new NotificationManager();
        this.savingsManager = new SavingsManager();
        this.overdraftCalculator = new OverdraftCalculator(account);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public ExpenseTracker getExpenseTracker() {
        return expenseTracker;
    }

    public void setExpenseTracker(ExpenseTracker expenseTracker) {
        this.expenseTracker = expenseTracker;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public SavingsManager getSavingsManager() {
        return savingsManager;
    }

    public void setSavingsManager(SavingsManager savingsManager) {
        this.savingsManager = savingsManager;
    }

    public OverdraftCalculator getOverdraftCalculator() {
        return overdraftCalculator;
    }

    public void setOverdraftCalculator(OverdraftCalculator overdraftCalculator) {
        this.overdraftCalculator = overdraftCalculator;
    }

    public void initializeSampleData() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("          INITIALIZING SAMPLE DATA (10+ TRANSACTIONS)");
        System.out.println("=".repeat(70) + "\n");

        Calendar cal = Calendar.getInstance();

        System.out.println("Adding FOOD transactions...");

        cal.set(2026, Calendar.FEBRUARY, 1);
        expenseTracker.addManualTransaction("Grocery Shopping", 52.0,
            TransactionCategory.FOOD, cal.getTime(), "Weekly shopping", "John Doe");
        bankAccount.withdraw(52.0);

        cal.set(2026, Calendar.FEBRUARY, 5);
        expenseTracker.syncTransaction("Tesco Supermarket", 48.0,
            TransactionCategory.FOOD, cal.getTime(), "Groceries", "TXN001");
        bankAccount.withdraw(48.0);

        cal.set(2026, Calendar.FEBRUARY, 10);
        expenseTracker.addManualTransaction("Sainsbury's", 55.0,
            TransactionCategory.FOOD, cal.getTime(), "Food expenses", "John Doe");
        bankAccount.withdraw(55.0);

        cal.set(2026, Calendar.FEBRUARY, 15);
        expenseTracker.syncTransaction("Asda", 50.0,
            TransactionCategory.FOOD, cal.getTime(), "Monthly shopping", "TXN002");
        bankAccount.withdraw(50.0);

        System.out.println("  ✓ Food subtotal: £205 (EXCEEDS £200 limit)");

        System.out.println("\nAdding TRANSPORTATION transactions...");

        cal.set(2026, Calendar.FEBRUARY, 2);
        expenseTracker.syncTransaction("Uber", 35.0,
            TransactionCategory.TRANSPORTATION, cal.getTime(), "Taxi to work", "TXN003");
        bankAccount.withdraw(35.0);

        cal.set(2026, Calendar.FEBRUARY, 8);
        expenseTracker.addManualTransaction("Petrol Station", 40.0,
            TransactionCategory.TRANSPORTATION, cal.getTime(), "Fuel", "John Doe");
        bankAccount.withdraw(40.0);

        cal.set(2026, Calendar.FEBRUARY, 12);
        expenseTracker.syncTransaction("Train Ticket", 45.0,
            TransactionCategory.TRANSPORTATION, cal.getTime(), "Commute", "TXN004");
        bankAccount.withdraw(45.0);

        cal.set(2026, Calendar.FEBRUARY, 16);
        expenseTracker.addManualTransaction("Bus Pass", 50.0,
            TransactionCategory.TRANSPORTATION, cal.getTime(), "Monthly pass", "John Doe");
        bankAccount.withdraw(50.0);

        System.out.println("  ✓ Transportation subtotal: £170 (EXCEEDS £160 limit)");

        System.out.println("\nAdding BILLS transaction...");

        cal.set(2026, Calendar.FEBRUARY, 1);
        expenseTracker.syncTransaction("Electricity Bill", 85.0,
            TransactionCategory.BILLS, cal.getTime(), "Utility payment", "TXN005");
        bankAccount.withdraw(85.0);

        System.out.println("  ✓ Bills subtotal: £85 (WITHIN £212 limit)");

        System.out.println("\nAdding SHOPPING transaction...");

        cal.set(2026, Calendar.FEBRUARY, 5);
        expenseTracker.addManualTransaction("Zara", 95.0,
            TransactionCategory.SHOPPING, cal.getTime(), "Clothing", "John Doe");
        bankAccount.withdraw(95.0);

        System.out.println("  ✓ Shopping subtotal: £95 (WITHIN £128 limit)");

        System.out.println("\nAdding additional transaction...");

        cal.set(2026, Calendar.FEBRUARY, 17);
        expenseTracker.syncTransaction("McDonald's", 15.0,
            TransactionCategory.FOOD, cal.getTime(), "Fast food", "TXN006");
        bankAccount.withdraw(15.0);

        System.out.println("  ✓ Additional food: £15");

        System.out.println("\n" + "-".repeat(70));
        System.out.println("✅ Sample data initialized successfully!");
        System.out.println("   Total transactions: " + expenseTracker.getAllTransactions().size());
        System.out.println("   Food total: £220 (EXCEEDS £200 monthly limit) ❌");
        System.out.println("   Transportation total: £170 (EXCEEDS £160 monthly limit) ❌");
        System.out.println("   Bills total: £85 (WITHIN limit) ✅");
        System.out.println("   Shopping total: £95 (WITHIN limit) ✅");
        System.out.println("-".repeat(70) + "\n");
    }

    public void processTransaction(Transaction transaction) {
        expenseTracker.addTransaction(transaction);
        bankAccount.withdraw(transaction.getAmount());
        checkAndNotifyBudgetLimits();
        checkAndNotifyOverdraft();
    }

    public void addManualExpense(String itemName, double amount, TransactionCategory category,
                                Date date, String comments) {
        ManualTransaction transaction = expenseTracker.addManualTransaction(
            itemName, amount, category, date, comments, "User");
        bankAccount.withdraw(amount);
        checkAndNotifyBudgetLimits();
        checkAndNotifyOverdraft();
    }

    public void checkAndNotifyBudgetLimits() {
        for (TransactionCategory category : TransactionCategory.values()) {
            if (category == TransactionCategory.OTHER) continue;

            BudgetStatus status = expenseTracker.checkBudgetStatus(category);
            SpendingLimit limit = expenseTracker.getSpendingLimitByCategory(category);

            if (limit == null) continue;

            double spent = expenseTracker.getMonthlySpentByCategory(category);
            double limitAmount = limit.getMonthlyLimit();

            if (status == BudgetStatus.LIMIT_EXCEEDED) {
                String message = String.format("%s spending EXCEEDED monthly limit! " +
                    "Spent: £%.2f / £%.2f", category.getDisplayName(), spent, limitAmount);
                notificationManager.createNotification(
                    NotificationType.BUDGET_EXCEEDED, message, Priority.CRITICAL);
            } else if (status == BudgetStatus.APPROACHING_LIMIT) {
                String message = String.format("%s spending approaching limit! " +
                    "Spent: £%.2f / £%.2f (%.1f%%)",
                    category.getDisplayName(), spent, limitAmount, (spent/limitAmount)*100);
                notificationManager.createNotification(
                    NotificationType.BUDGET_WARNING, message, Priority.HIGH);
            }
        }
    }

    public void checkAndNotifyOverdraft() {
        if (bankAccount.isOverdrawn()) {
            double overdraftAmount = overdraftCalculator.getOverdraftAmount();
            double dailyCharge = overdraftCalculator.calculateDailyCharge();

            String message = String.format("Account overdrawn! Amount: £%.2f | " +
                "Daily charge: £%.4f (9p per £100)", overdraftAmount, dailyCharge);
            notificationManager.createNotification(
                NotificationType.OVERDRAFT_CHARGE, message, Priority.HIGH);
        }
    }

    public void processOverdraftCharges(int days) {
        if (bankAccount.isOverdrawn()) {
            overdraftCalculator.applyOverdraftCharge(days);
        }
    }

    public void displayAccountSummary() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                      ACCOUNT SUMMARY");
        System.out.println("=".repeat(70) + "\n");

        System.out.printf("Account Number     : %s%n", bankAccount.getAccountNumber());
        System.out.printf("Account Holder     : %s%n", bankAccount.getAccountHolderName());
        System.out.printf("Current Balance    : £%.2f%n", bankAccount.getBalance());
        System.out.printf("Overdraft Limit    : £%.2f%n", bankAccount.getOverdraftLimit());
        System.out.printf("Account Status     : %s%n", bankAccount.getAccountStatus());

        if (bankAccount.isOverdrawn()) {
            System.out.printf("⚠️  Overdraft Amount : £%.2f%n",
                overdraftCalculator.getOverdraftAmount());
        }
        System.out.println();
    }

    public String generateExpenseReport() {
        StringBuilder report = new StringBuilder();
        report.append("\n" + "=".repeat(70) + "\n");
        report.append("                 SPENDING SUMMARY BY CATEGORY\n");
        report.append("=".repeat(70) + "\n\n");

        for (TransactionCategory category : TransactionCategory.values()) {
            if (category == TransactionCategory.OTHER) continue;

            double totalSpent = expenseTracker.getTotalSpentByCategory(category);
            double monthlySpent = expenseTracker.getMonthlySpentByCategory(category);
            SpendingLimit limit = expenseTracker.getSpendingLimitByCategory(category);

            if (limit != null) {
                double monthlyLimit = limit.getMonthlyLimit();
                String status = monthlySpent > monthlyLimit ? "❌ EXCEEDED" : "✅ OK";

                report.append(String.format("%-15s: £%.2f / £%.2f (Monthly) %s%n",
                    category.getDisplayName(), monthlySpent, monthlyLimit, status));
            }
        }

        TransactionCategory highest = expenseTracker.getHighestSpendingCategory();
        if (highest != null) {
            report.append(String.format("%n🔴 HIGHEST SPENDING: %s (£%.2f)%n",
                highest.getDisplayName(),
                expenseTracker.getTotalSpentByCategory(highest)));
        }

        return report.toString();
    }

    public void displaySavingsGoals() {
        savingsManager.displayAllSavingGoals();
    }

    public void displayNotifications() {
        notificationManager.displayNotifications();
    }

    public void run() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                     iMONEY BANKING APP");
        System.out.println("=".repeat(70) + "\n");

        initializeSampleData();

        displayAccountSummary();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("  DEMONSTRATING POLYMORPHISM - displayTransactionDetails()");
        System.out.println("=".repeat(70));
        expenseTracker.displayAllTransactions();

        System.out.println(generateExpenseReport());

        System.out.println("\n" + "=".repeat(70));
        System.out.println("              CHECKING BUDGET LIMITS AND ALERTS");
        System.out.println("=".repeat(70) + "\n");
        checkAndNotifyBudgetLimits();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("               CHECKING OVERDRAFT STATUS");
        System.out.println("=".repeat(70) + "\n");
        checkAndNotifyOverdraft();

        overdraftCalculator.displayOverdraftInfo();

        displaySavingsGoals();

        displayNotifications();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("                     END OF REPORT");
        System.out.println("=".repeat(70) + "\n");
    }

    public static void main(String[] args) {

        BankAccount account = new BankAccount("12345678", "John Doe", 500.0);

        iMoneyApp app = new iMoneyApp(account);
        app.run();
    }
}
