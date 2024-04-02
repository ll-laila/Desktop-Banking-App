package com.example.banking.Controllers.Client;

import com.example.banking.Models.CheckingAccount;
import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.DaoImpl.CheckingAccountDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.ClientDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.SavingsAccountDaoImpl;
import com.example.banking.Models.Model;
import com.example.banking.Models.SavingsAccount;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label ch_acc_num;
    public Label transaction_limit;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sv_acc_date;
    public Label sv_acc_bal;
    public TextField amount_to_sv;
    public Button trans_to_sv_btn;
    public TextField amount_to_ch;
    public Button trans_to_cv_btn;

    String pAddress = Model.getInstance().getUsername();
    ClientDaoImpl clientImpl = new ClientDaoImpl();
    Client client = clientImpl.getInfos(Model.getInstance().getUsername());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onChecking();
        onSavings();
        trans_to_sv_btn.setOnAction(event -> onSendToSavings());
        trans_to_cv_btn.setOnAction(event -> onSendToChecking());
        amount_to_ch.setText(" ");
        amount_to_sv.setText(" ");
    }

    private void onChecking(){
          CheckingAccountDaoImpl ch_Account = new CheckingAccountDaoImpl();
          CheckingAccount checking_Acc = ch_Account.getChecking_Account(pAddress);
          ch_acc_num.setText(checking_Acc.getAccountNumber());
          transaction_limit.setText(Integer.toString(checking_Acc.getTransactionLimit()));
          ch_acc_date.setText(client.getDate());
          ch_acc_bal.setText(Float.toString(checking_Acc.getBalance()));
    }

    private void onSavings(){
        SavingsAccountDaoImpl ch_Account = new SavingsAccountDaoImpl();
        SavingsAccount savings_Acc = ch_Account.getSavings_Account(pAddress);
        sv_acc_num.setText(savings_Acc.getAccountNumber());
        withdrawal_limit.setText(Double.toString(savings_Acc.getWithdrawalLimit()));
        sv_acc_date.setText(client.getDate());
        sv_acc_bal.setText(Float.toString(savings_Acc.getBalance()));
    }

    private void onSendToSavings(){
        ClientDaoImpl client = new ClientDaoImpl();
        client.TransferTo_SavingsAcc(pAddress,Float.parseFloat(amount_to_sv.getText()));
    }

    private void onSendToChecking(){
        ClientDaoImpl client = new ClientDaoImpl();
        client.TransferTo_CheckingAcc(pAddress,Float.parseFloat(amount_to_ch.getText()));
    }

}
