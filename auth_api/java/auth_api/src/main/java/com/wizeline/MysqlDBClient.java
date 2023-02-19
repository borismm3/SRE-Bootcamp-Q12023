package com.wizeline;

import java.sql.*;

public class MysqlDBClient {
    private static MysqlDBClient instance;
    private static Connection connection;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String url = "jdbc:mysql://sre-bootcamp-selection-challenge.cabf3yhjqvmq.us-east-1.rds.amazonaws.com:3306/bootcamp_tht";
    private String username = "secret";
    private String password = "jOdznoyH6swQB9sTGdLUeeSrtejWkcw";

    private MysqlDBClient() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static MysqlDBClient getInstance() {
        if (instance == null) instance = new MysqlDBClient();
        return instance;
    }

    public void getData() {
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println("username: " + rs.getString("username") +
                                    "\nrole: " + rs.getString("role") +
                                    "\npassword: " + rs.getString("password"));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public String validationCredential(String userSent, String pwdSent) {
        String table = "users";
        String column = "username";
        String querySelectFromUsers = String.format("SELECT * FROM %s WHERE %s='%s'", table, column, userSent);
        String pwdDb;
        String saltDb;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(querySelectFromUsers);
            if (rs.next()) {
                pwdDb = rs.getString("password");
                saltDb = rs.getString("salt");
                pwdSent = Sha512.get_SHA_512_SecurePassword(pwdSent, saltDb);
                return pwdSent.equals(pwdDb) ? rs.getString("role") : null;
            } else { return null; }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ignored) { }
                rs = null;
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException ignored) { }
                stmt = null;
            }
        }
    }
}
