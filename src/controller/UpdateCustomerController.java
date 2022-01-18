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
import model.Customer;
import model.Division;

/**
 *  Controller class for the update customer screen of the application
 *
 * @author Lois Vernon Pua
 */
public class UpdateCustomerController implements Initializable {

 Parent scene;
 Stage stage;
 Customer customer;
 
    @FXML
    private TextField idText;
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
     * Initializes the add customer screen part of the application.
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       countryComboBox.setItems(DBCountries.getAllCountries());
    }    
  /**
     * Determines the content of the divisions label and populates the divisions combo-box,  all based on the country combo-box selection.
     *
     * @param event making a selection in the country combo-box.
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

    /** When cancel is initiated, redirects to customer screen.
     *
     * @param event  the method or event when clicking the cancel button.
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */
   
    @FXML
  void onActionCancelUpdate(ActionEvent event) throws IOException {
      
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?(Changes may not be saved)");
 

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/ViewCustomers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        }
      
    }
  /** Validates field before a customer can be updated into the data base.
     *
     * @param event the method or event when clicking the save button.
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */
    
    @FXML
   void onActionUpdateCustomer(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?(This customer will not be updated into the database)");
        

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {

            String customerName = nameText.getText();
            String address = addressText.getText();
            String postalCode = postalCodeText.getText();
            String phone = phoneText.getText();
            Division division = divisionComboBox.getValue();

            if (!postalCode.isEmpty() && !customerName.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !(division == null))
            {
                DBCustomers.updateCustomer(customerName, address, postalCode, phone, division.getDivisionId(), customer.getCustomerId());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../view/ViewCustomers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else
                {
                  JOptionPane.showMessageDialog(null, "Error! Some fields are blank!");
                }
        }
    }

    /**
     * Sends the customer that is selected in the customer table from view customer screen to update customer screen.
     *
     * @param customer the customer to send in the database
     */
    public void sendCustomer(Customer customer) {

        this.customer = customer;

        idText.setText(Integer.toString(customer.getCustomerId()));
        nameText.setText(customer.getCustomerName());
        addressText.setText(customer.getAddress());

        for (Country country : countryComboBox.getItems())
        {
            if(customer.countryId == country.getCountryId())
            {
                countryComboBox.setValue(country);
                break;
            }
        }

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



        for(Division divsion : divisionComboBox.getItems())
        {
            if(customer.divisionId == divsion.getDivisionId())
            {
                divisionComboBox.setValue(divsion);
                break;
            }
        }

        phoneText.setText(customer.getPhone());
        postalCodeText.setText(customer.getPostalCode());

    }
}
