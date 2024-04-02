package com.example.banking.Models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final CheckingAccount checkingAccount;
    private final SavingsAccount savingsAccount;
    private final ObjectProperty<LocalDate> dateCreated;



    public Client(String firstName, String lastName, String payeeAddress, CheckingAccount checkingAccount, SavingsAccount savingsAccount, LocalDate dateCreated){
        this.firstName = new SimpleStringProperty(this, "First Name", firstName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lastName);
        this.payeeAddress = new SimpleStringProperty(this, "Payee Address", payeeAddress);
        this.checkingAccount = checkingAccount;
        this.savingsAccount = savingsAccount;
        this.dateCreated = new SimpleObjectProperty<>(this, "Date Created", dateCreated);

    }

    public StringProperty firstNameProperty() {
        return this.firstName;
    }
    public String getFirstName() {
        return this.firstName.get();
    }

    public StringProperty lastNameProperty() {
        return this.lastName;
    }
    public String getLastName() {
        return this.lastName.get();
    }

    public StringProperty payeeAddressProperty() {
        return this.payeeAddress;
    }
    public String getPayeeAddress() {
        return this.payeeAddress.get();
    }

    public CheckingAccount getCheckingAccount() {
        return this.checkingAccount;
    }

    public SavingsAccount getSavingsAccount() {
        return this.savingsAccount;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return this.dateCreated;
    }

    public String getDate() {
        LocalDate dateValue = this.dateCreated.get();
        String formattedDate = dateValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formattedDate;
    }


}