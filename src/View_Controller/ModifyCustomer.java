package View_Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.CustomersCRUD;
import util.FirstLevelDivisionCRUD;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static View_Controller.MainForm.*;

public class ModifyCustomer implements Initializable {
    // FXML
    @FXML private TextField customerIDTF;
    @FXML private ComboBox<FirstLevelDivision> customerFirstLevelCombo;
    @FXML private ComboBox<Country> customerCountryCombo;
    @FXML private TextField customerNameTF;
    @FXML private TextField customerPostalTF;
    @FXML private TextField customerPhoneTF;
    @FXML private TextField customerAddressTF;

    // Boolean variables for error checking
    private boolean nameCheck;
    private boolean phoneCheck;
    private boolean postalCheck;
    private boolean addressCheck;
    private boolean firstLevelCheck;
    private boolean countryCheck;

    // Modify customer variables
    int indexOfCustomer = customerIndexToModify();
    Customer modifyCustomer = customerToModify();

    /**
     * Initializes when ModifyCustomer screen is loaded
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FirstLevelDivisionCRUD.getUSDivisions();
        FirstLevelDivisionCRUD.getUKDivisions();
        FirstLevelDivisionCRUD.getCADivisions();
        populateFields();
    }

    /**
     * Populates form fields with modify customer data
     */
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


    /**
     * Tries to modify customer object. Returns to main screen if successful
     * @param actionEvent Save button press
     * @throws IOException
     */
    public void modifyCustomer(ActionEvent actionEvent) throws IOException {
        checkFields();
        highlightErrors();
        if(checkDifferences()) {
            returnToMainScreen(actionEvent);
        } else {
            if(checkAllErrors()) {
                modifyCustomerObject();
                returnToMainScreen(actionEvent);
            } else {
                highlightErrors();
                showErrorAlert();
            }
            setChecksToFalse();
        }
    }

    /**
     * Attempts to modify customer object based on provided data
     */
    private void modifyCustomerObject() {
        if(checkAllErrors()) {
            int customerID = customerToModify().getCustomerID();
            String customerName = customerNameTF.getText().trim();
            String address = customerAddressTF.getText().trim();
            String postal = customerPostalTF.getText().trim();
            String phone = customerPhoneTF.getText().trim();
            int divisionID = customerFirstLevelCombo.getValue().getDivisionID();
            String createdBy = User.getCurrentUser();
            Customer customer = new Customer(customerID, customerName, address, phone, postal, divisionID);
            customer.setCountry(customerCountryCombo.getValue());
            customer.setCreatedBy(createdBy);
            customer.setLastUpdatedBy(User.getCurrentUser());
            Customer.updateCustomer(customerIndexToModify(), customer);
            CustomersCRUD.updateCustomer(customer);
        } else {
            showErrorAlert();
        }
    }

    /**
     * Checks all differences in fields
     * @return True if differences are present
     */
    public boolean checkDifferences() {
        boolean differencesPresent;
        differencesPresent = checkNameDiff() && checkPhoneDiff() && checkPostalDiff() && checkFirstLevelDiff()
                && checkCountryDiff() && checkAddressDiff();
        return differencesPresent;
    }

    /**
     * Checks address field for differences
     * @return True if differences
     */
    private boolean checkAddressDiff() {
        boolean isDifferent;
        isDifferent = !customerAddressTF.getText().equals(customerToModify().getAddress());
        return isDifferent;
    }

    /**
     * Checks country combo box for differences
     * @return True if differences are present
     */
    private boolean checkCountryDiff() {
        boolean isDifferent;
        isDifferent = customerCountryCombo.getValue() != customerToModify().getCountry();
        return isDifferent;
    }

    /**
     * Checks first level division combo box for differences
     * @return True if differences are present
     */
    private boolean checkFirstLevelDiff() {
        boolean isDifferent;
        isDifferent = customerFirstLevelCombo.getValue().getDivisionID() != customerToModify().getDivisionID();
        return isDifferent;
    }

    /**
     * Checks postal field for differences
     * @return True if differences are present
     */
    private boolean checkPostalDiff() {
        boolean isDifferent;
        isDifferent = !customerPostalTF.getText().equals(customerToModify().getPostalCode());
        return isDifferent;
    }

