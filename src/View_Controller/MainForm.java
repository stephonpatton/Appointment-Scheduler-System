package View_Controller;

import Model.Appointment;
import Model.Customer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.AppointmentsCRUD;
import util.CustomersCRUD;
import util.Database;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    // Appointment TableView FXML
    @FXML private TableView<Appointment> appointmentsTableView;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, Integer> appointIDCol;
    @FXML private TableColumn<Appointment, Integer> userIDCol;
    @FXML private TableColumn<Appointment, String> contactCol;
    @FXML private TableColumn<Appointment, Integer> customerIDCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, Timestamp> startCol;
    @FXML private TableColumn<Appointment, Timestamp> endCol;
    @FXML private TableColumn<Appointment, String> locationCol;
    @FXML private TableColumn<Appointment, String> descriptionCol;

    // Customer TableView FXML
    @FXML private TableView<Customer> customersTableView;
    @FXML private TableColumn<Customer, Integer> customerTVIDCol;
    @FXML private TableColumn<Customer, String> customerTVNameCol;
    @FXML private TableColumn<Customer, Integer> customerTVDivIDCol;
    @FXML private TableColumn<Customer, String> customerTVPhoneIDCol;
    @FXML private TableColumn<Customer, String> customerTVAddressCol;
    @FXML private TableColumn<Customer, String> customerTVPostalCol;

    // Appointment to modify variables
    private static Appointment tempAppointment;
    private static int appointmentIndex;

    // Customer to modify variables
    private static Customer tempCustomer;
    private static int customerIndex;

    /**
     * Initializes once the main screen has loaded. Sets the cells for both tables and populates the tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAppointmentCells();
        setCustomerCells();
        populateAppointmentsTable();
        populateCustomersTable();
    }

    /**
     * Sets the cells for appointments table view
     */
    private void setAppointmentCells() {
        appointIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**
     * Sets the cells for customers table view
     */
    private void setCustomerCells() {
        customerTVIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerTVNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTVDivIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerTVPhoneIDCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTVAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTVPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    }

    /**
     * Populates appointments table with data
     */
    public void populateAppointmentsTable() {
        appointmentsTableView.setItems(Appointment.getAllAppointments());
    }

    /**
     * Populates custombers table with data
     */
    private void populateCustomersTable() {
        customersTableView.setItems(Customer.getAllCustomers());
    }

    /**
     * Opens the AddAppointment screen upon add button press
     * @param actionEvent Add button press
     * @throws IOException
     */
    public void openAddAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/AddAppointment.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root, 670, 500));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


    /**
     * Opens the ModifyAppointment screen upon modify button press
     * @param actionEvent Modify button press
     * @throws IOException
     */
    public void openModifyAppointment(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/ModifyAppointment.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Modify Appointment");
        stage.setScene(new Scene(root, 670, 500));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    /**
     * Gets the selected appointment after a button has been pressed
     * @param actionEvent Modify button press
     */
    public void getSelectedAppointment(ActionEvent actionEvent) {
        try {
            if(appointmentsTableView.getSelectionModel().getSelectedItem() == null) {
                selectAppointmentAlert();
            } else {
                tempAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();

                // Setting the index
                appointmentIndex = Appointment.getAllAppointments().indexOf(tempAppointment);
                openModifyAppointment(actionEvent);
            }
        }catch(Exception e) {
            selectAppointmentAlert();
        }
    }

    /**
     * Deletes selected appointment from the table and database
     */
    public void deleteSelectedAppointment() {
        try {
            if(appointmentsTableView.getSelectionModel().getSelectedItem() == null) {
                selectAppointmentAlert();
            } else {
                Appointment appoint = appointmentsTableView.getSelectionModel().getSelectedItem();
                ButtonType deleteButton = new ButtonType("Delete");
                ButtonType cancelButton = new ButtonType("Cancel");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting appointment...", deleteButton, cancelButton);
                alert.setContentText("Are you sure you want to delete this appointment?");
                alert.showAndWait().ifPresent(response -> {
                    if(response == deleteButton) {
                        if(Appointment.deleteAppointment(appoint)) {
                            appointmentCancelledAlert(appoint);
                            AppointmentsCRUD.deleteAppointment(appoint);
                            populateAppointmentsTable();
                            appointmentsTableView.getSelectionModel().clearSelection();
                            alert.close();
                        } else {
                            System.err.println("Appointment failed to delete.");
                            alert.close();
                        }
                    } else if(response == cancelButton) {
                        appointmentsTableView.getSelectionModel().clearSelection();
                        alert.close();
                    }
                });
            }
        }catch(Exception e) {
            selectAppointmentAlert();
        }
    }

    /**
     * Deletes selected customer from customer table
     */
    public void deleteSelectedCustomer() {
        try {
            if(customersTableView.getSelectionModel().getSelectedItem() == null) {
                selectCustomerAlert();
            }
            if(areAppointsEmpty(customersTableView.getSelectionModel().getSelectedItem())) {
                Customer customer = customersTableView.getSelectionModel().getSelectedItem();
                ButtonType deleteButton = new ButtonType("Delete");
                ButtonType cancelButton = new ButtonType("Cancel");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting customer...", deleteButton, cancelButton);
                alert.setContentText("Are you sure you want to delete this customer?");
                alert.showAndWait().ifPresent(response -> {
                    if(response == deleteButton) {
                        if(Customer.deleteCustomer(customer)) {
                            customerRemovedAlert(customer);
                            CustomersCRUD.deleteCustomer(customer);
                            populateCustomersTable();
                            customersTableView.getSelectionModel().clearSelection();
                            alert.close();
                        } else {
                            System.err.println("Customer failed to delete.");
                            alert.close();
                        }
                    } else if(response == cancelButton) {
                        customersTableView.getSelectionModel().clearSelection();
                        alert.close();
                    }
                });
            } else {
                hasAppointmentsAlert();
            }
        }catch(Exception e) {
            System.err.println("Please select a customer.");
        }
    }


    /**
     * Lets the user know that the customer still has scheduled appointments and cannot be deleted currently
     */
    private void hasAppointmentsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("This customer still has scheduled appointments.");
        alert.setContentText("Please cancel scheduled appointments for this customer before deleting customer");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Lets the user know that they did not select an appointment in the table
     */
    private void selectAppointmentAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Please select an appointment");
        alert.setContentText("An appointment must be selected");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Lets the user know that they did not select a customer in the table
     */
    private void selectCustomerAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Please select a customer");
        alert.setContentText("A customer must be selected");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Lets the user know that the appointment they deleted has been cancelled
     * @param appointment Appointment to cancel
     */
    private void appointmentCancelledAlert(Appointment appointment) {
        int id = appointment.getAppointmentID();
        String type = appointment.getType();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Appointment Cancelled");
        alert.setContentText("Appointment ID: " + id + "\n" + "Appointment Type: " + type + "\n" + "This appointment was successfully cancelled");
        alert.showAndWait().ifPresent(response -> {
        });
    }

    /**
     * Lets the user know that the customer has successfully been removed
     * @param customer Customer to remove
     */
    private void customerRemovedAlert(Customer customer) {
        int id = customer.getCustomerID();
        String name = customer.getCustomerName();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Customer Removed");
        alert.setContentText("Customer ID: " + id + "\n" + "Customer Name: " + name + "\n" + "This customer was successfully removed");
        alert.showAndWait().ifPresent(response -> {
        });
    }

    /**
     * Gets the appointment to modify
     * @return Appointment to modify
     */
    public static Appointment appointmentToModify() {
        return tempAppointment;
    }

    /**
     * Gets the index of the appointment to modify
     * @return The index of the appointment to modify
     */
    public static int appointmentIndexToModify() {
        return appointmentIndex;
    }

    /**
     * Gets the customer to modify
     * @return Customer to modify
     */
    public static Customer customerToModify() {
        return tempCustomer;
    }

    /**
     * Gets the customer index to modify
     * @return Gets the customer index to modify
     */
    public static int customerIndexToModify() {
        return customerIndex;
    }

    /**
     * Gets the selected customer from the customer table
     * @param actionEvent Modify button pressed
     */
    public void getSelectedCustomer(ActionEvent actionEvent) {
        try {
            if(customersTableView.getSelectionModel().getSelectedItem() == null) {
                selectCustomerAlert();
            } else {
                tempCustomer = customersTableView.getSelectionModel().getSelectedItem();

                // Setting the index
                customerIndex = Customer.getAllCustomers().indexOf(tempCustomer);
                openModifyCustomer(actionEvent);
            }
        }catch(Exception e) {
            selectCustomerAlert();
        }
    }

    /**
     * Opens add customer screen
     * @param actionEvent Add button pressed
     * @throws IOException
     */
    public void openAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/AddCustomer.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root, 670, 370));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    /**
     * Opens modify customer screen
     * @param actionEvent Modify button pressed
     * @throws IOException
     */
    public void openModifyCustomer(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/ModifyCustomer.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Modify Customer");
        stage.setScene(new Scene(root, 670, 370));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    /**
     * Checks if customer has any appointments or not
     * @param customer Provided customer to check
     * @return true if customer doesn't have appointments
     */
    public boolean areAppointsEmpty(Customer customer) {
        boolean isEmpty = false;
        for(int i = 0; i < Appointment.getAllAppointments().size(); i++) {
            isEmpty = Appointment.getAllAppointments().get(i).getCustomerID() != customer.getCustomerID();
            if(!isEmpty) {
                i = Appointment.getAllAppointments().size() - 1;
                return false;
            }
        }
        return isEmpty;
    }

    /**
     * Filters appointments by month
     * @return ObservableList of appointments for this month
     */
    public ObservableList<Appointment> filterByMonth() {
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        for(Appointment appoint : Appointment.getAllAppointments()) {
            if(appoint.getStartDate().getMonth() == LocalDate.now().getMonth()) {
                monthAppointments.add(appoint);
            }
        }
        return monthAppointments;
    }

    /**
     * Filters appointments by week
     * @return ObservableList of appointments for the next 7 days
     */
    public ObservableList<Appointment> filterByWeek() {
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
        for(Appointment appoint : Appointment.getAllAppointments()) {
            if((LocalDate.now().getDayOfYear() + 7 >= appoint.getStartDate().getDayOfYear()) && (LocalDate.now().getDayOfYear() <= appoint.getStartDate().getDayOfYear())) {
                weekAppointments.add(appoint);
            }
        }
        return weekAppointments;
    }

    /**
     * Populates table with appointments for the month
     */
    public void populateByMonth() {
        if(filterByMonth().isEmpty()) {
            noAppointmentsMonthAlert();
            populateAppointmentsTable();
        } else {
            appointmentsTableView.setItems(filterByMonth());
        }
    }

    /**
     * Populates table with appointments for the week
     */
    public void populateByWeek() {
        if(filterByWeek().isEmpty()) {
            noAppointmentsWeekAlert();
            populateAppointmentsTable();
        } else {
            appointmentsTableView.setItems(filterByWeek());
        }
    }

    /**
     * Lets the user know that there aren't any appointments for the current month
     */
    private void noAppointmentsMonthAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("No appointments for this month.");
        alert.setContentText("There are no appointments in this current month.");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Lets the user know that there are no appointments for the next 7 days
     */
    private void noAppointmentsWeekAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("No appointments for this week.");
        alert.setContentText("There are no appointments in this current week.");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Logs the user out
     * @throws SQLException
     */
    public void logoutUser() throws SQLException {
        Database.closeConnection();
        Platform.exit();
    }

    public void openContactScheduleReport(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/ContactScheduleReport.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Contact Schedule Reports");
        stage.setScene(new Scene(root, 834, 400));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
