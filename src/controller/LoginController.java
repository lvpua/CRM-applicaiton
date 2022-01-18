package controller;

import DAOmodel.DBAppointments;
import DAOmodel.DBUsers;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Appointment;

/**
 *  Controller class for Login Screen of the application.
 *
 * @author Lois Vernon Pua
 */
public class LoginController implements Initializable {
Parent scene;
Stage stage;

     private String confirmSure;
    private String confirmExit;
    private String invalidLoginData;
    private String pleaseEnterValid;
    
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameText;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordText;
    @FXML
    private Button loginButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Label zoneIdLabel;
    @FXML
    private Label switchLabelZoneId;

    /**
     * The main method that initializes and populates the log in screen.The
     *
     * @param url the location.
     * @param resourcebundle the resources.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        
        try
        {

            ResourceBundle rb = ResourceBundle.getBundle("languages/language", Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr"))
            {
                titleLabel.setText(rb.getString("title"));
                
                usernameLabel.setText(rb.getString("usernameLabel"));
                passwordLabel.setText(rb.getString("passwordLabel"));
                zoneIdLabel.setText(rb.getString("zoneIdLabel"));
                loginButton.setText(rb.getString("loginButton"));
                exitButton.setText(rb.getString("exit"));
                switchLabelZoneId.setText((ZoneId.systemDefault()).getId());
              confirmSure = rb.getString("confirmSure");
                confirmExit = rb.getString("confirmExit");
                invalidLoginData = rb.getString("invalidLoginData");
                pleaseEnterValid = rb.getString("pleaseEnterValidData");

            }
        }
        catch (Exception e)
        {
            System.out.println();
        }



    }

      /**
     *Method that authenticates the user name and password(When enter key is pressed rather than clicked). Also tracks the user activity by recording user login attempts to a direct text file.
     *
     * 
     *
     * Lambda - Implemented a lambda expression on this part which is the filtering of the appointments list by user id to check for with 15 minutes of logging in.
     *
     * @param event pressing the ENTER key on the keyboard.
     * @throws IOException The exception that will be thrown in an error.
     */  

    @FXML
    void onPressEnterKeyLogin(KeyEvent event) throws IOException {
        try {
            
         if(event.getCode().equals(KeyCode.ENTER))
        {

            String username = usernameText.getText();
            String password = passwordText.getText();

            DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            LocalDateTime localDate = LocalDateTime.now();
            String string = dateformat.format(localDate);


            int userId = DBUsers.validateAllUser(username, password);

            FileWriter fWriter = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fWriter);

            if (userId != 0)
            {


                outputFile.println(string + " " + usernameText.getText() + "You have successfully logged in!");
                outputFile.close();

                LocalDateTime now = LocalDateTime.now();

                ObservableList<Appointment> aList = DBAppointments.getAllAppointments();
                ObservableList<Appointment> uList = aList.filtered((Appointment ap) -> ap.getUserId() == userId);

                boolean name = false;

                for (Appointment a : uList)
                {

                    {
                        if (a.getStart().toLocalDateTime().isAfter(now) && a.getStart().toLocalDateTime().isBefore(now.plusMinutes(15)))
                        {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setHeaderText("UPCOMING APPOINTMENT ALERT!");
                            alert3.setContentText("Upcoming appointment scheduled within the next 15 minutes: appointment: " + a.getAppointmentId() + " at " + a.getStart().toLocalDateTime());
                            alert3.showAndWait();

                            name = true;
                        }
                    }
                }

                if (!name)
                {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setHeaderText("No upcoming appointments in 15 minutes");
                  
                    alert3.showAndWait();

                }


                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else
            {
                outputFile.println(string + " " + usernameText.getText() + " Login attempt failed!");
                outputFile.close();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(invalidLoginData);
                alert.setContentText(pleaseEnterValid);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    usernameText.clear();
                    passwordText.clear();
                }

            }
        }
        
    }  catch (IOException e)
        {
            System.out.println();
        }

    }
    
    
     /**
     * This method authenticates the user name and password, and tracks the user activity by recording user login attempts to a text file.
     *
     * Lambda - Implemented a lambda expression on this part which is the filtering of the appointments list by user id to check for with 15 minutes of logging in.
     *
     * @param event The method or event when clicking the login button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
      
            
        String username = usernameText.getText();
        String password = passwordText.getText();

        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localdatettime = LocalDateTime.now();

        String string = dateformat.format(localdatettime);

        int userNameId = DBUsers.validateAllUser(username, password);

        FileWriter fWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fWriter);

        if (userNameId != 0)
        {
           


            outputFile.println(string + " " + usernameText.getText() + "You have successfully logged in!");
            outputFile.close();

            LocalDateTime now = LocalDateTime.now();

            ObservableList<Appointment> aList = DBAppointments.getAllAppointments();
            ObservableList<Appointment> uList = aList.filtered((Appointment ap) -> ap.getUserId() == userNameId);

            boolean name = false;

            for (Appointment appointment : uList)
            {

                {
                    if (appointment.getStart().toLocalDateTime().isAfter(now) && appointment.getStart().toLocalDateTime().isBefore(now.plusMinutes(15)))
                    {
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setHeaderText("UPCOMING APPOINTMENT ALERT!");
                        alert3.setContentText("Upcoming appointment scheduled within the next 15 minutes: appointment: " + appointment.getAppointmentId() + " at " + appointment.getStart().toLocalDateTime());
                        alert3.showAndWait();

                        name = true;
                    }
                }
            }

            if (!name)
            {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText("No upcoming appointments in 15 minutes");
                
                alert3.showAndWait();
            }


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else
        {
            outputFile.println(string + " " + usernameText.getText() + " Login attempt failed!");
            outputFile.close();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(invalidLoginData);
            alert.setContentText(pleaseEnterValid);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                usernameText.clear();
                passwordText.clear();
            }

        }
        
   
    }
    
    
    /** Exits the application.
     *
     * @param event when clicking the exit button.
     */
    @FXML
    private void onActionExitApplication(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit now?");
      

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
    }
    
}
  
}
