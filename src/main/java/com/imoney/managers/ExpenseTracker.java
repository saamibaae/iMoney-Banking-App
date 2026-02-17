package com.imoney.managers;

import com.imoney.models.*;
import com.imoney.enums.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpenseTracker {

    private ArrayList<Transaction> transactions;
    private ArrayList<SpendingLimit> spendingLimits;

    public ExpenseTracker() {
        this.transactions = new ArrayList<>();
        this.spendingLimits = new ArrayList<>();
        initializeDefaultSpendingLimits();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<SpendingLimit> getSpendingLimits() {
        return spendingLimits;
    }

    public void setSpendingLimits(ArrayList<SpendingLimit> spendingLimits) {
        this.spendingLimits = spendingLimits;
    }

    public void initializeDefaultSpendingLimits() {
        spendingLimits.add(new SpendingLimit(TransactionCategory.TRANSPORTATION, 33, 40, 132, 160));
        spendingLimits.add(new SpendingLimit(TransactionCategory.BILLS, 50, 53, 200, 212));
        spendingLimits.add(new SpendingLimit(TransactionCategory.FOOD, 45, 50, 180, 200));
        spendingLimits.add(new SpendingLimit(TransactionCategory.SHOPPING, 30, 32, 120, 128));

        System.out.println("✓ Default spending limits initialized from Table 2 (PDF)");
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ManualTransaction addManualTransaction(String itemName, double amount,
                                                 TransactionCategory category, Date date,
                                                 String comments, String enteredBy) {
        ManualTransaction transaction = new ManualTransaction(itemName, amount, category,
                                                             date, comments, enteredBy);
        transactions.add(transaction);
        return transaction;
    }

    public SyncedTransaction syncTransaction(String itemName, double amount,
                                            TransactionCategory category, Date date,
                                            String comments, String bankId) {
        SyncedTransaction transaction = new SyncedTransaction(itemName, amount, category,
                                                             date, comments, bankId);
        transactions.add(transaction);
        return transaction;
    }

    public void addSpendingLimit(SpendingLimit limit) {
        spendingLimits.add(limit);
    }

    public SpendingLimit getSpendingLimitByCategory(TransactionCategory category) {
        for (SpendingLimit limit : spendingLimits) {
            if (limit.getCategory() == category) {
                return limit;
            }
        }
        return null;
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Transaction> getTransactionsByCategory(TransactionCategory category) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory() == category) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            Date transDate = t.getTransactionDate();
            if (!transDate.before(startDate) && !transDate.after(endDate)) {
                result.add(t);
            }
        }
        return result;
    }

    public double getTotalSpentByCategory(TransactionCategory category) {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getCategory() == category) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double getMonthlySpentByCategory(TransactionCategory category) {
        double total = 0;
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        for (Transaction t : transactions) {
            cal.setTime(t.getTransactionDate());
            if (t.getCategory() == category &&
                cal.get(Calendar.MONTH) == currentMonth &&
                cal.get(Calendar.YEAR) == currentYear) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double getWeeklySpentByCategory(TransactionCategory category) {
        double total = 0;
        Calendar cal = Calendar.getInstance();
        int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int currentYear = cal.get(Calendar.YEAR);

        for (Transaction t : transactions) {
            cal.setTime(t.getTransactionDate());
            if (t.getCategory() == category &&
                cal.get(Calendar.WEEK_OF_YEAR) == currentWeek &&
                cal.get(Calendar.YEAR) == currentYear) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public BudgetStatus checkBudgetStatus(TransactionCategory category) {
        SpendingLimit limit = getSpendingLimitByCategory(category);
        if (limit == null) {
            return BudgetStatus.WITHIN_BUDGET;
        }

        double monthlySpent = getMonthlySpentByCategory(category);
        double monthlyLimit = limit.getMonthlyLimit();

        double percentage = (monthlySpent / monthlyLimit) * 100;

        if (percentage > 100) {
            return BudgetStatus.LIMIT_EXCEEDED;
        } else if (percentage >= 80) {
            return BudgetStatus.APPROACHING_LIMIT;
        } else {
            return BudgetStatus.WITHIN_BUDGET;
        }
    }

    public TransactionCategory getHighestSpendingCategory() {
        double maxSpent = 0;
        TransactionCategory highestCategory = null;

        for (TransactionCategory category : TransactionCategory.values()) {
            if (category == TransactionCategory.OTHER) continue;

            double spent = getTotalSpentByCategory(category);
            if (spent > maxSpent) {
                maxSpent = spent;
                highestCategory = category;
            }
        }

        return highestCategory;
    }

    public void displayAllTransactions() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              ALL TRANSACTIONS OVERVIEW                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("Total Transactions: " + transactions.size() + "\n");

        int count = 1;
        for (Transaction transaction : transactions) {
            System.out.printf("Transaction #%d:%n", count++);

            transaction.displayTransactionDetails();
            System.out.println();
        }
    }
}
