package com.arraybrowser;
import java.sql.*;

public class ComputeProjection {
  public static int port = 5433;
  public static String host = "localhost";
  public static String username = "leilani";
  public static String password = "password";
  public static String db = "scalar";

  public static String updateRow = "update s1.brightkite2 set x = ?," +
      " y = ? where latitude = ? and longitude = ? and zoom = ?";

  public static void main(String[] args) {
    Connection conn;
    //Connection conn2;
    try {
      Class.forName("com.vertica.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.err.println("Could not find the JDBC driver class.\n");
      e.printStackTrace();
      return;
    }
    try {
      conn = DriverManager.getConnection("jdbc:vertica://"+host+":"+port+"/"+db, username,password);
      //conn2 = DriverManager.getConnection("jdbc:vertica://"+host+":"+port+"/"+db, username,password);
      Statement mySelect = conn.createStatement();
      //PreparedStatement ps = conn2.prepareStatement(updateRow);
      ResultSet myResult = mySelect.executeQuery("SELECT * FROM s1.brightkite");
      while (myResult.next()) {
        //System.out.println(myResult.getString(1));
        double lat = myResult.getDouble("latitude");
        double lng = myResult.getDouble("longitude");
        int id = myResult.getInt("id");
        for(int zoom = 1; zoom <= 3; zoom++) {
          Point p = project(lat,lng,zoom);
          System.out.println(id+","+p.x+","+p.y+","+zoom);
          /*
          ps.setDouble(1,p.x);
          ps.setDouble(2,p.y);
          ps.setDouble(3,lat);
          ps.setDouble(4,lng);
          ps.setInt(5,zoom);
          ps.executeUpdate();
          */
        }
      }
      mySelect.close();
      conn.close();
    } catch (SQLException e) {
      System.err.println("Could not connect to the database.\n");
      e.printStackTrace();
      return;
    }
  }

  public static Point project(double lat, double lng, int zoom) {
    double r2d = Math.PI / 180;
    double max = 85.0840591556;
    lat = Math.max(Math.min(max, lat), -1.0 * max);
    double x = lng * r2d;
    double y = lat * r2d;
    y = Math.log(Math.tan((Math.PI / 4) + (y / 2)));
    Point p = new Point(x,y);
    double scale = 256 * Math.pow(2,zoom);

    double a = 0.5 / Math.PI;
    double b = 0.5;
    double c = -0.5;
    double d = 0.5;
    
    p.x = scale * (a * p.x + b);
    p.y = scale * (c * p.y + d);
    return p;
  }

  private static class Point {
    public double x;
    public double y;

    public Point(double x,double y) {
      this.x = x;
      this.y = y;
    }
  }
}

