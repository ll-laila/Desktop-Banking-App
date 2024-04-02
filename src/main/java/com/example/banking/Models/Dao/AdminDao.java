package com.example.banking.Models.Dao;

import com.example.banking.Models.Client;

import java.util.List;

public interface AdminDao {

    public boolean insertClient(String fName, String lName, String payeeAddress, String password, String date);
    public void deleteClient(String payeeAddress);

    public void addCheckingAccount(String owner, String accountNumber, int transactionLimit, float balance);
    public void deleteCheckingAccount(String payeeAddress);
    public void addSavingsAccount(String owner, String accountNumber, int withdrawalLimit, float balance);
    public void deleteSavingsAccount(String payeeAddress);
    public List<Client> listClient();
    public boolean deposit(String pAddressCk_Acc, float amount);
    public Client searchClient(String payeeAddress);

}
