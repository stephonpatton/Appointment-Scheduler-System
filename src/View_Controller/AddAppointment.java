package View_Controller;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.AppointmentsCRUD;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {
    @FXML private TextField addAppointIDTF;
    @FXML private ComboBox<Contact> addAppointContactCombo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAppointIDTF.setText("AUTO GEN: " + AppointmentsCRUD.getNextIDCount());
//        addAppointContactTF.setText(AppointmentsCRUD.getContactName(1));
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        contacts.addAll(Contact.getAllContacts());
        addAppointContactCombo.setItems(contacts);
    }



    public void createAppointObject() {

    }

    public boolean checkFields() {
        boolean success = false;

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

    private void highlightErrors() {

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



//<!--   <children>-->
//<!--      <ComboBox fx:id="fruitCombo" layoutX="15.0" layoutY="33.0" prefWidth="90.0" promptText="choose">-->
//<!--         <items>-->
//<!--            <FXCollections fx:factory="observableArrayList">-->
//<!--               <String fx:value="Apple" />-->
//<!--               <String fx:value="Orange" />-->
//<!--               <String fx:value="Pear" />-->
//<!--            </FXCollections>-->
//<!--         </items>-->
//<!--      </ComboBox>-->
//
//<!--   </children>-->