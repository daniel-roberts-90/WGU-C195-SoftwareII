package Controller;

import Data.AppointmentData;
import Data.ContactData;
import Data.ReportData;
import Model.Appointments;
import Model.Contacts;
import Model.Report;
import Model.ReportingMonth;
import Model.ReportingType;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

/**
 * FXML Controller class
 *
 * @author DanielAccount
 */
public class ReportController {

    @FXML
    private Button backButton;
    @FXML
    private Tab scheduleTab;
    @FXML
    private TableView<Appointments> scheduleTableView;
    @FXML
    private TableColumn<?, ?> IDColSched;
    @FXML
    private TableColumn<?, ?> titleColSched;
    @FXML
    private TableColumn<?, ?> typeColSched;
    @FXML
    private TableColumn<?, ?> descriptionColSched;
    @FXML
    private TableColumn<?, ?> startColSched;
    @FXML
    private TableColumn<?, ?> endColSched;
    @FXML
    private TableColumn<?, ?> customerIDColSched;
    @FXML
    private TableColumn<?, ?> locationColSched;
    @FXML
    private Tab totalsTab;
    @FXML
    private TableView<ReportingType> totalsTypeTableView;
    @FXML
    private TableColumn<?, ?> typeColTotals;
    @FXML
    private TableColumn<?, ?> typeTotalColTotals;
    @FXML
    private TableView<ReportingMonth> totalsMonthTableView;
    @FXML
    private TableColumn<?, ?> monthColTotals;
    @FXML
    private TableColumn<?, ?> monthTotalColTotals;
    @FXML
    private Tab customerCountryTab;
    @FXML
    private TableView<Report> customerCountryTableView;
    @FXML
    private TableColumn<?, ?> customerCountryCol;
    @FXML
    private TableColumn<?, ?> customerTotalCol;
    @FXML
    private ComboBox<String> contactCombo;

    /**
     * Sets up table and loads contact names into combo box
     * Initializes the controller class.
     * @throws java.sql.SQLException
     */
   
    public void initialize() throws SQLException{
        
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        customerTotalCol.setCellValueFactory(new PropertyValueFactory<>("countryCount"));
        IDColSched.setCellValueFactory(new PropertyValueFactory<>("appID"));
        titleColSched.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        descriptionColSched.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        locationColSched.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        typeColSched.setCellValueFactory(new PropertyValueFactory<>("appType"));
        startColSched.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColSched.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColSched.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        typeColTotals.setCellValueFactory(new PropertyValueFactory<>("appType"));
        typeTotalColTotals.setCellValueFactory(new PropertyValueFactory<>("appTotal"));
        monthColTotals.setCellValueFactory(new PropertyValueFactory<>("appMonth"));
        monthTotalColTotals.setCellValueFactory(new PropertyValueFactory<>("appTotal"));

        ObservableList<Contacts> contactsObservableList = ContactData.getAllContacts();
        ObservableList<String> allContactsName = FXCollections.observableArrayList();
        contactsObservableList.forEach(contacts -> allContactsName.add(contacts.getContactName()));
        contactCombo.setItems(allContactsName);
        
    } 
    
    /**
     * Action when "Back" button is clicked
     * navigates back to Main Menu
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
     * Loads appointments for selected contact
     * @param event 
     */

