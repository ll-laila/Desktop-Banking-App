package com.example.banking.Controllers.Admin;

import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.DaoImpl.CheckingAccountDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.SavingsAccountDaoImpl;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientSearchCellController implements Initializable {

    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label sv_acc_lbl;
    public Label ch_acc_lbl;
    public Label date_lbl;

    public Client client;
    ListView<Client> listView ;


    public ClientSearchCellController(ListView<Client> listView, Client client) {
        this.listView = listView;
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabelsText();
    }

    private void setLabelsText() {
        Platform.runLater(() -> {
            fName_lbl.setText(client.getFirstName());
            lName_lbl.setText(client.getLastName());
            pAddress_lbl.setText(client.getPayeeAddress());
            ch_acc_lbl.setText(new CheckingAccountDaoImpl().getChecking_Account(client.getPayeeAddress()).getAccountNumber());
            sv_acc_lbl.setText(new SavingsAccountDaoImpl().getSavings_Account(client.getPayeeAddress()).getAccountNumber());
            date_lbl.setText(client.getDate());
        });
    }

}
