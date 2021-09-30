package View_Controller;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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

public class ContactScheduleReport implements Initializable {
    @FXML private ComboBox<Contact> contactComboBox;

    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentIDCol;
    @FXML private TableColumn<Appointment, Integer> customerIDCol;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, String> descriptionCol;
    @FXML private TableColumn<Appointment, Timestamp> startCol;
    @FXML private TableColumn<Appointment, Timestamp> endCol;

    private static ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

    public void returnToMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/MainForm.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 1040, 850));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactComboBox.setItems(Contact.getAllContacts());
        setAppointmentCells();
    }

    /**
     * Sets the cells for appointments table view
     */
    private void setAppointmentCells() {
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    public void filterByContact() throws SQLException {
        contactAppointments.clear();
        contactAppointments.addAll(AppointmentsCRUD.getAppointmentsByContact(contactComboBox.getValue()));
        appointmentTableView.setItems(contactAppointments);
    }
}