    @FXML
    private void onScheduleTab(Event event) {
        
        try {
        int contactID = 0;
        ObservableList<Appointments> getAllAppointmentData = AppointmentData.getAllAppointments();
        ObservableList<Appointments> appointmentData = FXCollections.observableArrayList();
        ObservableList<Contacts> getAllContacts = ContactData.getAllContacts();
        Appointments contactAppointmentData;

        String contactName = contactCombo.getSelectionModel().getSelectedItem();

        if (contactName != null) {
            for (Contacts contact : getAllContacts) {
                if (contactName.equals(contact.getContactName())) {
                    contactID = contact.getContactID();
                }
            }

            for (Appointments appointment : getAllAppointmentData) {
                if (appointment.getContactID() == contactID) {
                    contactAppointmentData = appointment;
                    appointmentData.add(contactAppointmentData);
                }
            }
            scheduleTableView.setItems(appointmentData);
        } else {
            // Handle the case when no contact is selected
            // You can clear the table or show a message to the user
            scheduleTableView.setItems(FXCollections.emptyObservableList());
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
}
    /**
     * Calculates and displays total appointments by type and month
     * @param event 
     */

    @FXML
    private void onTotalsTab(Event event) {
        
            try {
            ObservableList<Appointments> getAllAppointments = AppointmentData.getAllAppointments();
            ObservableList<Month> appMonth = FXCollections.observableArrayList();
            ObservableList<Month> eachAppMonth = FXCollections.observableArrayList();

            ObservableList<String> appType = FXCollections.observableArrayList();
            ObservableList<String> uniqueAppointment = FXCollections.observableArrayList();

            ObservableList<ReportingType> reportOfType = FXCollections.observableArrayList();
            ObservableList<ReportingMonth> reportMonth = FXCollections.observableArrayList();


            /**
             * Lambda - adds appointments to the report by type of appointment
             * This lambda statement iterates through each appointment in the list of all appointments (getAllAppointments).
             * For each appointment (represented by the parameter "appointments"), it retrieves the appointment type using the
             * "getAppType()" method from the Appointments class.
             * The retrieved appointment type is then added to the "appType" list, which collects all the appointment types.
             */
            getAllAppointments.forEach(appointments -> {
                appType.add(appointments.getAppType());
            });

            /**
             * Lambda #2 that gives the added results of the appointment by the Local date time of months.
             * This lambda statement creates a stream of appointments from the list of all appointments (getAllAppointments).
             * For each appointment in the stream (represented by the parameter "appointment"), it retrieves the start date of
             * the appointment using the "getStart()" method from the Appointments class.
             * From the start date, it then extracts the month using the "getMonth()" method.
             * The extracted month is then added to the "appMonth" list, which collects all the months from the appointments.
             */
            getAllAppointments.stream().map(appointment -> {
                return appointment.getStart().getMonth();
            }).forEach(appMonth::add);

            /**
             * Lambda #3 assigns key value pairs and filters the appointments by months for the report.
             * This lambda statement creates a stream from the "appMonth" list, which contains all the months
             * extracted from the appointments.
             * For each month in the stream (represented by the parameter "month"), it checks if the "eachAppMonth"
             * list, which contains distinct months, does not already contain the current month.
             * If the current month is not present in the "eachAppMonth" list, it means it's a new and unique month,
             * so it adds the current month to the "eachAppMonth" list.
             * This operation filters out any duplicate months and retains only distinct months in the list.
             */
            appMonth.stream().filter(month -> {
                return !eachAppMonth.contains(month);
            }).forEach(eachAppMonth::add);

            for (Appointments appointments: getAllAppointments) {
                String appointmentsAppointmentType = appointments.getAppType();
                if (!uniqueAppointment.contains(appointmentsAppointmentType)) {
                    uniqueAppointment.add(appointmentsAppointmentType);
                }
            }

            for (Month month: eachAppMonth) {
                int totalMonth = Collections.frequency(appMonth, month);
                String monthName = month.name();
                ReportingMonth appointmentMonth = new ReportingMonth(monthName, totalMonth);
                reportMonth.add(appointmentMonth);
            }
            totalsMonthTableView.setItems(reportMonth);

            for (String type: uniqueAppointment) {
                String typeToSet = type;
                int typeTotal = Collections.frequency(appType, type);
                ReportingType appointmentTypes = new ReportingType(typeToSet, typeTotal);
                reportOfType.add(appointmentTypes);
            }
            totalsTypeTableView.setItems(reportOfType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    /**
     * Displays table with the count of customers in each country
     * @param event 
     */

    @FXML
    private void onCustomerCountryTab(Event event) {
        
         try {

            ObservableList<Report> countryCollection = ReportData.getCountries();
            ObservableList<Report> addCountries = FXCollections.observableArrayList();


            countryCollection.forEach(addCountries::add);

            customerCountryTableView.setItems(addCountries);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
    }
    /**
     * Loads appointments for the selected contact into the table
     * @param event 
     */

    @FXML
    private void onContact(ActionEvent event) {
        
      try {

            int contactID = 0;

            ObservableList<Appointments> getAllAppointmentData = AppointmentData.getAllAppointments();
            ObservableList<Appointments> appointmentData = FXCollections.observableArrayList();
            ObservableList<Contacts> getAllContacts = ContactData.getAllContacts();

            Appointments contactAppointmentData;

            String contactName = contactCombo.getSelectionModel().getSelectedItem();

            for (Contacts contact: getAllContacts) {
                if (contactName.equals(contact.getContactName())) {
                    contactID = contact.getContactID();
                }
            }

            for (Appointments appointment: getAllAppointmentData) {
                if (appointment.getContactID() == contactID) {
                    contactAppointmentData = appointment;
                    appointmentData.add(contactAppointmentData);
                }
            }
            scheduleTableView.setItems(appointmentData);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }   
        
    }
    
}
