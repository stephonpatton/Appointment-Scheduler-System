package util;

import Model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsCRUD {
    /**
     * Loads all contacts from the database
     */
    public static void loadAllContacts() {
        try {
            Connection conn = Database.getConnection();
            ResultSet rs;
            PreparedStatement ps;
            String query = "SELECT * FROM contacts";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                Contact temp = new Contact();
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                temp.setContactID(contactID);
                temp.setContactName(contactName);
                temp.setEmail(email);

                // Adds to local system
                Contact.addContact(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
