package Model;

/**
 * Represents a reporting type entity in the application.
 * Each reporting type contains data for the appointment type and appointment total.
 */
public class ReportingType {
    public String appType;
    public int appTotal;

    /**
     * Constructs a ReportingType object with the provided data.
     *
     * @param appType The appointment type.
     * @param appTotal The total number of appointments for the type.
     */
    public ReportingType(String appType, int appTotal) {
        this.appType = appType;
        this.appTotal = appTotal;
    }

    /**
     * Gets the appointment type.
     *
     * @return The appointment type.
     */
    public String getAppType() {
        return appType;
    }

    /**
     * Gets the total number of appointments for the type.
     *
     * @return The total number of appointments for the type.
     */
    public int getAppTotal() {
        return appTotal;
    }
}