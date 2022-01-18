package DAOmodel;

import model.Customer;
import utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**Database connection class 
 *
 * @author Lois Vernon Pua
 */
public class DBCustomers {


    /**
     * Adds a customer to the database.
     *
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param divisionId The division id of the customer.
     */
    public static void addCustomer(String customerName, String address, String postalCode, String phone, int divisionId)
    {
        try
        {

            String sqladdcustomers = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, NOW(), 'Lois V Pua', NOW(), 'Lois V Pua', ?)";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqladdcustomers);

            preparedstatement.setString(1, customerName);
            preparedstatement.setString(2, address);
            preparedstatement.setString(3, postalCode);
            preparedstatement.setString(4, phone  );
            preparedstatement.setInt(5, divisionId);

            preparedstatement.execute();


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }



    }

    /**
     * Updates a customer in the database.
     *
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param divisionId The division id of the customer.
     * @param customerId The id of the customer.
     */
    public static void updateCustomer(String customerName, String address, String postalCode, String phone, int divisionId, int customerId)
    {
        try
        {

            String sqlupdatecustomers = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";


            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqlupdatecustomers);

            preparedstatement.setString(1, customerName);
            preparedstatement.setString(2, address);
            preparedstatement.setString(3, postalCode);
            preparedstatement.setString(4, phone  );
            preparedstatement.setInt(5, divisionId);
            preparedstatement.setInt(6, customerId);

            preparedstatement.execute();


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Deletes a customer from the database.
     *
     * @param customerId The id of the customer.
     */
    public static void deleteCustomer(int customerId)
    {
      
        try {

            String sqldeletecustomersappt = "DELETE from appointments where Customer_ID = ?";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqldeletecustomersappt);

            preparedstatement.setInt(1, customerId);

            preparedstatement.execute();


            String sqldeletecustomers = "DELETE from customers where Customer_ID = ?";

            PreparedStatement psdelcustomers = DBConnection.getConnection().prepareStatement(sqldeletecustomers);

            psdelcustomers.setInt(1, customerId);

            psdelcustomers.execute();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * The method to extract all customers in the database.
     *
     * @return will return all customers in the database
     */
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> allCustomerList = FXCollections.observableArrayList();

        try
        {
            String sqlgetAllCustomers = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, " +
                    "first_level_divisions.COUNTRY_ID, first_level_divisions.Division FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID ORDER BY Customer_ID";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqlgetAllCustomers);
            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                String customerName = results.getString("Customer_Name");
                String address = results.getString("Address");
                String postalCode = results.getString("Postal_Code");
                String phone = results.getString("Phone");
                String divisionName = results.getString("Division");
                int divisionId = results.getInt("Division_ID");
                int customerId = results.getInt("Customer_ID");
                int countryId = results.getInt("COUNTRY_ID");

                Customer customer = new Customer(customerId, customerName, address, postalCode, phone, divisionId, countryId, divisionName);
                allCustomerList.add(customer);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return allCustomerList;
    }
    
}
