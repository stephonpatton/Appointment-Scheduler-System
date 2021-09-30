package View_Controller;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import util.AppointmentsCRUD;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerAppointments implements Initializable {
    @FXML private ComboBox<Object> monthComboBox;
    @FXML private TableView<Appointment> appointmentsTableView;
//    private TableColumn<Appointment, Integer> totalCol;
    @FXML private TableColumn<Appointment, Integer> totalCol;
    @FXML private TableColumn<Appointment, String> typeCol;

    private ObservableList<Object> months = FXCollections.observableArrayList();
    private static ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillObservableList();
        monthComboBox.setItems(months);
        setAppointmentCells();
    }

    private void setAppointmentCells() {
        Appointment.setMonthCount(customerAppointments);
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    }


    public void filterByMonth() throws SQLException {
        customerAppointments.clear();
        int month = getMonthID(monthComboBox);
        customerAppointments.addAll(AppointmentsCRUD.getAppointmentsByMonthAndType(month));
        setAppointmentCells();
        appointmentsTableView.setItems(customerAppointments);
    }

    private void fillObservableList() {
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
    }

    private int getMonthID(ComboBox<Object> combobox) {
        int month;
        switch(combobox.getValue().toString()) {
            case "January":
                month = 1;
                break;
            case "February":
                month = 2;
                break;
            case "March":
                month = 3;
                break;
            case "April":
                month = 4;
                break;
            case "May":
                month = 5;
                break;
            case "June":
                month = 6;
                break;
            case "July":
                month = 7;
                break;
            case "August":
                month = 8;
                break;
            case "September":
                month = 9;
                break;
            case "October":
                month = 10;
                break;
            case "November":
                month = 11;
                break;
            case "December":
                month = 12;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + combobox.getValue().toString());
        }
        return month;
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
