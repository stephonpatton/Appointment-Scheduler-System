package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class FirstLevelDivision {
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

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
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of a division
     * @param createDate Given date a division was created
     */
    public void setCreateDate(Date createDate) {
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
}
