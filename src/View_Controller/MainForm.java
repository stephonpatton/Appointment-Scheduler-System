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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.AppointmentsCRUD;
import util.CustomersCRUD;

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

    private static Appointment tempAppointment;
    private static int appointmentIndex;

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
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root, 670, 500));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


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
            System.err.println("Please select an appointment to modify");
            selectAppointmentAlert();
        }
    }

    public void deleteSelectedAppointment(ActionEvent actionEvent) {
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
            System.err.println("Please select an appoint to delete");
            selectAppointmentAlert();
        }
    }

    public void deleteSelectedCustomer(ActionEvent actionEvent) {
        try {
            if(customersTableView.getSelectionModel().getSelectedItem() == null) {
                selectCustomerAlert();
            } else {
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
            }
        }catch(Exception e) {
            System.err.println("Please select an appoint to delete");
            selectCustomerAlert();
        }
    }

    private void selectAppointmentAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Please select an appointment");
        alert.setContentText("An appointment must be selected");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    private void selectCustomerAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Please select a customer");
        alert.setContentText("A customer must be selected");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    private void appointmentCancelledAlert(Appointment appointment) {
        int id = appointment.getAppointmentID();
        String type = appointment.getType();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Appointment Cancelled");
        alert.setContentText("Appointment ID: " + id + "\n" + "Appointment Type: " + type + "\n" + "This appointment was successfully cancelled");
        alert.showAndWait().ifPresent(response -> {
        });
    }

    private void customerRemovedAlert(Customer customer) {
        int id = customer.getCustomerID();
        String name = customer.getCustomerName();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Customer Removed");
        alert.setContentText("Customer ID: " + id + "\n" + "Customer Name: " + name + "\n" + "This customer was successfully removed");
        alert.showAndWait().ifPresent(response -> {
        });
    }

    public static Appointment appointmentToModify() {
        return tempAppointment;
    }

    public static int appointmentIndexToModify() {
        return appointmentIndex;
    }
}
