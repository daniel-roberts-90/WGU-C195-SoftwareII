package Controller;

import Data.AppointmentData;
import Data.UserData;
import Model.Appointments;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Handles the logic for user login and displaying upcoming appointments
 * @author Daniel Roberts
 */
public class LoginController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label testLabel;
    /**
     * Initializes the controller class.
     * Sets default locale
     * gets system time zone
     * displays zone information in label
     * Loads resource bundle for language localization and 
     * sets text for labels and buttons to english or french depending on system location
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
         try
        {

            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);

            ZoneId zone = ZoneId.systemDefault();

            testLabel.setText(String.valueOf(zone));
            
            rb = ResourceBundle.getBundle("Resources/Language", Locale.getDefault());
            titleLabel.setText(rb.getString("titleLabel"));
            usernameLabel.setText(rb.getString("userNameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            loginButton.setText(rb.getString("loginButton"));
            exitButton.setText(rb.getString("exitButton"));

        } catch(MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    
    }   
    
    /**
     * Action when "Login" button is clicked
     * gets all appointments from the database
     * gets system time and checks for appointments plus or minus 15 minutes
     * gets resource bundle for language localization
     * verifies user credentials and throws an error message if failed
     * Creates a file writer to track login activity for successful and failed attempts
     * If credentials are valid, navigates to Main Menu page and
     * gives message whether or not an appointment is within plus or minus 15 minutes
     * @param event
     * @throws IOException
     * @throws Exception 
     */

    @FXML
    private void onLogin(ActionEvent event) throws IOException, Exception {
        
       try {

            ObservableList<Appointments> getAllAppointments = AppointmentData.getAllAppointments();
            LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
            LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime;
            int getAppID = 0;
            LocalDateTime displayTime = null;
            boolean appointmentWithin15Min = false;

            ResourceBundle rb = ResourceBundle.getBundle("Resources/Language", Locale.getDefault());

            String username = usernameField.getText();
            String password = passwordField.getText();
            int userId = UserData.verifyUser(username, password);

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            
           try (PrintWriter outputFile = new PrintWriter(fileWriter)) {
               if (userId > 0) {
                   
                    Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                   
                   outputFile.print("User: " + username + " login successful at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                   
                   for (Appointments appointment : getAllAppointments) {
                       startTime = appointment.getStart();
                       if ((startTime.isAfter(currentTimeMinus15Min) || startTime.isEqual(currentTimeMinus15Min)) && (startTime.isBefore(currentTimePlus15Min) || (startTime.isEqual(currentTimePlus15Min)))) {
                           getAppID = appointment.getAppID();
                           displayTime = startTime;
                           appointmentWithin15Min = true;
                       }
                   }
                   if (appointmentWithin15Min != false) {
                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " + getAppID + " and appointment start time of: " + displayTime);
                       ButtonType okButton = new ButtonType("OK");
                       alert.getButtonTypes().setAll(okButton);
                       Optional<ButtonType> result = alert.showAndWait();
                       System.out.println(rb.getString("Appointment"));
                   } else {
                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                       ButtonType okButton = new ButtonType("OK");
                       alert.getButtonTypes().setAll(okButton);
                       Optional<ButtonType> result = alert.showAndWait();
                       System.out.println(rb.getString("None"));
                   }
               } else if (userId < 0) {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle(rb.getString("Warning"));
                   alert.setContentText(rb.getString("Incorrect"));
                   ButtonType okButton = new ButtonType("OK");
                   alert.getButtonTypes().setAll(okButton);
                   Optional<ButtonType> result = alert.showAndWait();
                   
                   outputFile.print("User: " + username + " login attempt failed at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
               }
           }
        } catch (IOException | SQLException ioException) {
            ioException.printStackTrace();
        }
        
    }
    
    /**
     * declares stage
     */

    Stage stage;
    /**
     * Confirms whether user wants to exit the program
     * if confirmed, program is closed
     * @param event 
     */
    @FXML
    private void onExit(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Are you sure you want to exit?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == yesButton) { 
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
    }
  }       
}
