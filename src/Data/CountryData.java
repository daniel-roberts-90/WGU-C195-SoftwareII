package Data;

import Main.DataConnect;
import Model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A utility class to manage countries data from the database.
 * Extends the Country class to inherit countryID and countryName fields.
 */
public class CountryData extends Country {

    /**
     * Constructor to create a CountryData object with countryID and countryName.
     *
     * @param countryID   The ID of the country.
     * @param countryName The name of the country.
     */
    public CountryData(int countryID, String countryName) {
        super(countryID, countryName);
    }

    /**
     * Retrieves all countries from the database.
     *
     * @return A list of all countries as CountryData objects.
     */
    public static ObservableList<CountryData> getAllCountries() {
        ObservableList<CountryData> countriesObservableList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM countries";
            PreparedStatement ps = DataConnect.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Retrieve data from the result set and create a CountryData object for each record.
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                CountryData country = new CountryData(countryID, countryName);
                countriesObservableList.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countriesObservableList;
    }
}
