package com.example.banking.Models.Dao.DaoImpl;

import com.example.banking.Models.CheckingAccount;
import com.example.banking.Models.Client;
import com.example.banking.Models.Dao.ChekingAccountDao;
import com.example.banking.Models.DatabaseDriver;
import com.example.banking.Models.SavingsAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckingAccountDaoImpl implements ChekingAccountDao {

    private final DatabaseDriver databaseDriver;

    public CheckingAccountDaoImpl() {
        this.databaseDriver = new DatabaseDriver();
    }


    @Override
    public float getChecking_Balance(String owner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT Balance FROM checkingaccounts WHERE Owner = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                float balance = resultSet.getFloat("Balance");
                return balance;
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
        return 0;
    }

    @Override
    public String getNumChecking_Account(String owner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String accountNumber = "";
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT AccountNumber FROM checkingaccounts WHERE Owner = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                accountNumber  = resultSet.getString("AccountNumber");
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
        return accountNumber;
    }




    @Override
    public CheckingAccount getChecking_Account(String owner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT * FROM checkingaccounts WHERE Owner = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);

                ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String accountOwner = resultSet.getString("Owner");
                        String accountNumber = resultSet.getString("AccountNumber");
                        int transactionLimit = resultSet.getInt("TransactionLimit");
                        float balance = resultSet.getFloat("Balance");

                        return new CheckingAccount(accountOwner, accountNumber, transactionLimit, balance);
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


}
