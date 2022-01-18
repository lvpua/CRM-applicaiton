package DAOmodel;

import model.Division;
import utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


/** Class that handles the database interaction with the first level division.
 *
 * @author Lois Vernon Pua
 */


public class DBDivisions {
     /**
     * Returns the firstLevelUnitedStates Division from the database.
     * @return returns all divisions that have a country id of 1
     */
    public static ObservableList<Division> firstLevelUSDivisions()
    {
        ObservableList<Division> getUSDivisions = FXCollections.observableArrayList();

        try
        {
            String sqlCommand = "SELECT * from first_level_divisions where COUNTRY_ID = 1";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqlCommand);

            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                String divisionName = results.getString("Division");
                int divisionId = results.getInt("Division_ID");
                int countryId = results.getInt("COUNTRY_ID");
                Division D = new Division(divisionId, divisionName, countryId);
                getUSDivisions.add(D);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return getUSDivisions;
    }
    /**
     * Returns the firstLevelCanada Division from the database.
     *
     * @return returns all divisions that have a country id of 2
     */
    public static ObservableList<Division> firstLevelCADivisions()
    {
        ObservableList<Division> getCADivisions = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * from first_level_divisions where COUNTRY_ID = 2";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                String divisionName = results.getString("Division");
                int divisionId = results.getInt("Division_ID");
                int countryId = results.getInt("COUNTRY_ID");
                Division division = new Division(divisionId, divisionName, countryId);
                getCADivisions.add(division);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return getCADivisions;
    }

    /**
     * Returns the firstLevelUnitedKingdom Division from the database.
     *
     * @return returns all divisions that have a country id of 3
     */
    public static ObservableList<Division> firstLevelgetUKDivisions()
    {
        ObservableList<Division> getUKDivisions = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * from first_level_divisions where COUNTRY_ID = 3";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                String divisionName = results.getString("Division");
                int divisionId = results.getInt("Division_ID");
                int countryId = results.getInt("COUNTRY_ID");
                Division division = new Division(divisionId, divisionName, countryId);
                getUKDivisions.add(division);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return getUKDivisions;
    }



    
}
