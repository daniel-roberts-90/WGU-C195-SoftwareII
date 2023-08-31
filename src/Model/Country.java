package Model;


/**
 * 
 * @author Daniel Roberts
 */

/**
 * Represents a country entity in the application.
 * Each country has an ID and a name.
 */
public class Country {

    private String countryName;
    private int countryID;
    
    /**
     * Constructs a Country object with the provided data.
     *
     * @param countryID The ID of the country.
     * @param countryName The name of the country.
     */

    public Country(int countryID,String countryName ) {

        this.countryName = countryName;
        this.countryID = countryID;
    }

    /**
     * Gets the name of the country.
     *
     * @return The name of the country.
     */
    public String getCountryName() {

        return countryName;
    }
    /**
     * Gets the ID of the country.
     *
     * @return The ID of the country.
     */
    public int getCountryID(){

        return countryID;
    }

/**
 * Returns a string representation of the country in the format "countryID - countryName".
 *
 * @return A string representation of the country.
 */
@Override
    public String toString() {
        return countryID + " - " + countryName;
    }

}