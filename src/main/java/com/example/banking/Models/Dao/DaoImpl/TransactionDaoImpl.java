package com.example.banking.Models.Dao.DaoImpl;

import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.TransactionDao;
import com.example.banking.Models.DatabaseDriver;
import com.example.banking.Models.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    private final DatabaseDriver databaseDriver;

    public TransactionDaoImpl() {
        this.databaseDriver = new DatabaseDriver();
    }

    @Override
    public Transaction getTransaction(String payeeAddress_sender,String pAddress_reciever) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT * FROM transactions WHERE Sender = ? OR Receiver = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, payeeAddress_sender);
            preparedStatement.setString(2, pAddress_reciever);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String sender = resultSet.getString("Sender");
                        String receiver = resultSet.getString("Receiver");
                        float amount = resultSet.getFloat("Amount");
                        String dateString = resultSet.getString("Date");
                        LocalDate dateCreated = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        String msg = resultSet.getString("Message");
                        return new Transaction(sender, receiver, amount, dateCreated, msg);
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public Boolean AddTransaction(String sender, String receiver, float amount, String date, String message) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String insertQuery = "INSERT INTO transactions (Sender, Receiver, Amount, Date, Message) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, receiver);
            preparedStatement.setFloat(3, amount);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, message);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public List<Float> TotalBalance(String owner){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Float> balanceTotal = new ArrayList<>();
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT Amount FROM transactions WHERE Sender = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                balanceTotal.add(resultSet.getFloat("Amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return balanceTotal;
    }


}
