package util;

import Model.Appointment;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class Time {
    private static Appointment appointmentSoon;
    public void localTimeToEst() {

    }

//    public static LocalDateTime localTimeToUTC() {
//
//    }

    public static Timestamp localTimeToUTCTS(LocalDateTime ldt) {
        Timestamp ts;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        ts = Timestamp.valueOf(ldt.format(format));
        return ts;
    }

    public static LocalDateTime convertTStoLDT(Timestamp ts) {
//        LocalDateTime ldt = ts.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime ldt = ts.toLocalDateTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        return ldt;
    }

    public static LocalDateTime timestampToESTLDT(Timestamp dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        return LocalDateTime.parse(dateTime.toString(), format).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("EST")).toLocalDateTime();
    }

    public static LocalDateTime utcToLocalTime(Timestamp dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        return LocalDateTime.parse(dateTime.toString(), format).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }


    public static boolean checkBusinessHours(LocalDateTime utcLDT) {
        // TODO: End time could be equal to 200 UTC but nothing over
        // TODO: Start time cannot be 200 UTC at all
        boolean isValid = false;
        int localHour = utcLDT.getHour();
        int localMin = utcLDT.getMinute();
//        LocalDateTime test = LocalDateTime.parse(utcLDT.getYear() + "-" + utcLDT.getMonth() + "-" + utcLDT.getDayOfMonth() + "T" + "02:15");
//        System.out.println("CLOSING TIME: " + test);
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

    public static boolean checkIfAppointment15() throws SQLException {
        boolean isAppointment = false;
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT Appointment_ID, Start FROM appointments WHERE Start BETWEEN ? AND ?";
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(15)));
            rs = ps.executeQuery();
            rs.next();
                int appointmentId = rs.getInt("Appointment_ID");
                Timestamp start = rs.getTimestamp("Start");

                appointmentSoon = Appointment.getByID(appointmentId);
                isAppointment = true;
//                return true;

        }catch(SQLException e) {
            isAppointment = false;
            System.err.println("NO RESULTS");
        }
        return isAppointment;
    }



//    public static boolean checkStartGreaterMin() {
//        //todo: implmenent.. maybe not here
//    }
//    public static boolean checkBusinessHours(LocalDateTime utcLDT) {
//        boolean isValid = false;
//        int localStartHour = utcLDT.getHour();
//        try {
//            LocalDateTime tmp = ldtToEst(utcLDT);
//            System.out.println("VALUE OF TMP" + tmp);
//            int estStartHour = tmp.getHour();
////        int estStartHour = 8;
//            if(estStartHour >= 8 && estStartHour <= 22) {
//                isValid = true;
//            } else {
//                isValid = false;
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//
//        return isValid;
//    }

    public void utcToEST() {

    }

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

    public static Timestamp convertStringToUTCTS(String date, String time) {
        final long timeAtLocal = System.currentTimeMillis(); // or System.currentTimeMillis(); or new Date().getTime(); etc.
        long offset = TimeZone.getDefault().getOffset(timeAtLocal);
        final Timestamp timeAtUTC = new Timestamp(timeAtLocal - offset);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateTime = date + " " + time;
            Date parsedDate = formatter.parse(dateTime);
            Timestamp ts = new Timestamp(parsedDate.getTime());
            System.out.println("TIMESTAMP: " + ts.getTime());
            return ts;
        }catch (Exception e) {
            return null;
        }
    }



    public static LocalDateTime ldtToEst(LocalDateTime ldt) {
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
//        return LocalDateTime.parse(ldt.toString(), format).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("EST")).toLocalDateTime();
//        return ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
        return ldt.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();

    }

//    public static LocalDateTime convertStringToLDT(String date, String time) {
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
//    }

    public static Appointment getAppointmentSoon() {
        return appointmentSoon;
    }

}
