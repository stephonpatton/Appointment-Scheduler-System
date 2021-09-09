package View_Controller;

import Model.Appointment;
import Model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    @FXML private TableView<Appointment> appointmentsTableView;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, Integer> appointIDCol;
    @FXML private TableColumn<Appointment, Integer> userIDCol;
    @FXML private TableColumn<Appointment, Integer> customerIDCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, Timestamp> startCol;
    @FXML private TableColumn<Appointment, Timestamp> endCol;
    @FXML private TableColumn<Appointment, String> locationCol;
    @FXML private TableColumn<Appointment, String> descriptionCol;

    @FXML private TableView<Customer> customersTableView;
    @FXML private TableColumn<Customer, Integer> customerTVIDCol;
    @FXML private TableColumn<Customer, String> customerTVNameCol;
    @FXML private TableColumn<Customer, Integer> customerTVDivIDCol;
    @FXML private TableColumn<Customer, String> customerTVPhoneIDCol;
    @FXML private TableColumn<Customer, String> customerTVAddressCol;
    @FXML private TableColumn<Customer, String> customerTVPostalCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAppointmentCells();
        setCustomerCells();
        populateAppointmentsTable();
        populateCustomersTable();
    }

    private void setAppointmentCells() {
        appointIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void setCustomerCells() {
        customerTVIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerTVNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTVDivIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerTVPhoneIDCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTVAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTVPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    }

    private void populateAppointmentsTable() {
        appointmentsTableView.setItems(Appointment.getAllAppointments());
    }

    private void populateCustomersTable() {
        customersTableView.setItems(Customer.getAllCustomers());
    }
}
