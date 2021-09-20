package View_Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.AppointmentsCRUD;
import util.CustomersCRUD;
import util.FirstLevelDivisionCRUD;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDTF.setText("AUTO GEN: " + CustomersCRUD.getNextCustomerID());
        customerFirstLevelCombo.setItems(FirstLevelDivision.getAllDivisions());
        customerCountryCombo.setItems(Country.getAllCountries());
        FirstLevelDivisionCRUD.getUSDivisions();
        FirstLevelDivisionCRUD.getUKDivisions();
        FirstLevelDivisionCRUD.getCADivisions();
        customerFirstLevelCombo.setValue(FirstLevelDivision.getAllDivisions().get(0));
        customerCountryCombo.setValue(Country.getAllCountries().get(0));
        filterByCountry();
    }

    public void createCustomer(ActionEvent actionEvent) throws IOException {
        checkFields();
        highlightErrors();
        if(createCustomerObject()) {
            returnToMainScreen(actionEvent);
        } else {
            setChecksToFalse();
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

    private void setChecksToFalse() {
        nameCheck = false;
        phoneCheck = false;
        postalCheck = false;
        addressCheck = false;
        firstLevelCheck = false;
        countryCheck = false;
    }

    public boolean createCustomerObject() {
        boolean success;
        try {

            int customerID = CustomersCRUD.getNextCustomerID();
            String customerName = customerNameTF.getText().trim();
            String address = customerAddressTF.getText().trim();
            String postal = customerPostalTF.getText().trim();
            String phone = customerPhoneTF.getText().trim();
            int divisionID = customerFirstLevelCombo.getValue().getDivisionID();
            String createdBy = User.getCurrentUser();
            Customer customer = new Customer(customerID, customerName, address, phone, postal, divisionID);
            // TODO: CREATE OBJECT (might need to add create_date and last_update)
            customer.setCreatedBy(createdBy);
            Customer.addCustomer(customer);
            CustomersCRUD.insertCustomer(customer);

            success = true;
        }catch(Exception e) {
            showErrorAlert();
            System.err.println("SOMETHING FUCKED UP");
            success = false;
        }
        return success;


    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Failed to create customer");
        alert.setContentText("Customer was not created.. please check highlighted fields and try again");
        alert.showAndWait().ifPresent(response -> {

        });
    }

    public void filterByCountry() {
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

    public void returnToMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View_Controller/MainForm.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 1000, 850));
        stage.setResizable(false);
        stage.show();

        //Hides current window
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
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
