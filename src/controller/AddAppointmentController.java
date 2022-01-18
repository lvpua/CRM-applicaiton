package controller;

import DAOmodel.DBAppointments;
import DAOmodel.DBContacts;
import DAOmodel.DBCustomers;
import DAOmodel.DBUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the add appointment screen.
 *
 * @author Lois Vernon Pua
 */
public class AddAppointmentController implements Initializable {
    Parent scene;
    Stage stage;

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TextField appointmentIdText;
    @FXML
    private TextField titleText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField locationText;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private TextField typeText;
    @FXML
    private ComboBox<LocalTime> startTimeComboBox;
    @FXML
    private ComboBox<LocalTime> endTimeComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField customerId;
    @FXML
    private ComboBox<User> userIdComboBox;

    /**
     * Initializes the controller class.
     * @param url  the location 
     * @param resourceBundle the resources
     */

    @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
        
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        customerTable.setItems(DBCustomers.getAllCustomers());


        contactComboBox.setItems(DBContacts.getAllContacts());
        userIdComboBox.setItems(DBUsers.getAllUsers());




        LocalTime appointmentStartTimeMinEST = LocalTime.of(8, 0);
        LocalDateTime startMinEST = LocalDateTime.of(LocalDate.now(), appointmentStartTimeMinEST);
        ZonedDateTime startMinZDT = startMinEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime startMinLocal = startMinZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentStartTimeMin = startMinLocal.toLocalTime();

        LocalTime appointmentStartTimeMaxEST = LocalTime.of(21, 45);
        LocalDateTime startMaxEST = LocalDateTime.of(LocalDate.now(), appointmentStartTimeMaxEST);
        ZonedDateTime startMaxZDT = startMaxEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime startMaxLocal = startMaxZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentStartTimeMax = startMaxLocal.toLocalTime();

        while (appointmentStartTimeMin.isBefore(appointmentStartTimeMax.plusSeconds(1)))
        {
            startTimeComboBox.getItems().add(appointmentStartTimeMin);
            appointmentStartTimeMin = appointmentStartTimeMin.plusMinutes(15);
        }


        LocalTime appointmentEndTimeMinEST = LocalTime.of(8, 15);
        LocalDateTime endMinEST = LocalDateTime.of(LocalDate.now(), appointmentEndTimeMinEST);
        ZonedDateTime endMinZDT = endMinEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime endMinLocal = endMinZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentEndTimeMin = endMinLocal.toLocalTime();

        LocalTime appointmentEndTimeMaxEST = LocalTime.of(22, 0);
        LocalDateTime endMaxEST = LocalDateTime.of(LocalDate.now(), appointmentEndTimeMaxEST);
        ZonedDateTime endMaxZDT = endMaxEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime endMaxLocal = endMaxZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentEndTimeMax = endMaxLocal.toLocalTime();

        while (appointmentEndTimeMin.isBefore(appointmentEndTimeMax.plusSeconds(1)))
        {
            endTimeComboBox.getItems().add(appointmentEndTimeMin);
            appointmentEndTimeMin = appointmentEndTimeMin.plusMinutes(15);
        }





    }
       
    /** Check the validity of fields before enabling the appointment to be saved.
     *
     * @param event the method or event clicking the save button.
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
       
             {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("The new appointment will be added to the calendar, are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            String customer_Id = customerId.getText();
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            Contact contact = contactComboBox.getValue();
            String type = typeText.getText();
            LocalDate date = datePicker.getValue();

            LocalTime st = startTimeComboBox.getValue();
            LocalTime et = endTimeComboBox.getValue();
            User userId = userIdComboBox.getValue();
           
           

            if (contact == null || type.isBlank() || date ==null || st ==null || et ==null || customer_Id.isBlank() || userId ==null || title.isBlank() || description.isBlank() )
            {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText("INVALID ENTRIES OR SOME FIELDS ARE MISSING!");
                alert2.showAndWait();}
            else {  
                Timestamp start = Timestamp.valueOf(LocalDateTime.of( date, startTimeComboBox.getValue()));
                Timestamp end = Timestamp.valueOf(LocalDateTime.of( date, endTimeComboBox.getValue()));
                int cId = Integer.parseInt(customer_Id);


                if (LocalDateTime.of(date, endTimeComboBox.getValue()).isAfter(LocalDateTime.of(date, startTimeComboBox.getValue())))
                {

                    Appointment newAppointment = new Appointment(Integer.parseInt("0"), title, description, location, contact.getContactId(), contact.getContactName(), type, start, end, cId, userId.getUserId());


                    if (DBAppointments.checkOverlappingAppointment(newAppointment))
                    {

                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setHeaderText("APPOINTMENT OVERLAP");
                        alert3.setContentText("Appointment overlaps with an existing appointment for the selected customer.");
                        alert3.showAndWait();
                    }
                    else {

                        DBAppointments.addAppointment(title, description, location, type, start, end, cId, userId.getUserId(), contact.getContactId());


                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/ViewAppointments.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }
                else
                {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setHeaderText("TIME ERROR");
                    alert3.setContentText("Appointment end time overlaps with appointment start time.");
                    alert3.showAndWait();
                }

            }
            
               
            
        }
          
             }
    }
        
   
    

     /** Method to cancel appointment. After confirmation, will return to the appointments screen.
     *
     * @param event clicking button.
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */
    @FXML
    void onActionCancelAdd(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?(Changes will not be saved)");
        

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/ViewAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }
    }

    
    /**
     * Fills the customer id text field based on a selected customer in the table.
     *
     * @param event when a customer is clicked from the table.
     */
    @FXML
   void onMouseClickFillCustomerTextField(MouseEvent event) {
       customerId.setText(String.valueOf(customerTable.getSelectionModel().getSelectedItem().getCustomerId()));
    }

  
     
   
}
