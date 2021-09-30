package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.AppointmentsCRUD;
import util.Time;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {
    // Instance variables for object
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private int customerID;
    private int userID;
    private int contactID;

    private String contactName;
    private Contact contact;
    private int startHr, startMin;
    private int endHr, endMin;
    LocalDate startDate;
    LocalDate endDate;


    private static int totalMonthCount;
    private int totalCount;

    // ObservableList containing all appointments in the database
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Adds appointment to the ObservableList
     * @param appointment Provided appointment object
     */
    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
    }

    /**
     * Updates the appointment object in the ObservableList
     * @param index Index of appointment object to update
     * @param appointment Updated appointment object
     */
    public static void updateAppointment(int index, Appointment appointment) {
        allAppointments.set(index, appointment);
    }

    /**
     * Deletes an appointment object from the system
     * @param appointment Provided appointment object to delete
     * @return True if appointment was successfully deleted
     */
    public static boolean deleteAppointment(Appointment appointment) {
        return allAppointments.remove(appointment);
    }

    /**
     * Returns all appointments in the ObservableList
     * @return All appointments in the system
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * Gets the appointment ID of an appointment
     * @return The appointment ID of an object
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID of an appointment object
     * @param appointmentID Given appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the title of an appointment
     * @return The title of an appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of an appointment
     * @param title Given title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of an appointment
     * @return The description of an appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of an appointment
     * @param description Given description of an appointment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location of an appointment
     * @return The location of an appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of an appointment
     * @param location Given location of an appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the type of an appointment
     * @return The type of an appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of an appointment
     * @param type Given type of an appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start date of an appointment
     * @return The start date of an appointment
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Sets the start date of an appointment
     * @param start Given start date
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * Gets end date/time of an appointment
     * @return The end date/time of an appointment
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Sets the end date/time of an appointment
     * @param end Given end date/time of an appointment
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * Gets creation date of an appointment
     * @return The date an appointment was created
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the creation date of an appointment
     * @param createdDate Given creation date of an appointment
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets who/what created an appointment
     * @return Who/what created an appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets who/what created an appointment
     * @param createdBy Given information on who/what created an appointment
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the time of the last update of an appointment
     * @return The time of the last update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the time an appointment was last updated
     * @param lastUpdate Given time of last update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets who/what last updated an appointment
     * @return Who/what last updated an appointment
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Sets who/what last updated an appointment
     * @param lastUpdateBy Given information on who/what last updated an appointment
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Gets customerID of an appointment
     * @return CustomerID of an appointment
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets customerID of an appointment
     * @param customerID Given customerID for an appointment
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets userID of an appointment
     * @return UserID of an appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets userID of an appointment
     * @param userID Given userID for an appointment
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets contactID of an appointment
     * @return contactID of an appointment
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contactID of an appointment
     * @param contactID Given contactID of an appointment
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Helper method for combobox to access contact name
     * @param contactName Provided contact name
     */
    public void setContactName(String contactName) {
//        this.contactName = AppointmentsCRUD.getContactName(this.getContactID());
        this.contactName = contactName;
    }

    /**
     * Gets the contact name of an appointment (helper method)
     * @return Contact name for a given appointment
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * Gets the contact object assigned to an appointment
     * @return The contact object assigned to an appointment
     */
    public Contact getContact() {
        return this.contact;
    }

    /**
     * Assigns a contact object to an appointment
     * @param contact Provided contact object
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Sets the start hour of an object (useful for spinner)
     * @param startHr Inputted start hour
     */
    public void setStartHr(int startHr) {
        this.startHr = startHr;
    }

    /**
     * Gets the start hour of an appointment
     * @return The start hour of an appointment
     */
    public int getStartHr() {
        return this.startHr;
    }

    /**
     * Sets the start minute of an appointment
     * @param startMin Inputted start minute of an appointment
     */
    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    /**
     * Gets the start minutes of an appointment
     * @return Start minute (time) of an appointment
     */
    public int getStartMin() {
        return this.startMin;
    }

    /**
     * Sets the end hour time of an appointment
     * @param endHr Inputted end hour time
     */
    public void setEndHr(int endHr) {
        this.endHr = endHr;
    }

    /**
     * Sets the end minutes of an appointment
     * @param endMin Inputted end minutes time
     */
    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    /**
     * Gets end hour time of an appointment
     * @return The end hour time of an appointment
     */
    public int getEndHr() {
        return endHr;
    }

    /**
     * Gets the end minutes time of an appointment
     * @return The end minutes time of an appointment
     */
    public int getEndMin() {
        return endMin;
    }

    /**
     * Sets the start date of an appointment
     * @param startDate Inputted start date for an appointment
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the start date of an appointment
     * @return The start date of an appointment
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the end date of an appointment
     * @param endDate Inputted end date of an appointment
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the end date of an appointment
     * @return The end date of an appointment
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Gets the local start time of an appointment
     * @return The local time of an appointment as a timestamp
     */
    public Timestamp getStartLocal() {
        return Timestamp.valueOf(Time.utcToLocalTime(start));
    }

    /**
     * Gets the local end time of an appointment
     * @return The local end time of an appointment as a timestamp
     */
    public Timestamp getEndLocal() {
        return Timestamp.valueOf(Time.utcToLocalTime(end));
    }


    /**
     * Gets the start time of an appointment as a LocalDateTime object
     * @return Start time as LocalDateTime object
     */
    public LocalDateTime getStartLDT() {
        return Time.convertTStoLDT(start);
    }

    /**
     * Gets an appointment object by a provided appointmentID
     * @param appointmentID Provided appointment ID
     * @return Appointment object assigned to provided appointment ID
     */
    public static Appointment getByID(int appointmentID) {
        Appointment temp = new Appointment();
        for(Appointment appoint : allAppointments) {
            if(appoint.getAppointmentID() == appointmentID) {
                temp = appoint;
                return temp;
            }
        }
        return temp;
    }

    public static ObservableList<Appointment> getAppointmentsByContact(Contact contact) throws SQLException {
        AppointmentsCRUD.loadAllAppointments();
        ObservableList<Appointment> temp = FXCollections.observableArrayList();
        for(Appointment appoint : allAppointments) {
            if(appoint.getContact() == contact) {
                temp.add(appoint);
            }
        }
        return temp;
    }

    public static int getTotalMonthCount() {
        return totalMonthCount;
    }

    public static void setMonthCount(ObservableList<Appointment> appointments) {
        totalMonthCount = appointments.size();
    }

    public void setTotal(int total) {
        totalCount = total;
    }

    public int getTotal() {
        return totalCount;
    }
}
