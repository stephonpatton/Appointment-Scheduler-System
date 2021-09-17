package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.AppointmentsCRUD;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

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


    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);

        for (Appointment allAppointment : allAppointments) {
            System.out.println(allAppointment.getType());
            System.out.println(allAppointment.getCreatedBy());
            System.out.println(allAppointment.getLastUpdate());
            System.out.println(allAppointment.getUserID());
            System.out.println(allAppointment.getCustomerID());
        }

        System.out.println("TOTAL COUNT " + allAppointments.size());
    }

    public static void updateAppointment(int index, Appointment appointment) {
        allAppointments.set(index, appointment);
    }

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

    public void setContactName() {
        this.contactName = AppointmentsCRUD.getContactName(this.getContactID());
    }

    public String getContactName() {
        return this.contactName;
    }

    public Contact getContact() {
        return this.contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setStartHr(int startHr) {
        this.startHr = startHr;
    }

    public int getStartHr() {
        return this.startHr;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getStartMin() {
        return this.startMin;
    }

    public void setEndHr(int endHr) {
        this.endHr = endHr;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public int getEndHr() {
        return endHr;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
}
