package com.example.banking.Models.Dao.DaoImpl;

import com.example.banking.Models.*;
import com.example.banking.Models.Dao.AdminDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AdminDaoImpl implements AdminDao {

    private final DatabaseDriver databaseDriver;
    CheckingAccountDaoImpl ck_Account = new CheckingAccountDaoImpl();
    SavingsAccountDaoImpl sv_Account = new SavingsAccountDaoImpl();

    public AdminDaoImpl() {
        this.databaseDriver = new DatabaseDriver();
    }

    @Override
    public boolean insertClient(String fName, String lName, String payeeAddress, String password, String date) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "INSERT INTO clients (FirstName, LastName, PayeeAddress, Password, Date) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, lName);
            preparedStatement.setString(3, payeeAddress);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, date);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void deleteClient(String payeeAddress) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "DELETE FROM clients WHERE PayeeAddress = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, payeeAddress);
            preparedStatement.executeUpdate();
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
    }

    @Override
    public void addCheckingAccount(String owner, String accountNumber, int transactionLimit, float balance) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "INSERT INTO checkingaccounts (Owner, AccountNumber, TransactionLimit, Balance) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, owner);
                preparedStatement.setString(2, accountNumber);
                preparedStatement.setInt(3, transactionLimit);
                preparedStatement.setFloat(4, balance);
                preparedStatement.executeUpdate();
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
    }


    @Override
    public void deleteCheckingAccount(String owner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "DELETE FROM checkingaccounts WHERE Owner = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);
            preparedStatement.executeUpdate();

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
    }


    @Override
    public void addSavingsAccount(String owner, String accountNumber, int withdrawalLimit, float balance) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
               connection = databaseDriver.getConnection();
               String query = "INSERT INTO savingsaccounts (Owner, AccountNumber, WithdrawalLimit, Balance) VALUES (?, ?, ?, ?)";
               preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, owner);
                preparedStatement.setString(2, accountNumber);
                preparedStatement.setInt(3, withdrawalLimit);
                preparedStatement.setFloat(4, balance);
                preparedStatement.executeUpdate();
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
    }


    @Override
    public void deleteSavingsAccount(String owner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "DELETE FROM savingsAccounts WHERE Owner = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, owner);
            preparedStatement.executeUpdate();
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
    }


    @Override
    public List<Client> listClient() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Client> clients = new ArrayList<>();
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT * FROM clients";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String payeeAddress = resultSet.getString("PayeeAddress");
                    CheckingAccount checkingAccount = ck_Account.getChecking_Account(payeeAddress);
                    SavingsAccount savingsAccount = sv_Account.getSavings_Account(payeeAddress);
                    String dateString = resultSet.getString("Date");
                    LocalDate dateCreated = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    clients.add(new Client(firstName,lastName,payeeAddress,checkingAccount,savingsAccount,dateCreated));
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
        return clients;
    }


    @Override
    public boolean deposit(String payeeAddress, float amount) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String updateQuery = "UPDATE checkingaccounts SET Balance = Balance + ? WHERE Owner = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setFloat(1, amount);
            preparedStatement.setString(2, payeeAddress);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
         } catch (SQLException e) {
                e.printStackTrace();
                return false;
        } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }


    @Override
    public Client searchClient(String payeeAddress) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = databaseDriver.getConnection();
            String query = "SELECT * FROM clients WHERE PayeeAddress = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, payeeAddress);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String firstName = resultSet.getString("FirstName");
                        String lastName = resultSet.getString("LastName");
                        String foundPayeeAddress = resultSet.getString("PayeeAddress");
                        String dateString = resultSet.getString("Date");
                        LocalDate dateCreated = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        CheckingAccount checkingAccount = ck_Account.getChecking_Account(payeeAddress);
                        SavingsAccount savingsAccount = sv_Account.getSavings_Account(payeeAddress);
                        return new Client(firstName, lastName, foundPayeeAddress, checkingAccount, savingsAccount, dateCreated);
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

}
