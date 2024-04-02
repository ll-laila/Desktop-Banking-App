package com.example.banking.Models.Dao;

import com.example.banking.Models.SavingsAccount;

public interface SavingsAccountDao {

    public float getSavings_Balance(String owner);
    public String getNumSavings_Account(String owner);
    public SavingsAccount getSavings_Account(String owner);

}
