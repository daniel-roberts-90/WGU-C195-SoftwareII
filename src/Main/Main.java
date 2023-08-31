package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Daniel Roberts
 * Main Class - extends Application class to create Java App
 */
public class Main extends Application{
     /**
     * Starting point of application.Initializes the database connection
     * Launches and closes the JavaFX application/Database Connection
     * @param args
     */

    public static void main(String[] args) {
        
        DataConnect.startConnection();
        launch(args);
        DataConnect.closeConnection();

        
    }
    
    /**
     * Loads the user interface from FXML file
     * Creates a new scene - sets scene - sets scene title - shows the primary scene to display the GUI
     * @param stage
     * @throws Exception 
     */

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Global Consulting INC.");
        stage.show();
    }
    
}
