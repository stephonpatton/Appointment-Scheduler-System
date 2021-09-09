package Model;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Get a contact object's ID
     * @return The id of a contact
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contact ID to a contact
     * @param contactID Given contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the name of a contact object
     * @return The name of a contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact name
     * @param contactName Given contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets a contacts email address
     * @return Email address of a contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of a contact
     * @param email Given email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
