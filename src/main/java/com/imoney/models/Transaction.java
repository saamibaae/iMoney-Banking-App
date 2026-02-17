package com.imoney.models;

import com.imoney.enums.TransactionCategory;
import java.util.Date;

public abstract class Transaction {

    private String itemName;
    private double amount;
    private TransactionCategory category;
    private Date transactionDate;
    private String comments;

    public Transaction(String itemName, double amount, TransactionCategory category,
                      Date transactionDate, String comments) {
        this.itemName = itemName;
        this.amount = amount;
        this.category = category;
        this.transactionDate = transactionDate;
        this.comments = comments;
    }

    public String getItemName() {
        return itemName;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getComments() {
        return comments;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public abstract void displayTransactionDetails();

    @Override
    public String toString() {
        return String.format("%s | £%.2f | %s | %tF | %s",
            itemName, amount, category.getDisplayName(), transactionDate, comments);
    }
}
