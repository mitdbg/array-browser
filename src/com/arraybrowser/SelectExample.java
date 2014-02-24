package com.arraybrowser;
import java.sql.*;

public class SelectExample {
  public static int port = 5433;
  public static String host = "localhost";
  public static String username = "leilani";
  public static String password = "password";
  public static String db = "scalar";

  public static void main(String[] args) {
    Connection conn;
    try {
      Class.forName("com.vertica.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.err.println("Could not find the JDBC driver class.\n");
      e.printStackTrace();
      return;
    }
    try {
      conn = DriverManager.getConnection("jdbc:vertica://"+host+":"+port+"/"+db, username,password);
      Statement mySelect = conn.createStatement();
      ResultSet myResult = mySelect.executeQuery("SELECT distinct user_id FROM s1.brightkite limit 10");
      while (myResult.next()) {
        System.out.println(myResult.getString(1));
      }
      mySelect.close();
      conn.close();
    } catch (SQLException e) {
      System.err.println("Could not connect to the database.\n");
      e.printStackTrace();
      return;
    }
  } // end of main method
} // end of class 

