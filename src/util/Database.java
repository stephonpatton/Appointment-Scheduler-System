package util;

import java.sql.*;
import java.util.Properties;

public class Database {

    private static final String DATABASE_URL = "jdbc:mysql://wgudb.ucertify.com:3306/WJ08U87?verifyServerCertificate=false&useSSL=true";
    private static final String DB_PASSWORD = "53689393671";
    private static final String DB_USERNAME = "U08U87";

    public static void init() {
        Connection conn = null;
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

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL, DB_USERNAME, DB_PASSWORD);
        return conn;
    }

    public static boolean login(String user, String pass) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs;

        try {
            String query = "SELECT * FROM "
        }

//        try {
//            PreparedStatement stmt = null;
//            ResultSet rs;
//            String query = "SELECT Type FROM appointments WHERE User_ID = (SELECT User_ID FROM users WHERE User_Name = ?)";
//
//            try {
//                stmt = conn.prepareStatement(query);
//                stmt.setString(1, user);
//                rs = stmt.executeQuery();
//                while(rs.next()) {
//                    String type = rs.getString("Type");
//                    System.out.println(type); //TODO: Delete all this later
//                }
//            }catch(SQLException e) {
//                throw new Error("Problem", e);
//            } finally {
//                if(stmt != null) {stmt.close();}
//            }
//        } catch (SQLException e) {
//            System.err.println("Connection not established.");
//        }
    }
}

