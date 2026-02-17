package com.imoney.managers;

import com.imoney.models.SavingGoal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SavingsManager {

    private ArrayList<SavingGoal> savingGoals;

    public SavingsManager() {
        this.savingGoals = new ArrayList<>();
        initializeDefaultGoals();
    }

    public ArrayList<SavingGoal> getSavingGoals() {
        return savingGoals;
    }

    public void setSavingGoals(ArrayList<SavingGoal> savingGoals) {
        this.savingGoals = savingGoals;
    }

    public void initializeDefaultGoals() {
        Calendar cal = Calendar.getInstance();

        cal.set(2027, Calendar.DECEMBER, 31);
        SavingGoal turkeyHoliday = new SavingGoal("Holiday - Visit Turkey", 500.0, cal.getTime());
        turkeyHoliday.setPerWeekAmount(10.0);
        turkeyHoliday.setCurrentSaved(120.0);
        savingGoals.add(turkeyHoliday);

        cal.set(2030, Calendar.DECEMBER, 31);
        SavingGoal newCar = new SavingGoal("Buying a New Car", 8000.0, cal.getTime());
        newCar.setPerMonthAmount(200.0);
        newCar.setCurrentSaved(1200.0);
        savingGoals.add(newCar);

        cal.set(2026, Calendar.DECEMBER, 31);
        SavingGoal emergencyFund = new SavingGoal("Emergency Fund", 1000.0, cal.getTime());
        emergencyFund.setPerWeekAmount(15.0);
        emergencyFund.setCurrentSaved(300.0);
        savingGoals.add(emergencyFund);

        System.out.println("✓ Default saving goals initialized from Table 3 (PDF)");
    }

    public void addSavingGoal(SavingGoal goal) {
        savingGoals.add(goal);
    }

    public void removeSavingGoal(SavingGoal goal) {
        savingGoals.remove(goal);
    }

    public List<SavingGoal> getAllSavingGoals() {
        return new ArrayList<>(savingGoals);
    }

    public List<SavingGoal> getActiveSavingGoals() {
        List<SavingGoal> active = new ArrayList<>();
        for (SavingGoal goal : savingGoals) {
            if (goal.isActive()) {
                active.add(goal);
            }
        }
        return active;
    }

    public void updateGoalProgress(String goalName, double amount) {
        SavingGoal goal = getGoalByName(goalName);
        if (goal != null) {
            goal.addToSavings(amount);
            if (goal.isGoalAchieved()) {
                System.out.printf("🎉 Congratulations! Goal '%s' achieved!%n", goalName);
            }
        }
    }

    public SavingGoal getGoalByName(String goalName) {
        for (SavingGoal goal : savingGoals) {
            if (goal.getGoalName().equalsIgnoreCase(goalName)) {
                return goal;
            }
        }
        return null;
    }

    public double getTotalSavingsTarget() {
        double total = 0;
        for (SavingGoal goal : savingGoals) {
            total += goal.getTargetAmount();
        }
        return total;
    }

    public double getTotalCurrentSavings() {
        double total = 0;
        for (SavingGoal goal : savingGoals) {
            total += goal.getCurrentSaved();
        }
        return total;
    }

    public void displayAllSavingGoals() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   SAVINGS GOALS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        if (savingGoals.isEmpty()) {
            System.out.println("No savings goals set.");
            return;
        }

        int index = 1;
        for (SavingGoal goal : savingGoals) {
            System.out.printf("%d. %s%n", index++, goal.getGoalName());
            System.out.printf("   Target Amount  : £%.2f by %tY%n",
                goal.getTargetAmount(), goal.getTargetDate());

            if (goal.getPerWeekAmount() > 0) {
                System.out.printf("   Saving Rate    : £%.2f per week%n", goal.getPerWeekAmount());
            } else if (goal.getPerMonthAmount() > 0) {
                System.out.printf("   Saving Rate    : £%.2f per month%n", goal.getPerMonthAmount());
            }

            System.out.printf("   Current Saved  : £%.2f (%.1f%%)%n",
                goal.getCurrentSaved(), goal.getProgressPercentage());
            System.out.printf("   Remaining      : £%.2f%n", goal.getRemainingAmount());
            System.out.printf("   Status         : %s%n%n",
                goal.isGoalAchieved() ? "✅ ACHIEVED" : "🔄 IN PROGRESS");
        }

        System.out.printf("Total Target: £%.2f | Total Saved: £%.2f%n",
            getTotalSavingsTarget(), getTotalCurrentSavings());
    }
}
