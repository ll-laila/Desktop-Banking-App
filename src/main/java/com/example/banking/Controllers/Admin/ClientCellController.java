package com.example.banking.Controllers.Admin;

import com.example.banking.Models.Client;
import com.example.banking.Models.CheckingAccount;
import com.example.banking.Models.Dao.DaoImpl.CheckingAccountDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.SavingsAccountDaoImpl;
import com.example.banking.Models.SavingsAccount;
import com.example.banking.Models.Dao.DaoImpl.AdminDaoImpl;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {

    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label sv_acc_lbl;
    public Label ch_acc_lbl;
    public Label date_lbl;
    public Button delete_btn;

    public Client client;
    ListView<Client> listView ;

    public AdminDaoImpl admin = new AdminDaoImpl();

    public ClientCellController(ListView<Client> listView, Client client) {
        this.listView = listView;
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabelsText();
        delete_btn.setOnAction(event -> onDelete());
    }

    private void setLabelsText() {
        CheckingAccountDaoImpl ck_Account = new CheckingAccountDaoImpl();
        SavingsAccountDaoImpl sv_Account = new SavingsAccountDaoImpl();
        Platform.runLater(() -> {
            fName_lbl.setText(client.getFirstName());
            lName_lbl.setText(client.getLastName());
            pAddress_lbl.setText(client.getPayeeAddress());
            ch_acc_lbl.setText(ck_Account.getChecking_Account(client.getPayeeAddress()).getAccountNumber());
            sv_acc_lbl.setText(sv_Account.getSavings_Account(client.getPayeeAddress()).getAccountNumber());
            date_lbl.setText(client.getDate());
        });
    }

    public void onDelete() {
        admin.deleteClient(pAddress_lbl.getText());
        admin.deleteCheckingAccount(pAddress_lbl.getText());
        admin.deleteSavingsAccount(pAddress_lbl.getText());

        listView.getItems().remove(client);
        listView.getItems().addAll(admin.listClient());
    }
}
