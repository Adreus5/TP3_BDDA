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

    //------- EXO 2 -------
    /* public List<EmployeeInfo> getEmployees() throws SQLException {
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
    */

    //------- EXO 4 -------
    public List<EmployeeInfo> getEmployeesPS() throws SQLException {
        List<EmployeeInfo> employeeList = new ArrayList<>();
        String query = "SELECT EID, ENAME, SAL FROM emp";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int eid = res.getInt("EID");
                String name = res.getString("ENAME");
                float salary = res.getFloat("SAL");
                EmployeeInfo employee = new EmployeeInfo(eid, name, salary);
                employeeList.add(employee);
            }
            return employeeList;
        }
    }

    public boolean raiseSalary(String job, float amount) throws SQLException {
        String query = "UPDATE emp SET SAL = SAL + " + amount + " WHERE JOB = '" + job + "'";

        try (Statement stmt = connection.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query);
            return rowsUpdated > 0;
        }
    }


    public boolean raiseSalaryPS(String job, float amount) throws SQLException {
        String query = "UPDATE emp SET SAL = SAL + ? WHERE JOB = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, amount);
            stmt.setString(2, job);

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        }
    }

    //------- EXO 5 -------
    /*public List<DepartmentInfo> getDepartments(Integer did, String dname, String dloc) throws SQLException {
        List<DepartmentInfo> departmentList = new ArrayList<>();
        String query = "SELECT DID, DNAME, DLOC FROM dept WHERE 1=1";
        if (did != null) {
            query += " AND DID = " + did;
        }

        if (dname != null) {
            query += " AND DNAME = '" + dname + "'";
        }

        if (dloc != null) {
            query += " AND DLOC = '" + dloc + "'";
        }
        try (Statement stmt = connection.createStatement()) {
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("DID");
                String name = res.getString("DNAME");
                String loc = res.getString("DLOC");
                DepartmentInfo department = new DepartmentInfo(id, name, loc);
                departmentList.add(department);
            }
            return departmentList;
        }
    }
*/
    public List<DepartmentInfo> getDepartmentsPS(Integer did, String dname, String dloc) throws SQLException {
        List<DepartmentInfo> departmentList = new ArrayList<>();
        String query = "SELECT DID, DNAME, DLOC FROM dept WHERE 1=1";
        if (did != null) {
            query += " AND DID = ?";
        }

        if (dname != null) {
            query += " AND DNAME = ?";
        }

        if (dloc != null) {
            query += " AND DLOC = ?";
        }
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int parameterIndex = 1;

            if (did != null) {
                stmt.setInt(parameterIndex++, did);
            }

            if (dname != null) {
                stmt.setString(parameterIndex++, dname);
            }

            if (dloc != null) {
                stmt.setString(parameterIndex, dloc);
            }

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("DID");
                String name = res.getString("DNAME");
                String loc = res.getString("DLOC");
                DepartmentInfo department = new DepartmentInfo(id, name, loc);
                departmentList.add(department);
            }
        }
        return departmentList;
    }

    public List<String> executeQuery(String query) throws SQLException {
        List<String> result = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet res = stmt.executeQuery();

            ResultSetMetaData metaData = res.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Construction d'un en-tête pour plus de lisibilité
            StringBuilder head = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                head.append(metaData.getColumnName(i));
                if (i < columnCount) {
                    head.append(", ");
                }
            }
            result.add(head.toString());

            //Récupérer le contenu pour chaque colonne sur la ligne sélectionnée
            while (res.next()) {
                StringBuilder ligne = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    ligne.append(res.getString(i));
                    if (i < columnCount) {
                        ligne.append(", ");
                    }
                }
                result.add(ligne.toString());
            }
        }
        return result;
    }

    public List<String> executeStatement(String statement) throws SQLException {
        List<String> result = new ArrayList<>();
        if (statement.trim().toLowerCase().startsWith("select")) {
            result = executeQuery(statement);
        } else {
            try (Statement stmt = connection.createStatement()) {
                boolean hasResultSet = stmt.execute(statement);

                if (!hasResultSet) {
                    int count = stmt.getUpdateCount();
                    result.add("Nombre de lignes affectées : " + count);
                }
            }
        }
        return result;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Connection to the database is closed.");
        }
    }
}







