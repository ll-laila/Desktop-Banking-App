package com.example.banking.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseDriver {

    private Connection conn;

    public DatabaseDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
             e.getMessage();
        }
        try{
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/easycashbank","root","");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }


    public ResultSet getClientData(String pAddress, String password){
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+pAddress+"'AND Password='"+password+"';");
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }


    public ResultSet getAdminData(String userName, String password){
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE Username='"+userName+"'AND Password='"+password+"';");
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

}
