package Model;

/**
 * Represents a user entity in the application.
 * Each user has an ID, username, and password.
 */
public class User {

    public int userID;
    public String userName;
    public String userPassword;

    /**
     * Constructs a User object with the provided data.
     *
     * @param userID The ID of the user.
     * @param userName The username of the user.
     * @param userPassword The password of the user.
     */
    public User(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * Gets the ID of the user.
     *
     * @return The ID of the user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Returns a string representation of the user in the format "userID - userName".
     *
     * @return A string representation of the user.
     */
    @Override
    public String toString() {
        return userID + "-" + userName;
    }
}