package controller;

import DAOmodel.DBAppointments;
import DAOmodel.DBContacts;
import DAOmodel.DBCustomers;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

/**
 * FXML Controller class
 *
 * @author Lois Vernon Pua
 */
public class ViewReportsController implements Initializable {

    Parent scene;
    Stage stage;
    
    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private Label report1ResultsLabel;
    @FXML
    private TableView<Appointment> report2;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumnReport2;
    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumnReport2;
    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumnReport2;
    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumnReport2;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumnReport2;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumnReport2;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumnReport2;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private Label report3ResultsLabel;

    /**
     * Initializes the report screen of the application. Pre-populates the table associated with this part of the application.
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdColumnReport2.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleColumnReport2.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumnReport2.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentTypeColumnReport2.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumnReport2.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumnReport2.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumnReport2.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        monthComboBox.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        typeComboBox.setItems(DBAppointments.getAllTypes());

        contactComboBox.setItems(DBContacts.getAllContacts());
        

    }    
    
    
    /**
     * Runs the first rounds of reports and displays the number of appointments by distinct month and type.
     *
     * @param event clicking on the button for running the report.
     */

    @FXML
    void onActionRunReport1(ActionEvent event) {
        try { 
        String monthcb = monthComboBox.getValue();
        if (monthcb == null)
        {
            return;
        }

        String typecb = typeComboBox.getValue();
        if (typecb == null)
        {
            return;
        }

        int total = DBAppointments.getMonthAndTypeCount(monthcb, typecb);

        report1ResultsLabel.setText(String.valueOf(total));

    } catch (NumberFormatException e) {
         e.printStackTrace();
        
    }
    }
    
    
    /**
     * Runs the second report. Displaying the specific appointment information based on a specific contact.
     *
     * Lambda - For the lambda expression, the implementation of filtering and facilitating to find all appointments based on the contact id was used. 
     *
     * @param event the method or event clicking on the button for running the report.
     */

    @FXML
    void onActionRunReport2(ActionEvent event) {
      try{  
        Contact contact = contactComboBox.getValue();

        if (contact == null)
        {
            return;
        }
        ObservableList<Appointment> filteredapptList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> contactList;
          contactList = filteredapptList.filtered(ap ->
          {
              
              if (ap.getContactId() == contact.getContactId()) {
                  return true;
              }
              return false;
              
          });

        report2.setItems(contactList);

    }catch (NumberFormatException e) {
         e.printStackTrace();
        
    }
      
    }

    /**
     * Runs the third reports that will display the total number of appointments stored in the database.
     *
     * @param event the method or event clicking on the button for running the report.
     */
    @FXML
    void onActionRunReport3(ActionEvent event) {
        
            report3ResultsLabel.setText(String.valueOf(DBCustomers.getAllCustomers().size()));
    }

     /**
     * The method that will send the user back to main menu.
     *
     * @param event  the method or event when clicking on the back to main menu button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    
    @FXML
    void onActionGoToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
