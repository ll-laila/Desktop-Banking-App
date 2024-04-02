package com.example.banking.Models.Dao;

import com.example.banking.Models.CheckingAccount;

public interface ChekingAccountDao {

    public float getChecking_Balance(String owner);
    public String getNumChecking_Account(String owner);
    public CheckingAccount getChecking_Account(String owner);

}
