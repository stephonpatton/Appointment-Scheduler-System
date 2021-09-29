package View_Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController extends Application implements Initializable {
    ResourceBundle rb;

    /**
     * Starts application and loads up main screen (MainForm.fxml)
     * @param primaryStage main stage to be set after starting
     * @throws Exception if it fails to start properly
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent main = null;

        // Uncomment for french
//        Locale.setDefault(new Locale("fr"));
        ResourceBundle rb = ResourceBundle.getBundle("languages/rb");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View_Controller/LoginPage.fxml"));
        loader.setResources(rb);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/LoginPage.fxml")), rb);

        // Checks users locale
        if(Locale.getDefault().getLanguage().equals("fr")) {
            primaryStage.setTitle(rb.getString("title"));
        } else {
            primaryStage.setTitle("Login Page");
        }

        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.setResizable(false);
        LoginPage.setStage(primaryStage);
        primaryStage.show();
    }

    /**
     * Main method for the entire program. Loads in sample data and uses FXML launch command to start
     * @param args default arguments with java programs
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
    }
}
