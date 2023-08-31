package Model;
/**
 * @author Daniel Roberts
 */

/**
 * Represents a report entity in the application.
 * Each report contains data for country count, country name, appointment month, and appointment total.
 */
public class Report {
    private int countryCount;
    private String countryName;
    public String appMonth;
    public int appTotal;

    /**
     * Constructs a Report object with the provided data.
     *
     * @param countryCount The count of countries in the report.
     * @param countryName The name of the country in the report.
     */
    public Report(int countryCount, String countryName) {
        this.countryCount = countryCount;
        this.countryName = countryName;
    }

    /**
     * Gets the name of the country in the report.
     *
     * @return The name of the country in the report.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Gets the count of countries in the report.
     *
     * @return The count of countries in the report.
     */
    public int getCountryCount() {
        return countryCount;
    }

    /**
     * Gets the appointment month in the report.
     *
     * @return The appointment month in the report.
     */
    public String getAppMonth() {
        return appMonth;
    }

    /**
     * Gets the total number of appointments in the report.
     *
     * @return The total number of appointments in the report.
     */
    public int getAppointmentTotal() {
        return appTotal;
    }
}