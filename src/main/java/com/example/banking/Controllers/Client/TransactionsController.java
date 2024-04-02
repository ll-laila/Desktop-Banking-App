package com.example.banking.Controllers.Client;


import com.example.banking.Models.Dao.DaoImpl.ClientDaoImpl;
import com.example.banking.Models.Model;
import com.example.banking.Models.Transaction;
import com.example.banking.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView<Transaction>  transaction_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Transaction> transactions = loadTransactionsFromDatabase();
        transaction_listview.setCellFactory(param -> new TransactionCellFactory());
        transaction_listview.getItems().addAll(transactions);
    }

    private List<Transaction> loadTransactionsFromDatabase() {
        ClientDaoImpl client = new ClientDaoImpl();
        String username = Model.getInstance().getUsername();
        return client.listTransaction(username);
    }

}
