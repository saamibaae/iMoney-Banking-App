package com.imoney.models;

import com.imoney.enums.TransactionCategory;
import java.util.Date;

public class SyncedTransaction extends Transaction {

    private String bankTransactionId;
    private Date syncTimestamp;

    public SyncedTransaction(String itemName, double amount, TransactionCategory category,
                            Date transactionDate, String comments, String bankTransactionId) {

        super(itemName, amount, category, transactionDate, comments);
        this.bankTransactionId = bankTransactionId;
        this.syncTimestamp = new Date();
    }

    public String getBankTransactionId() {
        return bankTransactionId;
    }

    public Date getSyncTimestamp() {
        return syncTimestamp;
    }

    public void setBankTransactionId(String bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public void setSyncTimestamp(Date syncTimestamp) {
        this.syncTimestamp = syncTimestamp;
    }

    @Override
    public void displayTransactionDetails() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║        SYNCED TRANSACTION (Bank Auto-Sync)                ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.printf("  Item Name         : %s%n", getItemName());
        System.out.printf("  Amount            : £%.2f%n", getAmount());
        System.out.printf("  Category          : %s%n", getCategory().getDisplayName());
        System.out.printf("  Transaction Date  : %tF%n", getTransactionDate());
        System.out.printf("  Comments          : %s%n", getComments());
        System.out.printf("  Bank Trans ID     : %s%n", bankTransactionId);
        System.out.printf("  Synced At         : %tF %<tT%n", syncTimestamp);
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return String.format("[SYNCED] %s | Bank ID: %s", super.toString(), bankTransactionId);
    }
}
