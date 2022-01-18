package controller;

import DAOmodel.DBAppointments;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Appointment;

/**
 * Controller class for the view appointments section of the application.
 *
 * @author Lois Vernon Pua
 */
public class ViewAppointmentsController implements Initializable {
    Parent scene;
    Stage stage;

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn1;
    @FXML
    private RadioButton viewByWeekRadioButton;
    @FXML
    private ToggleGroup viewToggleGroup;
    @FXML
    private RadioButton viewByMonthRadioButton;
    @FXML
    private RadioButton viewAllRadioButton;

     /**
     * Initializes the view appointments screen. Pre-populates the table and pre-selects the radio button.
     *
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
         viewAllRadioButton.setSelected(true);

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
         endColumn1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentTable.setItems(DBAppointments.getAllAppointments());  
    }    
    
    /**
     * The method that sends the user back to main screen.
     *
     * @param event the method or event when clicking on the back to main menu button.
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */

    @FXML
    void onActionGoToMainMenu(ActionEvent event) throws IOException {
         stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Deletes an appointment from the database.
     *
     * @param event the method or even when clicking on the delete appointment button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */

    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws IOException {
        try {
          if (appointmentTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please Select a valid appointment!");
            alert.setContentText("No appointment was selected.");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("ARE YOU SURE?");
            alert.setContentText("The appointment will now be permanently deleteded from the database!");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();

                String typeMessage = appointmentTable.getSelectionModel().getSelectedItem().getType();

                String idMessage = String.valueOf(appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId());

                DBAppointments.deleteAppointment(appointmentId);

                appointmentTable.setItems(DBAppointments.getAllAppointments());


                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("DELETED!");
                alert2.setContentText("Successfully deleted appointment " + idMessage + ", a " + typeMessage + " appointment.");

                alert2.showAndWait();
            }
            else
            {
               JOptionPane.showMessageDialog(null, "Error! Appointment was not deleted!");
            }


        }
    } catch (NumberFormatException e) {
         e.printStackTrace();
        
    }
    }

      /**
     * The Method that sends the user to the update appointment screen.
     *
     * @param event the method or event clicking on the update appointment button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
   void onActionGoToUpdateAppointmentScreen(ActionEvent event) throws IOException {
       
       try {
       if (appointmentTable.getSelectionModel().isEmpty())
        {

           JOptionPane.showMessageDialog(null, "Error! No appointment selection was made.");
        }

        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointmentController UACController = loader.getController();
            UACController.sendAppointment(appointmentTable.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    } catch (NumberFormatException e) {
         e.printStackTrace();
   }
   }
   
    /**
     * The method that sends the user to the add appointments screen.
     *
     * @param event the method or event when clicking on the add appointment button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */

    @FXML
    void onActionGoToAddAppointmentScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

     /**
     * Shows current week appointments in the table/database.
     *
     * @param event the method or event when clicking on the 'CURRENT WEEK' radio button.
     */
    
    @FXML
    void onActionViewByWeek(ActionEvent event) {
          appointmentTable.setItems(DBAppointments.getWeeklyAppointments());
    }
    
   /**
     * Shows current month appointments in the table/database.
     *
     * @param event the method or event when clicking on the 'CURRENT MONTH' radio button.
     */
    
    @FXML
    void onActionViewByMonth(ActionEvent event) {
        appointmentTable.setItems(DBAppointments.getMonthlyAppointments());
    }

    /**
     * Shows all appointments in the table/database.
     *
     * @param event the method or event when clicking on the 'ALL' radio button.
     */
    
    @FXML
   void onActionViewAll(ActionEvent event) {
       appointmentTable.setItems(DBAppointments.getAllAppointments());
    }
    
}


