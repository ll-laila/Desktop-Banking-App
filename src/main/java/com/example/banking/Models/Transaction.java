package com.example.banking.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transaction {
    private final StringProperty sender;
    private final StringProperty receiver;
    private final FloatProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;


    public Transaction(String sender, String receiver, float amount, LocalDate date, String message){
        this.sender = new SimpleStringProperty(this, "Sender", sender);
        this.receiver = new SimpleStringProperty(this, "Receiver", receiver);
        this.amount = new SimpleFloatProperty(this, "Amount", amount);
        this.date = new SimpleObjectProperty<>(this, "Date", date);
        this.message = new SimpleStringProperty(this, "Message", message);

    }

    public StringProperty senderProperty() {
        return this.sender;
    }
    public String getSender() {
        return this.sender.get();
    }
    public StringProperty receiverProperty() {
        return this.receiver;
    }
    public String getReceiver() {
        return this.receiver.get();
    }
    public FloatProperty amountProperty() {
        return this.amount;
    }
    public float getAmount() {
        return this.amount.get();
    }
    public ObjectProperty<LocalDate> dateProperty() {
        return this.date;
    }
    public LocalDate getDate() {
        return this.date.get();
    }
    public StringProperty messageProperty() {
        return this.message;
    }

}
