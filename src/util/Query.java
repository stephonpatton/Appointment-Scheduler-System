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
                System.out.println("USER ID IS: " + userID);
                success = true;
            }
        }catch(SQLException ex) {
            throw new Error("Problem", ex);
        }
        return success;
    }
}
