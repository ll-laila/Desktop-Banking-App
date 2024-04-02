package com.example.banking.Controllers;

import com.example.banking.Models.Model;
import com.example.banking.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public ChoiceBox<AccountType> acc_selector;
   @FXML
   public Label payee_adress_lbl;
   @FXML
   public TextField payee_adress_fld;
   @FXML
   public Label password_lbl;
   @FXML
   public PasswordField password_fld;
   @FXML
   public Button login_btn;
   @FXML
   public Label error_lbl;

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT,AccountType.ADMIN));
      acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
      acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));
      login_btn.setOnAction(event ->{
         if(!Model.getInstance().getViewFactory().getIsLogout()) {
            onLogin();
         }else{
            error_lbl.setText("No Login Credentials.");
         }
      });

   }

   private void onLogin(){
      Stage stage = (Stage)error_lbl.getScene().getWindow();
      if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
         // Evaluate Client login Credentials
         Model.getInstance().evaluateClientCred(payee_adress_fld.getText(),password_fld.getText());
         if(Model.getInstance().getClientLoginSuccessFlag()){
            Model.getInstance().setUsername(payee_adress_fld.getText());
            Model.getInstance().getViewFactory().showClientWindow();
            // Close the login stage
            Model.getInstance().getViewFactory().closeStage(stage);
         } else {
            payee_adress_fld.setText("");
            password_fld.setText("");
            error_lbl.setText("No Login Credentials.");
         }

      } else if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.ADMIN){
         // Evaluate Admin login Credentials
         Model.getInstance().evaluateAdminCred(payee_adress_fld.getText(),password_fld.getText());
         if(Model.getInstance().getAdminLoginSuccessFlag()){
            Model.getInstance().getViewFactory().showAdminWindow();
            // Close the login stage
            Model.getInstance().getViewFactory().closeStage(stage);
         } else {
            payee_adress_fld.setText("");
            password_fld.setText("");
            error_lbl.setText("No Login Credentials.");
         }
      }
   }


}
