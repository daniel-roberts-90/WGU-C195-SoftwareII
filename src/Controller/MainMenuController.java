package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Handles navigation to different views - Appointments, Customer, Reports
 * @author Daniel Roberts
 */
public class MainMenuController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button customerButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button logoutButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    /**
     * Action when "Appointments" button is clicked
     * Loads the Appointments view and switches scenes
     * @param event
     * @throws IOException 
     */

    @FXML
    private void onAppointments(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
      /**
     * Action when "Appointments" button is clicked
     * Loads the Appointments view and switches scenes
     * @param event
     * @throws IOException 
     */

    @FXML
    private void onCustomer(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show(); 
        
    }

      /**
     * Action when "Appointments" button is clicked
     * Loads the Appointments view and switches scenes
     * @param event
     * @throws IOException 
     */
    
    @FXML
    private void onReports(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
      /**
     * Action when "Logout" button is clicked
     * Throws a confirmation message to ensure user wishes to logout of program
     * If the result is YES, program logs out and returns to Login view
     * @param event
     * @throws IOException 
     */

    @FXML
    private void onLogout(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setContentText("Are you sure you want to logout?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == yesButton) { 
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
  }   
}
