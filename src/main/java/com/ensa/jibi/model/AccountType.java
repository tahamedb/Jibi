package com.ensa.jibi.model;

public enum AccountType {
    Hsab_1(200),
    Hsab_2(5000),
    Hsab_3(20000);
    private final double accountLimit;

    AccountType(double accountLimit) {
        this.accountLimit = accountLimit;
    }

    public double getAccountLimit() {
        return accountLimit;
    }
}
