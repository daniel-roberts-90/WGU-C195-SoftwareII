package Data;

import Main.DataConnect;
import Model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A utility class to manage customer data from the database.
 */
public class CustomerData {

    /**
     * Retrieves all customers from the database.
     *
     * @return A list of all customers as Customer objects.
     * @throws SQLException if an SQL exception occurs during database retrieval.
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        ObservableList<Customer> customersObservableList = FXCollections.observableArrayList();
        try {
            String query = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_code, c.Phone, c.Division_ID, cn.Country_ID " +
                    " FROM customers AS c " +
                    " INNER JOIN first_level_divisions AS f ON c.Division_ID = f.Division_ID " +
                    " INNER JOIN countries AS cn ON cn.Country_ID = f.Country_ID";

            PreparedStatement ps = DataConnect.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Retrieve data from the result set and create a Customer object for each record.
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerTelephone = rs.getString("Phone");
                int divID = rs.getInt("Division_ID");
                int countryID = rs.getInt("Country_ID");
                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerTelephone, divID, countryID);
                customersObservableList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersObservableList;
    }

    /**
     * Creates a new customer in the database.
     *
     * @param customerName      The name of the customer.
     * @param customerAddress   The address of the customer.
     * @param customerPostalCode The postal code of the customer.
     * @param customerTelephone The telephone number of the customer.
     * @param divisionID        The ID of the division the customer belongs to.
     */
    public static void createCustomer(String customerName, String customerAddress, String customerPostalCode, String customerTelephone, Integer divisionID) {
        try {
            String query = "INSERT INTO customers VALUES(NULL,?,?,?,?,NOW(),'JF', NOW(),'JF',?)";

            PreparedStatement ps = DataConnect.connection.prepareStatement(query);

            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerTelephone);
            ps.setInt(5, divisionID);
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing customer in the database.
     *
     * @param customerID         The ID of the customer to update.
     * @param customerName       The updated name of the customer.
     * @param customerAddress    The updated address of the customer.
     * @param customerPostalCode The updated postal code of the customer.
     * @param customerTelephone  The updated telephone number of the customer.
     * @param divisionID         The updated ID of the division the customer belongs to.
     */
    public static void updateCustomer(String customerID, String customerName, String customerAddress, String customerPostalCode, String customerTelephone, int divisionID) {
        try {
            String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ? , Phone = ?, Division_ID = ? WHERE Customer_ID = ? ";

            PreparedStatement ps = DataConnect.connection.prepareStatement(query);

            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerTelephone);
            ps.setInt(5, divisionID);
            ps.setString(6, customerID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * Deletes a customer from the database.
     *
     * @param customerID The ID of the customer to delete.
     */
    public static void deleteCustomer(int customerID) {
        try {
            String query = "DELETE from Customers WHERE Customer_ID = ?";
            PreparedStatement ps = DataConnect.connection.prepareStatement(query);
            ps.setInt(1, customerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}