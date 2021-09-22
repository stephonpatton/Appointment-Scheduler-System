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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
        System.out.println(Locale.getDefault());
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
            User.setCurrentUser(username);
            System.out.println("LOGIN SUCCESSFUL");
            System.out.println("ZONE ID IS" + ZoneId.systemDefault());
            ContactsCRUD.loadAllContacts();
            AppointmentsCRUD.loadAllAppointments();
            CustomersCRUD.loadAllCustomers();
            CountriesCRUD.loadAllCountries();
            FirstLevelDivisionCRUD.loadAllFirstLevel();
            showMainScreen(actionEvent);
        } else {
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

    public static Stage getStage() {
        return LoginPage.stage;
    }

    public static void setStage(Stage stage) {
        LoginPage.stage = stage;
    }
}
