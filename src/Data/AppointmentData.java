package Data;

import Model.Appointments;
import Main.DataConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.sql.*;


public class AppointmentData {

    /**
     * Retrieves all appointments from the database.
     *
     * @return A list of all appointments.
     * @throws SQLException if there's an issue executing the SQL query.
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            // SQL query to retrieve appointments along with customer, user, and contact information.
            String query = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID " +
                    "FROM appointments AS a " +
                    "INNER JOIN customers AS c ON a.Customer_ID = c.Customer_ID " +
                    "INNER JOIN users AS u ON a.User_ID = u.User_ID " +
                    "INNER JOIN contacts AS con ON a.Contact_ID = con.Contact_ID";
            System.out.println(query);
            PreparedStatement ps = DataConnect.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Retrieve data from the result set and create an Appointments object for each record.
                int appID = rs.getInt("Appointment_ID");
                String appTitle = rs.getString("Title");
                String appDescription = rs.getString("Description");
                String appLocation = rs.getString("Location");
                String appType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments appointment = new Appointments(appID, appTitle, appDescription, appLocation, appType, start, end, customerID, userID, contactID);
                appointmentsList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * Creates a new appointment in the database.
     *
     * @param appTitle The title of the appointment.
     * @param appDescription The description of the appointment.
     * @param appLocation The location of the appointment.
     * @param appType The type of the appointment.
     * @param start The start date and time of the appointment.
     * @param end The end date and time of the appointment.
     * @param customerID The ID of the customer associated with the appointment.
     * @param userID The ID of the user associated with the appointment.
     * @param contactID The ID of the contact associated with the appointment.
     */
    public static void createAppointment(String appTitle, String appDescription, String appLocation, String appType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        try {
            // SQL query to insert a new appointment into the database.
            String query = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?, ?,?,?) ";

            PreparedStatement ps = DataConnect.connection.prepareStatement(query);

            ps.setString(1, appTitle);
            ps.setString(2, appDescription);
            ps.setString(3, appLocation);
            ps.setString(4, appType);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            System.out.println(ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates an existing appointment in the database.
     *
     * @param appTitle The updated title of the appointment.
     * @param appDescription The updated description of the appointment.
     * @param appLocation The updated location of the appointment.
     * @param appType The updated type of the appointment.
     * @param start The updated start date and time of the appointment.
     * @param end The updated end date and time of the appointment.
     * @param customerID The updated ID of the customer associated with the appointment.
     * @param userID The updated ID of the user associated with the appointment.
     * @param contactID The updated ID of the contact associated with the appointment.
     * @param appointmentID The ID of the appointment to be updated.
     */
    public static void updateAppointment(String appTitle, String appDescription, String appLocation, String appType, LocalDateTime Start, LocalDateTime End, int customerID, int userID, int contactID, int appID) {
        try {
            // SQL query to update an existing appointment in the database.
            String query = "UPDATE appointments SET Title = ?, Description = ? ,  Location = ?, Type = ?," +
                    " Start = ?, End = ? , Customer_ID = ? , User_ID = ? , Contact_ID = ? WHERE Appointment_ID = ? ";
            PreparedStatement ps = DataConnect.connection.prepareStatement(query);

            ps.setString(1, appTitle);
            ps.setString(2, appDescription);
            ps.setString(3, appLocation);
            ps.setString(4, appType);
            ps.setTimestamp(5, Timestamp.valueOf(Start));
            ps.setTimestamp(6, Timestamp.valueOf(End));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setInt(10, appID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Deletes an appointment from the database.
     *
     * @param appID The ID of the appointment to be deleted.
     * @throws SQLException if there's an issue executing the SQL query.
     */
    public static void deleteAppointment(int appID) throws SQLException {
        try {
            // SQL query to delete an appointment from the database.
            String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DataConnect.connection.prepareStatement(query);
            ps.setInt(1, appID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}