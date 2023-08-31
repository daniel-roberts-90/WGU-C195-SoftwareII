package Data;

import Main.DataConnect;
import Model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A utility class to manage division data from the database.
 */
public class DivisionData extends Division {

    /**
     * Constructor for the DivisionData class.
     * @param divID The ID of the division.
     * @param divName The name of the division.
     * @param country_ID The ID of the country the division belongs to.
     */
    public DivisionData(int divID, String divName, int country_ID) {
        super(divID, divName, country_ID);
    }

    /**
     * Retrieves all divisions from the database.
     *
     * @return A list of all divisions as Division objects.
     * @throws SQLException if an SQL exception occurs during database retrieval.
     */
    public static ObservableList<Division> getAllDivisions() throws SQLException {
        ObservableList<Division> DivisionsObservableList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * from first_level_divisions";
            PreparedStatement ps = DataConnect.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Retrieve data from the result set and create a Division object for each record.
                int divID = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                int country_ID = rs.getInt("Country_ID");
                Division division = new Division(divID, divName, country_ID);
                DivisionsObservableList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DivisionsObservableList;
    }

    /**
     * Retrieves all divisions from the database filtered by country ID.
     *
     * @param countryID The ID of the country to filter the divisions.
     * @return A list of divisions filtered by country as Division objects.
     * @throws SQLException if an SQL exception occurs during database retrieval.
     */
    public static ObservableList<Division> getAllFilteredDivisions(int countryID) throws SQLException {
        ObservableList<Division> filteredDivisionsObservableList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * from first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = DataConnect.connection.prepareStatement(query);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Retrieve data from the result set and create a Division object for each record.
                int divID = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                int country_ID = rs.getInt("Country_ID");
                Division division = new Division(divID, divName, country_ID);
                filteredDivisionsObservableList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return filteredDivisionsObservableList;
    }

}
