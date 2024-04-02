package com.example.banking.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Admin {

    private final StringProperty userName;



    public Admin(String userName){
        this.userName = new SimpleStringProperty(this, "Username", userName);
    }


    public StringProperty userNameProperty() {
        return userName;
    }
}
