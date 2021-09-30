package util;

import Model.Appointment;
import Model.Country;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public static ObservableList<FirstLevelDivision> getFirstLevelByCountry(Country country) {
        ObservableList<FirstLevelDivision> temp = FXCollections.observableArrayList();
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT COUNT(*) AS Total, Division FROM customers" +
                    " JOIN first_level_divisions AS F ON F.Division_ID = customers.Division_ID INNER JOIN countries WHERE countries.Country_ID = ? AND F.COUNTRY_ID = ? GROUP BY(F.Division_ID) ORDER BY Total;";
            ps = conn.prepareStatement(query);
            ps.setInt(1, country.getCountryID());
            ps.setInt(2, country.getCountryID());
            rs = ps.executeQuery();

            while(rs.next()) {
                FirstLevelDivision fl = new FirstLevelDivision();
//                int id = rs.getInt("Appointment_ID");
                String division = rs.getString("Division");
                int total = rs.getInt("Total");
//                appoint.setAppointmentID(id);
                fl.setTotal(total);
                fl.setDivision(division);
                temp.add(fl);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return temp;
    }
}
