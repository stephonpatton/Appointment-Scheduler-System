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
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.AppointmentsCRUD;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {
    @FXML private TextField addAppointIDTF;
    @FXML private ComboBox<Contact> addAppointContactCombo;
    @FXML private TextField addAppointTitleTF;
    @FXML private TextField addAppointDescriptionTF;
    @FXML private TextField addAppointLocationTF;
    @FXML private TextField addAppointTypeTF;
    @FXML private DatePicker addAppointStartPicker;
    @FXML private Spinner<Integer> startHrSpinner;
    @FXML private Spinner<Integer> startMinSpinner;
    @FXML private DatePicker addAppointEndPicker;
    @FXML private Spinner<Integer> endHrSpinner;
    @FXML private Spinner<Integer> endMinSpinner;
    @FXML private TextField addAppointCustomerTF;
    @FXML private TextField addAppointUserTF;

    // Checks if valid data is present or not
    private boolean titleCheck;
    private boolean descriptionCheck;
    private boolean locationCheck;
    private boolean typeCheck;
    private boolean startDateCheck;
    private boolean startTimeHrCheck;
    private boolean startTimeMinCheck;
    private boolean endDateCheck;
    private boolean endTimeHrCheck;
    private boolean endTimeMinCheck;
    private boolean customerIDCheck;
    private boolean userIDCheck;
    private boolean contactCheck;

    private final ArrayList<Integer> hours = new ArrayList<>();
    private final ArrayList<Integer> mins = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAppointIDTF.setText("AUTO GEN: " + AppointmentsCRUD.getNextIDCount());
        addAppointContactCombo.setItems(Contact.getAllContacts());
        initArrays();

        startHrSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(hours)));
        startMinSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(mins)));
        endHrSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(hours)));
        endMinSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(mins)));
    }

    public void createAppointObject() {
        int appointmentID = AppointmentsCRUD.getNextIDCount();
        String title = addAppointTitleTF.getText();
        String description = addAppointDescriptionTF.getText();
        String location = addAppointLocationTF.getText();
        String type = addAppointTypeTF.getText();

        int contactID = addAppointContactCombo.getValue().getContactID();
        int customerID = Integer.parseInt(addAppointCustomerTF.getText());
        int userID = Integer.parseInt(addAppointUserTF.getText());

        Appointment appoint = new Appointment();
        appoint.setAppointmentID(appointmentID);
        appoint.setTitle(title);
        appoint.setDescription(description);
        appoint.setLocation(location);
        appoint.setType(type);
        appoint.setContactID(contactID);
        appoint.setUserID(userID);
        appoint.setCustomerID(customerID);

        //TODO: TIME and DATES

        Appointment.addAppointment(appoint);
    }

    public boolean checkFields() {
        boolean success = false;
//        boolean success = true;

        return success;
    }



    public void createAppointment(ActionEvent actionEvent) throws IOException {
        if(checkFields()) {
            createAppointObject();
            returnToMainScreen(actionEvent);
        } else {
            highlightErrors();
        }
    }

    private void setChecksToFalse() {
        titleCheck = false;
        descriptionCheck = false;
        locationCheck = false;
        contactCheck = false;
        typeCheck = false;
        startDateCheck = false;
        startTimeHrCheck = false;
        endDateCheck = false;
        endTimeHrCheck = false;
        customerIDCheck = false;
        userIDCheck = false;
        startTimeMinCheck = false;
        endTimeMinCheck = false;
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

    private void highlightErrors() {
        if(!titleCheck) {
            addAppointTitleTF.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointTitleTF.setStyle("-fx-border-color: #9f07");
        }
        if(!descriptionCheck) {
            addAppointDescriptionTF.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointDescriptionTF.setStyle("-fx-border-color: #9f07");
        }
        if(!locationCheck) {
            addAppointLocationTF.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointLocationTF.setStyle("-fx-border-color: #9f07");
        }
        if(!contactCheck) {
            addAppointContactCombo.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointContactCombo.setStyle("-fx-border-color: #9f07");
        }
        if(!typeCheck) {
            addAppointTypeTF.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointTypeTF.setStyle("-fx-border-color: #9f07");
        }
        if(!startDateCheck) {
            addAppointStartPicker.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointStartPicker.setStyle("-fx-border-color: #9f07");
        }
        if(!startTimeHrCheck) {
            startHrSpinner.setStyle("-fx-border-color: #ae0700");
        } else {
            startHrSpinner.setStyle("-fx-border-color: #9f07");
        }
        if(!startTimeMinCheck) {
            startMinSpinner.setStyle("-fx-border-color: #ae0700");
        } else {
            startMinSpinner.setStyle("-fx-border-color: #9f07");
        }
        if(!endDateCheck) {
            addAppointEndPicker.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointEndPicker.setStyle("-fx-border-color: #9f07");
        }
        if(!endTimeHrCheck) {
            endHrSpinner.setStyle("-fx-border-color: #ae0700");
        } else {
            endHrSpinner.setStyle("-fx-border-color: #9f07");
        }
        if(!endTimeMinCheck) {
            endMinSpinner.setStyle("-fx-border-color: #ae0700");
        } else {
            endMinSpinner.setStyle("-fx-border-color: #9f07");
        }
        if(!customerIDCheck) {
            addAppointCustomerTF.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointCustomerTF.setStyle("-fx-border-color: #9f07");
        }
        if(!userIDCheck) {
            addAppointUserTF.setStyle("-fx-border-color: #ae0700");
        } else {
            addAppointUserTF.setStyle("-fx-border-color: #9f07");
        }
    }

    private void initArrays() {
        for(int i = 0; i < 24; i++) {
            hours.add(i);
        }
        mins.add(0);
        mins.add(15);
        mins.add(30);
        mins.add(45);
    }

}