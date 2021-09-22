package util;

import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.User;

import java.sql.*;
import java.util.TimeZone;

public class CustomersCRUD {
    public static void loadAllCustomers() throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        try {
            String query = "SELECT * FROM customers";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                Customer tempCustomer = null;

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
            String query = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Division_ID, Customer_ID) VALUES(?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getCreatedBy());
            ps.setInt(6, customer.getDivisionID());
            ps.setInt(7, customer.getCustomerID());
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public static void updateCustomer(Customer customer) {
        final long timeAtLocal = System.currentTimeMillis(); // or System.currentTimeMillis(); or new Date().getTime(); etc.
        long offset = TimeZone.getDefault().getOffset(timeAtLocal);
        final Timestamp timeAtUTC = new Timestamp(timeAtLocal - offset);

        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps;
            String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setTimestamp(5, timeAtUTC);
            ps.setString(6, User.getCurrentUser());
            ps.setInt(7, customer.getDivisionID());
            ps.setInt(8, customer.getCustomerID());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
