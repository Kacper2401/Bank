package sample.users;

import sample.Connect;
import sample.transfer.TransferException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManagement extends Connect {

    public ResultSet logIn(String login, String password) throws AccountException.AccountNotFound {
        Connection dbh = getConnect();
        ResultSet rs;
        String query;

        query = "SELECT account_balance," +
                "       id " +
                "FROM   accounts " +
                "WHERE  login = ? " +
                "       AND password = ?" +
                "       AND active = 'Y'";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setString (1, login);
            preparedQuery.setString (2, hashPassword(password));

            rs = preparedQuery.executeQuery();

            rs.last();

            if(rs.getRow() == 1) {
                return rs;
            }else {
                throw new AccountException.AccountNotFound();
            }
        }catch (SQLException error) {
            System.out.println("Error with Login: " + error.getMessage());
        }

        return null;
    }

    public void addAccount(String login, String password) throws AccountException.AccountExist {
        Connection dbh = getConnect();
        String query;

        query = "INSERT INTO accounts" +
                "            (login," +
                "             password)" +
                "VALUES      (?," +
                "             ?)  ";

        try {
            checkIfAccountExists(dbh, login);

            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setString(1, login);
            preparedQuery.setString(2, hashPassword(password));

            preparedQuery.execute();
        }catch (SQLException error) {
            System.out.println("Error with adding account: " + error.getMessage());
        }finally {
            closeConnection(dbh);
        }
    }

    public String checkAccountName(int accountId) {
        Connection dbh = getConnect();
        ResultSet rs;
        String query;

        query = "SELECT login " +
                "FROM   accounts " +
                "WHERE  id = ? ";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setInt(1, accountId);

            rs = preparedQuery.executeQuery();

            rs.last();

            return rs.getString(1);
        }catch (SQLException error) {
            System.out.println("Error with account name: " + error.getMessage());
        }finally {
            closeConnection(dbh);
        }

        return null;
    }

    public int checkAccountId(String accountName) throws TransferException.UserDoseNotExist {
        Connection dbh = getConnect();
        ResultSet rs;
        String query;

        query = "SELECT id " +
                "FROM   accounts " +
                "WHERE  login = ? ";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setString(1, accountName);

            rs = preparedQuery.executeQuery();

            rs.last();

            if(rs.getRow() == 0) {
                throw new TransferException.UserDoseNotExist();
            }else {
                return rs.getInt(1);
            }
        }catch (SQLException error) {
            System.out.println("Error with account id: " + error.getMessage());
        }finally {
            closeConnection(dbh);
        }

        return -1;
    }

    public void deleteAccount(int accountId) {
        Connection dbh = getConnect();
        String query;

        query = "UPDATE accounts " +
                "SET    active = 'N' " +
                "WHERE  id = ?";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setInt(1, accountId);

            preparedQuery.execute();
        }catch (SQLException error) {
            System.out.println("Error with delete account: " + error.getMessage());
        }finally {
            closeConnection(dbh);
        }
    }

    private void checkIfAccountExists(Connection dbh, String userName) throws AccountException.AccountExist {
        ResultSet rs;
        String query;

        query = "SELECT id " +
                "FROM   accounts " +
                "WHERE  login = ? ";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setString (1, userName);

            rs = preparedQuery.executeQuery();

            rs.last();

            if(rs.getRow() == 1) {
                throw new AccountException.AccountExist();
            }
        }catch (SQLException error) {
            System.out.println("Error with Login: " + error.getMessage());
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            StringBuilder hashText;

            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            hashText = new StringBuilder(no.toString(16));

            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }

            return hashText.toString();
        }catch (NoSuchAlgorithmException error) {
            System.out.println("Error with Algorithm: " + error.getMessage());
        }

        return null;
    }
}
