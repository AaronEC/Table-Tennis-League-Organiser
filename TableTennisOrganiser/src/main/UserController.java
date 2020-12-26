package main;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controls FMXL GUI elements for User class
 * @author Aaron
 */
public class UserController extends User {

    @FXML private TextField userInput;
    @FXML private PasswordField passwordInput;

    /**
    * Opens the main program scene with the user logged in as 
    * either a 'viewer' or an 'admin'
    * @param event 
    * @throws
    */
    public void login(ActionEvent event) throws IOException {
        
        loginVerify(userInput.getText(), passwordInput.getText());
        
        if(super.getLoginType().equalsIgnoreCase("Viewer")) {
           createViewer(event); 
        }
        
        if(super.getLoginType().equalsIgnoreCase("Admin")) {
           createAdmin(event); 
        }
    }
        
        protected void createAdmin(ActionEvent event) throws IOException {
        
        Parent adminParent = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene adminScene = new Scene(adminParent);

        //Gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(adminScene);
        window.setTitle("Secretary");
        window.show();
    }

    protected void createViewer(ActionEvent event) throws IOException    {
        Parent viewerParent = FXMLLoader.load(getClass().getResource("Viewer.fxml"));
        Scene viewerScene = new Scene(viewerParent);

        //Gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewerScene);
        window.setTitle("Player");
        window.show();
    }
}