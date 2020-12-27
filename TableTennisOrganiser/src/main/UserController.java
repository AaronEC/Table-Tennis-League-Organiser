package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * Controls FMXL GUI elements for User class
 * @author Aaron
 */
public class UserController extends User implements Initializable{

    @FXML private PasswordField passwordInput;
    @FXML private ChoiceBox<String> loginSelect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginSelect.getItems().add("Secretary");
        loginSelect.getItems().add("Player");
        loginSelect.setValue("Secretary");
    }
    
    /**
    * Opens the main program scene with the user logged in as 
    * either a 'viewer' or an 'admin'
    * @param event 
    * @throws
    */
    public void login(ActionEvent event) throws IOException {
        
        //Create new user class at logon
        User user = new User();
        //Check login type
        user.loginVerify(loginSelect.getValue().toString(), passwordInput.getText());
        //Open coresponding scene
        if(user.getLoginType().equalsIgnoreCase("Viewer")) {
            createViewer(event); 
        }
        else if(user.getLoginType().equalsIgnoreCase("Admin")) {
            createAdmin(event); 
        }
        else if(user.getLoginType().equalsIgnoreCase("Error")) {
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
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.setTitle("Secretary View");
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
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewerScene);
        window.setTitle("Player View");
        window.show();
    }
    
    /**
     * Creates a pop up window with the specified text. May extend method to 
     * include variable inputs if popups are needed in other parts of program.
     */
    protected void popupWindow()    {
        //Initialise scene
        Stage popUpWindow=new Stage();
        popUpWindow.getIcons().add(new Image(Main.class.getResourceAsStream("ErrorIcon.png")));
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        //Labels and button text
        popUpWindow.setTitle("Error");
        Label messageLabel= new Label("Please enter correct login details");
        Button OK= new Button("OK");
        //Logic and layout
        OK.setOnAction(e -> popUpWindow.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(messageLabel, OK);
        layout.setAlignment(Pos.CENTER);
        //Create scene
        Scene window= new Scene(layout, 200, 100);
        popUpWindow.setScene(window);
        popUpWindow.showAndWait();
    }
    
    public void logout(ActionEvent event) throws IOException {
        
        Parent viewerParent = FXMLLoader.load(getClass().getResource("User.fxml"));
        Scene viewerScene = new Scene(viewerParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewerScene);
        window.setTitle("Login");
        window.show();
    }
    
}