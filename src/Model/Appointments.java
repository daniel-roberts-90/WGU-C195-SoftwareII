package Model;

import java.time.LocalDateTime;

/**
 * @author DanielAccount
 * Represents an appointment entity in the application.
 * Each appointment has an ID, title, description, location, type, start and end date/time,
 * associated customer ID, user ID, and contact ID. 
 */
public class Appointments {
    
    private int appID;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private LocalDateTime start;
    private LocalDateTime end;
    public int customerID;
    public int userID;
    public int contactID;
    
    /**
     * Constructs an Appointment object with the provided data.
     *
     * @param appID The ID of the appointment.
     * @param appTitle The title of the appointment.
     * @param appDescription The description of the appointment.
     * @param appLocation The location of the appointment.
     * @param appType The type/category of the appointment.
     * @param start The start date and time of the appointment.
     * @param end The end date and time of the appointment.
     * @param customerID The ID of the associated customer for the appointment.
     * @param userID The ID of the user who created the appointment.
     * @param contactID The ID of the contact associated with the appointment.
     */
    
    
    public Appointments(int appID, String appTitle, String appDescription,
            String appLocation, String appType, LocalDateTime start, LocalDateTime end,
            int customerID, int userID, int contactID){
    
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }  
            
    /**
     * Gets the title of the appointment.
     *
     * @return The title of the appointment.
     */        
    public String getAppTitle(){
        return appTitle;
    }
    /**
     * Gets the description of the appointment.
     *
     * @return The description of the appointment.
     */
    public String getAppDescription(){
        return appDescription;
    }
     /**
     * Gets the ID of the appointment.
     *
     * @return The ID of the appointment.
     */
    public int getAppID(){
        return appID;
    }
    /**
     * Gets the type/category of the appointment.
     *
     * @return The type/category of the appointment.
     */
    public String getAppType(){
    return appType;
}
     /**
     * Gets the location of the appointment.
     *
     * @return The location of the appointment.
     */
    public String getAppLocation(){
        return appLocation;
    }
     /**
     * Gets the start date and time of the appointment.
     *
     * @return The start date and time of the appointment.
     */
    public LocalDateTime getStart(){
        return start;
    }
    /**
     * Gets the end date and time of the appointment.
     *
     * @return The end date and time of the appointment.
     */
    public LocalDateTime getEnd(){
        return end;
    }
    /**
     * Gets the ID of the associated customer for the appointment.
     *
     * @return The ID of the associated customer for the appointment.
     */
    public int getCustomerID(){
        return customerID;
    }
    /**
     * Gets the ID of the user who created the appointment.
     *
     * @return The ID of the user who created the appointment.
     */
    public int getUserID(){
        return userID;
    }
     /**
     * Gets the ID of the contact associated with the appointment.
     *
     * @return The ID of the contact associated with the appointment.
     */
    public int getContactID(){
        return contactID;
    }
}

