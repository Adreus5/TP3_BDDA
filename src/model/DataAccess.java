package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccess {

    private Connection connection;

    //Hello first commmit
    public DataAccess(String url, String login, String password) throws
            SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, login, password);
        System.out.println("connected to " + url);
    }

    public List<EmployeeInfo> getEmployees() throws SQLException {
        List<EmployeeInfo> employeeList = new ArrayList<>();

        String query = "SELECT EID, ENAME, SAL FROM emp";
        try (Statement stmt = connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                int eid = res.getInt("EID");
                String name = res.getString("ENAME");
                float salary = res.getFloat("SAL");
                EmployeeInfo employee = new EmployeeInfo(eid, name, salary);
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    public boolean raiseSalary(String job, float amount) throws SQLException {
        String query = "UPDATE emp SET SAL = SAL + ? WHERE JOB = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, amount);
            stmt.setString(2, job);

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        }
    }
}






