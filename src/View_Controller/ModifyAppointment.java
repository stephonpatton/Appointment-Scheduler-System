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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static View_Controller.MainForm.appointmentIndexToModify;
import static View_Controller.MainForm.appointmentToModify;

public class ModifyAppointment implements Initializable {
    // FXML
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

    // Arrays for time spinners
    private final ArrayList<Integer> hours = new ArrayList<>();
    private final ArrayList<Integer> mins = new ArrayList<>();

    // Appointment to modify data
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

    /**
     * Initializes when ModifyAppointment screen is loaded
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initArrays();
        startHrSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(hours)));
        startMinSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(mins)));
        endHrSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(hours)));
        endMinSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(mins)));
        populateFields();
    }


    /**
     * Tries to modify appointment object. Returns to main screen if successful
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void modifyAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        checkFields();
        highlightErrors();
        if(checkDifferences()) {
            returnToMainScreen(actionEvent);
        } else {
            if(checkAllErrors()) {
                if(modifyAppointmentObject()) {
                    returnToMainScreen(actionEvent);
                } else {
                    highlightErrors();
                    showErrorAlert();
                }
            } else {
                highlightErrors();
                showErrorAlert();
            }
            setChecksToFalse();
        }
    }

    /**
     * USES LAMBDA FUNCTION: This uses the string to timestamp conversion lambda in order to create timestamps for time data.
     * Tries to modify an appointment based on provided input from user.
     *
     *
     * @return True if the appointment was successfully modified
     * @throws SQLException
     */
    public boolean modifyAppointmentObject() throws SQLException {
        Appointment appoint = new Appointment();
        boolean isCreated = false;
        if(checkAllErrors()) {
            int custID = Integer.parseInt(modifyAppointCustomerTF.getText().trim());
            int userID = Integer.parseInt(modifyAppointUserTF.getText().trim());
            String description = modifyAppointDescriptionTF.getText().trim();
            String type = modifyAppointTypeTF.getText().trim();
            int contactID = modifyAppointContactCombo.getValue().getContactID();
            String location = modifyAppointLocationTF.getText().trim();
            String title = modifyAppointTitleTF.getText().trim();

            String startDate = modifyAppointStartPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startHrTime = String.valueOf(startHrSpinner.getValue());
            String startMinTime = String.valueOf(startMinSpinner.getValue());
            String startTime = "0" + startHrTime + ":" + startMinTime + ":00";

            // End date and time info
            String endDate = modifyAppointEndPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endHrTime = String.valueOf(endHrSpinner.getValue());
            String endMinTime = String.valueOf(endMinSpinner.getValue());
            String endTime = "0" + endHrTime + ":" + endMinTime + ":00";

            Time.TimestampInterface timestamp = (String date, String time) -> {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateTime = date + " " + time;
                    Date parsedDate = formatter.parse(dateTime);
                    Timestamp ts = new Timestamp(parsedDate.getTime());
                    System.out.println("TIMESTAMP: " + ts.getTime());
                    return ts;
                }catch (Exception e) {
                    return null;
                }
            };

            Timestamp startTs = timestamp.stringToTimestamp(startDate, startTime);
            Timestamp endTs = timestamp.stringToTimestamp(endDate, endTime);

            LocalDateTime startLdt = Time.convertTStoLDT(startTs);
            LocalDateTime endLdt = Time.convertTStoLDT(endTs);

            LocalDateTime startTSUTC = Time.convertTStoLDT(startTs);
            LocalDateTime endTSUTC = Time.convertTStoLDT(endTs);

            if(endLdt.getHour() == 2) {
                if(endLdt.getMinute() != 0) {
                    endTimeMinCheck = false;
                    highlightErrors();
                    return false;
                }
            }

            if(startLdt.getHour() == 2) {
                startTimeHrCheck = false;
                highlightErrors();
                return false;
            }

            if(Time.checkBusinessHours(startLdt)) {
                if(Time.checkBusinessHours(endLdt)) {
                } else {
                    endTimeHrCheck = false;
                    highlightErrors();
                    return false;
                }
            } else {
                startTimeHrCheck = false;
                highlightErrors();
                return false;
            }

            if(CustomersCRUD.isOverlap(custID, startLdt, endLdt, Integer.parseInt(modifyAppointIDTF.getText()))) {
                showOverlapAlert();
                startTimeHrCheck = false;
                endTimeHrCheck = false;
                startTimeMinCheck = false;
                endTimeMinCheck = false;
                customerIDCheck = false;
                highlightErrors();
                return false;
            }

            if(modifyAppointStartPicker.getValue().getDayOfYear() > modifyAppointEndPicker.getValue().getDayOfYear()) {
                startDateCheck = false;
                endDateCheck = false;
                highlightErrors();
                return false;
            }

            if(!Query.checkCustomerInDB(custID)) {
                customerIDCheck = false;
                highlightErrors();
                return false;
            }

            if(!Query.checkUserInDB(userID)) {
                userIDCheck = false;
                highlightErrors();
                return false;
            }

            if(startHrSpinner.getValue() > endHrSpinner.getValue() || (endHrSpinner.getValue() == startHrSpinner.getValue() && endMinSpinner.getValue() <= startMinSpinner.getValue())) {
                System.err.println("END HOUR GREATER THAN START HOUR");
                startTimeHrCheck = false;
                endTimeHrCheck = false;
                startTimeMinCheck = false;
                endTimeMinCheck = false;
                highlightErrors();
                return false;
            } else {
                appoint.setTitle(title);
                appoint.setCustomerID(custID);
                appoint.setUserID(userID);
                appoint.setDescription(description);
                appoint.setType(type);
                appoint.setContactID(contactID);
                appoint.setLocation(location);
                appoint.setAppointmentID(Integer.parseInt(modifyAppointIDTF.getText()));
                appoint.setContactName(modifyAppointContactCombo.getValue().getContactName());
                appoint.setContact(modifyAppointContactCombo.getValue());
                appoint.setStartDate(modifyAppointStartPicker.getValue());
                appoint.setEndDate(modifyAppointEndPicker.getValue());
                appoint.setStartHr(startHrSpinner.getValue());
                appoint.setStartMin(startMinSpinner.getValue());
                appoint.setEndHr(endHrSpinner.getValue());
                appoint.setEndMin(endMinSpinner.getValue());
                appoint.setStart(Timestamp.valueOf(startTSUTC));
                appoint.setEnd(Timestamp.valueOf(endTSUTC));
                appoint.setLastUpdateBy(User.getCurrentUser());

                //CONTACT AND DATES
                Appointment.updateAppointment(appointmentIndexToModify(), appoint);
                AppointmentsCRUD.updateAppointment(appoint.getAppointmentID(), appoint);
                isCreated = true;
            }
        } else {
            showErrorAlert();
        }
        return isCreated;
    }

    private void showOverlapAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("There is overlap for the customer with appointments.");
        alert.setContentText("Please adjust the time settings to not overlap with other appointments");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Checks all error checking variables for form fields
     * @return True if all error checking variables are true
     */
    public boolean checkAllErrors() {
        boolean isValid;
        isValid = descriptionCheck && titleCheck && locationCheck && typeCheck && startDateCheck
                && startTimeHrCheck && startTimeMinCheck && endDateCheck && endTimeHrCheck && endTimeMinCheck
                && customerIDCheck && userIDCheck && contactCheck;

        return isValid;
    }

    /**
     * Returns to the main screen
     * @param actionEvent Button press
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
     * Populates the form fields with appointment object data in the system
     */
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

        LocalDateTime startLDT = Time.utcToLocalTime(modifyAppointment.getStart());
        String hour = String.valueOf(startLDT.getHour());

        LocalDateTime endLDT = Time.utcToLocalTime(modifyAppointment.getEnd());
        String endHour = String.valueOf(endLDT.getHour());

        startHrSpinner.getValueFactory().setValue(Integer.valueOf(hour));
        startMinSpinner.getValueFactory().setValue(modifyAppointment.getStartMin());
        endHrSpinner.getValueFactory().setValue(Integer.valueOf(endHour));
        endMinSpinner.getValueFactory().setValue(modifyAppointment.getEndMin());
        modifyAppointStartPicker.setValue(modifyAppointment.getStartDate());
        modifyAppointEndPicker.setValue(modifyAppointment.getEndDate());
    }

    /**
     * Checks the form fields for differences against the appointment object
     * @return True if differences are present
     */
    public boolean checkDifferences() {
        boolean differencesPresent;
        differencesPresent = checkTitleDiff() && checkDescriptionDiff() && checkLocationDiff() && checkTypeDiff()
                && checkCustomerIDDiff() && checkUserIDDiff() && checkContactDiff() && checkStartDateDiff()
                && checkEndDateDiff() && checkStartHrDiff() && checkStartMinDiff() && checkEndHrDiff()
                && checkEndMinDiff();
        return differencesPresent;
    }

    /**
     * Checks contact field for differences
     * @return True if differences are present
     */
    private boolean checkContactDiff() {
        boolean isDifferent;
        isDifferent = modifyAppointContactCombo.getValue() != modifyAppointment.getContact();
        return isDifferent;
    }

    /**
     * Checks end hour spinner for differences
     * @return True if differences are present
     */
    private boolean checkEndHrDiff() {
        boolean isDifferent;
        isDifferent = endHrSpinner.getValue() != modifyAppointment.getEndHr();
        return isDifferent;
    }

    /**
     * Checks end minute field for differences
     * @return True if differences are present
     */
    private boolean checkEndMinDiff() {
        boolean isDifferent;
        isDifferent = endMinSpinner.getValue() != modifyAppointment.getEndMin();
        return isDifferent;
    }

    /**
     * Checks start minute spinner for differences
     * @return True if differences are present
     */
    private boolean checkStartMinDiff() {
        boolean isDifferent;
        isDifferent = startMinSpinner.getValue() != modifyAppointment.getStartMin();
        return isDifferent;
    }

    /**
     * Checks start hour spinner for differences
     * @return True if differences are present
     */
    private boolean checkStartHrDiff() {
        boolean isDifferent;
        isDifferent = startHrSpinner.getValue() != modifyAppointment.getStartHr();
        return isDifferent;
    }

    /**
     * Checks start date picker for differences
     * @return True if differences are present
     */
    private boolean checkStartDateDiff() {
        boolean isDifferent;
        isDifferent = modifyAppointStartPicker.getValue() != modifyAppointment.getStartDate();
        return isDifferent;
    }

    /**
     * Checks end date picker for differences
     * @return True if differences are present
     */
    private boolean checkEndDateDiff() {
        boolean isDifferent;
        isDifferent = modifyAppointEndPicker.getValue() != modifyAppointment.getEndDate();
        return isDifferent;
    }

    /**
     * Checks user ID field for differences
     * @return True if differences are present
     */
    private boolean checkUserIDDiff() {
        boolean isDifferent;
        isDifferent = Integer.parseInt(modifyAppointUserTF.getText()) != modifyAppointment.getUserID();
        return isDifferent;
    }

    /**
     * Checks title field for differences
     * @return True if differences are present
     */
    private boolean checkTitleDiff() {
        boolean isDifferent;
        isDifferent = !modifyAppointTitleTF.getText().equals(modifyAppointment.getTitle());
        return isDifferent;
    }

    /**
     * Checks description field for differences
     * @return True if differences are present
     */
    private boolean checkDescriptionDiff() {
        boolean isDifferent;
        isDifferent = !modifyAppointDescriptionTF.getText().equals(modifyAppointment.getDescription());
        return isDifferent;
    }

    /**
     * Checks location field for differences
     * @return True if differences are present
     */
    private boolean checkLocationDiff() {
        boolean isDifferent;
        isDifferent = !modifyAppointLocationTF.getText().equals(modifyAppointment.getLocation());
        return isDifferent;
    }

    /**
     * Checks type field for differences
     * @return True if differences are present
     */
    private boolean checkTypeDiff() {
        boolean isDifferent;
        isDifferent = !modifyAppointTypeTF.getText().equals(modifyAppointment.getType());
        return isDifferent;
    }

    /**
     * Checks customer ID field for differences
     * @return True if differences are present
     */
    private boolean checkCustomerIDDiff() {
        boolean isTheSame;
        int tryInt;
        try {
            tryInt = Integer.parseInt(modifyAppointCustomerTF.getText().trim());
            isTheSame = tryInt != modifyAppointment.getCustomerID();
            System.out.println("BOOLEAN VALUE : " + isTheSame);
            customerIDCheck = true;
        }catch (Exception e) {
            isTheSame = false;
            customerIDCheck = false;
        }
        return isTheSame;
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
     * Checks title field for valid data
     */
    public void checkTitleField() {
        titleCheck = modifyAppointTitleTF.getLength() != 0;
    }

    /**
     * Checks description field for valid data
     */
    public void checkDescription() {
        descriptionCheck = modifyAppointDescriptionTF.getLength() != 0;
    }

    /**
     * Checks location field for valid data
     */
    public void checkLocationField() {
        locationCheck = modifyAppointLocationTF.getLength() != 0;
    }

    /**
     * Checks type field for valid data
     */
    public void checkTypeField() {
        typeCheck = modifyAppointTypeTF.getLength() != 0;
    }

    /**
     * Checks customer ID field for valid data
     */
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

    /**
     * Checks user ID field for valid data
     */
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

    /**
     * Checks contact ComboBox for valid data
     */
    public void checkContactCombo() {
        contactCheck = modifyAppointContactCombo.getValue() != null;
    }

    /**
     * Checks start picker for valid data
     */
    private void checkStartDate() {
        startDateCheck = modifyAppointStartPicker.getValue() != null;
    }

    /**
     * Checks end date picker for valid data
     */
    private void checkEndDate() {
        endDateCheck = modifyAppointEndPicker.getValue() != null;
    }

    /**
     * Checks start hour picker for valid data
     */
    private void checkStartHr() {
        startTimeHrCheck = startHrSpinner.getValue() != null;
    }

    /**
     * Checks start minute spinner for valid data
     */
    private void checkStartMin() {
        startTimeMinCheck = startMinSpinner.getValue() != null;
    }

    /**
     * Checks end hour spinner for valid data
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
     * Initializes time arrays for spinner
     */
    private void initArrays() {
        for(int i = 0; i < 23; i++) {
            hours.add(i);
        }
        mins.add(0);
        mins.add(15);
        mins.add(30);
        mins.add(45);
    }

    /**
     * Sets all error checks to false for form fields
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
     * Shows user that invalid data was provided
     */
    public void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid data in field(s)");
        alert.setContentText("Invalid data was provided. Please check fields highlighted in red");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Highlights fields in the form with invalid data
     */
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
