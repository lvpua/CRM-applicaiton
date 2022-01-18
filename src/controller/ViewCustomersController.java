package controller;

import DAOmodel.DBCustomers;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Customer;

/**
 * Controller class for view customer screen of the application.
 *
 * @author Lois Vernon Pua
 */
public class ViewCustomersController implements Initializable {
    Parent scene;
    Stage stage;

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> idColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, Integer> divisionColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;

    /**
     * Initializes the view customer screen of the application.Also populates the customer table.
     * @param url the location.
     * @param resourceBundle the resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(DBCustomers.getAllCustomers());
        
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
         idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

    }    

    /**
     * Sends the user to the add customer screen.
     *
     * @param event the method or event when clicking on the add customer button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
    void onActionGoToAddCustomerScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sends the user to the Main Menu screen.
     *
     * @param event the method or event when clicking on the back to main menu button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    
    @FXML
    void onActionGoToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes a customer from the database.
     *
     * @param event the method or event when clicking on the delete customer button.
     * @throws IOException The exception that will be thrown in an error from fxml loader.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {
        
        try {
           if (customerTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT A CUSTOMER!");
           
            Optional<ButtonType> result = alert.showAndWait();
        }

        else
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("ARE YOU SURE?");
              alert.setContentText("Customer will be permanently deleted from the database!");
          

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();

                DBCustomers.deleteCustomer(customerId);

                customerTable.setItems(DBCustomers.getAllCustomers());


                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("CUSTOMER DELETED!");
                alert2.setContentText("The selected customer was successfully deleted!");

                alert2.showAndWait();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Customer selected was not deleted!");
            }


        }



    } catch (NumberFormatException e) {
         e.printStackTrace();
    }
    }
    
    /**
     * Sends the user to the update customer screen.
     *
     * @param event the method or event when clicking on the update customer button.
     * @throws IOException The exception that will be thrown in an error from the fxml loader.
     */
    @FXML
    void onActionGoToUpdateCustomerScreen(ActionEvent event) throws IOException {
        try{
           if (customerTable.getSelectionModel().isEmpty())
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT A CUSTOMER!");
            alert.setContentText("Customer was not selected for update");

            Optional<ButtonType> result = alert.showAndWait();
        }

        else
        {

            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateCustomer.fxml"));
            loader.load();

            UpdateCustomerController ADMController = loader.getController();
            ADMController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }  catch (NumberFormatException e) {
         e.printStackTrace();

    }
    
}
}
