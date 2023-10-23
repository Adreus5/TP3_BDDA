package model;

import java.sql.*;

public class DataAccess {

  private Connection connection;
  //Hello first commmit
  public DataAccess(String url, String login, String password) throws
          SQLException, ClassNotFoundException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager.getConnection(url, login, password);
    System.out.println("connected to " + url);
  }

}
