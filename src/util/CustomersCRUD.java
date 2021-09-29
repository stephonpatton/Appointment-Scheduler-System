package util;

import Model.Customer;
import Model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;

public class CustomersCRUD {
    /**
     * Loads all customer data from the database
     * @throws SQLException If loading fails
     */
    public static void loadAllCustomers() throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        try {
            String query = "SELECT * FROM customers";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
                Customer tempCustomer;

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

    /**
     * Inserts a customer into the datbase
     * @param customer Provided customer object
     */
    public static void insertCustomer(Customer customer) {
        final long timeAtLocal = System.currentTimeMillis();
        long offset = TimeZone.getDefault().getOffset(timeAtLocal);
        final Timestamp timeAtUTC = new Timestamp(timeAtLocal - offset);
        PreparedStatement ps;
        try {
            Connection conn = Database.getConnection();
            String query = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Division_ID, Customer_ID, Create_Date, Last_Update) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getCreatedBy());
            ps.setInt(6, customer.getDivisionID());
            ps.setInt(7, customer.getCustomerID());
            ps.setTimestamp(8, timeAtUTC);
            ps.setTimestamp(9, timeAtUTC);
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }
    }

    /**
     * Updates customer information at a given index
     * @param customer Provided customer object
     */
    public static void updateCustomer(Customer customer) {
        // Used to convert local time to utc time
        final long timeAtLocal = System.currentTimeMillis();
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

    /**
     * Deletes a customer object from the database
     * @param customer Provided customer object
     */
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

    /**
     * Gets the next customer ID for database entry
     * @return Next ID used in the database
     */
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nextID;
    }

    /**
     * Checks if provided appointment information for a customer will cause overlap
     * @param customerID Provided by user
     * @param startLocal Provided by user
     * @param endLocal Provided by user
     * @return true if overlap will occur
     * @throws SQLException If database check fails to occur
     */
    public static boolean isOverlap(int customerID, LocalDateTime startLocal, LocalDateTime endLocal, int appointmentID) throws SQLException {
        boolean overlap;
        Connection conn = Database.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Boolean> checkAppoints = new ArrayList<>();

        try {
            String query = "SELECT Start, End FROM appointments INNER JOIN customers AS c WHERE c.Customer_ID = ? AND appointments.Customer_ID = ? AND Appointment_ID != ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, customerID);
            ps.setInt(2, customerID);
            ps.setInt(3, appointmentID);
            rs = ps.executeQuery();

            while(rs.next()) {
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");

                LocalDateTime startLdt = start.toLocalDateTime();
                LocalDateTime endLdt = end.toLocalDateTime();

                if(startLocal.isEqual(startLdt) || endLocal.isEqual(endLdt)) {
                    checkAppoints.add(true);
                } else if(startLocal.isAfter(startLdt) && startLocal.isBefore(endLdt)) {
                    checkAppoints.add(true);
                } else {
                    checkAppoints.add(false);
                }
            }
        }catch(SQLException e) {
            throw new Error("Problem", e);
        }

        overlap = checkAppoints.contains(true);
        return overlap;
    }
}
