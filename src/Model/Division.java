package Model;
/**
 * @author Daniel Roberts
 */

/**
 * Represents a division entity in the application.
 * Each division has an ID, name, and associated country ID.
 */
public class Division {
    
    private int divID;
    private String divName;
    public int countryID;

    /**
     * Constructs a Division object with the provided data.
     *
     * @param divID The ID of the division.
     * @param divName The name of the division.
     * @param countryID The ID of the country associated with the division.
     */
    public Division(int divID, String divName, int countryID) {
        this.divID = divID;
        this.divName = divName;
        this.countryID = countryID;
    }

    /**
     * Gets the ID of the division.
     *
     * @return The ID of the division.
     */
    public int getDivID() {
        return divID;
    }

    /**
     * Gets the name of the division.
     *
     * @return The name of the division.
     */
    public String getDivName() {
        return divName;
    }

    /**
     * Gets the ID of the country associated with the division.
     *
     * @return The ID of the country associated with the division.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Returns a string representation of the division in the format "divID - divName".
     *
     * @return A string representation of the division.
     */
    @Override
    public String toString() {
        return divID + "-" + divName;
    }
}

