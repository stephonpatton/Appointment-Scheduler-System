package View_Controller;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static View_Controller.MainForm.appointmentIndexToModify;
import static View_Controller.MainForm.appointmentToModify;

public class ModifyAppointment implements Initializable {
    @FXML private TextField modifyAppointIDTF;
    @FXML private ComboBox<Contact> modifyAppointContactCombo;
    @FXML private TextField modifyAppointTitleTF;
    @FXML private TextField modifyAppointDescriptionTF;
    @FXML private TextField modifyAppointLocationTF;
    @FXML private TextField modifyAppointTypeTF;
    @FXML private DatePicker modifyAppointStartPicker;
    @FXML private Spinner<Integer> startHrSpinner;
    @FXML private Spinner<Integer> startMinSpinner;
    @FXML private DatePicker modifyAppointEndPicker;
    @FXML private Spinner<Integer> endHrSpinner;
    @FXML private Spinner<Integer> endMinSpinner;
    @FXML private TextField modifyAppointCustomerTF;
    @FXML private TextField modifyAppointUserTF;

    private final ArrayList<Integer> hours = new ArrayList<>();
    private final ArrayList<Integer> mins = new ArrayList<>();


    int indexOfAppointment = appointmentIndexToModify();
    Appointment modifyAppointment = appointmentToModify();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initArrays();
        startHrSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(hours)));
        startMinSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(mins)));
        endHrSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(hours)));
        endMinSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(mins)));
        populateFields();
    }


    public void modifyAppointment(ActionEvent actionEvent) {

    }

    public void returnToMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/MainForm.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 1000, 850));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    private void populateFields() {
        // Gets appointment to modify
        Appointment appointmentToModify = Appointment.getAllAppointments().get(indexOfAppointment);
        modifyAppointIDTF.setText(String.valueOf(modifyAppointment.getAppointmentID()));
        modifyAppointTitleTF.setText(modifyAppointment.getTitle());
        modifyAppointDescriptionTF.setText(modifyAppointment.getDescription());
        modifyAppointLocationTF.setText(modifyAppointment.getLocation());
        modifyAppointContactCombo.setItems(Contact.getAllContacts());
        modifyAppointContactCombo.setValue(Contact.getContactByID(modifyAppointment.getContactID()));
        modifyAppointTypeTF.setText(modifyAppointment.getType());
        modifyAppointCustomerTF.setText(String.valueOf(modifyAppointment.getCustomerID()));
        modifyAppointUserTF.setText(String.valueOf(modifyAppointment.getUserID()));

        startHrSpinner.getValueFactory().setValue(modifyAppointment.getStartHr());
        startMinSpinner.getValueFactory().setValue(modifyAppointment.getStartMin());
        endHrSpinner.getValueFactory().setValue(modifyAppointment.getEndHr());
        endMinSpinner.getValueFactory().setValue(modifyAppointment.getEndMin());
        modifyAppointStartPicker.setValue(modifyAppointment.getStartDate());
        modifyAppointEndPicker.setValue(modifyAppointment.getEndDate());
    }


    // TODO: Check if fields have been changed
    // TODO: If fields have changed, check for validity
    // TODO: If valid, update appointment object with new data

    private void initArrays() {
        for(int i = 8; i < 23; i++) { //TODO: May have to change later after conversion and stuff
            hours.add(i);
        }
        mins.add(0);
        mins.add(15);
        mins.add(30);
        mins.add(45);
    }
}
