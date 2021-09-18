package util;

import Model.Country;
import javafx.collections.ObservableList;

import java.sql.*;

public class CountriesCRUD {
    public static void loadAllCountries() {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT * FROM countries";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Timestamp createdDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                Country country = new Country();
                country.setCountryID(countryID);
                country.setCountry(countryName);
                country.setCreateDate(createdDate);
                country.setCreatedBy(createdBy);
                country.setLastUpdate(lastUpdate);
                country.setLastUpdatedBy(lastUpdateBy);
                Country.addCountry(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
