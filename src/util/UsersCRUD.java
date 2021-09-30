package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersCRUD {
    /**
     * Gets userID based on username provided
     * @param username Provided username
     * @return User ID of a user based on username
     * @throws SQLException If database issues occur
     */
    public static int getUserID(String username) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        int userID = 0;

        try {
            String query = "SELECT User_ID FROM users WHERE User_Name = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while(rs.next()) {
                userID = rs.getInt("User_ID");
            }
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
        return userID;
    }
}
