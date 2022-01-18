package controller;

import DAOmodel.DBCountries;
import DAOmodel.DBCustomers;
import DAOmodel.DBDivisions;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Country;
import model.Division;

/**
 * Controller class for add customer screen part of the application.
 *
 * @author Lois Vernon Pua
 */
public class AddCustomerController implements Initializable {
    
Parent Scene;
Stage stage;

    @FXML
    private TextField customerIdText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField addressText;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private ComboBox<Division> divisionComboBox;
    @FXML
    private TextField postalCodeText;
    @FXML
    private TextField phoneText;
    @FXML
    private Label divisionSwitchLabel;

 /**
 * This method initializes the customer screen. Populates the country combo-box, and clears the contents of the division combobox.
 *
 * @param url the location.
 * @param resourceBundle the resources.
 */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        divisionComboBox.getSelectionModel().clearSelection();
        countryComboBox.setItems(DBCountries.getAllCountries());
    } 
    
   /**
     * Method that determines the content of the division based on label and combo-box that will populate it after (all based on the selection).
     *
     * @param event the event of making a selection in the country's combo-box.
     */
    
    @FXML
   void onActionDivision(ActionEvent event) {
       
        Country country = countryComboBox.getSelectionModel().getSelectedItem();

        switch (country.getCountryId()) {
            case 1:
                divisionSwitchLabel.setText("State:");
                break;
            case 2:
                divisionSwitchLabel.setText("Sub-division:");
                break;
            case 3:
                divisionSwitchLabel.setText("Province:");
                break;
            default:
                break;
        }


        switch (country.getCountryId()) {
            case 1:
                divisionComboBox.setItems(DBDivisions.firstLevelUSDivisions());
                break;
            case 2:
                divisionComboBox.setItems(DBDivisions.firstLevelCADivisions());
                break;
            case 3:
                divisionComboBox.setItems(DBDivisions.firstLevelgetUKDivisions());
                break;
            default:
                divisionComboBox.isDisabled();
                break;
        }



    }

     /** Checks the validitiy of datas and redirects back to main screen if all fields are valid.
     *
     * @param event the method of event when clicking the save button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
   
    @FXML
   void onActionAddCustomer(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE? (New customer will now be added to the database!)");
       

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {

            String customerName = nameText.getText();
            String address = addressText.getText();
            String postalCode = postalCodeText.getText();
            String phone = phoneText.getText();
            Division division = divisionComboBox.getValue();


            if (division != null && !address.isEmpty() && !customerName.isEmpty() && !postalCode.isEmpty() && !phone.isEmpty())
            {
                DBCustomers.addCustomer(customerName, address, postalCode, phone, division.getDivisionId());
                
                
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("../view/ViewCustomers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error, Some fields are empty!");
            }
        }
       
    }
   
  /** Method that will redirect to main screen if cancel add is initiated.
     *
     * @param event the method or event when  clicking the cancel button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
   
    @FXML
        void onActionCancelAdd(ActionEvent event) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("This will clear all fields and cancel adding a customer, are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("../view/ViewCustomers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }
    
}
