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
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

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
        else if(super.getLoginType().equalsIgnoreCase("Admin")) {
            createAdmin(event); 
        }
        else if(super.getLoginType().equalsIgnoreCase("Error")) {
            popupWindow();
        }
    }
    /**
     * Creates the JavaFX scene and class which will contain and control the 
     * functions needed by the 'Admin' (club secretary) user.
     * @param event
     * @throws IOException 
     */
    protected void createAdmin(ActionEvent event) throws IOException {
        
        Parent adminParent = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene adminScene = new Scene(adminParent);

        //Gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(adminScene);
        window.setTitle("Secretary");
        window.show();
    }
    /**
     * Creates the JavaFX scene and class which will contain and control the 
     * functions needed by the 'Viewer' (league players) user.
     * @param event
     * @throws IOException 
     */
    protected void createViewer(ActionEvent event) throws IOException    {
        Parent viewerParent = FXMLLoader.load(getClass().getResource("Viewer.fxml"));
        Scene viewerScene = new Scene(viewerParent);

        //Gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(viewerScene);
        window.setTitle("Player");
        window.show();
    }
    
    /**
     * Creates a pop up window with the specified text. May extend method to 
     * include variable inputs if popups are needed in other parts of program.
     */
    protected void popupWindow()    {
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Login Error");
        Label label1= new Label("Please enter correct login details");
        Button button1= new Button("OK");
        button1.setOnAction(e -> popupwindow.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }
}