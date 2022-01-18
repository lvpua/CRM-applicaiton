package DAOmodel;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utilities.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/** Handles the user interaction with the database.
 *
 * @author Lois Vernon Pua
 */
public class DBUsers {
    
    /**
     * This method returns all users in the database.
     *
     * @return returns all the users in the database
     */
    public static ObservableList<User> getAllUsers()
    {

        ObservableList<User> filteredUserlist = FXCollections.observableArrayList();

        try
        {
            String sqlgetUsers = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlgetUsers);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(userId, userName, password);
                filteredUserlist.add(user);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return filteredUserlist;
    }
  /**
     * Validates the login entry information every time and is supplied by the user.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return will return if true, false otherwise.
     */
    public static int validateAllUser(String username, String password)
    {
        String sqlUserValidate = "SELECT * FROM users WHERE User_name = 'test' AND Password = 'test'";

        try
        {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlUserValidate);
            ResultSet rs = ps.executeQuery();
            {
               rs.next();
             
                    if(rs.getString("User_name").equals(username)) {
                        
                if(rs.getString("Password").equals(password)) {
                      return rs.getInt("User_ID");
                }
             }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
   
        return -1;

  
}
    }
