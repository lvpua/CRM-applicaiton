package DAOmodel;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utilities.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




/** Contacts database.
 *
 * @author Lois Vernon Pua
 */
public class DBContacts {
    
    /**
     * The method to return all contacts from the database at once.
     *
     * @return all contacts in the database
     */
    
    public static ObservableList<Contact> getAllContacts()
    {

        ObservableList<Contact> filteredContactList = FXCollections.observableArrayList();
        
        if (filteredContactList != null) {
        try
        {
            String sql = "SELECT * from contacts";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                String contactName = results.getString("Contact_Name");
                String contactEmail = results.getString("Email");
                int contactId = results.getInt("Contact_ID");
                Contact contact = new Contact(contactId, contactName, contactEmail);
                filteredContactList.add(contact);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return filteredContactList;
    }
        return null;
}
}
