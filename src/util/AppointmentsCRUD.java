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
        ObservableList<Appointment> temp = FXCollections.observableArrayList();
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        try {
            String query = "";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                String appointmentID = rs.getString("Appointment_ID");
                System.out.println("APPOINTMENT ID IS: " + appointmentID);
            }
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }


        return temp;
    }
}
