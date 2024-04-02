package com.example.banking.Controllers.Client;

import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.DaoImpl.CheckingAccountDaoImpl;
import com.example.banking.Models.Dao.DaoImpl.SavingsAccountDaoImpl;
import com.example.banking.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {


    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label recever_lbl;
    public Label amount_lbl;
    private final Transaction transaction;

    ListView<Transaction> listView ;

    public TransactionCellController(ListView<Transaction> listView, Transaction transaction) {
        this.listView = listView;
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabelsText();
    }

    private void setLabelsText() {
        Platform.runLater(() -> {
            sender_lbl.setText(transaction.getSender());
            recever_lbl.setText(transaction.getReceiver());
            amount_lbl.setText(Float.toString(transaction.getAmount()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd");
            String formattedDate = transaction.getDate().format(formatter);
            trans_date_lbl.setText(formattedDate);
        });
    }

}
