package View_Controller;

import Model.Appointment;
import Model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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


    int indexOfAppointment = appointmentIndexToModify();
    Appointment modifyAppointment = appointmentToModify();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Gets appointment to modify
        Appointment appointmentToModify = Appointment.getAllAppointments().get(indexOfAppointment);
        modifyAppointIDTF.setText(String.valueOf(modifyAppointment.getAppointmentID()));
        // TODO: Do the rest of the fields
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
}
