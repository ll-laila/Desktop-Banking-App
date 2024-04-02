package com.example.banking.Controllers.Client;

import com.example.banking.Models.CheckingAccount;
import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.DaoImpl.CheckingAccountDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.ClientDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.SavingsAccountDaoImpl;
import com.example.banking.Models.Model;
import com.example.banking.Models.SavingsAccount;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Profile implements Initializable {
    public Label ch_acc_num;
    public Label transaction_limit;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sv_acc_date;
    public Label sv_acc_bal;
    public Label fName;
    public Label lName;
    public Label pAddress;
    //public Label password;
    public Label dateCreated;

    ClientDaoImpl clientImpl = new ClientDaoImpl();
    Client client = clientImpl.getInfos(Model.getInstance().getUsername());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onInfos();
        onChecking();
        onSavings();
    }


    private void onInfos(){
        fName.setText(client.getFirstName());
        lName.setText(client.getLastName());
        pAddress.setText(Model.getInstance().getUsername());
        dateCreated.setText(client.getDate());
        //password.setText(clientImpl.getPassword(Model.getInstance().getUsername()));
    }

    private void onChecking(){
        CheckingAccountDaoImpl ch_Account = new CheckingAccountDaoImpl();
        CheckingAccount checking_Acc = ch_Account.getChecking_Account(Model.getInstance().getUsername());
        ch_acc_num.setText(checking_Acc.getAccountNumber());
        transaction_limit.setText(Integer.toString(checking_Acc.getTransactionLimit()));
        ch_acc_date.setText(client.getDate());
        ch_acc_bal.setText(Float.toString(checking_Acc.getBalance()));
    }

    private void onSavings(){
        SavingsAccountDaoImpl ch_Account = new SavingsAccountDaoImpl();
        SavingsAccount savings_Acc = ch_Account.getSavings_Account(Model.getInstance().getUsername());
        sv_acc_num.setText(savings_Acc.getAccountNumber());
        withdrawal_limit.setText(Double.toString(savings_Acc.getWithdrawalLimit()));
        sv_acc_date.setText(client.getDate());
        sv_acc_bal.setText(Float.toString(savings_Acc.getBalance()));
    }
}
