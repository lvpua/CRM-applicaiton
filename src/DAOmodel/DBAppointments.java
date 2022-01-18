package DAOmodel;

import model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DBConnection;
import java.sql.*;

/**
 *
 * @author Lois Vernon Pua
 */

public class DBAppointments {
    
    /**
     * Adds an appointment to the database.
     *
     * @param start The start time and date of the appointment.
     * @param end The end time and date of the appointment.
     * @param customerId The customerID for the appointment.
     * @param userId The userID for with the appointment.
     * @param contactId The contact id for the appointment.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of appointment.
     */
        public static void addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId)
        {

            try

            {
                String sqladdappt = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?, ?, ?)";
                PreparedStatement psaa = DBConnection.getConnection().prepareStatement(sqladdappt);

                psaa.setString(1, title);
                psaa.setString(2, description);
                psaa.setString(3, location);
                psaa.setString(4, type);
                psaa.setTimestamp(5, start);
                psaa.setTimestamp(6, end);
                psaa.setInt(7, customerId);
                psaa.setInt(8, userId);
                psaa.setInt(9, contactId);
         


                psaa.execute();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        
     /**
     * Updates an appointment in the database.
     *
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of appointment.
     * @param start The start time and date of the appointment.
     * @param end The end time and date of the appointment.
     * @param customerId The customerID for the appointment.
     * @param userId The userID for with the appointment.
     * @param contactId The contact id for the appointment.
     * @param appointmentId The id of the appointment.
     */
    public static void updateAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId, int appointmentId)
    {
        try
        {
            String sqlupdateappt = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqlupdateappt);

            preparedstatement.setString(1, title);
            preparedstatement.setString(2, description);
            preparedstatement.setString(3, location);
            preparedstatement.setString(4, type);
            preparedstatement.setTimestamp(5, start);
            preparedstatement.setTimestamp(6, end);
            preparedstatement.setInt(7, customerId);
            preparedstatement.setInt(8, userId);
            preparedstatement.setInt(9, contactId);
            preparedstatement.setInt(10, appointmentId);

            preparedstatement.execute();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    
    /**
     * Deletes an appointment from the database.
     *
     * @param appointmentId The id of the appointment.
     */
    public static void deleteAppointment(int appointmentId)
    {
        try {

            String sqldeleteappt = "DELETE from appointments where Appointment_ID = ?";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqldeleteappt);

            preparedstatement.setInt(1, appointmentId);

            preparedstatement.execute();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Returns All types of appointments in the database.
     *
     * @return All types of appointments in the database
     */
    public static ObservableList<String> getAllTypes()
    {
        ObservableList<String> getTypes = FXCollections.observableArrayList();
        try {

            String sqlget = "SELECT DISTINCT type from appointments";

            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqlget);


            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                getTypes.add(results.getString(1));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return getTypes;
    }


    /**
     *
     * Returns the number of appointments with a specified type and month.
     *
     * @param month the month specified
     * @param type the type specified
     * @return the total number of appointments
     */
    public static int getMonthAndTypeCount(String month, String type)
    {
        int total = 0;
    
        if (total == 0) {
         
        try {

            String sqltotal = "SELECT count(*) from appointments WHERE type = ? AND monthname(start) = ?";


            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqltotal);


            preparedstatement.setString(1, type);
            preparedstatement.setString(2, month);

            ResultSet results = preparedstatement.executeQuery();

            if (results.next())
            {
                return results.getInt(1);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return total;
    }
            return 0;
    }

        
    /**
     * Filters appointments based on the query by start and end range. 
     *
     * @return returns all the appointments in the database
     */
     public static ObservableList<Appointment> getAllAppointments()
    {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try
        {
            String sqlappointments = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID " +
                    "FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID  ORDER BY Appointment_ID";
            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqlappointments);
            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {

                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String contactName = results.getString("Contact_Name");
                String type = results.getString("Type");
                Timestamp start = results.getTimestamp("Start");
                Timestamp end = results.getTimestamp("End");
                int contactId = results.getInt("Contact_ID");
                int appointmentid = results.getInt("Appointment_ID");
                int customerId = results.getInt("Customer_ID");
                int userId = results.getInt("User_ID");


                Appointment appointment = new Appointment(appointmentid, title, description, location, contactId, contactName, type, start, end, customerId, userId);
                allAppointments.add(appointment);
            }
        }
        catch (SQLException error)
        {
            error.printStackTrace();
        }
        return allAppointments;
    }
     
      /**
     * The method the returns appointments by weekly schedule.
     *
     * @return appointments from the database for the current week
     */
    public static ObservableList<Appointment> getWeeklyAppointments()
    {
        ObservableList<Appointment> weeklyAppointmentList = FXCollections.observableArrayList();

        try
        {
            String sqlweeklyappointments = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID AND week(Start, 0) = week(curdate(), 0) ORDER BY Appointment_ID";
            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqlweeklyappointments);
            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String contactName = results.getString("Contact_Name");
                String type = results.getString("Type");
                Timestamp start = results.getTimestamp("Start");
                Timestamp end = results.getTimestamp("End");
                int appointmentId = results.getInt("Appointment_ID");
                int contactId = results.getInt("Contact_ID");
                int customerId = results.getInt("Customer_ID");
                int userId = results.getInt("User_ID");

                Appointment appointment = new Appointment(appointmentId, title, description, location, contactId, contactName, type, start, end, customerId, userId);


                weeklyAppointmentList.add(appointment);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return weeklyAppointmentList;
    }
    
    /**
     * The method the returns appointments by monthly schedule.
     *
     * @return appointments in the database for the current month
     */
    public static ObservableList<Appointment> getMonthlyAppointments()
    {
       ObservableList<Appointment> monthlyAppointmentList = FXCollections.observableArrayList();

        try
        {
            String sqlmonthlyappointments = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID AND month(Start) = month(curdate()) ORDER BY Appointment_ID";
            PreparedStatement preparedstatement = DBConnection.getConnection().prepareStatement(sqlmonthlyappointments);
            ResultSet results = preparedstatement.executeQuery();

            while (results.next())
            {
                int appointmentid = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                int contactId = results.getInt("Contact_ID");
                String contactName = results.getString("Contact_Name");
                String type = results.getString("Type");
                Timestamp start = results.getTimestamp("Start");
                Timestamp end = results.getTimestamp("End");
                int customerId = results.getInt("Customer_ID");
                int userId = results.getInt("User_ID");

                Appointment appointment = new Appointment(appointmentid, title, description, location, contactId, contactName, type, start, end, customerId, userId);
                monthlyAppointmentList.add(appointment);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return monthlyAppointmentList;
    }
    
     /**
     *  Checks for overlapping appointments.
     *
     * @param appointment the appointment to check if overlapping.
     * @return true if overlapping appointments exist, false otherwise.
     */
    public static Boolean checkOverlappingAppointment (Appointment appointment)
    {
        try
        {
            String sqloverlapcheck = "SELECT * FROM appointments WHERE ((? <= Start AND ? > Start) OR (? >= Start AND ? < End)) AND Customer_ID = ? AND Appointment_ID <> ?";

            PreparedStatement prepartedstatement = DBConnection.getConnection().prepareStatement(sqloverlapcheck);
            
            
            
            prepartedstatement.setTimestamp(1, appointment.getStart());
            prepartedstatement.setTimestamp(2, appointment.getEnd());
            prepartedstatement.setTimestamp(3, appointment.getStart());
            prepartedstatement.setTimestamp(4, appointment.getStart());
            prepartedstatement.setInt(5, appointment.getCustomerId());
            prepartedstatement.setInt(6, appointment.getAppointmentId());

            ResultSet results = prepartedstatement.executeQuery();

            


            while (results.next())
            {

                return true;
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

}
