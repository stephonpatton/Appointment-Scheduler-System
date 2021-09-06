package View_Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import util.Database;
import util.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {
    // Username and password fields
    @FXML private TextField usernameTF;
    @FXML private PasswordField passwordTF;

    /**
     * Initialize database once program runs and loginpage is loaded
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            System.out.println("LOGIN SUCCESSFUL");
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
        alert.setHeaderText("Invalid username and/or password");
        alert.setContentText("Invalid username and/or password. Please try again.");
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
        stage.setScene(new Scene(root, 800, 750));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
