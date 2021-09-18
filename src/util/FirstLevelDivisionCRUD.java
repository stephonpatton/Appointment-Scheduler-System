package util;

import Model.Country;
import Model.FirstLevelDivision;
import javafx.collections.ObservableList;

import java.sql.*;

public class FirstLevelDivisionCRUD {
    public static void loadAllFirstLevel() {
        Connection conn;
        try {
            conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT * FROM first_level_divisions";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("COUNTRY_ID");

                FirstLevelDivision firstLevel = new FirstLevelDivision();
                firstLevel.setDivisionID(divisionID);
                firstLevel.setDivision(division);
                firstLevel.setCreateDate(createDate);
                firstLevel.setCreatedBy(createdBy);
                firstLevel.setLastUpdate(lastUpdate);
                firstLevel.setLastUpdatedBy(lastUpdatedBy);
                firstLevel.setCountryID(countryID);

                FirstLevelDivision.addDivision(firstLevel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
