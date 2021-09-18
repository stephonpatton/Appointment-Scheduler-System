package util;

import Model.Appointment;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

import static java.time.LocalDateTime.now;



public class AppointmentsCRUD {

//    static final long timeAtLocal = System.currentTimeMillis(); // or System.currentTimeMillis(); or new Date().getTime(); etc.
//    static final long offset = TimeZone.getDefault().getOffset(timeAtLocal);
//    static final Timestamp timeAtUTC = new Timestamp(timeAtLocal - offset);

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
                tempAppoint.setStartHr(start.toLocalDateTime().getHour());
                tempAppoint.setStartMin(start.toLocalDateTime().getMinute());
                tempAppoint.setEndHr(end.toLocalDateTime().getHour());
                tempAppoint.setEndMin(end.toLocalDateTime().getMinute());
                tempAppoint.setStartDate(start.toLocalDateTime().toLocalDate());
                tempAppoint.setEndDate(end.toLocalDateTime().toLocalDate());
                System.out.println("TIMESTAMP FROM DB" + start.toLocalDateTime().getHour() + ":" + start.toLocalDateTime().getMinute() + ":" + start.toLocalDateTime().getSecond());
                // TODO: ^ THIS WILL GET THE INFO FOR ME

                // helper method for viewing appointment TableView
//                tempAppoint.setContactName();
                tempAppoint.setContactName(AppointmentsCRUD.getContactName(contactID));
//                tempAppoint.setCreatedDate(Timestamp.valueOf(formatDate.format(createdDate))); // TODO: Maybe change... printing tailing .0 at the end; also useful for create operation

                // Adds to appointment list


                Appointment.addAppointment(tempAppoint);

            }
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public static void insertAppointment(Appointment appointment) {
        final long timeAtLocal = System.currentTimeMillis(); // or System.currentTimeMillis(); or new Date().getTime(); etc.
        long offset = TimeZone.getDefault().getOffset(timeAtLocal);
        final Timestamp timeAtUTC = new Timestamp(timeAtLocal - offset);
        if(!Appointment.getAllAppointments().contains(appointment)) {
            Appointment.addAppointment(appointment);
        }
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
//            ps.setInt(8, appointment.getAppointmentID());
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

    public static void updateAppointment(int id, Appointment appointment) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        final long timeAtLocal = System.currentTimeMillis(); // or System.currentTimeMillis(); or new Date().getTime(); etc.
        long offset = TimeZone.getDefault().getOffset(timeAtLocal);
        final Timestamp timeAtUTC = new Timestamp(timeAtLocal - offset);
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

    public static void deleteAppointment(Appointment appointment) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
//            ResultSet rs;
            String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, appointment.getAppointmentID());
            ps.executeUpdate();
            System.out.println("Appointment deleted from database...");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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
            System.out.println("ID IS " + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
//        return Appointment.getAllAppointments().size() + 1;
    }

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

    public static LocalDateTime toUTCFromLDT(String date, String time) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate =  LocalDateTime.parse(date + " " + time, format).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        return localDate;
    }
}
