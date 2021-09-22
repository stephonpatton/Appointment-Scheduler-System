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
import util.AppointmentsCRUD;
import util.CustomersCRUD;
import util.FirstLevelDivisionCRUD;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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

    // TODO: DO WHEN I GET BACK
    private void modifyCustomerObject() {
//        Customer customer = new Customer();
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
            // TODO: CREATE OBJECT (might need to add create_date and last_update)
            customer.setCreatedBy(createdBy);
            customer.setLastUpdatedBy(User.getCurrentUser());
            Customer.updateCustomer(customerIndexToModify(), customer);
            CustomersCRUD.updateCustomer(customer);
        } else {
            showErrorAlert();
        }
    }

    public boolean checkDifferences() {
        boolean differencesPresent;
        differencesPresent = checkNameDiff() && checkPhoneDiff() && checkPostalDiff() && checkFirstLevelDiff()
                && checkCountryDiff() && checkAddressDiff();
        return differencesPresent;
    }

    private boolean checkAddressDiff() {
        boolean isDifferent;
        isDifferent = !customerAddressTF.getText().equals(customerToModify().getAddress());
        return isDifferent;
    }

    private boolean checkCountryDiff() {
        boolean isDifferent;
        isDifferent = customerCountryCombo.getValue() != customerToModify().getCountry();
        return isDifferent;
    }

    private boolean checkFirstLevelDiff() {
        boolean isDifferent;
        isDifferent = customerFirstLevelCombo.getValue().getDivisionID() != customerToModify().getDivisionID();
        return isDifferent;
    }

    private boolean checkPostalDiff() {
        boolean isDifferent;
        isDifferent = !customerPostalTF.getText().equals(customerToModify().getPostalCode());
        return isDifferent;
    }

    private boolean checkPhoneDiff() {
        boolean isDifferent;
        isDifferent = !customerPhoneTF.getText().equals(customerToModify().getPhone());
        return isDifferent;
    }

    private boolean checkNameDiff() {
        boolean isDifferent;
        isDifferent = !customerNameTF.getText().equals(customerToModify().getCustomerName());
        return isDifferent;
    }

    public boolean checkAllErrors() {
        boolean isValid;
        isValid = nameCheck && phoneCheck && postalCheck && addressCheck && firstLevelCheck && countryCheck;
        return isValid;
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

    public void checkFields() {
        checkNameField();
        checkPhoneField();
        checkPostalField();
        checkAddressField();
        checkFirstLevelField();
        checkCountryField();
    }

    private void checkNameField() {
        nameCheck = customerNameTF.getText().length() != 0 && !customerNameTF.getText().matches("[0-9]*");
    }

    private void checkPhoneField() {
        phoneCheck = customerPhoneTF.getText().length() != 0;
    }

    private void checkPostalField() {
        postalCheck = customerPostalTF.getText().length() != 0;
    }

    private void checkAddressField() {
        addressCheck = customerAddressTF.getText().length() != 0;
    }

    private void checkFirstLevelField() {
        firstLevelCheck = customerFirstLevelCombo.getValue() != null;
    }

    private void checkCountryField() {
        countryCheck = customerCountryCombo.getValue() != null;
    }

    // TODO: Check for valid data
    // TODO: Check for differences
    // TODO: Update object if differences
    // TODO: Save to arraylist and then update database with new information

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Failed to modify customer");
        alert.setContentText("Customer was not modified.. please check highlighted fields and try again");
        alert.showAndWait().ifPresent(response -> {

        });
    }


    private void setChecksToFalse() {
        nameCheck = false;
        phoneCheck = false;
        postalCheck = false;
        addressCheck = false;
        firstLevelCheck = false;
        countryCheck = false;
    }

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
