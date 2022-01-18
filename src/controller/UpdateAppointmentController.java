package controller;

import DAOmodel.*;
import java.io.IOException;
import model.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.sql.Timestamp;

/**
 * Controller class for update appointment section of the project.
 *
 * @author Lois Vernon Pua
 */
public class UpdateAppointmentController implements Initializable {
    Parent scene;
    Stage stage;
    Appointment appointment;

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private ComboBox<LocalTime> startTimeComboBox;
    @FXML
    private ComboBox<LocalTime> endTimeComboBox;
    @FXML
    private ComboBox<User> userIdComboBox;
   
    @FXML
    private TextField appointmentIdText;
    @FXML
    private TextField titleText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField locationText;
    @FXML
    private TextField typeText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField customerId;

  /**
     * Method that initializes the update appointment part of the application. Pre-populates the customer table, the contact, and the user's combo-boxes. Also handles the time's conversion.
     *
     * @param url the location.
     * @param resourceBundle the resources.
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
    
/** Checks the validity of fields/data first before saving the appointment inside the database. If valid or true, will return back to the appointment screen.
     *
     * @param event the method or event when clicking the save button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */

    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE? (This appointment will now be added)");
       

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
            LocalTime startTime = startTimeComboBox.getValue();
            LocalTime endTime = endTimeComboBox.getValue();
            User userId = userIdComboBox.getValue();
            int appointment_Id = Integer.parseInt(appointmentIdText.getText());


            if (contact == null || type.isBlank() || date ==null || startTime ==null || endTime ==null || customer_Id.isBlank() || userId ==null || title.isBlank() || description.isBlank())
            {   Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText("INVALID ENTRIES OR SOME FIELDS ARE MISSING!");
                alert2.showAndWait();
            }
            else {
                Timestamp start = Timestamp.valueOf(LocalDateTime.of( date, startTimeComboBox.getValue()));
                Timestamp end = Timestamp.valueOf(LocalDateTime.of( date, endTimeComboBox.getValue()));
                int cId = Integer.parseInt(customer_Id);


                if (LocalDateTime.of(date, endTimeComboBox.getValue()).isAfter(LocalDateTime.of(date, startTimeComboBox.getValue())))
                {
                    Appointment newAppointment = new Appointment(appointment_Id, title, description, location, contact.getContactId(), contact.getContactName(), type, start, end, cId, userId.getUserId());


                    if (DBAppointments.checkOverlappingAppointment(newAppointment))
                    {

                       JOptionPane.showMessageDialog(null, "Error! Appointment overlaps with existing appointment!");
                    }
                    else {

                        DBAppointments.updateAppointment(title, description, location, type, start, end, cId, userId.getUserId(), contact.getContactId(), appointment_Id);


                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/ViewAppointments.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Invalid Entry! Please Enter appropriate values for each field!");
                 JOptionPane.showMessageDialog(null, "OR APPOINTMENT END TIME COLLIDES WITH APPOINTMENT START TIME!");
            }
        }
        }
    }
    

     /** When cancel update is initiated, redirects to appointments screen.
     *
     * @param event  the method or even when clicking the cancel/back button.
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */
    
    @FXML
    void onActionCancelUpdate(ActionEvent event) throws IOException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?(Latest Changes will not be saved)");
     

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/ViewAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

     /**
     * Fills the customer id text field from a selected customer in the table.
     *
     * @param event the method or event clicking on a customer entry inside the table.
     */
    
    @FXML
    void onMouseClickFillCustomerTextField(MouseEvent event) {
     customerId.setText(String.valueOf(customerTable.getSelectionModel().getSelectedItem().getCustomerId()));
    }
    
    /**
     * This method sends the appointment that is selected in the appointment table in the 'VIEW APPOINTMENTS' screen to the 'UPDATE APPOINTMENT' screen.
     *
     * @param appointment the appointment to send
     */
    public void sendAppointment(Appointment appointment)
    {

        this.appointment = appointment;

        appointmentIdText.setText(Integer.toString(appointment.getAppointmentId()));
        descriptionText.setText(appointment.getDescription());
        locationText.setText(appointment.getLocation());
        titleText.setText(appointment.getTitle());

        for (Contact contacts : contactComboBox.getItems()) {
            if (appointment.contactId == contacts.getContactId()) {
                contactComboBox.setValue(contacts);
                break;
            }
        }

        typeText.setText(appointment.getType());

        LocalTime setStart = appointment.getStart().toLocalDateTime().toLocalTime();
        startTimeComboBox.setValue(setStart);
        LocalTime setEnd = appointment.getEnd().toLocalDateTime().toLocalTime();
        endTimeComboBox.setValue(setEnd);
        LocalDate appointmentDate = appointment.getStart().toLocalDateTime().toLocalDate();
        datePicker.setValue(appointmentDate);
        customerId.setText(String.valueOf(appointment.getCustomerId()));

        for (User users : userIdComboBox.getItems())
        {
            if (appointment.userId == users.getUserId())
            {
                userIdComboBox.setValue(users);
                break;
            }
        }
    }
}
