package com.imoney.enums;

public enum TransactionCategory {
    TRANSPORTATION("Transportation"),
    BILLS("Bills"),
    FOOD("Food"),
    SHOPPING("Shopping"),
    OTHER("Other");

    private String displayName;

    private TransactionCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
