package Model;

import util.UsersCRUD;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
    private static String currentUser;
    private int userID;
    private String userName; // must be unique
    private String password;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /**
     * Gets the userID of a user object
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the userID of a user
     * @param userID Given userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Sets the username of a user object
     * @return Gets the userID of a user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of a user object
     * @param userName Provided username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password of a user
     * @return Password of a user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password to a user
     * @param password Given password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets creation date of a user
     * @return The date a user was created
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date of a user
     * @param createDate Date a user was created
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets where user was created by/from (script, another user, etc)
     * @return Who/what created a user
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets who/what created a user
     * @param createdBy Given information about who/what created user
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the last update time of a user object
     * @return The time this user was last updated
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update time of a user object
     * @param lastUpdate Time that a user object was last updated
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the user who last updated a user object
     * @return Who last updated a user object
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets who last updated a user object
     * @param lastUpdatedBy Given information about who last updated user
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String username) {
        currentUser = username;
    }
}
