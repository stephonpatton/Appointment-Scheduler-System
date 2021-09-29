package View_Controller;

import Model.Country;
import Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {
    // Username and password fields
    @FXML private Label passwordLabel;
    @FXML private Label usernameLabel;
    @FXML private Label languageLabel;
    @FXML private TextField usernameTF;
    @FXML private PasswordField passwordTF;
    @FXML private Button loginButton;
    @FXML private Button cancelButton;
    ResourceBundle rb;
    static Stage stage;

    /**
     * Initialize database once program runs and loginpage is loaded
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
        passwordLabel.setText(rb.getString("passwordLabel"));
        usernameLabel.setText(rb.getString("usernameLabel"));
        languageLabel.setText(String.valueOf(ZoneId.systemDefault()));
        usernameTF.setPromptText(rb.getString("username"));
        passwordTF.setPromptText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        cancelButton.setText(rb.getString("cancel"));
        Database.init();
    }

    /**
     * Closes the application is the cancel button is pressed
     */
    public void cancelButtonPressed() {
        Platform.exit();
    }

    /**
     * Attempts to login with provided credentials from user after login is pressed
     * @param actionEvent - login button pressed
     * @throws SQLException
     * @throws IOException
     */
    public void loginButtonPressed(ActionEvent actionEvent) throws SQLException, IOException {
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        if(Query.login(username, password)) {
            Logger.logUser(username);
            User.setCurrentUser(username);
            System.out.println("LOGIN SUCCESSFUL");
            System.out.println("ZONE ID IS" + ZoneId.systemDefault());
            ContactsCRUD.loadAllContacts();
            AppointmentsCRUD.loadAllAppointments();
            CustomersCRUD.loadAllCustomers();
            CountriesCRUD.loadAllCountries();
            FirstLevelDivisionCRUD.loadAllFirstLevel();
            if(Time.checkIfAppointment15()) {
                appointmentAlert();
                System.out.println("APPOINTMENT IN 15");
            } else {
                noAppointmentAlert();
                System.out.println("NO APPOINTMENT");
            }
            showMainScreen(actionEvent);
        } else {
            String filename = "login_activity.txt";
            FileWriter fileWriter = new FileWriter(filename, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(username + " failed to login on" + LocalDateTime.now().toLocalDate() + " " + LocalDateTime.now().toLocalTime());
            System.out.println(username + " failed to login on" + LocalDateTime.now().toLocalDate() + LocalDateTime.now().toLocalTime()); // TODO: Delete later
            printWriter.close();
            invalidUserPassAlert();
        }
    }

    /**
     * Alerts the user that an invalid username or password was provided
     */
    public void invalidUserPassAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(rb.getString("header"));
        alert.setContentText(rb.getString("content"));
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Opens the main screen after a user has successfully logged in
     * @param actionEvent
     * @throws IOException
     */
    public void showMainScreen(ActionEvent actionEvent) throws IOException {
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
     * Gets login stage
     * @return Login stage
     */
    public static Stage getStage() {
        return LoginPage.stage;
    }

    /**
     * Sets the stage to the login stage
     * @param stage Login stage
     */
    public static void setStage(Stage stage) {
        LoginPage.stage = stage;
    }

    /**
     * Lets the user know they have an appointment in the next 15 mins
     */
    private void appointmentAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Appointment in the next 15 minutes");
        alert.setContentText("There is an appointment in the next 15 mins. \n ID: " + Time.getAppointmentSoon().getAppointmentID() +
                "\n Date/Time: " + Time.getAppointmentSoon().getStartLocal());
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Lets the user know they do not have any appointments in the next 15 mins
     */
    private void noAppointmentAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("No appointments in the next 15 minutes");
        alert.setContentText("There are no appointments in the next 15 minutes. Thank you.");
        alert.showAndWait().ifPresent(response -> {

        });
    }
}
