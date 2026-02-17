package com.imoney.models;

import com.imoney.enums.TransactionCategory;
import java.util.Date;

public class ManualTransaction extends Transaction {

    private String enteredBy;
    private Date entryTimestamp;

    public ManualTransaction(String itemName, double amount, TransactionCategory category,
                            Date transactionDate, String comments, String enteredBy) {

        super(itemName, amount, category, transactionDate, comments);
        this.enteredBy = enteredBy;
        this.entryTimestamp = new Date();
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public Date getEntryTimestamp() {
        return entryTimestamp;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public void setEntryTimestamp(Date entryTimestamp) {
        this.entryTimestamp = entryTimestamp;
    }

    @Override
    public void displayTransactionDetails() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║        MANUAL TRANSACTION (User Entry)                    ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.printf("  Item Name         : %s%n", getItemName());
        System.out.printf("  Amount            : £%.2f%n", getAmount());
        System.out.printf("  Category          : %s%n", getCategory().getDisplayName());
        System.out.printf("  Transaction Date  : %tF%n", getTransactionDate());
        System.out.printf("  Comments          : %s%n", getComments());
        System.out.printf("  Entered By        : %s%n", enteredBy);
        System.out.printf("  Entry Time        : %tF %<tT%n", entryTimestamp);
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return String.format("[MANUAL] %s | By: %s", super.toString(), enteredBy);
    }
}
