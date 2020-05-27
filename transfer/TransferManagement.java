package sample.transfer;

import sample.users.AccountManagement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TransferManagement extends AccountManagement {
    public ResultSet getUserTransfers(int userId) {
        Connection dbh = getConnect();
        ResultSet rs;
        String query;

        query = "SELECT from_account_id, " +
                "       to_account_id," +
                "       amount," +
                "       date," +
                "       title," +
                "       description " +
                "FROM   transfer_history " +
                "WHERE  from_account_id = ?" +
                "       OR to_account_id = ?";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setInt(1, userId);
            preparedQuery.setInt(2, userId);

            rs = preparedQuery.executeQuery();

            return rs;
        } catch (SQLException throwable) {
            System.out.println("Can't download user data: " + throwable.getMessage());
        }

        return null;
    }

    public void moneyTransfer(int fromAccountId, int toAccountId, float amount, String title, String description) throws TransferException.NotEnoughMoney {
        Connection dbh = getConnect();
        String query;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        checkIfEnoughMoney(dbh, fromAccountId, amount);

        updateAccountBalance(dbh, fromAccountId, (amount * -1));
        updateAccountBalance(dbh, toAccountId, amount);

        query = "INSERT INTO transfer_history" +
                "            (from_account_id," +
                "             to_account_id," +
                "             amount," +
                "             date," +
                "             title," +
                "             description) "+
                "VALUES      (?," +
                "             ?," +
                "             ?," +
                "             ?," +
                "             ?," +
                "             ?)";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setInt(1, fromAccountId);
            preparedQuery.setInt(2, toAccountId);
            preparedQuery.setFloat(3, amount);
            preparedQuery.setTimestamp(4, Timestamp.valueOf(formatter.format(date)));
            preparedQuery.setString(5, title);
            preparedQuery.setString(6, description);

            preparedQuery.execute();

        } catch (SQLException throwable) {
            System.out.println("Can't download user data: " + throwable.getMessage());
        }
    }

    private void checkIfEnoughMoney(Connection dbh, int accountId, float amount) throws TransferException.NotEnoughMoney {
        String query;
        ResultSet rs;

        query = "SELECT id " +
                "FROM   accounts " +
                "WHERE  account_balance - ? >= 0" +
                "       AND id = ?";

        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setFloat(1, amount);
            preparedQuery.setInt(2, accountId);

            rs = preparedQuery.executeQuery();

            rs.last();

            if(rs.getRow() != 1) {
                throw new TransferException.NotEnoughMoney();
            }
        } catch (SQLException throwable) {
            System.out.println("Can't check user account balance: " + throwable.getMessage());
        }
    }

    private void updateAccountBalance(Connection dbh, int accountId, float amount) {
        String query;

        query = "UPDATE accounts " +
                "SET    account_balance = account_balance + ? " +
                "WHERE  id = ?";
        try {
            PreparedStatement preparedQuery = dbh.prepareStatement(query);

            preparedQuery.setFloat(1, amount);
            preparedQuery.setInt(2, accountId);

            preparedQuery.execute();
        } catch (SQLException throwable) {
            System.out.println("Can't update user account balance: " + throwable.getMessage());
        }
    }
}