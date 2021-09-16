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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initArrays();
        startHrSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(hours)));
        startMinSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(mins)));
        endHrSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(hours)));
        endMinSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(mins)));
        populateFields();
    }


    public void modifyAppointment(ActionEvent actionEvent) throws IOException {
        checkFields();
        highlightErrors();
        if(!checkDifferences()) {
            returnToMainScreen(actionEvent);
        } else {
            if(modifyAppointmentObject()) {
               returnToMainScreen(actionEvent);
            } else {
                showErrorAlert();
                setChecksToFalse();
            }
        }
        if(modifyAppointmentObject()) {
            returnToMainScreen(actionEvent);
        } else {
            setChecksToFalse();
        }
    }

    public boolean modifyAppointmentObject() {
        boolean isCreated = false;
        if(checkAllErrors()) {
            // TODO: Set values to new obj
        } else {
            showErrorAlert();
        }

        return isCreated;
    }

    public boolean checkAllErrors() {
        boolean isValid;

        isValid = descriptionCheck && titleCheck && locationCheck && typeCheck && startDateCheck
                && startTimeHrCheck && startTimeMinCheck && endDateCheck && endTimeHrCheck && endTimeMinCheck
                && customerIDCheck && userIDCheck && contactCheck;

        return isValid;
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

    public boolean checkDifferences() {
        return true;
    }

    public void checkFields() {
        checkTitleField();
        checkDescription();
        checkLocationField();
        checkTypeField();
        checkCustomerIDField();
        checkUserIDField();
        checkContactCombo();
        checkStartDate();
        checkEndDate();
        checkStartHr();
        checkStartMin();
        checkEndHr();
        checkEndMin();
    }

    public void checkTitleField() {
        titleCheck = modifyAppointTitleTF.getLength() != 0;
    }

    public void checkDescription() {
        descriptionCheck = modifyAppointDescriptionTF.getLength() != 0;
    }

    public void checkLocationField() {
        locationCheck = modifyAppointLocationTF.getLength() != 0;
    }

    public void checkTypeField() {
        typeCheck = modifyAppointTypeTF.getLength() != 0;
    }

    public void checkCustomerIDField() {
        int tryCustID = 0;
        if(modifyAppointCustomerTF.getLength() != 0) {
            try {
                tryCustID = Integer.parseInt(modifyAppointCustomerTF.getText().trim());
                customerIDCheck = true;
            } catch(Exception e) {
                customerIDCheck = false;
            }
        } else {
            customerIDCheck = false;
        }
    }

    public void checkUserIDField() {
        int tryUserID = 0;
        if(modifyAppointUserTF.getLength() != 0) {
            try {
                tryUserID = Integer.parseInt(modifyAppointUserTF.getText().trim());
                userIDCheck = true;
            }catch(Exception e) {
                userIDCheck = false;
            }
        } else {
            userIDCheck = false;
        }
    }

    public void checkContactCombo() {
        contactCheck = modifyAppointContactCombo.getValue() != null;
    }

    private void checkStartDate() {
        startDateCheck = modifyAppointStartPicker.getValue() != null;
    }

    private void checkEndDate() {
        endDateCheck = modifyAppointEndPicker.getValue() != null;
    }

    private void checkStartHr() {
        startTimeHrCheck = startHrSpinner.getValue() != null;
    }

    private void checkStartMin() {
        startTimeMinCheck = startMinSpinner.getValue() != null;
    }

    private void checkEndHr() {
        endTimeHrCheck = endHrSpinner.getValue() != null;
    }

    private void checkEndMin() {
        endTimeMinCheck = endMinSpinner.getValue() != null;
    }


    private void initArrays() {
        for(int i = 8; i < 23; i++) { //TODO: May have to change later after conversion and stuff
            hours.add(i);
        }
        mins.add(0);
        mins.add(15);
        mins.add(30);
        mins.add(45);
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

    public void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid data in field(s)");
        alert.setContentText("Invalid data was provided. Please check fields highlighted in red");
        alert.showAndWait().ifPresent(response -> {

        });
    }


    private void highlightErrors() {
        if(!titleCheck) {
            modifyAppointTitleTF.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointTitleTF.setStyle("-fx-border-color: #9f07");
        }
        if(!descriptionCheck) {
            modifyAppointDescriptionTF.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointDescriptionTF.setStyle("-fx-border-color: #9f07");
        }
        if(!locationCheck) {
            modifyAppointLocationTF.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointLocationTF.setStyle("-fx-border-color: #9f07");
        }
        if(!contactCheck) {
            modifyAppointContactCombo.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointContactCombo.setStyle("-fx-border-color: #9f07");
        }
        if(!typeCheck) {
            modifyAppointTypeTF.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointTypeTF.setStyle("-fx-border-color: #9f07");
        }
        if(!startDateCheck) {
            modifyAppointStartPicker.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointStartPicker.setStyle("-fx-border-color: #9f07");
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
            modifyAppointEndPicker.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointEndPicker.setStyle("-fx-border-color: #9f07");
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
            modifyAppointCustomerTF.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointCustomerTF.setStyle("-fx-border-color: #9f07");
        }
        if(!userIDCheck) {
            modifyAppointUserTF.setStyle("-fx-border-color: #ae0700");
        } else {
            modifyAppointUserTF.setStyle("-fx-border-color: #9f07");
        }
    }
}
