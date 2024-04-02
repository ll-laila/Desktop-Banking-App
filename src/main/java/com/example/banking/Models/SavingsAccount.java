package com.example.banking.Models;

import javafx.beans.property.*;

public class SavingsAccount extends Account {
    // the withdrawal Limit from the savings
    private final DoubleProperty withdrawalLimit;

    public SavingsAccount(String owner, String accountNumber, double withdrawalLimit, float balance){
        super(owner, accountNumber, balance);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "Withdrawal Limit", withdrawalLimit);

    }

    public DoubleProperty withdrawalLimitProperty() {
        return this.withdrawalLimit;
    }

    public double getWithdrawalLimit() {
        return this.withdrawalLimit.get();
    }


    public String getAccountNumber() {
        return this.accountNumber.get();
    }


    public float getBalance() {
        return this.balance.get();
    }


}
