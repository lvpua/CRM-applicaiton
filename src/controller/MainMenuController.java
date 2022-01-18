package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;

/**
 * FXML Controller class for the Main Menu screen.
 *
 * @author Lois Vernon Pua
 */


public class MainMenuController implements Initializable {
    Parent scene;
    Stage stage;

    /** The method that initializes the Main Menu screen.
     *
     * @param url The location.
     * @param resourceBundle The resources.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
      
    }    

     /**
     *When the method is initiated, sends the user to the customer screen section.
     *
     * @param event when clicking on the view customers button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    
    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/ViewCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

     /**
     * When the method is initiated, sends the user to the report screen section.
     *
     * @param event when clicking on the view reports button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    
    @FXML
    void onActionViewReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/ViewReports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

     /**
     * The method that will open appointments at the event the method is clicked.
     *
     * @param event when clicking on the view appointments button.
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */
    
    @FXML
    void onActionViewAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/ViewAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

     /**
     * When the user logs out, will direct back to the main menu screen.
     *
     * @param event when clicking the log out button..
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */
    
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?(System will now exit)");
       

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
}
