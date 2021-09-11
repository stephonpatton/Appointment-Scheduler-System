package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    public static void addContact(Contact contact) {
        allContacts.add(contact);
    }

    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    public static ObservableList<String> getAllContactNames() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        for (Contact allContact : allContacts) {
            temp.add(allContact.getContactName());
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

    public String toString() {
        return contactName;
    }
}
