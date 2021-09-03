package View_Controller;

import Model.*;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/*
 * Javadoc location: /out/javadoc/index.html
 */


/*
 * "FUTURE ENHANCEMENT":
 * 1. Implement a small databased with the application so data can persist after application close
 * 2. Maybe provide exact measurements and placement X's and Y's for JavaFX objects so application can be consistent with application in demonstration video
 */

public class MainController extends Application implements Initializable {

    /**
     * Starts application and loads up main screen (mainform.fxml)
     * @param primaryStage main stage to be set after starting
     * @throws Exception if it fails to start properly
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/mainform.fxml")));
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(new Scene(root, 1100, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Main method for the entire program. Loads in sample data and uses FXML launch command to start
     * @param args default arguments with java programs
     */
    public static void main(String[] args) {

        launch(args);
    }


    /**
     * Opens up the Add Part page when the add button is pressed
     * @param actionEvent when a button is pressed
     */
    public void openAddPartWindow(ActionEvent actionEvent) {
        Parent root;
        try {
            //Setting scene to AddPart window and setting properties for it
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/AddPart.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Add Part");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();

            //Hides current window
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the add product window upon a click
     * @param actionEvent button being pressed
     */
    public void openAddProductWindow(ActionEvent actionEvent) {
        Parent root;
        try {
            //Setting new scene and adding properties to scene
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/AddProduct.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(new Scene(root, 920, 500));
            stage.setResizable(false);
            stage.show();

//            Hides current window after scene has been set
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes application when the exit button is pressed
     */
    public void closeApp() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
