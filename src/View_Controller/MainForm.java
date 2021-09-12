package View_Controller;

import Model.Appointment;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.AppointmentsCRUD;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainForm implements Initializable {
    @FXML private TableView<Appointment> appointmentsTableView;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, Integer> appointIDCol;
    @FXML private TableColumn<Appointment, String> contactCol;
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
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
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

    public void openAddAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/AddAppointment.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 670, 500));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

    }
}
