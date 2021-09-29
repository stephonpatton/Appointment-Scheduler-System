package util;

import Model.FirstLevelDivision;
import java.sql.*;

public class FirstLevelDivisionCRUD {
    /**
     * Loads all first level division data from database and stores locally
     */
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

                // Adds locally
                FirstLevelDivision.addDivision(firstLevel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets all US division data from database
     */
    public static void getUSDivisions() {
        Connection conn;
        try {
            conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 1";
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

                FirstLevelDivision.addUSDivision(firstLevel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets all UK division data from database
     */
    public static void getUKDivisions() {
        Connection conn;
        try {
            conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 2";
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

                FirstLevelDivision.addUKDivision(firstLevel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets all Canadian division data from database
     */
    public static void getCADivisions() {
        Connection conn;
        try {
            conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 3";
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

                FirstLevelDivision.addCADivision(firstLevel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
