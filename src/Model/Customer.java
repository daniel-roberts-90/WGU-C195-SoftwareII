package Model;

/**
 * @author Daniel Roberts
 */

/**
 * Represents a customer entity in the application.
 * Each customer has an ID, name, address, postal code, telephone number, division ID, and country ID.
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerTelephone;
    private int divID;
    private int countryID;

    /**
     * Constructs a Customer object with the provided data.
     *
     * @param customerID The ID of the customer.
     * @param customerName The name of the customer.
     * @param customerAddress The address of the customer.
     * @param customerPostalCode The postal code of the customer.
     * @param customerTelephone The telephone number of the customer.
     * @param divID The ID of the division associated with the customer.
     * @param countryID The ID of the country associated with the customer.
     */
    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode,
                    String customerTelephone, int divID, int countryID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerTelephone = customerTelephone;
        this.divID = divID;
        this.countryID = countryID;
    }

    /**
     * Gets the ID of the customer.
     *
     * @return The ID of the customer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the address of the customer.
     *
     * @return The address of the customer.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Gets the postal code of the customer.
     *
     * @return The postal code of the customer.
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Gets the telephone number of the customer.
     *
     * @return The telephone number of the customer.
     */
    public String getCustomerPhoneNumber() {
        return customerTelephone;
    }

    /**
     * Gets the ID of the division associated with the customer.
     *
     * @return The ID of the division associated with the customer.
     */
    public int getDivID() {
        return divID;
    }

    /**
     * Gets the ID of the country associated with the customer.
     *
     * @return The ID of the country associated with the customer.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Returns a string representation of the customer in the format "customerID - customerName".
     *
     * @return A string representation of the customer.
     */
    @Override
    public String toString() {
        return customerID + "-" + customerName;
    }
}