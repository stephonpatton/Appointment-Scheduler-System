package util;

import java.sql.*;
import java.util.Properties;

public class Database {
    public static void login(String user, String pass) {
        Connection conn = null;
        String username = "U08U87";
        String password = "53689393671";
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        try {
            String databaseURL = "jdbc:mysql://wgudb.ucertify.com:3306/WJ08U87?verifyServerCertificate=false&useSSL=true";
            conn = DriverManager.getConnection(databaseURL, connectionProps);
            System.out.println("CONNECTED!");
            PreparedStatement stmt = null;
            ResultSet rs;
            String query = "SELECT Type FROM appointments WHERE User_ID = (SELECT User_ID FROM users WHERE User_Name = ?)";

            try {
                stmt = conn.prepareStatement(query);
                stmt.setString(1, user);
                rs = stmt.executeQuery();
                while(rs.next()) {
                    String type = rs.getString("Type");
                    System.out.println(type); //TODO: Delete all this later
                }
            }catch(SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if(stmt != null) {stmt.close();}
            }
        } catch (SQLException e) {
            System.err.println("Connection not established.");
        }
    }
}

