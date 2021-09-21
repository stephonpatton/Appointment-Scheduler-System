package View_Controller;

import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.FirstLevelDivisionCRUD;

import java.net.URL;
import java.util.ResourceBundle;

import static View_Controller.MainForm.*;

public class ModifyCustomer implements Initializable {
    @FXML private TextField customerIDTF;
    @FXML private ComboBox<FirstLevelDivision> customerFirstLevelCombo;
    @FXML private ComboBox<Country> customerCountryCombo;
    @FXML private TextField customerNameTF;
    @FXML private TextField customerPostalTF;
    @FXML private TextField customerPhoneTF;
    @FXML private TextField customerAddressTF;

    private boolean nameCheck;
    private boolean phoneCheck;
    private boolean postalCheck;
    private boolean addressCheck;
    private boolean firstLevelCheck;
    private boolean countryCheck;

    int indexOfCustomer = customerIndexToModify();
    Customer modifyCustomer = customerToModify();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FirstLevelDivisionCRUD.getUSDivisions();
        FirstLevelDivisionCRUD.getUKDivisions();
        FirstLevelDivisionCRUD.getCADivisions();
        populateFields();
    }

    public void populateFields() {
        customerIDTF.setText(String.valueOf(modifyCustomer.getCustomerID()));
        customerNameTF.setText(modifyCustomer.getCustomerName());
        customerPhoneTF.setText(modifyCustomer.getPhone());
        customerPostalTF.setText(modifyCustomer.getPostalCode());
        customerAddressTF.setText(modifyCustomer.getAddress());
        customerFirstLevelCombo.setItems(FirstLevelDivision.getAllDivisions());
        customerCountryCombo.setItems(Country.getAllCountries());

        if(modifyCustomer.getDivisionID() <= 54) {
            customerFirstLevelCombo.setItems(FirstLevelDivision.getAllUSDivisions());
            customerCountryCombo.setValue(Country.getAllCountries().get(0));
        } else if(modifyCustomer.getDivisionID() > 100) {
            customerFirstLevelCombo.setItems(FirstLevelDivision.getAllUKDivisions());
            customerCountryCombo.setValue(Country.getAllCountries().get(1));
        } else {
            customerFirstLevelCombo.setItems(FirstLevelDivision.getAllCADivisions());
            customerCountryCombo.setValue(Country.getAllCountries().get(2));
        }
        customerFirstLevelCombo.setValue(FirstLevelDivision.getFirstLevelByID(modifyCustomer.getDivisionID()));
    }

    public void modifyCustomer(ActionEvent actionEvent) {
    }

    public void returnToMainScreen(ActionEvent actionEvent) {
    }

    public void filterByCountry(ActionEvent actionEvent) {
        if(customerCountryCombo.getValue().equals(Country.getAllCountries().get(0))) {
            customerFirstLevelCombo.setItems(FirstLevelDivision.getAllUSDivisions());
            customerFirstLevelCombo.setValue(FirstLevelDivision.getAllUSDivisions().get(0));
        } else if(customerCountryCombo.getValue().equals(Country.getAllCountries().get(1))) {
            customerFirstLevelCombo.setItems(FirstLevelDivision.getAllUKDivisions());
            customerFirstLevelCombo.setValue(FirstLevelDivision.getAllUKDivisions().get(0));
        } else {
            customerFirstLevelCombo.setItems(FirstLevelDivision.getAllCADivisions());
            customerFirstLevelCombo.setValue(FirstLevelDivision.getAllCADivisions().get(0));
        }
    }


    // TODO: Check for valid data
    // TODO: Check for differences
    // TODO: Update object if differences
    // TODO: Save to arraylist and then update database with new information
}
