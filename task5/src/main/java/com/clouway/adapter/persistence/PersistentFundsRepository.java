package com.clouway.adapter.persistence;

import com.clouway.core.ConnectionProvider;
import com.clouway.core.FundsRepository;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 09.06.16.
 */
public class PersistentFundsRepository implements FundsRepository {

  private ConnectionProvider connectionProvider;

  public PersistentFundsRepository(ConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  public double getBalance(String email) {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    Double amount = null;
    try {
      statement = connection.prepareStatement("SELECT amount FROM users WHERE email=?");
      statement.setString(1, email);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        amount = resultSet.getDouble("amount");
      }
      resultSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return amount;
  }

  public void deposit(double amount, String email) {
    Double balance = getBalance(email);
    Double actualAmount = balance + amount;
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("UPDATE users SET amount=? WHERE email=?");
      statement.setDouble(1, actualAmount);
      statement.setString(2, email);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean withdraw(Double amount, String email) {
    Double balance = getBalance(email);
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    boolean succesfull = true;
    if (amount > balance) {
      return succesfull = false;
    }
    Double actualAmount = balance - amount;
    try {
      statement = connection.prepareStatement("UPDATE users SET amount=? WHERE email=?");
      statement.setDouble(1, actualAmount);
      statement.setString(2, email);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return succesfull;
  }

  public void updateHistory(String date, String email, String operation, Double amount) {
    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("INSERT INTO transactions (date, email, operation, amount) VALUES (?,?,?,?)");
      statement.setString(1, date);
      statement.setString(2, email);
      statement.setString(3, operation);
      statement.setDouble(4, amount);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void getHistory(PrintWriter out, Integer limit, Integer offset) {

    Connection connection = connectionProvider.get();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("SELECT * FROM transactions LIMIT " + limit + " OFFSET " + offset + "");
      ResultSet resultSet = statement.executeQuery();
      out.print("<table width=25% border=1>");
      out.print("<center><h1>Transaction History:</h1></center>");
      ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
      out.print("<tr>");
      out.print("<th>" + resultSetMetaData.getColumnName(1) + "</th>");
      out.print("<th>" + resultSetMetaData.getColumnName(2) + "</th>");
      out.print("<th>" + resultSetMetaData.getColumnName(3) + "</th>");
      out.print("<th>" + resultSetMetaData.getColumnName(4) + "</th>");
      out.print("<th>" + resultSetMetaData.getColumnName(5) + "</th>");
      out.print("</tr>");
      while (resultSet.next()) {
        out.print("<tr>");
        out.print("<td>" + resultSet.getInt(1) + "</td>");
        out.print("<td>" + resultSet.getString(2) + "</td>");
        out.print("<td>" + resultSet.getString(3) + "</td>");
        out.print("<td>" + resultSet.getString(4) + "</td>");
        out.print("<td>" + resultSet.getDouble(5) + "</td>");
        out.print("</tr>");
      }
      out.print("</table>");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public Integer getNumberOfID() {
    Connection connection = connectionProvider.get();
    ResultSet resultSet = null;
    PreparedStatement statement;
    Integer counter = null;
    try {
      statement = connection.prepareStatement("SELECT COUNT(*) FROM transactions");
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        counter = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return counter;
  }
}
