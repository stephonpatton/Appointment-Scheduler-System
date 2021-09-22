package View_Controller;

import Model.Contact;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.AppointmentsCRUD;
import util.ContactsCRUD;
import util.CustomersCRUD;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.time.ZonedDateTime.now;



/*
 * Javadoc location: /out/javadoc/index.html
 */


/*
 * "FUTURE ENHANCEMENT":
 * 1. Implement a small databased with the application so data can persist after application close
 * 2. Maybe provide exact measurements and placement X's and Y's for JavaFX objects so application can be consistent with application in demonstration video
 */

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
        //TODO: FIGURE OUT LOCATION HERE AND setDefault(new Locale(...)) here before loading login form
//        Locale.setDefault(new Locale("fr"));
//        ResourceBundle rb = ResourceBundle.getBundle("language_files/languages_en.properties", Locale.getDefault());

        //TODO: TIME LOGIC BELOW -- can be used to help determine location (after ResourceBundle issue is fixed)
        ZonedDateTime zone = ZonedDateTime.now();
        ZoneId locationId = ZoneId.of(zone.getZone().toString());
        System.out.println(LocalTime.now(locationId));
        System.out.println(zone.getHour() + ":" + zone.getMinute() + ":" + zone.getSecond() + "    " + zone.getZone());

        System.out.println(ZoneId.getAvailableZoneIds());

        Locale.setDefault(new Locale("fr"));
        ResourceBundle rb = ResourceBundle.getBundle("languages/rb");

        //TODO: Below is for setResource
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View_Controller/LoginPage.fxml"));
        loader.setResources(rb);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/LoginPage.fxml")), rb);

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
        System.out.println(Locale.getDefault());
        Locale.setDefault(new Locale("no", "NO"));
        ResourceBundle rb = ResourceBundle.getBundle("languages/rb");
    }
}
