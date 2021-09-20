package util;

import Model.Appointment;
import Model.Customer;

import java.sql.*;

public class CustomersCRUD {
    public static void loadAllCustomers() throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        try {
            Customer tempCustomer;
            String query = "SELECT * FROM customers";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                // Get data from database
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");

                // Sets data to object
                tempCustomer = new Customer(customerID, customerName, address, phone,
                        postalCode, divisionID, createDate, lastUpdatedBy,
                        lastUpdate, createdBy);

                // Adds to appointment list
                Customer.addCustomer(tempCustomer);
            }
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public static void insertCustomer(Customer customer) {
        PreparedStatement ps;
        try {
            Connection conn = Database.getConnection();
            String query = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getCreatedBy());
            ps.setInt(6, customer.getDivisionID());
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public static void updateCustomer(Customer customer) {
        // TODO: TOMORROW OR TONIGHT
    }

    public static void deleteCustomer(Customer customer) {
        PreparedStatement ps;
        try {
            Connection conn = Database.getConnection();
            String query = "DELETE FROM customers WHERE Customer_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, customer.getCustomerID());
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public static int getNextCustomerID() {
        int nextID = 0;
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            String query = "SELECT MAX(LAST_INSERT_ID(Customer_ID)) + 1 AS NEXT_ID FROM customers";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                nextID = rs.getInt("NEXT_ID");
            }
            System.out.println("ID IS " + nextID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nextID;
    }
}