    /**
     * Checks phone for differences
     * @return True if differences are present
     */
    private boolean checkPhoneDiff() {
        boolean isDifferent;
        isDifferent = !customerPhoneTF.getText().equals(customerToModify().getPhone());
        return isDifferent;
    }

    /**
     * Checks name field for differences
     * @return True if differences are present
     */
    private boolean checkNameDiff() {
        boolean isDifferent;
        isDifferent = !customerNameTF.getText().equals(customerToModify().getCustomerName());
        return isDifferent;
    }

    /**
     * Checks all error variables
     * @return True if all error variables are set to true
     */
    public boolean checkAllErrors() {
        boolean isValid;
        isValid = nameCheck && phoneCheck && postalCheck && addressCheck && firstLevelCheck && countryCheck;
        return isValid;
    }

    /**
     * Returns to the main screen
     * @param actionEvent Button press
     * @throws IOException
     */
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

    /**
     * Filters first level division combo box based on selected country
     * @param actionEvent ComboBox selection
     */
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

    /**
     * Checks all fields for valid data
     */
    public void checkFields() {
        checkNameField();
        checkPhoneField();
        checkPostalField();
        checkAddressField();
        checkFirstLevelField();
        checkCountryField();
    }

    /**
     * Checks name field for valid data
     */
    private void checkNameField() {
        nameCheck = customerNameTF.getText().length() != 0 && !customerNameTF.getText().matches("[0-9]*");
    }

    /**
     * Checks phone field for valid data
     */
    private void checkPhoneField() {
        phoneCheck = customerPhoneTF.getText().length() != 0;
    }

    /**
     * Checks postal field for valid data
     */
    private void checkPostalField() {
        postalCheck = customerPostalTF.getText().length() != 0;
    }

    /**
     * Checks address field for valid data
     */
    private void checkAddressField() {
        addressCheck = customerAddressTF.getText().length() != 0;
    }

    /**
     * Checks first level division combo box for valid data
     */
    private void checkFirstLevelField() {
        firstLevelCheck = customerFirstLevelCombo.getValue() != null;
    }

    /**
     * Checks country combo box for valid data
     */
    private void checkCountryField() {
        countryCheck = customerCountryCombo.getValue() != null;
    }

    /**
     * Lets user know that the customer was not modified and to check highlighted fields
     */
    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Failed to modify customer");
        alert.setContentText("Customer was not modified.. please check highlighted fields and try again");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    /**
     * Sets all error checking variables to false
     */
    private void setChecksToFalse() {
        nameCheck = false;
        phoneCheck = false;
        postalCheck = false;
        addressCheck = false;
        firstLevelCheck = false;
        countryCheck = false;
    }

    /**
     * Highlights all fields that contain errors
     */
    private void highlightErrors() {
        if(!nameCheck) {
            customerNameTF.setStyle("-fx-border-color: #ae0700");
        } else {
            customerNameTF.setStyle("-fx-border-color: #9f07");
        }
        if(!phoneCheck) {
            customerPhoneTF.setStyle("-fx-border-color: #ae0700");
        } else {
            customerPhoneTF.setStyle("-fx-border-color: #9f07");
        }
        if(!postalCheck) {
            customerPostalTF.setStyle("-fx-border-color: #ae0700");
        } else {
            customerPostalTF.setStyle("-fx-border-color: #9f07");
        }
        if(!addressCheck) {
            customerAddressTF.setStyle("-fx-border-color: #ae0700");
        } else {
            customerAddressTF.setStyle("-fx-border-color: #9f07");
        }
        if(!firstLevelCheck) {
            customerFirstLevelCombo.setStyle("-fx-border-color: #ae0700");
        } else {
            customerFirstLevelCombo.setStyle("-fx-border-color: #9f07");
        }
        if(!countryCheck) {
            customerCountryCombo.setStyle("-fx-border-color: #ae0700");
        } else {
            customerCountryCombo.setStyle("-fx-border-color: #9f07");
        }
    }
}
