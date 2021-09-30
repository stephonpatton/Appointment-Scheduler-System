package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;

public class FirstLevelDivision {
    // Local variables for FirstLevelDivision objects
    private int divisionID;
    private String division;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    private int total;

    // ObservableLists to hold all division, us divisions, uk divisions, and canadian divisions
    private static ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> usDivisions = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> ukDivisions = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> caDivisions = FXCollections.observableArrayList();

    /**
     * Adds a US division to the system
     * @param division Provided division object
     */
    public static void addUSDivision(FirstLevelDivision division) {
        usDivisions.add(division);
    }

    /**
     * Adds a UK division to the system
     * @param division Provided division
     */
    public static void addUKDivision(FirstLevelDivision division) {
        ukDivisions.add(division);
    }

    /**
     * Adds a CA division to the system
     * @param division Provided division
     */
    public static void addCADivision(FirstLevelDivision division) {
        caDivisions.add(division);
    }

    /**
     * Adds a division to the all divisions ObservableList
     * @param division Provided division
     */
    public static void addDivision(FirstLevelDivision division) {
        allDivisions.add(division);
    }

    /**
     * Returns all divisions in the system
     * @return ObservableList of all divisions
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() {
        return allDivisions;
    }

    /**
     * Returns all US divisions in the system
     * @return ObservableList of all US divisions
     */
    public static ObservableList<FirstLevelDivision> getAllUSDivisions() {
        return usDivisions;
    }

    /**
     * Returns all UK divisions in the system
     * @return ObservableList of all UK divisions
     */
    public static ObservableList<FirstLevelDivision> getAllUKDivisions() {
        return ukDivisions;
    }

    /**
     * Returns all Canadian divisions in the system
     * @return ObservableList of all CA divisions
     */
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

    /**
     * Gets a FirstLevelDivision object based on division ID
     * @param id Provided division ID of object
     * @return FirstLevelDivision object
     */
    public static FirstLevelDivision getFirstLevelByID(int id) {
        FirstLevelDivision temp = null;

        for (FirstLevelDivision firstLevel : allDivisions) {
            if (firstLevel.getDivisionID()== id) {
                temp = firstLevel;
            }
        }
        return temp;
    }

    /**
     * Helper method for ComboBox to present objects in a readable way
     * @return Name of division
     */
    public String toString() {
        return division;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }
}
