package View_Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import util.Database;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginPage implements Initializable {
    @FXML private TextField usernameTF;
    @FXML private TextField passwordTF;

    @FXML private Button loginButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database.init();
    }

    public void cancelButtonPressed() {
        Platform.exit();
    }

    public void loginButtonPressed() throws SQLException {
        Database.login(usernameTF.getText(), passwordTF.getText());
        System.out.println("Username is: " + usernameTF.getText());
        System.out.println("Password is: " + passwordTF.getText());
    }
}
