package com.example.banking.Models;

import com.example.banking.Views.AccountType;
import com.example.banking.Views.ViewFactory;

import java.sql.ResultSet;
import java.time.LocalDate;

public class Model {

    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private AccountType loginAccountType = AccountType.CLIENT;


    // Client Data Section
    private final Client client;
    private String username;
    private boolean clientLoginSuccessFlag;


    // Admin Data Section
    private final Admin admin;
    private boolean adminLoginSuccessFlag;


    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        //Client Data Section
        this.clientLoginSuccessFlag = false;
        this.client = new Client("","","",null,null,null);
        //Admin Data Section
        this.adminLoginSuccessFlag = false;
        this.admin = new Admin("");
    }


    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }



    public ViewFactory getViewFactory() {
        return viewFactory;
    }
    public DatabaseDriver getDatabaseDriver(){
        return databaseDriver;
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }



    /*
    *  Client Method Section
    * */

    public boolean getClientLoginSuccessFlag() {
        return clientLoginSuccessFlag;
    }
    public void setClientLoginSuccessFlag(boolean flag) {
        this.clientLoginSuccessFlag = flag;
    }

    public Client getClient() {
        return client;
    }


    public void evaluateClientCred(String pAddress, String password){
         ResultSet resultSet = databaseDriver.getClientData(pAddress,password);
         try {
            if (resultSet.next()){
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[] dataParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dataParts[0]),Integer.parseInt(dataParts[1]),Integer.parseInt(dataParts[2]));
                this.client.dateProperty().set(date);
                this.clientLoginSuccessFlag = true;
            }
        }catch (Exception e){
             e.printStackTrace();
         }
    }




    /*
     *  Admin Method Section
     * */

    public boolean getAdminLoginSuccessFlag() {
        return adminLoginSuccessFlag;
    }
    public void setAdminLoginSuccessFlag(boolean flag) {
        this.adminLoginSuccessFlag = flag;
    }

    public Admin getAdmin() {
        return admin;
    }


    public void evaluateAdminCred(String userName, String password){
        ResultSet resultSet = databaseDriver.getAdminData(userName,password);
        try {
            if (resultSet.next()){
                this.admin.userNameProperty().set(resultSet.getString("Username"));
                this.adminLoginSuccessFlag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
     *  Methodes Section
     * */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
