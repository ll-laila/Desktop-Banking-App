package com.example.banking.Controllers.Admin;

import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.AdminDao;
import com.example.banking.Models.Dao.DaoImpl.AdminDaoImpl;
import com.example.banking.Views.ClientCellFactory;
import com.example.banking.Views.ClientSearchCellFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DepositController implements Initializable {


    public TextField pAddress_fld;
    public Button search_btn;
    public ListView result_listview;
    public TextField amount_fld;
    public Button deposit_btn;
    public Label error_lbl_search;
    public Label error_lbl_deposit;

    public AdminDaoImpl admin = new AdminDaoImpl();

  //  Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        search_btn.setOnAction(event -> {
//            client = onSearchClient();
//        });
//        result_listview.setCellFactory(param -> new ClientSearchCellFactory());
//        result_listview.getItems().add(client);
        deposit_btn.setOnAction(event -> onDeposit());
    }

    public void onDeposit(){
        if(admin.deposit(pAddress_fld.getText(),Float.parseFloat(amount_fld.getText()))){
            error_lbl_deposit.setText("Amount Added Successfully.");
        }else{
            error_lbl_deposit.setText("Failed to add the amount. Please check your inputs and try again.");
        }
    }



    public Client onSearchClient() {
        if(admin.searchClient(pAddress_fld.getText()) != null){
            return admin.searchClient(pAddress_fld.getText());
        }else{
            error_lbl_search.setText("Client not found.");
            return null;
        }
    }

}
