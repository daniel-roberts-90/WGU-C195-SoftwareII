package Data;

import Main.DataConnect;
import Model.Appointments;
import Model.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A utility class to generate reports from appointment data in the database.
 */
public class ReportData extends Appointments {

    /**
     * Constructor for the ReportData class.
     *
     * @param appID          The ID of the appointment.
     * @param appTitle       The title of the appointment.
     * @param appDescription The description of the appointment.
     * @param appLocation    The location of the appointment.
     * @param appType        The type of the appointment.
     * @param start          The start date and time of the appointment.
     * @param end            The end date and time of the appointment.
     * @param customerID     The ID of the customer associated with the appointment.
     * @param userID         The ID of the user associated with the appointment.
     * @param contactID      The ID of the contact associated with the appointment.
     */
    public ReportData(int appID, String appTitle, String appDescription, String appLocation, String appType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        super(appID, appTitle, appDescription, appLocation, appType, start, end, customerID, userID, contactID);
    }

    /**
     * Generates a report with the count of appointments per country.
     *
     * @return A list of Report objects containing the count of appointments per country.
     * @throws SQLException if an SQL exception occurs during database retrieval.
     */
    public static ObservableList<Report> getCountries() throws SQLException {
        ObservableList<Report> countriesObservableList = FXCollections.observableArrayList();
        String query = "SELECT countries.Country, COUNT(*) AS countryCount FROM customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "INNER JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID " +
                "GROUP BY first_level_divisions.Country_ID " +
                "ORDER BY COUNT(*) DESC";
        PreparedStatement ps = DataConnect.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            // Retrieve data from the result set and create a Report object for each country count.
            String countryName = rs.getString("Country");
            int countryCount = rs.getInt("countryCount");
            Report report = new Report(countryCount, countryName);
            countriesObservableList.add(report);
        }
        return countriesObservableList;
    }
}
