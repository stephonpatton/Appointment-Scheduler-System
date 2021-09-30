package util;

import Model.Appointment;
import Model.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Time {
    private static Appointment appointmentSoon;
    /**
     * Converts a timestamp to LDT object in UTC
     * @param ts Timestamp object
     * @return LocalDateTime object in UTC timezone
     */
    public static LocalDateTime convertTStoLDT(Timestamp ts) {
        return ts.toLocalDateTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    /**
     * Converts UTC Timestamp object to LocalDateTime object in users local time
     * @param dateTime Timestamp object
     * @return LocalDateTime object in users local time
     */
    public static LocalDateTime utcToLocalTime(Timestamp dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        return LocalDateTime.parse(dateTime.toString(), format).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Checks if inputted hours are within business hours in EST (8am - 10pm)
     * @param utcLDT Time inputted (LocalDateTime object)
     * @return True if it is within business hours
     */
    public static boolean checkBusinessHours(LocalDateTime utcLDT) {
        boolean isValid = false;
        int localHour = utcLDT.getHour();
        try {
            System.out.println("VALUE OF UTC LDT" + utcLDT);
            if(localHour >= 12) {
                System.out.println("VALID TIME PROVIDED");
                isValid = true;
            } else if(localHour <= 2) {
                isValid = true;
            } else {
                isValid = false;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Checks if user who logged in has an appointment within the next 15 minutes
     * @return True if the user does have an appointment scheduled in the next 15 minutes
     */
    public static boolean checkIfAppointment15() {
        boolean isAppointment = false;
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT Appointment_ID, Start FROM appointments WHERE User_ID = ? AND Start BETWEEN ? AND ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, UsersCRUD.getUserID(User.getCurrentUser()));
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(15)));
            rs = ps.executeQuery();
            rs.next();
                int appointmentId = rs.getInt("Appointment_ID");
                Timestamp start = rs.getTimestamp("Start");

                appointmentSoon = Appointment.getByID(appointmentId);
                isAppointment = true;
        }catch(SQLException e) {
            isAppointment = false;
            System.err.println("NO RESULTS");
        }
        return isAppointment;
    }

    /**
     * Takes two strings and converts them to a timestamp
     * @param date Date provided in string format
     * @param time Time provided in string format
     * @return Timestamp formatted (yyyy-MM-dd HH:mm:ss)
     */
    public static Timestamp convertStringsToTime(String date, String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = date + " " + time;
            Date parsedDate = formatter.parse(dateTime);
            Timestamp ts = new Timestamp(parsedDate.getTime());
            System.out.println("TIMESTAMP: " + ts.getTime());
            return ts;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * Helper method to transfer which appointment is coming within the next 15 mins
     * @return Appointment object of appointment in next 15 mins
     */
    public static Appointment getAppointmentSoon() {
        return appointmentSoon;
    }
}
