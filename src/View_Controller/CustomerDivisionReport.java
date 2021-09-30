package View_Controller;

import Model.Country;
import Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.CountriesCRUD;
import util.FirstLevelDivisionCRUD;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerDivisionReport implements Initializable {
    @FXML private ComboBox<Country> countryComboBox;
    @FXML private TableView<FirstLevelDivision> divisionTableView;
    @FXML private TableColumn<FirstLevelDivision, Integer> totalCol;
    @FXML private TableColumn<FirstLevelDivision, String> divisionCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryComboBox.setItems(Country.getAllCountries());
        setTableCells();
//        divisionTableView.setItems(FirstLevelDivisionCRUD.getFirstLevelByCountry(countryComboBox.getValue()));
    }

    public void setTableCells() {
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    public void populateTable() {
        divisionTableView.setItems(FirstLevelDivisionCRUD.getFirstLevelByCountry(countryComboBox.getValue()));
    }

    public void returnToMainScreen(ActionEvent actionEvent) throws IOException {
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
}
