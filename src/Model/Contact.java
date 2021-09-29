package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {
    // Local variables for contact object
    private int contactID;
    private String contactName;
    private String email;

    // ObservableList holding all contacts in the database
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * Adds a contact to the ObservableList
     * @param contact Provided contact object
     */
    public static void addContact(Contact contact) {
        allContacts.add(contact);
    }

    /**
     * Returns all contacts in the ObservableList
     * @return All contacts in the system
     */
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    /**
     * Gets the name of all contacts in the ObservableList
     * @return ObservableList of all contact names in the system
     */
    public static ObservableList<String> getAllContactNames() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        for (Contact allContact : allContacts) {
            temp.add(allContact.getContactName());
        }
        return temp;
    }

    /**
     * Gets a contact object based on a given ID
     * @param id Provided contact ID
     * @return Contact object with provided contact ID
     */
    public static Contact getContactByID(int id) {
        Contact temp = null;
        for (Contact allContact : allContacts) {
            if (allContact.getContactID() == id) {
                temp = allContact;
            }
        }
        return temp;
    }

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

    /**
     * Method used for ComboBox to convert contact object to presentable data
     * @return Name of contact
     */
    public String toString() {
        return contactName;
    }
}
