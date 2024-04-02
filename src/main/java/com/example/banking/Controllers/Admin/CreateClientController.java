package com.example.banking.Controllers.Admin;

import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.AdminDao;
import com.example.banking.Models.Dao.DaoImpl.AdminDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    @FXML
    public TextField fName_fld;
    @FXML
    public TextField lName_fld;
    @FXML
    public TextField password_fld;
    @FXML
    public CheckBox ch_acc_box;
    @FXML
    public TextField ch_amount_fld;
    @FXML
    public CheckBox sv_acc_box;
    @FXML
    public TextField sv_amount_fld;
    @FXML
    public Button create_client_btn;
    @FXML
    public Label error_lbl;
    @FXML
    public Label pAddress_lbl;
    public CheckBox pAddress_box;
    public static final String PREFIX = "@";
    public StringBuffer error_mgs = new StringBuffer();

    public AdminDaoImpl admin = new AdminDaoImpl();
    ListView<Client> listView ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pAddress_box.setOnAction(event -> CheckBoxPaddress());
        ch_acc_box.setOnAction(event1 -> CheckBoxCheckingAcc());
        sv_acc_box.setOnAction(event2 -> CheckBoxSavingsAcc());
        create_client_btn.setOnAction(event -> {
            onSave();
            listView.getItems().addAll(admin.listClient());
        });
    }


    public void onSave(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);

        if (!pAddress_lbl.getText().isEmpty() && admin.insertClient(fName_fld.getText(), lName_fld.getText(), pAddress_lbl.getText(), password_fld.getText(), date)) {
            error_lbl.setText("Client created successfully.");
        } else {
            error_mgs.append("\nFailed to create the client. Please check your inputs and try again.");
            error_lbl.setText(error_mgs.toString());
        }
    }



    public void CheckBoxPaddress(){
        if (pAddress_box.isSelected()) {
            pAddress_lbl.setText(generatePayeeAddress(fName_fld.getText()));
        } else {
            pAddress_lbl.setText("");
            error_mgs.append("Payee Address not generated");
        }
    }

    public void CheckBoxCheckingAcc(){
        if (ch_acc_box.isSelected() && !pAddress_lbl.getText().isEmpty()) {
           admin.addCheckingAccount(pAddress_lbl.getText(),generateNumberAccount(),10,Float.parseFloat(ch_amount_fld.getText()));
        }else {
            error_mgs.append(" - Checking Account not created");
        }
    }

    public void CheckBoxSavingsAcc(){
        if (sv_acc_box.isSelected() && !pAddress_lbl.getText().isEmpty()) {
            admin.addSavingsAccount(pAddress_lbl.getText(),generateNumberAccount(),8,Float.parseFloat(sv_amount_fld.getText()));
        }else {
            error_mgs.append(" - Savings Account not created");
        }
    }

    // Generate a random alphanumeric string of the specified length
    private static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }

        return result.toString();
    }


    // Generate a random number of the specified length
    private static String generateRandomNumber(int length) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(10));
        }

        return result.toString();
    }


    // Generate PayeeAddress based on the specified format
    public static String generatePayeeAddress(String name) {

        String randomString = generateRandomString(2);
        String randomNumber = generateRandomNumber(4);

        return PREFIX + name + randomString + randomNumber;
    }


    // Generate NumberAccount
    public static String generateNumberAccount() {

        String randomString = generateRandomString(2);
        String randomNumber = generateRandomNumber(4);

        return randomString + randomNumber;
    }


}
