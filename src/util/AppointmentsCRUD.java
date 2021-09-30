package util;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class AppointmentsCRUD {
    /**
     * Loads all appointments from the database to store locally
     * @throws SQLException If appointments fail to be loaded from database
     */
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
                tempAppoint.setStartHr(start.toLocalDateTime().getHour());
                tempAppoint.setStartMin(start.toLocalDateTime().getMinute());
                tempAppoint.setEndHr(end.toLocalDateTime().getHour());
                tempAppoint.setEndMin(end.toLocalDateTime().getMinute());
                tempAppoint.setStartDate(start.toLocalDateTime().toLocalDate());
                tempAppoint.setEndDate(end.toLocalDateTime().toLocalDate());

                // helper method for viewing appointment TableView
                tempAppoint.setContactName(AppointmentsCRUD.getContactName(contactID));

                // Adds to appointment list
                Appointment.addAppointment(tempAppoint);
            }
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
    }

    /**
     * Inserts appointment into the database after user has provided all information
     * @param appointment Appointment object provided
     */
    public static void insertAppointment(Appointment appointment) {
        // Gets the local time of user in order to make utc object for database entries
        final long timeAtLocal = System.currentTimeMillis();
        long offset = TimeZone.getDefault().getOffset(timeAtLocal);
        final Timestamp timeAtUTC = new Timestamp(timeAtLocal - offset);

        // Checks if appointment is in local system, if not then it adds
        if(!Appointment.getAllAppointments().contains(appointment)) {
            Appointment.addAppointment(appointment);
        }
        // Tries to insert data into the database
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            String query = "INSERT INTO appointments(Title, Description, Location, Type, Customer_ID, " +
                    "User_ID, Contact_ID, Appointment_ID, Created_By, Start, End, Create_Date, Last_Update) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, appointment.getTitle().trim());
            ps.setString(2, appointment.getDescription().trim());
            ps.setString(3, appointment.getLocation().trim());
            ps.setString(4, appointment.getType().trim());
            ps.setInt(5, appointment.getCustomerID());
            ps.setInt(6, appointment.getUserID());
            ps.setInt(7, appointment.getContactID());
            ps.setInt(8, AppointmentsCRUD.getNextIDCount());
            ps.setString(9, appointment.getCreatedBy());
            ps.setTimestamp(10, appointment.getStart());
            ps.setTimestamp(11, appointment.getEnd());
            ps.setTimestamp(12, timeAtUTC);
            ps.setTimestamp(13, timeAtUTC);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates appointment data in the database
     * @param id Provided id
     * @param appointment Provided appointment object
     */
    public static void updateAppointment(int id, Appointment appointment) {
        // Changes local time of now to utc time
        final long timeAtLocal = System.currentTimeMillis();
        long offset = TimeZone.getDefault().getOffset(timeAtLocal);
        final Timestamp timeAtUTC = new Timestamp(timeAtLocal - offset);
        // Tries to update appointment in database
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            String query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, " +
                    "Customer_ID = ?, User_ID = ?, Contact_ID = ?, Appointment_ID = ?, Last_Updated_By = ?," +
                    "Start = ?, End = ?, Last_Update = ? WHERE Appointment_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, appointment.getTitle().trim());
            ps.setString(2, appointment.getDescription().trim());
            ps.setString(3, appointment.getLocation().trim());
            ps.setString(4, appointment.getType().trim());
            ps.setInt(5, appointment.getCustomerID());
            ps.setInt(6, appointment.getUserID());
            ps.setInt(7, appointment.getContactID());
            ps.setInt(8, appointment.getAppointmentID());
            ps.setString(9, appointment.getLastUpdateBy());
            ps.setTimestamp(10, appointment.getStart());
            ps.setTimestamp(11, appointment.getEnd());
            ps.setTimestamp(12, timeAtUTC);
            ps.setInt(13, appointment.getAppointmentID());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes an appointment from the database
     * @param appointment Provided appointment object
     */
    public static void deleteAppointment(Appointment appointment) {
        // Deletes an appointment from the database
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, appointment.getAppointmentID());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets the next ID count for the database (Appointment_ID)
     * @return The next ID for database entry
     */
    public static int getNextIDCount() {
        int id = 0;
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT MAX(LAST_INSERT_ID(Appointment_ID)) + 1 AS NEXT_ID FROM appointments";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                id = rs.getInt("NEXT_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    /**
     * Gets the contact name assigned to an appointment from the database
     * @param userID Provided userID used for inner join
     * @return Name of contact assigned to appointment
     */
    public static String getContactName(int userID) {
        String name = "";
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
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

    /**
     * Gets all appointments based on a contact
     * @param contact Provided contact object
     * @return An ObservableList of appointments based on contact
     */
    public static ObservableList<Appointment> getAppointmentsByContact(Contact contact) {
        ObservableList<Appointment> temp = FXCollections.observableArrayList();
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT * FROM appointments INNER JOIN contacts AS C WHERE appointments.Contact_ID = ? AND C.Contact_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, contact.getContactID());
            ps.setInt(2, contact.getContactID());
            rs = ps.executeQuery();

            while(rs.next()) {
                Appointment appoint = new Appointment();
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                String description = rs.getString("Description");

                appoint.setAppointmentID(id);
                appoint.setTitle(title);
                appoint.setType(type);
                appoint.setStart(start);
                appoint.setEnd(end);
                appoint.setCustomerID(customerID);
                appoint.setDescription(description);
                temp.add(appoint);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return temp;
    }


    public static ObservableList<Appointment> getAppointmentsByMonthAndType(int month) {
        ObservableList<Appointment> temp = FXCollections.observableArrayList();
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT type, COUNT(*) AS Total FROM appointments WHERE MONTH(Start) = ? AND MONTH(End) = ? GROUP BY MONTH(?), type ORDER BY Total ASC";
            ps = conn.prepareStatement(query);
            ps.setInt(1, month);
            ps.setInt(2, month);
            ps.setInt(3, month);
            rs = ps.executeQuery();

            while(rs.next()) {
                Appointment appoint = new Appointment();
//                int id = rs.getInt("Appointment_ID");
                String type = rs.getString("Type");
                int total = rs.getInt("Total");
//                appoint.setAppointmentID(id);
                appoint.setTotal(total);
                appoint.setType(type);
                temp.add(appoint);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return temp;
    }
}
