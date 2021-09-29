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

    public boolean createAppointObject() {
        boolean isCreated;
        try {
            int appointmentID = AppointmentsCRUD.getNextIDCount();
            String title = addAppointTitleTF.getText();
            String description = addAppointDescriptionTF.getText();
            String location = addAppointLocationTF.getText();
            String type = addAppointTypeTF.getText();

            int contactID = addAppointContactCombo.getValue().getContactID();

            int customerID = Integer.parseInt(addAppointCustomerTF.getText());
            int userID = Integer.parseInt(addAppointUserTF.getText());

            String startDate = addAppointStartPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startHrTime = String.valueOf(startHrSpinner.getValue());
            String startMinTime = String.valueOf(startMinSpinner.getValue());
            String startTime = "0" + startHrTime + ":" + startMinTime + ":00"; //TODO: Will need to check if starthrtime > 9, if so then do not add 0

            // End date and time info
            String endDate = addAppointEndPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endHrTime = String.valueOf(endHrSpinner.getValue());
            String endMinTime = String.valueOf(endMinSpinner.getValue());
            String endTime = "0" + endHrTime + ":" + endMinTime + ":00.0";


            System.out.println(startTime);
            Timestamp startTS = Time.convertStringsToTime(startDate, startTime);
            Timestamp endTS = Time.convertStringsToTime(endDate, endTime);
            LocalDateTime startTSUTC = Time.convertTStoLDT(startTS);
            LocalDateTime endTSUTC = Time.convertTStoLDT(endTS);
            System.out.println("TIME ADDED");


            //TODO: DO THIS LATER... MODIFY APPOINTMENT IS WORKING
            //TESTING
            assert startTS != null;
            LocalDateTime startLdt = Time.convertTStoLDT(startTS);
            LocalDateTime endLdt = Time.convertTStoLDT(endTS);
            if(endLdt.getHour() == 2) {
                if(endLdt.getMinute() != 0) {
                    endTimeMinCheck = false;
                    highlightErrors();
                    return false;
                }
            }
            System.out.println("LDT UTC VALUE: " + startLdt);
            boolean testing = Time.checkBusinessHours(startLdt);
            System.out.println(testing);
            if(Time.checkBusinessHours(startLdt) == true) {
                System.out.println("VALUE OF LDT IN EST: " + startLdt);
            } else {
                startTimeHrCheck = false;
                highlightErrors();
                return false;
            }

            if(Time.checkBusinessHours(endLdt) == true) {
                System.out.println("VALUE OF LDT END: " + endLdt);
            } else {
                endTimeHrCheck = false;
                highlightErrors();
                return false;
            }

            if(CustomersCRUD.isOverlap(customerID, startLdt, endLdt)) {
                // TODO: ALERT SAYING OVERLAP
                showOverlapAlert();
                startTimeHrCheck = false;
                endTimeHrCheck = false;
                startTimeMinCheck = false;
                endTimeMinCheck = false;
                customerIDCheck = false;
                highlightErrors();
                return false;
            }

            if(addAppointStartPicker.getValue().getDayOfYear() > addAppointEndPicker.getValue().getDayOfYear()) {
                // TODO: ALERT
                startDateCheck = false;
                endDateCheck = false;
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
                    System.out.println("START HR SPINNER VALUE: " + startHrSpinner.getValue());
                    appoint.setStartMin(startMinSpinner.getValue());
                    appoint.setEndHr(endHrSpinner.getValue());
                    appoint.setEndMin(endMinSpinner.getValue());

//                appoint.setStart(startTS);
                    appoint.setStart(Timestamp.valueOf(startTSUTC));
//                appoint.setEnd(endTS);
                    appoint.setEnd(Timestamp.valueOf(endTSUTC));
                    appoint.setStartDate(addAppointStartPicker.getValue());
                    appoint.setEndDate(addAppointEndPicker.getValue());

                    appoint.setCreatedBy(User.getCurrentUser());

                    Appointment.addAppointment(appoint);
                    AppointmentsCRUD.insertAppointment(appoint);
                    isCreated = true;
                }

            }


        }catch(Exception e) {
            showErrorAlert();
            isCreated = false;
//            e.printStackTrace();
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


    public void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid data in field(s)");
        alert.setContentText("Invalid data was provided. Please check fields highlighted in red");
        alert.showAndWait().ifPresent(response -> {

        });
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

    public void createAppointment(ActionEvent actionEvent) throws IOException {
        checkFields();
        highlightErrors();
        if(createAppointObject()) {
            returnToMainScreen(actionEvent);
        } else {
            setChecksToFalse();
        }
//        if(checkFields()) {
//            highlightErrors();
//            createAppointObject();
//            returnToMainScreen(actionEvent);
//        } else {
//            highlightErrors();
//            setChecksToFalse();
//        }
        //check fields
        // present errors
        // boolean for create appointment
            // else set all checks to false
    }



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



    private void checkTitleField() {
        titleCheck = addAppointTitleTF.getLength() != 0;
    }

    private void checkDescription() {
        descriptionCheck = addAppointDescriptionTF.getLength() != 0;
    }

    private void checkLocationField() {
        locationCheck = addAppointLocationTF.getLength() != 0;
    }

    private void checkTypeField() {
        typeCheck = addAppointTypeTF.getLength() != 0;
    }

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

    private void checkContactCombo() {
        contactCheck = addAppointContactCombo.getValue() != null;
    }

    private void checkStartDate() {
        startDateCheck = addAppointStartPicker.getValue() != null;
    }

    private void checkEndDate() {
        endDateCheck = addAppointEndPicker.getValue() != null;
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

    private void initArrays() {
        for(int i = 0; i < 24; i++) { //TODO: May have to change later after conversion and stuff
            hours.add(i);
        }
        mins.add(0);
        mins.add(15);
        mins.add(30);
        mins.add(45);
    }
}