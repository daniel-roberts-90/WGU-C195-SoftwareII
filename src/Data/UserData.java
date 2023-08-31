package Data;

import Main.DataConnect;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A utility class to handle user data and authentication.
 * This class provides methods to verify user credentials and retrieve all users from the database.
 */
public class UserData {

    /**
     * Constructor for the UserData class.
     * This constructor is not used in the current implementation.
     * It's better to have a default constructor without arguments to avoid issues.
     *
     * @param userID       The ID of the user.
     * @param userName     The username of the user.
     * @param userPassword The password of the user.
     */
    public UserData(int userID, String userName, String userPassword) {
        super(); // This constructor is not used and can be removed.
    }

    /**
     * Verifies user credentials by checking the provided username and password against the database.
     *
     * @param username The username of the user to be verified.
     * @param password The password of the user to be verified.
     * @return The user ID if the username and password match a user in the database, otherwise returns -1.
     */
    public static int verifyUser(String username, String password) {
        try {
            String query = "SELECT * FROM users ";
            PreparedStatement ps = DataConnect.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("User_Name").equals(username)) {
                    if (rs.getString("Password").equals(password)) {
                        return rs.getInt("User_ID");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return An ObservableList containing all User objects retrieved from the database.
     * @throws SQLException if an SQL exception occurs during database retrieval.
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> usersObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT u.User_ID, u.User_Name, u.Password " +
                    "FROM users AS u ";
            PreparedStatement ps = DataConnect.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Retrieve data from the result set and create a User object for each user.
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                User user = new User(userID, userName, userPassword);
                usersObservableList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersObservableList;
    }
}
