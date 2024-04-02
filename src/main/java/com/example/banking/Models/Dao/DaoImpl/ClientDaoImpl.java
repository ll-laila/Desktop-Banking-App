package com.example.banking.Models.Dao.DaoImpl;

import com.example.banking.Models.*;
import com.example.banking.Models.Dao.ClientDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ClientDaoImpl implements ClientDao {

    private final DatabaseDriver databaseDriver;
    CheckingAccountDaoImpl ck_Account = new CheckingAccountDaoImpl();
    SavingsAccountDaoImpl sv_Account = new SavingsAccountDaoImpl();

    public ClientDaoImpl() {
        this.databaseDriver = new DatabaseDriver();
    }


    @Override
    public Client getInfos(String pAddress) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Client client = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT * FROM clients WHERE PayeeAddress = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pAddress);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String payeeAddress = resultSet.getString("PayeeAddress");
                CheckingAccount checkingAccount = ck_Account.getChecking_Account(payeeAddress);
                SavingsAccount savingsAccount = sv_Account.getSavings_Account(payeeAddress);
                String dateString = resultSet.getString("Date");
                LocalDate dateCreated = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                client = new Client(firstName,lastName,payeeAddress,checkingAccount,savingsAccount,dateCreated);
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
        return client;
    }

    @Override
    public boolean AddMoney(String payeeAddress, float amount) {
        try (Connection connection = databaseDriver.getConnection()) {
            String updateQuery = "UPDATE checkingaccounts SET Balance = Balance + ? WHERE Owner = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setFloat(1, amount);
                preparedStatement.setString(2, payeeAddress);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean MinusMoney(String payeeAddress, float amount) {
        try (Connection connection = databaseDriver.getConnection()) {
            String updateQuery = "UPDATE checkingaccounts SET Balance = Balance - ? WHERE Owner = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setFloat(1, amount);
                preparedStatement.setString(2, payeeAddress);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean TransferTo_SavingsAcc(String payeeAddress, float amount) {
        try (Connection connection = databaseDriver.getConnection()) {
            // Update the savings account by subtracting the amount
            String updateSavingsQuery = "UPDATE checkingaccounts SET Balance = Balance - ? WHERE Owner = ?";
            try (PreparedStatement updateSavingsStatement = connection.prepareStatement(updateSavingsQuery)) {
                updateSavingsStatement.setFloat(1, amount);
                updateSavingsStatement.setString(2, payeeAddress);

                int rowsAffectedSavings = updateSavingsStatement.executeUpdate();

                if (rowsAffectedSavings > 0) {
                    String updateCheckingQuery = "UPDATE savingsaccounts SET Balance = Balance + ? WHERE Owner = ?";
                    try (PreparedStatement updateCheckingStatement = connection.prepareStatement(updateCheckingQuery)) {
                        updateCheckingStatement.setFloat(1, amount);
                        updateCheckingStatement.setString(2, payeeAddress);
                        int rowsAffectedChecking = updateCheckingStatement.executeUpdate();
                        return rowsAffectedChecking > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean TransferTo_CheckingAcc(String payeeAddress, float amount) {
        try (Connection connection = databaseDriver.getConnection()) {
            // Update the savings account by subtracting the amount
            String updateSavingsQuery = "UPDATE savingsaccounts SET Balance = Balance - ? WHERE Owner = ?";
            try (PreparedStatement updateSavingsStatement = connection.prepareStatement(updateSavingsQuery)) {
                updateSavingsStatement.setFloat(1, amount);
                updateSavingsStatement.setString(2, payeeAddress);

                int rowsAffectedSavings = updateSavingsStatement.executeUpdate();

                if (rowsAffectedSavings > 0) {
                    String updateCheckingQuery = "UPDATE checkingaccounts SET Balance = Balance + ? WHERE Owner = ?";
                    try (PreparedStatement updateCheckingStatement = connection.prepareStatement(updateCheckingQuery)) {
                        updateCheckingStatement.setFloat(1, amount);
                        updateCheckingStatement.setString(2, payeeAddress);
                        int rowsAffectedChecking = updateCheckingStatement.executeUpdate();
                        return rowsAffectedChecking > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean SendMoney(String pAddress_sender,String pAddress_receiver, float amount) {
        Connection connection = null;
        try {
            connection = databaseDriver.getConnection();
            String updateQuery = "UPDATE checkingaccounts SET Balance = Balance - ? WHERE Owner = ?";
            try ( PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setFloat(1, amount);
                updateStatement.setString(2, pAddress_sender);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    String updateChQuery = "UPDATE checkingaccounts SET Balance = Balance + ? WHERE Owner = ?";
                    try (PreparedStatement updateChStatement = connection.prepareStatement(updateChQuery)) {
                        updateChStatement.setFloat(1, amount);
                        updateChStatement.setString(2, pAddress_receiver);
                        int rowsAffectedCh = updateChStatement.executeUpdate();
                        if (updateChStatement != null) updateChStatement.close();
                        return rowsAffectedCh > 0;

                    }
                }
                if (updateStatement != null) updateStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



    public List<Transaction> listTransaction(String payeeAddress) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Transaction> transactions = new ArrayList<>();
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT * FROM transactions WHERE Sender = ? OR Receiver = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, payeeAddress);
            preparedStatement.setString(2, payeeAddress);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String sender = resultSet.getString("Sender");
                String receiver = resultSet.getString("Receiver");
                float amount = resultSet.getFloat("Amount");
                String dateString = resultSet.getString("Date");
                LocalDate dateCreated = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String msg = resultSet.getString("Message");
                transactions.add(new Transaction(sender, receiver, amount, dateCreated, msg));
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
        return transactions;
    }


    public List<Transaction> listLastTransactions(String payeeAddress){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Transaction> transactions = new ArrayList<>();
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT * FROM transactions WHERE Sender = ? OR Receiver = ? ORDER BY Date DESC LIMIT 4";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, payeeAddress);
            preparedStatement.setString(2, payeeAddress);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String sender = resultSet.getString("Sender");
                String receiver = resultSet.getString("Receiver");
                float amount = resultSet.getFloat("Amount");
                String dateString = resultSet.getString("Date");
                LocalDate dateCreated = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String msg = resultSet.getString("Message");
                transactions.add(new Transaction(sender, receiver, amount, dateCreated, msg));
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
        return transactions;
    }



}
