package com.imoney.models;

import com.imoney.enums.TransactionCategory;

public class SpendingLimit {

    private TransactionCategory category;
    private double weeklyLimit;
    private double monthlyLimit;
    private double weeklyMinThreshold;
    private double monthlyMinThreshold;

    public SpendingLimit(TransactionCategory category, double weeklyMin, double weeklyMax,
                        double monthlyMin, double monthlyMax) {
        this.category = category;
        this.weeklyMinThreshold = weeklyMin;
        this.weeklyLimit = weeklyMax;
        this.monthlyMinThreshold = monthlyMin;
        this.monthlyLimit = monthlyMax;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public double getWeeklyLimit() {
        return weeklyLimit;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public double getWeeklyMinThreshold() {
        return weeklyMinThreshold;
    }

    public double getMonthlyMinThreshold() {
        return monthlyMinThreshold;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public void setWeeklyLimit(double weeklyLimit) {
        this.weeklyLimit = weeklyLimit;
    }

    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public void setWeeklyMinThreshold(double weeklyMinThreshold) {
        this.weeklyMinThreshold = weeklyMinThreshold;
    }

    public void setMonthlyMinThreshold(double monthlyMinThreshold) {
        this.monthlyMinThreshold = monthlyMinThreshold;
    }

    public boolean isWeeklyLimitExceeded(double spent) {
        return spent > weeklyLimit;
    }

    public boolean isMonthlyLimitExceeded(double spent) {
        return spent > monthlyLimit;
    }

    public double getWeeklyRemaining(double spent) {
        return weeklyLimit - spent;
    }

    public double getMonthlyRemaining(double spent) {
        return monthlyLimit - spent;
    }

    @Override
    public String toString() {
        return String.format("%s: Weekly £%.0f-£%.0f | Monthly £%.0f-£%.0f",
            category.getDisplayName(), weeklyMinThreshold, weeklyLimit,
            monthlyMinThreshold, monthlyLimit);
    }
}
