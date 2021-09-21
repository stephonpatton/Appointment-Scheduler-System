package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.Timestamp;

public class FirstLevelDivision {
    private static ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> usDivisions = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> ukDivisions = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> caDivisions = FXCollections.observableArrayList();


    private int divisionID;
    private String division;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public static void addUSDivision(FirstLevelDivision division) {
        usDivisions.add(division);
    }

    public static void addUKDivision(FirstLevelDivision division) {
        ukDivisions.add(division);
    }

    public static void addCADivision(FirstLevelDivision division) {
        caDivisions.add(division);
    }

    public static void addDivision(FirstLevelDivision division) {
        allDivisions.add(division);
    }

    public static ObservableList<FirstLevelDivision> getAllDivisions() {
        return allDivisions;
    }

    public static ObservableList<FirstLevelDivision> getAllUSDivisions() {
        return usDivisions;
    }

    public static ObservableList<FirstLevelDivision> getAllUKDivisions() {
        return ukDivisions;
    }

    public static ObservableList<FirstLevelDivision> getAllCADivisions() {
        return caDivisions;
    }

    /**
     * Gets the divisionID of a division
     * @return The division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the divisionID of a division
     * @param divisionID Given id for a division
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Gets the name of a division
     * @return The name of a division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of a division
     * @param division Given name of a division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the creation date of a division
     * @return The date a division was created
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of a division
     * @param createDate Given date a division was created
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets information on who created a division object
     * @return Information on who/what created a division object
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets who/what created a division object
     * @param createdBy Who/what created a division object
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the timestamp of the last time a division object was updated
     * @return the last updated timestamp
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the timestamp of the last update to a division object
     * @param lastUpdate Given timestamp of last update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the last user who updated a division object
     * @return Who last updated a division object
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets who/what last updated a division object
     * @param lastUpdatedBy Given information on who last updated
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the countryID of a division
     * @return The countryID of a division
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets the countryID of a division
     * @param countryID Given ID of a country
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public static FirstLevelDivision getFirstLevelByID(int id) {
        FirstLevelDivision temp = null;

        for (FirstLevelDivision firstLevel : allDivisions) {
            if (firstLevel.getDivisionID()== id) {
                temp = firstLevel;
            }
        }
        return temp;
    }

    public String toString() {
        return division;
    }
}
