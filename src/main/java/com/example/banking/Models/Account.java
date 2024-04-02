package com.example.banking.Models;

import javafx.beans.property.*;

public class Account {
    public final StringProperty owner;
    public final StringProperty accountNumber;
    public final FloatProperty balance;



    public Account(String owner, String accountNumber, float balance){
        this.owner = new SimpleStringProperty(this, "Owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "Account Number", accountNumber);
        this.balance = new SimpleFloatProperty(this, "Balance", balance);

    }

    public StringProperty ownerProperty() {
        return this.owner;
    }

    public StringProperty accountNumberProperty() {
        return this.accountNumber;
    }
    public String getAccountNumber() {
        return this.accountNumber.get();
    }

    public FloatProperty balanceProperty() {
        return this.balance;
    }

    public float getBalance() {
        return this.balance.get();
    }


}
