package Controller;

import Data.CountryData;
import Data.CustomerData;
import Data.DivisionData;
import static Main.DataConnect.connection;
import Model.Country;
import Model.Customer;
import Model.Division;
import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * FXML Controller class
 * @author Daniel Roberts
 * Handles logic for managing customers
 */
public class CustomersController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<CustomerData, Integer> IDCol;
    @FXML
    private TableColumn<CustomerData, String> nameCol;
    @FXML
    private TableColumn<CustomerData, String> addressCol;
    @FXML
    private TableColumn<CustomerData, Integer> postalCodeCol;
    @FXML
    private TableColumn<CustomerData, Integer> telephoneCol;
    @FXML
    private TableColumn<CustomerData, String> firstLevelCol;
    @FXML
    private TableColumn<CustomerData, String> countryCol;
    @FXML
    private TextField customerID;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField customerPostalCode;
    @FXML
    private TextField customerTelephone;
    @FXML
    private ComboBox<CountryData> customerCountry;
    @FXML
    private Button editButton;
    @FXML
    private TableView<Customer> customerTableView;
    
    private Division divisions;
    private CountryData countryID;
    @FXML
    private ComboBox<Division> customerDiv;
    @FXML
    private Button updateButton;

    /**
     * Initializes the controller class.
     * Populates table view with all customers
     * Populates the country and division combo boxes
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
      ObservableList<Customer> allCustomers = null;
        try {
            allCustomers = CustomerData.getAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customerTableView.setItems(allCustomers);

        IDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        firstLevelCol.setCellValueFactory(new PropertyValueFactory<>("divID"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryID"));

        ObservableList<CountryData> countries = CountryData.getAllCountries();
        customerCountry.setItems(CountryData.getAllCountries());
        customerCountry.setVisibleRowCount(5);
        customerCountry.setPromptText("- Choose A Country -");

        try {
            ObservableList<Division> div = DivisionData.getAllDivisions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if(customerCountry.getValue() == null) {
                customerDiv.setDisable(true);
            }
            else if(customerCountry.getValue() != null){
                customerDiv.setDisable(false);
            }
            customerDiv.setItems(DivisionData.getAllDivisions());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customerDiv.setVisibleRowCount(5);
        customerDiv.setPromptText("- Choose A Division -");
    }   

    /**
     * Action when "Add" button is clicked
     * Validates the input fields and
     * throws error message if field is blank
     * If validation passes, refreshes the table view with the added customer
     * @param event 
     */
    @FXML
    private void onAdd(ActionEvent event) {
        
     try{
            if(customerName.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a customer to the form");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String  addCustomerName = customerName.getText();

            if(customerTelephone.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a phone number");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String addPhoneNumber = customerTelephone.getText();

            if(customerPostalCode.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a postal code");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String addPostalCode = customerPostalCode.getText();

            if(customerDiv.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please Add a Country or Division");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            Integer addDivisionID = customerDiv.getValue().getDivID();

            if(customerAddress.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add an address");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String addAddress = customerAddress.getText();

             CustomerData.createCustomer(addCustomerName,addAddress, addPostalCode, addPhoneNumber, addDivisionID);
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Alert");
               alert.setContentText("Customer added successfully");
               ButtonType okButton = new ButtonType("OK");
               alert.getButtonTypes().setAll(okButton);
               alert.showAndWait();
               
               ObservableList<Customer> allCustomers = null;
               try {
                   allCustomers = CustomerData.getAllCustomers();
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
               customerTableView.setItems(allCustomers);


        } catch (NumberFormatException | NullPointerException e ) {
            e.printStackTrace(); // alert here
        }
   
        
    }
    
    /**
     * Action when "Delete" button is clicked
     * Gets the selected customer from the table view
     * Checks if customer is associated with an existing appointment and
     * if true, error message is thrown not allowing for deletion
     * if false, confirmation message is thrown asking for user to confirm deletion
     * if user confirms, customer data is removed from the database and
     * table view is updated and refreshed
     * @param event
     * @throws SQLException 
     */

    @FXML
    private void onDelete(ActionEvent event) throws SQLException {
        
        try {

            Customer deleteCustomer = customerTableView.getSelectionModel().getSelectedItem();

                if (deleteCustomer == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("Please select a customer to delete");
                    ButtonType okButton = new ButtonType("OK");
                    alert.getButtonTypes().setAll(okButton);
                    alert.showAndWait();
                } 
                    
                    boolean hasAppointments = hasAppointments(deleteCustomer.getCustomerID());
                    if (hasAppointments) {
                    // Notify the user and ask for confirmation to delete appointments
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setContentText("The selected customer has related appointments and cannot be deleted.\n");
                    ButtonType okButton = new ButtonType("OK");
                    alert.getButtonTypes().setAll(okButton);
                    alert.showAndWait();
                }
                    else {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Warning");
                    alert.setContentText("Are you sure you would like to remove the selected customer?");
                    ButtonType yesButton = new ButtonType("Yes");
                    ButtonType noButton = new ButtonType("No");
                    alert.getButtonTypes().setAll(yesButton, noButton);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == yesButton) {
                        CustomerData.deleteCustomer(deleteCustomer.getCustomerID());
                        alert.setTitle("Alert");
                        alert.setContentText("Customer:" + " " + deleteCustomer.getCustomerID() + " " +  "-" + " " + deleteCustomer.getCustomerName() + " " + " has been deleted!");
                        ButtonType okButton = new ButtonType("OK");
                        alert.getButtonTypes().setAll(okButton);
                        alert.showAndWait();
                    }
                }
            ObservableList<Customer> allCustomers = null;
            try {
                allCustomers = CustomerData.getAllCustomers();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            customerTableView.setItems(allCustomers);


        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Action when "Edit" button is clicked
     * Gets the selected customer data and
     * populates input fields with that data
     * @param event 
     */

    @FXML
    private void onEdit(ActionEvent event) {
        
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        
        if (selectedCustomer == null) {
                // No appointment is selected, show an error message or handle it accordingly
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select a customer to edit.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            
        }

        customerID.setText(String.valueOf(selectedCustomer.getCustomerID()));
        customerName.setText(selectedCustomer.getCustomerName());
        customerTelephone.setText(selectedCustomer.getCustomerPhoneNumber());
        customerPostalCode.setText(selectedCustomer.getCustomerPostalCode());
        customerAddress.setText(selectedCustomer.getCustomerAddress());

        for(CountryData c : customerCountry.getItems()){
            if (selectedCustomer.getCountryID() == c.getCountryID()){
                customerCountry.setValue(c);
                break;
            }
        }

        for(Division D : customerDiv.getItems()){
            if(selectedCustomer.getDivID() == D.getDivID()){
                customerDiv.setValue(D);
                break;

            }
        }
        
    }
    
    /**
     * Action when "Back" button is clicked
     * Navigates user back to the Main Menu
     * @param event
     * @throws IOException 
     */
         

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();  
 
    }
    
    /**
     * Action when country combo box is selected
     * Division combo box is enabled or disabled based on whether a country is selected
     * Gets the selected country and
     * populates the division combo box with associated divisions from selected country
     * @param event
     * @throws SQLException 
     */

    @FXML
    private void onCustomerCountry(ActionEvent event) throws SQLException{

        if(customerCountry.getValue() == null) {
            customerDiv.setDisable(true);
        }

        else if(customerCountry.getValue() != null){
            customerDiv.setDisable(false);
        }

       Country cs = customerCountry.getValue();
       if (cs != null){
       customerDiv.setItems(DivisionData.getAllFilteredDivisions(cs.getCountryID()));}

    }
    
    /**
     * Action when "Update" button is clicked
     * Validates input fields and if blank throws error message
     * If validation passes, customer data is updated and displays confirmation
     * Refreshes table view
     * @param event 
     */

    @FXML
    private void onUpdate(ActionEvent event) {
        
        try {
           Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        
            if (selectedCustomer == null) {
                // No appointment is selected, show an error message or handle it accordingly
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select a customer to update.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }


            String updateCustomerID = customerID.getText();

            if(customerName.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a customer name");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String  updateCustomerName = customerName.getText();

            if(customerTelephone.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a phone number");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String updatePhoneNumber = customerTelephone.getText();

            if(customerPostalCode.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a postal code");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String updatePostalCode = customerPostalCode.getText();

            if(customerDiv.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please Add a Country or Division");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            Integer updateDivisionID = customerDiv.getValue().getDivID();

            if(customerAddress.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add an address");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String updateAddress = customerAddress.getText();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you would like to update the selected customer?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == yesButton) {

            CustomerData.updateCustomer(updateCustomerID,updateCustomerName,updateAddress, updatePostalCode, updatePhoneNumber, updateDivisionID);
            
            alert.setTitle("Alert");
            alert.setContentText("Customer updated successfully!");
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
            
            ObservableList<Customer> allCustomers = null;
           try {
                allCustomers = CustomerData.getAllCustomers();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            customerTableView.setItems(allCustomers);
            }
        } 
        catch (NumberFormatException | NullPointerException e ) {
            e.printStackTrace();
        }
        }

    /**
     * Checks if customer is associated with an existing appointment
     * if true, throws error message
     * Appointment must be updated with a different customer or deleted
     * for that customer to be deleted from database
     * @param customerId
     * @return
     * @throws SQLException 
     */
      private boolean hasAppointments(int customerId) throws SQLException {
        try {
            String sql = "SELECT COUNT(*) AS count FROM appointments WHERE Customer_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, customerId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int appointmentCount = resultSet.getInt("count");
                        return appointmentCount > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
