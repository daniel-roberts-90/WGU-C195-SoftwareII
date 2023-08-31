package Controller;

import Data.AppointmentData;
import static Data.AppointmentData.deleteAppointment;
import static Data.AppointmentData.updateAppointment;
import Data.ContactData;
import Data.CustomerData;
import Data.UserData;
import Model.Appointments;
import Model.Contacts;
import Model.Customer;
import Model.User;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.*;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 * Handles the logic for the Appointments view.
 * @author Daniel Roberts
 */

public class AppointmentsController implements Initializable {

    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button backButton;
    @FXML
    private RadioButton weekRadio;
    @FXML
    private ToggleGroup appointment;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableColumn<AppointmentData, Integer> appID;
    @FXML
    private TableColumn<AppointmentData, Integer> appTitle;
    @FXML
    private TableColumn<AppointmentData, LocalDateTime> appStartDate;
    @FXML
    private TableColumn<AppointmentData, LocalDateTime> appEndDate;
    @FXML
    private TableColumn<AppointmentData, String> appDescription;
    @FXML
    private TableColumn<AppointmentData, Integer> appCustomerID;
    @FXML
    private TableColumn<AppointmentData, Integer> appUserID;
    @FXML
    private TableColumn<AppointmentData, String> appLocation;
    @FXML
    private TableColumn<AppointmentData, Integer> appContact;
    @FXML
    private TableColumn<AppointmentData, String> appType;
    @FXML
    private RadioButton allRadio;
    @FXML
    private RadioButton monthRadio;
    @FXML
    private TextField updateAppointmentID;
    @FXML
    private TextField updateAppTitle;
    @FXML
    private DatePicker updateAppStartDate;
    @FXML
    private DatePicker updateAppEndDate;
    @FXML
    private ComboBox<LocalTime> updateAppStartTime;
    @FXML
    private ComboBox<LocalTime> updateAppEndTime;
    @FXML
    private ComboBox<Contacts> updateAppContact;
    @FXML
    private TextField updateAppType;
    @FXML
    private TextField updateAppDescription;
    @FXML
    private TextField updateAppLocation;
    @FXML
    private TableView<Appointments> appointmentsTableView;
    @FXML
    private Button editButton;
    @FXML
    private ComboBox<Customer> updateCustomerID;
    @FXML
    private ComboBox<User> updateUserID;
    private Label titleLabel;

/**
 * Initializes the controller class.
 * Populates the table view
 * @param url The location to resolve relative paths for the root object.
 * @param rb The resource bundle that contains localized resources.
 */
    @Override
    public void initialize(java.net.URL url, ResourceBundle rb) {
        
        allRadio.setSelected(true);

        ObservableList<Appointments> allAppointments = null;
        try {
            allAppointments = AppointmentData.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        appointmentsTableView.setItems(allAppointments);

        appID.setCellValueFactory(new PropertyValueFactory<>("appID"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));
        appDescription.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appLocation.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appType.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        

        ObservableList<Customer> customers = null;
        try {
            customers = CustomerData.getAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        updateCustomerID.setItems(customers);
        updateCustomerID.getValue();


        ObservableList<User> users = null;
        try {
            users = UserData.getAllUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        updateUserID.setItems(users);
        updateUserID.getValue();


        ObservableList<Contacts> contacts = null;
        try {
            contacts = ContactData.getAllContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateAppContact.setItems(contacts);
        updateAppContact.getValue();


        LocalDate date = LocalDate.now();
        LocalTime startComboStart = LocalTime.of(8, 0);
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime startEST = ZonedDateTime.of(date, startComboStart, ZoneId.of("America/New_York"));
        ZonedDateTime startZDT = startEST.withZoneSameInstant(localZone);
        ZonedDateTime endZDT = startZDT.plusHours(14);

        while (startZDT.isBefore(endZDT)) {
            updateAppStartTime.getItems().add(startZDT.toLocalTime());
            startZDT = startZDT.plusMinutes(30);
            updateAppEndTime.getItems().add(startZDT.toLocalTime());

        }

        updateAppStartDate.setValue(date);
        updateAppEndDate.setValue(date);
    }
    
    /**
     * Action when "All" radio button is selected
     * Retrieves all appointments and displays them in the table view
     * @param event 
     */

    @FXML
    private void onAllRadio(ActionEvent event) {
        
         try {
            ObservableList<Appointments> allAppointments = AppointmentData.getAllAppointments();

            if (allAppointments != null)
                for (Appointments LocalAppointment : allAppointments) {
                    appointmentsTableView.setItems(allAppointments);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Action when "Month" radio button is selected
     * Retrieves appointments for the current month and displays them in
     * the table view
     * @param event 
     */

    @FXML
    private void onMonthRadio(ActionEvent event) {
        
        try {
            ObservableList<Appointments> allAppointmentsList = AppointmentData.getAllAppointments();
            ObservableList<Appointments> appointmentsMonth = FXCollections.observableArrayList();

            LocalDateTime MonthStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
            LocalDateTime MonthEnd = MonthStart.plusDays(30);


            if (allAppointmentsList != null)

                allAppointmentsList.forEach(LocalAppointment -> {
                    if (LocalAppointment.getEnd().isAfter(MonthStart) && LocalAppointment.getEnd().isBefore(MonthEnd)) {
                        appointmentsMonth.add(LocalAppointment);
                    }
                    appointmentsTableView.setItems(appointmentsMonth);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Action when "Week" radio button is selected
     * Retrieves appointments for the current week and displays them
     * in the table view
     * @param event 
     */

    @FXML
    private void onWeekRadio(ActionEvent event) {
        
        try {

            ObservableList<Appointments> allAppointmentsList = AppointmentData.getAllAppointments();
            ObservableList<Appointments> appointmentsWeek = FXCollections.observableArrayList();

            LocalDateTime weekStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
            LocalDateTime weekEnd = weekStart.plusWeeks(1);

            if (allAppointmentsList != null)

                allAppointmentsList.forEach(LocalAppointment -> {
                    if (LocalAppointment.getEnd().isAfter(weekStart) && LocalAppointment.getEnd().isBefore(weekEnd)) {
                        appointmentsWeek.add(LocalAppointment);
                    }
                    appointmentsTableView.setItems(appointmentsWeek);
                });
        } catch (Exception e) {
            e.printStackTrace();

        }
        
    }

    /**
     * Action when "Add" button is clicked
     * Retrieves data from the input fields
     * Validates the input data
     * If validation passes, adds appointment to the database
     * and displays it in the table view
     * throws error message if a field is left blank
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    @FXML
    private void onAddAppointment(ActionEvent event) throws SQLException, IOException {
        
        try {

            if (updateAppTitle.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a Title to the appointment.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String addTitle = updateAppTitle.getText();

            if (updateAppDescription.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a description to the appointment.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String addDescription = updateAppDescription.getText();

            if (updateAppLocation.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a Location to the appointment.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String addLocation = updateAppLocation.getText();

            if (updateAppContact.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a contact to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            int addContact = updateAppContact.getValue().getContactID();


            if (updateAppType.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a type to the appointment.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String addType = updateAppType.getText();

            if (updateAppStartTime.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a start time to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }

            if (updateAppEndTime.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add an end time to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }


            if (updateAppStartDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a start date to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }

            LocalDate addStartDate = updateAppStartDate.getValue();
            LocalDate addEndDate = updateAppEndDate.getValue();

            System.out.println(addStartDate + "cat");
            System.out.println(addEndDate + "cat");
            LocalDateTime addStart;
            LocalDateTime addEnd;
            addStart = LocalDateTime.of(addStartDate, updateAppStartTime.getValue());
            addEnd = LocalDateTime.of(addEndDate, updateAppEndTime.getValue());

            if (updateUserID.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a user to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            int addUser = updateUserID.getValue().getUserID();

            if (updateCustomerID.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a customer to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            int addCustomer = updateCustomerID.getValue().getCustomerID();

            if (dateStartChecker(addStartDate) || startTimeChecker(addStart, addEnd) || sameTimeChecker(addStart, addEnd) || endTimeChecker(addStart, addEnd) || checkAppointmentOverlapAdd(addStart, addEnd, addCustomer)) {
                System.out.println("check");    
            } 
            
             if (dateEndChecker(addStartDate, addEndDate) || startTimeChecker(addStart, addEnd) || sameTimeChecker(addStart, addEnd) || endTimeChecker(addStart, addEnd) || checkAppointmentOverlapAdd(addStart, addEnd, addCustomer)) {
                System.out.println("check");
             }
            
            else {
                AppointmentData.createAppointment(addTitle, addDescription, addLocation, addType, addStart, addEnd, addCustomer, addUser, addContact);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Appointment added successfully!");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
            }
            ObservableList<Appointments> allAppointments = null;
            try {
                allAppointments = AppointmentData.getAllAppointments();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            appointmentsTableView.setItems(allAppointments);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }

        
    }
    
    /**
     * Action when "Delete" button is clicked
     * Gets the selected appointment from the table
     * If no appointment is selected, throws error message
     * Confirms user wants to delete prior to deleting with a Confirmation warning
     * If user confirms deletion, appointment is removed from database and
     * table view is updated
     * @param event 
     */

    @FXML
    private void onDelete(ActionEvent event) {
        
         try {
            Appointments deleteAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();

            if (deleteAppointment == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("No appointment selected");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
            } else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setContentText("Are you sure you would like to remove the selected appointment?" + " "  + "with an ID of: " + deleteAppointment.getAppID() + "," + " " + "and an appointment type of:" + " " + deleteAppointment.getAppType());
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");
                alert.getButtonTypes().setAll(yesButton, noButton);
                Optional<ButtonType> result = alert.showAndWait();
                


                if (result.isPresent() && result.get() == yesButton) {
                    AppointmentData.deleteAppointment(deleteAppointment.getAppID());                 
                    alert.setTitle("Alert");
                    alert.setContentText("Appointment:" + " " + deleteAppointment.getAppID() + " " +  "With a type:" + " " + deleteAppointment.getAppType() + " " + " has been deleted!");
                    ButtonType okButton = new ButtonType("OK");
                    alert.getButtonTypes().setAll(okButton);
                    alert.showAndWait();
                }
            }
            ObservableList<Appointments> allAppointments = null;
            try {
                allAppointments = AppointmentData.getAllAppointments();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            appointmentsTableView.setItems(allAppointments);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Action when "update" button is clicked
     * Gets the selected appointment from table
     * retrieves data from input fields
     * Validates the input data and throws error is field is blank
     * If validation passes, appointment is updated in database and
     * refreshed in the table view
     * @param event 
     */

    @FXML
    private void onUpdate(ActionEvent event) {
        
        try {
            Appointments selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        
            if (selectedAppointment == null) {
                // No appointment is selected, show an error message or handle it accordingly
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select an appointment to update.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
        }
            
            int appID = appointmentsTableView.getSelectionModel().getSelectedItem().getAppID();
          
            if (updateAppTitle.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a Title to the appointment.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String updateTitle = updateAppTitle.getText();

            if (updateAppDescription.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a description to the appointment.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String updateDescription = updateAppDescription.getText();

            if (updateAppLocation.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a Location to the appointment.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String updateLocation = updateAppLocation.getText();

            if (updateAppContact.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a contact to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            int updateContact = updateAppContact.getValue().getContactID();


            if (updateAppType.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a type to the appointment.");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            String updateType = updateAppType.getText();

            if (updateAppStartTime.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a start time to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }

            if (updateAppEndTime.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add an end time to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }


            if (updateAppStartDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a start date to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            
            if (updateAppEndDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add an end date to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            
            LocalDate updateStartDate = updateAppStartDate.getValue();
            LocalDate updateEndDate = updateAppEndDate.getValue();
            LocalDateTime updateStart = LocalDateTime.of(updateStartDate, updateAppStartTime.getValue());
            LocalDateTime updateEnd = LocalDateTime.of(updateEndDate, updateAppEndTime.getValue());

            if (updateUserID.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a user to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            int updateUser = updateUserID.getValue().getUserID();

            if (updateCustomerID.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Please add a customer to the appointment");
                ButtonType okButton = new ButtonType("OK");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
                return;
            }
            int updateCustomer = updateCustomerID.getValue().getCustomerID();

            if (dateStartChecker(updateStartDate) || startTimeChecker(updateStart, updateEnd) || sameTimeChecker(updateStart, updateEnd) || endTimeChecker(updateStart, updateEnd) || checkAppointmentOverlapUpdate(updateStart, updateEnd, updateCustomer, appID )) {
                System.out.println("check check");
            }
            
            if (dateEndChecker(updateStartDate, updateEndDate) || startTimeChecker(updateStart, updateEnd) || sameTimeChecker(updateStart, updateEnd) || endTimeChecker(updateStart, updateEnd) || checkAppointmentOverlapUpdate(updateStart, updateEnd, updateCustomer, appID )) {
                System.out.println("check check");
     
            } 
            
                else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setContentText("Are you sure you would like to update the selected appointment?");
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");
                alert.getButtonTypes().setAll(yesButton, noButton);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == yesButton) {
                    
                    AppointmentData.updateAppointment(updateTitle, updateDescription, updateLocation, updateType, updateStart, updateEnd, updateCustomer, updateUser, updateContact, appID);                 
                    
                    alert.setTitle("ALERT");
                    alert.setContentText("Appointment updated successfully");
                    ButtonType okButton = new ButtonType("OK");
                    alert.getButtonTypes().setAll(okButton);
                    alert.showAndWait();

                }
            }
            ObservableList<Appointments> allAppointments = null;
            try {
                allAppointments = AppointmentData.getAllAppointments();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            appointmentsTableView.setItems(allAppointments);
        } catch (NumberFormatException | NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Action when "Edit" button is clicked
     * Gets the selected appointment from table
     * populates the input fields with the data from selected appointment and
     * allows for updates to be made to fields
     * @param event 
     */

    @FXML
    private void onEdit(ActionEvent event) {
        
        Appointments selectAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();

        if (selectAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("No appointment selected");
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
            
        } else {

            updateAppointmentID.setText(String.valueOf(selectAppointment.getAppID()));
            updateAppTitle.setText(selectAppointment.getAppTitle());
            updateAppDescription.setText(selectAppointment.getAppDescription());
            updateAppLocation.setText(selectAppointment.getAppLocation());
            updateAppType.setText(selectAppointment.getAppType());

            for (Customer c : updateCustomerID.getItems())
                if (selectAppointment.getCustomerID() == c.getCustomerID()) {
                    updateCustomerID.setValue(c);
                    break;
                }

            for (User u : updateUserID.getItems())
                if (selectAppointment.getUserID() == u.getUserID()) {
                    updateUserID.setValue(u);
                    break;
                }

            for (Contacts c : updateAppContact.getItems())
                if (selectAppointment.getContactID() == c.getContactID()) {
                    updateAppContact.setValue(c);
                    break;
                }

            updateAppStartTime.setValue(LocalTime.from(selectAppointment.getStart()));
            updateAppEndTime.setValue(LocalTime.from(selectAppointment.getEnd()));

            updateAppStartDate.setValue((selectAppointment.getStart().toLocalDate()));
            updateAppEndDate.setValue((selectAppointment.getEnd().toLocalDate()));
        }

    }
    
    /**
     * Action when "Back" button is clicked
     * Navigates back to the Main Menu screen
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
         * Ensures appointment is set on or after the current date
         * throws error message if date is in past
         * @param updateAppStartDate
         * @return 
         */
    
       private boolean dateStartChecker(LocalDate updateAppStartDate) {
        if (updateAppStartDate.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("Appointment must be set on, or after the current date.");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
     }
       /**
        * Ensures end date is on or after start date
        * throws error if not validated
        * @param updateAppStartDate
        * @param updateAppEndDate
        * @return 
        */
       
       private boolean dateEndChecker(LocalDate updateAppStartDate, LocalDate updateAppEndDate) {
        if (updateAppEndDate.isBefore(updateAppStartDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("Appointment end date cannot be set before appointment start date.");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
     }
       /**
        * Ensures appointment start time is on or after appointment end time
        * @param updateAppStartTime
        * @param updateAppEndTime
        * @return 
        */
       
       private boolean startTimeChecker(LocalDateTime updateAppStartTime, LocalDateTime updateAppEndTime) {
        if (updateAppStartTime.isAfter(updateAppEndTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("Appointment must start before the end time.");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
       } 
       /**
        * Ensures appointment start and end times are not the same
        * @param updateAppStartTime
        * @param updateAppEndTime
        * @return 
        */
       private boolean sameTimeChecker(LocalDateTime updateAppStartTime, LocalDateTime updateAppEndTime) {
        if (updateAppStartTime.isEqual(updateAppEndTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("Appointment start and end times cannot be the same.");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
    
       }
       
       /**
        * Ensure appointment end time is after appointment start time
        * throws error if not validated
        * @param updateAppStartTime
        * @param updateAppEndTime
        * @return 
        */
       
        private boolean endTimeChecker(LocalDateTime updateAppStartTime, LocalDateTime updateAppEndTime) {
        if (updateAppEndTime.isBefore(updateAppStartTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("Appointment must end after the start time.");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
    }
        
        /**
         * Checks if appointment overlaps with an existing appointment
         * @param Start
         * @param End
         * @param customerID
         * @return
         * @throws SQLException 
         */

    private boolean checkAppointmentOverlapAdd(LocalDateTime Start, LocalDateTime End, int customerID) throws SQLException {
        for (Appointments a : AppointmentData.getAllAppointments()) {
            if (a.getCustomerID() != customerID) {
                continue;
            }
            if ((Start.isAfter(a.getStart()) || Start.isEqual(a.getStart())) && (Start.isBefore(a.getEnd()))) { //1
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Start time cannot overlap a current appointment.");
                alert.showAndWait();
                return true;
            }
            if ((End.isAfter(a.getStart())) && (End.isBefore(a.getEnd()) || End.isEqual(a.getEnd()))) { //2
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("End time cannot overlap a current appointment.");
                alert.showAndWait();
                return true;
            }
            if ((Start.isBefore(a.getStart()) || Start.isEqual(a.getStart())) && (End.isAfter(a.getEnd()) || End.isEqual(a.getEnd()))) { //3
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Start and end times overlap a current appointment.");
                alert.showAndWait();
                return true;
            }
        }
      return false;
    }
    
    /**
     * Checks if updated appointment overlaps with an existing appointment
     * @param Start
     * @param End
     * @param customerID
     * @param appID
     * @return
     * @throws SQLException 
     */
    private boolean checkAppointmentOverlapUpdate(LocalDateTime Start, LocalDateTime End, int customerID, int appID) throws SQLException {
        for (Appointments a : AppointmentData.getAllAppointments()) {
            if (a.getCustomerID() != customerID || a.getAppID() == appID) {
                continue;
            }
            if ((Start.isAfter(a.getStart()) || Start.isEqual(a.getStart())) && (Start.isBefore(a.getEnd()))) { //1
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Start time cannot overlap a current appointment.");
                alert.showAndWait();
                return true;
            }
            if ((End.isAfter(a.getStart())) && (End.isBefore(a.getEnd()) || End.isEqual(a.getEnd()))) { //2
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("End time cannot overlap a current appointment.");
                alert.showAndWait();
                return true;
            }
            if ((Start.isBefore(a.getStart()) || Start.isEqual(a.getStart())) && (End.isAfter(a.getEnd()) || End.isEqual(a.getEnd()))) { //3
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setContentText("Start and end times overlap a current appointment.");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }
       
}
