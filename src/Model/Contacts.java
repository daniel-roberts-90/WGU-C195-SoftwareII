package Model;

/**
 * 
 * @author Daniel Roberts
 */

/**
 * Represents a contact entity in the application.
 * Each contact has an ID and a name.
 */

public class Contacts {
    private int contactID;
    private String contactName;
    
    /**
     * Constructs a Contact object with the provided data.
     *
     * @param contactID The ID of the contact.
     * @param contactName The name of the contact.
     */

    public Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;

    }
    /**
     * Gets the ID of the contact.
     *
     * @return The ID of the contact.
     */
    public int getContactID() {

        return contactID;
    }
    /**
     * Gets the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getContactName() {

        return contactName;
    }

    /**
     * Returns a string representation of the contact in the format "contactID - contactName".
     *
     * @return A string representation of the contact.
     */
    @Override
    public String toString() {
        return contactID + " - " + contactName;
    }

}