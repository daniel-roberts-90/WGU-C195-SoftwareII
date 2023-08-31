package Data;

import Main.DataConnect;
import Model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A utility class to manage contacts data from the database.
 */
public class ContactData {

    /**
     * Retrieves all contacts from the database.
     *
     * @return A list of all contacts.
     * @throws SQLException if there's an issue executing the SQL query.
     */
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();

        // SQL query to retrieve all contacts.
        String query = "SELECT * from contacts";

        PreparedStatement ps = DataConnect.connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            // Retrieve data from the result set and create a Contacts object for each record.
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            Contacts contact = new Contacts(contactID, contactName);
            contactsObservableList.add(contact);
        }
        return contactsObservableList;
    }
}
