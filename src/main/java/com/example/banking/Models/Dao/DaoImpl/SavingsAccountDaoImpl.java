package com.example.banking.Models.Dao.DaoImpl;

import com.example.banking.Models.Dao.SavingsAccountDao;
import com.example.banking.Models.DatabaseDriver;
import com.example.banking.Models.SavingsAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SavingsAccountDaoImpl implements SavingsAccountDao {

    private final DatabaseDriver databaseDriver;

    public SavingsAccountDaoImpl() {
        this.databaseDriver = new DatabaseDriver();
    }



    @Override
    public float getSavings_Balance(String owner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT Balance FROM savingsaccounts WHERE Owner = ?";
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
    public String getNumSavings_Account(String owner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT AccountNumber FROM savingsaccounts WHERE Owner = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String accountNumber = resultSet.getString("AccountNumber");
                return accountNumber;
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
    public SavingsAccount getSavings_Account(String owner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT * FROM savingsaccounts WHERE Owner = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);

            ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String accountOwner = resultSet.getString("Owner");
                        String accountNumber = resultSet.getString("AccountNumber");
                        int withdrawalLimit = resultSet.getInt("WithdrawalLimit");
                        float balance = resultSet.getFloat("Balance");

                        return new SavingsAccount(accountOwner, accountNumber, withdrawalLimit, balance);
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
