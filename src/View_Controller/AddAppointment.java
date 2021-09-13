package View_Controller;

import Model.Appointment;
import Model.Contact;
import Model.User;
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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
            String startTime = "0" + startHrTime + ":" + startMinTime + ":00";
            System.out.println(startTime);
            Timestamp ts = convertStringsToTime(startDate, startTime);
            System.out.println("TIME ADDED");
            if(title.length() == 0 || description.length() == 0 || location.length() == 0 || type.length() == 0) {
                isCreated = false;
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
                appoint.setContactName();

                appoint.setStart(ts);

                appoint.setCreatedBy(User.getCurrentUser());

                //TODO: TIME and DATES

                Appointment.addAppointment(appoint);
                AppointmentsCRUD.insertAppointment(appoint);
                isCreated = true;
            }


        }catch(Exception e) {
            showErrorAlert();
            isCreated = false;
//            e.printStackTrace();
        }


        return isCreated;
    }

    private static Timestamp convertStringsToTime(String date, String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = date + " " + time;
            Date parsedDate = formatter.parse(dateTime);
            Timestamp ts = new Timestamp(parsedDate.getTime());
            System.out.println("TIMESTAMP: " + ts.getTime());
            return ts;
//            Date dateObj = formatter.parse(dateTime);
//            System.out.println(dateObj.getTime());
//            return new Timestamp(dateObj.getTime());
        }catch (Exception e) {
            return null;
        }
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
        for(int i = 0; i < 24; i++) {
            hours.add(i);
        }
        mins.add(0);
        mins.add(15);
        mins.add(30);
        mins.add(45);
    }
}