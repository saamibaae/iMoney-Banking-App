package com.imoney.models;

import java.util.Date;

public class SavingGoal extends BaseEntity {

    private String goalName;
    private double perWeekAmount;
    private double perMonthAmount;
    private double targetAmount;
    private Date targetDate;
    private double currentSaved;
    private boolean isActive;

    public SavingGoal(String goalName, double targetAmount, Date targetDate) {
        super();
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.currentSaved = 0.0;
        this.isActive = true;
        this.perWeekAmount = 0.0;
        this.perMonthAmount = 0.0;
    }

    public String getGoalName() {
        return goalName;
    }

    public double getPerWeekAmount() {
        return perWeekAmount;
    }

    public double getPerMonthAmount() {
        return perMonthAmount;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public double getCurrentSaved() {
        return currentSaved;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setPerWeekAmount(double perWeekAmount) {
        this.perWeekAmount = perWeekAmount;
    }

    public void setPerMonthAmount(double perMonthAmount) {
        this.perMonthAmount = perMonthAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public void setCurrentSaved(double currentSaved) {
        this.currentSaved = currentSaved;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addToSavings(double amount) {
        if (amount > 0) {
            currentSaved += amount;
            System.out.printf("✓ Added £%.2f to '%s'. Total saved: £%.2f%n",
                amount, goalName, currentSaved);

            if (currentSaved >= targetAmount) {
                isActive = false;
                System.out.printf("🎉 Congratulations! Goal '%s' achieved!%n", goalName);
            }
        }
    }

    public double getProgressPercentage() {
        return (currentSaved / targetAmount) * 100.0;
    }

    public boolean isGoalAchieved() {
        return currentSaved >= targetAmount;
    }

    public double getRemainingAmount() {
        return Math.max(0, targetAmount - currentSaved);
    }

    @Override
    public String toString() {
        String frequency = perWeekAmount > 0 ?
            String.format("£%.2f/week", perWeekAmount) :
            String.format("£%.2f/month", perMonthAmount);

        return String.format("%s | Target: £%.2f by %tY | Saving: %s | Progress: £%.2f (%.1f%%)",
            goalName, targetAmount, targetDate, frequency, currentSaved, getProgressPercentage());
    }
}
