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
                System.out.println(firstLevel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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
                System.out.println(firstLevel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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
                System.out.println(firstLevel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
