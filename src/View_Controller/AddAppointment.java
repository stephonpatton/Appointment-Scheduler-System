package View_Controller;

import Model.Appointment;
import Model.Contact;
import Model.User;
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
import util.AppointmentsCRUD;
import util.CustomersCRUD;
import util.Query;
import util.Time;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {
    // FXML
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

    // Arrays to hold time data for spinners (loads)
    private final ArrayList<Integer> hours = new ArrayList<>();
    private final ArrayList<Integer> mins = new ArrayList<>();

    /**
     * Initializes when AddAppointment.fxml loads.
     * Sets some items such as ID field, contact ComboBox, and time spinners
     * @param url
     * @param resourceBundle
     */
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

    /**
     * Tries to create appointment object based on data provided in form
     * @return True if the appointment object was created
     */
    public boolean createAppointObject() {
        boolean isCreated;
        try {
            // Getting data from form fields
            int appointmentID = AppointmentsCRUD.getNextIDCount();
            String title = addAppointTitleTF.getText();
            String description = addAppointDescriptionTF.getText();
            String location = addAppointLocationTF.getText();
            String type = addAppointTypeTF.getText();
            int contactID = addAppointContactCombo.getValue().getContactID();
            int customerID = Integer.parseInt(addAppointCustomerTF.getText());
            int userID = Integer.parseInt(addAppointUserTF.getText());

            // Start date and time infor
            String startDate = addAppointStartPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startHrTime = String.valueOf(startHrSpinner.getValue());
            String startMinTime = String.valueOf(startMinSpinner.getValue());
            String startTime = "0" + startHrTime + ":" + startMinTime + ":00";

            // End date and time info
            String endDate = addAppointEndPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endHrTime = String.valueOf(endHrSpinner.getValue());
            String endMinTime = String.valueOf(endMinSpinner.getValue());
            String endTime = "0" + endHrTime + ":" + endMinTime + ":00.0";

            Timestamp startTS = Time.convertStringsToTime(startDate, startTime);
            Timestamp endTS = Time.convertStringsToTime(endDate, endTime);

            LocalDateTime startTSUTC = Time.convertTStoLDT(startTS);
            LocalDateTime endTSUTC = Time.convertTStoLDT(endTS);

            LocalDateTime startLdt = Time.convertTStoLDT(startTS);
            LocalDateTime endLdt = Time.convertTStoLDT(endTS);

            // Checks if end time is not greater than 10, but maybe equals 10pm est
            if(endLdt.getHour() == 2) {
                if(endLdt.getMinute() != 0) {
                    endTimeMinCheck = false;
                    highlightErrors();
                    return false;
                }
            }

            // Checks if start time is within business hours
            if(!Time.checkBusinessHours(startLdt)) {
                startTimeHrCheck = false;
                highlightErrors();
                return false;
            }

            // Checks if end time is within business hours
            if(!Time.checkBusinessHours(endLdt)) {
                endTimeHrCheck = false;
                highlightErrors();
                return false;
            }

            // Checks if customer has overlap with appointments
            if(CustomersCRUD.isOverlap(customerID, startLdt, endLdt, appointmentID)) {
                showOverlapAlert();
                startTimeHrCheck = false;
                endTimeHrCheck = false;
                startTimeMinCheck = false;
                endTimeMinCheck = false;
                customerIDCheck = false;
                highlightErrors();
                return false;
            }

            // Checks if start date > end date (logical error)
            if(addAppointStartPicker.getValue().getDayOfYear() > addAppointEndPicker.getValue().getDayOfYear()) {
                // TODO: ALERT
                startDateCheck = false;
                endDateCheck = false;
                highlightErrors();
                return false;
            }

            // Checks if customer is in database before trying to assigning appointment
            if(!Query.checkCustomerInDB(customerID)) {
                // TODO: alert saying customer not in database
                customerIDCheck = false;
                highlightErrors();
                return false;
            }

            // Checks if user is in database before assigning appointment
            if(!Query.checkUserInDB(userID)) {
                //TODO: alert saying user not in database
                userIDCheck = false;
                highlightErrors();
                return false;
            }


            if(title.length() == 0 || description.length() == 0 || location.length() == 0 || type.length() == 0) {
                isCreated = false;
            } else {
                if(startHrSpinner.getValue() > endHrSpinner.getValue() || (endHrSpinner.getValue() == startHrSpinner.getValue() && endMinSpinner.getValue() <= startMinSpinner.getValue())) {
                    // TODO: Alert saying end time before start time
                    System.err.println("END HOUR GREATER THAN START HOUR");
                    startTimeHrCheck = false;
                    endTimeHrCheck = false;
                    startTimeMinCheck = false;
                    endTimeMinCheck = false;
                    highlightErrors();
                    return false;
                } else {
                    Appointment appoint = new Appointment();
                    appoint.setAppointmentID(appointmentID);
                    appoint.setTitle(title);
                    appoint.setDescription(description);
                    appoint.setLocation(location);
                    appoint.setType(type);
                    appoint.setContactID(contactID);
                    appoint.setUserID(userID);
                    appoint.setCustomerID(customerID);
                    appoint.setContactName(addAppointContactCombo.getValue().getContactName());

                    appoint.setStartHr(startHrSpinner.getValue());
                    appoint.setStartMin(startMinSpinner.getValue());
                    appoint.setEndHr(endHrSpinner.getValue());
                    appoint.setEndMin(endMinSpinner.getValue());
                    appoint.setStart(Timestamp.valueOf(startTSUTC));
                    appoint.setEnd(Timestamp.valueOf(endTSUTC));
                    appoint.setStartDate(addAppointStartPicker.getValue());
                    appoint.setEndDate(addAppointEndPicker.getValue());
                    appoint.setCreatedBy(User.getCurrentUser());

                    // Add locally and insert into database
                    Appointment.addAppointment(appoint);
                    AppointmentsCRUD.insertAppointment(appoint);
                    isCreated = true;
                }
            }
        }catch(Exception e) {
            showErrorAlert();
            isCreated = false;
        }
        return isCreated;
    }

    /**
     * Method to alert user overlap is present for a customer
     */
    private void showOverlapAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("There is overlap for the customer with appointments.");
        alert.setContentText("Please adjust the time settings to not overlap with other appointments");
        alert.showAndWait().ifPresent(response -> {

        });
    }


    /**
     * Method to show user invalid data has been inputted
     */
    public void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid data in field(s)");
        alert.setContentText("Invalid data was provided. Please check fields highlighted in red");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Checks all fields for valid data
     */
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

    /**
     * Method called when save button is pressed. Screen closes if appointment was created successfully
     * @param actionEvent On save button press
     * @throws IOException
     */
    public void createAppointment(ActionEvent actionEvent) throws IOException {
        checkFields();
        highlightErrors();
        if(createAppointObject()) {
            returnToMainScreen(actionEvent);
        } else {
            setChecksToFalse();
        }
    }

    /**
     * Returns to main screen
     * @param actionEvent Cancel or save button press
     * @throws IOException
     */
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

    /**
     * Highlights fields that contain errors in the form
     */
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

    /**
     * Checks title field for valid data
     */
    private void checkTitleField() {
        titleCheck = addAppointTitleTF.getLength() != 0;
    }

    /**
     * Checks description field for valid data
     */
    private void checkDescription() {
        descriptionCheck = addAppointDescriptionTF.getLength() != 0;
    }

    /**
     * Checks location field for valid data
     */
    private void checkLocationField() {
        locationCheck = addAppointLocationTF.getLength() != 0;
    }

    /**
     * Checks type field for valid data
     */
    private void checkTypeField() {
        typeCheck = addAppointTypeTF.getLength() != 0;
    }

    /**
     * Checks customer ID field for valid data
     */
    private void checkCustomerIDField() {
        int tryCustID = 0;
        if(addAppointCustomerTF.getLength() != 0) {
            try {
                tryCustID = Integer.parseInt(addAppointCustomerTF.getText().trim());
                customerIDCheck = true;
            } catch(Exception e) {
                customerIDCheck = false;
            }
        } else {
            customerIDCheck = false;
        }
    }

    /**
     * Checks user ID field for valid data
     */
    private void checkUserIDField() {
        int tryUserID = 0;
        if(addAppointUserTF.getLength() != 0) {
            try {
                tryUserID = Integer.parseInt(addAppointUserTF.getText().trim());
                userIDCheck = true;
            } catch(Exception e) {
                userIDCheck = false;
            }
        } else {
            userIDCheck = false;
        }
    }

    /**
     * Checks contact combo box for valid data
     */
    private void checkContactCombo() {
        contactCheck = addAppointContactCombo.getValue() != null;
    }

    /**
     * Checks start date date picker for valid data
     */
    private void checkStartDate() {
        startDateCheck = addAppointStartPicker.getValue() != null;
    }

    /**
     * Checks end date date picker for valid data
     */
    private void checkEndDate() {
        endDateCheck = addAppointEndPicker.getValue() != null;
    }

    /**
     * Checks start hr spinner for valid data
     */
    private void checkStartHr() {
        startTimeHrCheck = startHrSpinner.getValue() != null;
    }

    /**
     * Checks start min spinner for valid data
     */
    private void checkStartMin() {
        startTimeMinCheck = startMinSpinner.getValue() != null;
    }

    /**
     * Checks end hr spinner for valid data
     */
    private void checkEndHr() {
        endTimeHrCheck = endHrSpinner.getValue() != null;
    }

    /**
     * Checks end min spinner for valid data
     */
    private void checkEndMin() {
        endTimeMinCheck = endMinSpinner.getValue() != null;
    }

    /**
     * Sets all error checks to false
     */
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

    /**
     * Initializes arrays for minutes and hours upon screen load
     */
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