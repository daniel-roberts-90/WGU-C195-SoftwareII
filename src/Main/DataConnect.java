package Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Daniel Roberts
 * Creating variables representing database connection details
 * 
 */

public class DataConnect {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String database = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + 
            location + database + "?connectionTimeZone = SERVER";
    private static final String SQLdriver = "com.mysql.cj.jdbc.Driver"; 
    private static final String username = "sqlUser"; 
    private static String password = "Passw0rd!";
    public static Connection connection;  
    
    /**
     * Starts database connection - Prints to command line if connection
     * is successful or if error occurs
     */
    
    public static void  startConnection() {

        try {
            Class.forName(SQLdriver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection successful!");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error:" + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Closes database connection - prints to command line if connection 
     * successfully closes or if error occurs
     */
    
    
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}