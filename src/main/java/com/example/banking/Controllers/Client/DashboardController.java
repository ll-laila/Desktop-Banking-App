package com.example.banking.Controllers.Client;

import com.example.banking.Models.*;
import com.example.banking.Models.Dao.DaoImpl.CheckingAccountDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.ClientDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.SavingsAccountDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.TransactionDaoImpl;
import com.example.banking.Views.TransactionCellFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    public Text user_name;
    @FXML
    public Label login_date;
    @FXML
    public Label checking_bal;
    @FXML
    public Label checking_acc_num;
    @FXML
    public Label savings_bal;
    @FXML
    public Label savings_acc_num;
    @FXML
    public Label total_bal_lbl;
    @FXML
    public Label total_trans_lbl;
    @FXML
    public ListView transactions_listview;
    @FXML
    public TextField payee_fld;
    @FXML
    public TextField amount_fld;
    @FXML
    public TextArea message_fld;
    @FXML
    public Button send_money_btn;
    @FXML
    public Label error_lbl;

    CheckingAccountDaoImpl checkingAccount = new CheckingAccountDaoImpl();
    SavingsAccountDaoImpl savingsAccount = new SavingsAccountDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLoginDate();
        setUser_name();
        setChecking_bal_num();
        setSavings_bal_num();
        onSummary();
        send_money_btn.setOnAction(event -> onSend());
        List<Transaction> transactions = loadlastTransFromDatabase();
        transactions_listview.setCellFactory(param -> new TransactionCellFactory());
        transactions_listview.getItems().addAll(transactions);
    }

    private List<Transaction> loadlastTransFromDatabase() {
        ClientDaoImpl client = new ClientDaoImpl();
        String username = Model.getInstance().getUsername();
        return client.listLastTransactions(username);
    }

    private void setLoginDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        login_date.setText(formattedDate);
    }

    private void setUser_name() {
        ClientDaoImpl clientImpl = new ClientDaoImpl();
        Client client = clientImpl.getInfos(Model.getInstance().getUsername());
        String username = client.getFirstName()+" "+client.getLastName();
        user_name.setText("Hi, "+username);
    }

    private void setChecking_bal_num(){
        String username = Model.getInstance().getUsername();
        float ch_bal = checkingAccount.getChecking_Balance(username);
        checking_bal.setText(Float.toString(ch_bal)+"DH");
    }

    private void setSavings_bal_num(){
        String username = Model.getInstance().getUsername();
        float sv_bal = savingsAccount.getSavings_Balance(username);
        savings_bal.setText(Float.toString(sv_bal)+"DH");
    }


    private void onSend() {
        String username = Model.getInstance().getUsername();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        ClientDaoImpl client = new ClientDaoImpl();
        TransactionDaoImpl transaction = new TransactionDaoImpl();

        String amountText = amount_fld.getText();
        try {
            float amount = Float.parseFloat(amountText);

            if (client.SendMoney(username, payee_fld.getText(), amount)) {
                if (transaction.AddTransaction(username, payee_fld.getText(), amount, formattedDate, message_fld.getText())) {
                    error_lbl.setText("Transaction successful.");
                } else {
                    error_lbl.setText("Transaction not recorded.");
                }
            } else {
                error_lbl.setText("Transaction failed.");
            }
        } catch (NumberFormatException e) {
            error_lbl.setText("Invalid amount. Please enter a valid number.");
        }
    }

    private void onSummary(){

        // total Balance
        float totalBalance = 0;
        ClientDaoImpl clientImpl = new ClientDaoImpl();
        Client client = clientImpl.getInfos(Model.getInstance().getUsername());

        if (client.getCheckingAccount() != null) {
            totalBalance += client.getCheckingAccount().getBalance();
        }
        if (client.getSavingsAccount() != null) {
            totalBalance += client.getSavingsAccount().getBalance();
        }
        total_bal_lbl.setText("+ "+ totalBalance);

        // total Balance transactions
        float transBal = 0;
        TransactionDaoImpl transImpl = new TransactionDaoImpl();
        List<Float> transBalance = transImpl.TotalBalance(Model.getInstance().getUsername());
        for(int i=0;i<transBalance.size();i++){
            transBal += transBalance.get(i);
        }
        total_trans_lbl.setText("- "+Float.toString(transBal));

    }


}



