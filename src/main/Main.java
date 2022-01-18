package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DBConnection;

import java.sql.*;
import static javafx.application.Application.launch;

/** Controller class for the main part of the Application

 * @author Lois Vernon Pua
 */

public class Main extends Application
{


    /** This method sets up the initial JavaFX application stage.
     *
     * @param primaryStage The primary stage to be set.
     * @throws Exception The exception thrown.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        primaryStage.setTitle(" SOFTWARE II - SCHEDULING SYSTEM");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /** <b>
     * The javadocs folder is located in the Project primary folder.
     * </b>
     *
     * <p>
     * This method calls for and establishes the database connection.
     * </p>
     * @param args The arguments.
     * @throws SQLException The exception that will be thrown in an error from the fxml loader.
     */
    public static void main(String[] args) throws SQLException {


        DBConnection.openConnection();

        launch(args);

        DBConnection.openConnection();

    }
    
}
