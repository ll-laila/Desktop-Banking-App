package com.example.banking.Models.Dao;

import com.example.banking.Models.Transaction;

import java.util.List;

public interface TransactionDao {

    public Transaction getTransaction(String payeeAdress,String payeeAddress);
    public Boolean AddTransaction(String sender, String receiver, float amount, String Date, String message);
    public List<Float> TotalBalance(String owner);

}
