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

    public static MysqlDBClient getInstance() throws SQLException {
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

    public void validationCredential(String userSent, String pwdSent) {
        String table = "users";
        String column = "username";
        String querySelectFromUsers = String.format("SELECT * FROM %s WHERE %s='%s'", table, column, userSent);
        //String pwdDb, saltDb, roleDb;
        String pwdDb = "";
        String saltDb = "";
        String roleDb = "";
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(querySelectFromUsers);
            rs.next();
            pwdDb = rs.getString("password");
            saltDb = rs.getString("salt");

            if (Sha512.get_SHA_512_SecurePassword(pwdSent, saltDb).equals(pwdDb)) {
                System.out.println("CORRECT PASSWORD");
            } else {
                System.out.println("INCORRECT PASSWORD");
            }



//            System.out.println("username: " + rs.getString("username") +
//                                "\npassword: " + rs.getString("password") +
//                                "\nsalt: " + rs.getString("salt") +
//                                "\nrole " + rs.getString("role"));

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.out.println("THAT USER DOESN'T EXISTS");
            //return "USER DOESN'T EXISTS";
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException sqlEx) { }
                rs = null;
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException sqlEx) { }
                stmt = null;
            }
        }
    }
}
