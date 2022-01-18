package DAOmodel;

import utilities.DBConnection;
import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 *
 * @author Lois Vernon Pua
 */
public class DBCountries {
    /**
     * The method that returns the countries all at once.
     *
     * @return all countries in the database
     */
    public static ObservableList<Country> getAllCountries()
    {

        ObservableList<Country> filteredCountryList = FXCollections.observableArrayList();
        
        if(filteredCountryList != null);
        try
        {
            String sql = "SELECT * from countries";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                String countryName = results.getString("Country");
                int countryId = results.getInt("Country_ID");
                Country country = new Country(countryId, countryName);
                filteredCountryList.add(country);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return filteredCountryList;
    }
   
}
