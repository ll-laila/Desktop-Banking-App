package com.example.banking.Controllers.Admin;

import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.DaoImpl.AdminDaoImpl;
import com.example.banking.Views.ClientCellFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    @FXML
    public ListView<Client> clients_listview;
    public AdminDaoImpl admin = new AdminDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Client> clients = loadClientsFromDatabase();
        clients_listview.setCellFactory(param -> new ClientCellFactory());
        clients_listview.getItems().addAll(clients);
    }

    private List<Client> loadClientsFromDatabase() {
        return admin.listClient();
    }

}
