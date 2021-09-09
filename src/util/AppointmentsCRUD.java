package util;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentsCRUD {
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        try {
            Appointment tempAppoint;
            String query = "SELECT * FROM appointments"; //TODO: CHECK LATER
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

                // Sets data to object
                tempAppoint.setAppointmentID(appointmentID);
                tempAppoint.setTitle(title);
                tempAppoint.setDescription(description);
                tempAppoint.setLocation(location);
                tempAppoint.setType(type);

                // Adds to appointment list
                allAppointments.add(tempAppoint);
                System.out.println("APPOINTMENT ID IS: " + appointmentID);
            }
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }

        for (Appointment allAppointment : allAppointments) {
            System.out.println(allAppointment.getType());
        }
        return allAppointments;
    }


    //TODO: After UI, check if fields have valid data first, then make appointment object
}
