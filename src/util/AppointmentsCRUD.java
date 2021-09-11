package util;

import Model.Appointment;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AppointmentsCRUD {
    private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void loadAllAppointments() throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        try {
            Appointment tempAppoint;
            String query = "SELECT * FROM appointments";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                // Temp object to hold data
                tempAppoint = new Appointment();

                // Get data from database
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createdDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                // Sets data to object
                tempAppoint.setAppointmentID(appointmentID);
                tempAppoint.setTitle(title);
                tempAppoint.setDescription(description);
                tempAppoint.setLocation(location);
                tempAppoint.setType(type);
                tempAppoint.setStart(start);
                tempAppoint.setEnd(end);
                tempAppoint.setCreatedDate(createdDate);
                tempAppoint.setCreatedBy(createdBy);
                tempAppoint.setLastUpdate(lastUpdate);
                tempAppoint.setLastUpdateBy(lastUpdateBy);
                tempAppoint.setCustomerID(customerID);
                tempAppoint.setUserID(userID);
                tempAppoint.setContactID(contactID);

                // helper method for viewing appointment TableView
                tempAppoint.setContactName();
//                tempAppoint.setCreatedDate(Timestamp.valueOf(formatDate.format(createdDate))); // TODO: Maybe change... printing tailing .0 at the end; also useful for create operation

                // Adds to appointment list
                Appointment.addAppointment(tempAppoint);
            }
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public static void insertAppointment(Appointment appointment) {
        if(!Appointment.getAllAppointments().contains(appointment)) {
            Appointment.addAppointment(appointment);
        }
    }

    public static int getNextIDCount() {
        return Appointment.getAllAppointments().size() + 1;
    }

    public static String getContactName(int userID) {
        String name = "";
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs = null;
            String query = "SELECT Contact_Name FROM contacts INNER JOIN appointments WHERE contacts.Contact_ID = ? AND appointments.Contact_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, userID);
            rs = ps.executeQuery();

            while(rs.next()) {
                name = rs.getString("Contact_Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return name;
    }

    //TODO: After UI, check if fields have valid data first, then make appointment object
}
