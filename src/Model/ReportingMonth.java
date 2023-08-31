package Model;
/**
 * @author Daniel Roberts
 */

/**
 * Represents a reporting month entity in the application.
 * Each reporting month contains data for the appointment month and appointment total.
 */
public class ReportingMonth {
    public String appMonth;
    public int appTotal;

    /**
     * Constructs a ReportingMonth object with the provided data.
     *
     * @param appMonth The appointment month.
     * @param appTotal The total number of appointments for the month.
     */
    public ReportingMonth(String appMonth, int appTotal) {
        this.appMonth = appMonth;
        this.appTotal = appTotal;
    }

    /**
     * Gets the appointment month.
     *
     * @return The appointment month.
     */
    public String getAppMonth() {
        return appMonth;
    }

    /**
     * Gets the total number of appointments for the month.
     *
     * @return The total number of appointments for the month.
     */
    public int getAppTotal() {
        return appTotal;
    }
}