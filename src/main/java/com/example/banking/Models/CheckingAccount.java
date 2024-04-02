package com.example.banking.Models;

import javafx.beans.property.*;

public class CheckingAccount extends Account{
    // the number of transacion a client is allowed to do per day
    private final IntegerProperty transactionLimit;

    public CheckingAccount(String owner, String accountNumber, int limit, float balance){
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit", limit);

    }

    public IntegerProperty transactionLimitProperty() {
        return this.transactionLimit;
    }

    public int getTransactionLimit() {
        return this.transactionLimit.get();
    }


    public String getAccountNumber() {
        return this.accountNumber.get();
    }


    public float getBalance() {
        return this.balance.get();
    }

}
