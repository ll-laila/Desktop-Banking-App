package com.example.banking.Models.Dao;

import com.example.banking.Models.Client;
import com.example.banking.Models.Transaction;

import java.util.List;

public interface ClientDao {

    public Client getInfos(String payeeAddress);
    public boolean AddMoney(String pAddress_sender, float amount);
    public boolean MinusMoney(String pAddress_sender, float amount);
    public boolean TransferTo_SavingsAcc(String payeeAddress, float amount);
    public boolean TransferTo_CheckingAcc(String payeeAddress, float amount);
    public boolean SendMoney(String pAddress_sender,String pAddress_receiver, float amount);
    public List<Transaction> listTransaction(String payeeAddress);
    public List<Transaction> listLastTransactions(String payeeAddress);

    //public String getPassword(String pAddress);



}
