package util;

import java.sql.*;
import java.util.Properties;

public class Database {
    // Final variables for database credentials
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/client_schedule?verifyServerCertificate=false&useSSL=false";
    private static final String DB_PASSWORD = "Passw0rd!";
    private static final String DB_USERNAME = "sqlUser";

    /**
     * Initializes the connection to the MySQL database
     */
    public static void init() {
        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", DB_USERNAME);
        connectionProps.put("password", DB_PASSWORD);

        try {
            conn = DriverManager.getConnection(DATABASE_URL, connectionProps);
            System.out.println("CONNECTED!");
        }catch(SQLException ex) {
            throw new Error("Error: " + ex.getMessage());
        }
    }

    /**
     * Gets current connection to database. Useful for checking connection throughout program.
     * @return Current connection object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL, DB_USERNAME, DB_PASSWORD);
        return conn;
    }

    /**
     * Closes database connection upon application exit
     * @throws SQLException If connection cannot be established beforehand
     */
    public static void closeConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL, DB_USERNAME, DB_PASSWORD);
        conn.close();
    }
}

