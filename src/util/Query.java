package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {
    /**
     * Logs user into system based on provided username and password.
     * If the user is authenticated, this method returns true.
     * @param user
     * @param pass
     * @return True if username and password are correct
     * @throws SQLException
     */
    public static boolean login(String user, String pass) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        boolean success = false;

        try {
            // Query to get user id of provided username and password combo (easy check for validation)
            String query = "SELECT USER_ID FROM users WHERE User_Name = ? AND Password = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while(rs.next()) {
                String userID = rs.getString("User_ID");
                success = true;
            }
        }catch(SQLException ex) {
            throw new Error("Problem", ex);
        }
        return success;
    }

    /**
     * Checks if a customer is in the database based on customerID
     * @param customerID Provided customer ID
     * @return true if customer is in the database
     * @throws SQLException If customer is not in the database or connection fails
     */
    public static boolean checkCustomerInDB(int customerID) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        boolean success = false;

        try {
            String query = "SELECT Customer_Name FROM customers WHERE Customer_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            while(rs.next()) {
                String custName = rs.getString("Customer_Name");
                success = true;
            }
        }catch(SQLException ex) {
            throw new Error("Problem", ex);
        }
        return success;
    }

    /**
     * Checks if a user is in the database based on a user ID
     * @param userID Provided user ID
     * @return True if user is in the database
     * @throws SQLException If connection failed or user is not in the database
     */
    public static boolean checkUserInDB(int userID) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        boolean success = false;

        try {
            String query = "SELECT User_Name FROM users WHERE User_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while(rs.next()) {
                String uName = rs.getString("User_Name");
                success = true;
            }
        }catch(SQLException ex) {
            throw new Error("Problem", ex);
        }
        return success;
    }
}
