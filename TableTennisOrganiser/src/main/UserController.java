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
 * GUI controller class for controlling the user login screen.
 * Also contains global static GUI methods (such as pop up boxes).
 * @author Aaron
 */
public class UserController extends User implements Initializable{

    @FXML private PasswordField passwordInput;
    @FXML private ChoiceBox<String> loginSelect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginSelect.getItems().add("Secretary");
        loginSelect.getItems().add("Player");
        loginSelect.getItems().add("incorrect password test");
        loginSelect.setValue("Player");
    }
    
    /**
    * Handles login events and creation of 'Admin' or 'Viewer' interfaces.
    * @param event 
    * @throws
    */
    public void login(ActionEvent event) throws IOException {
        User user = new User();     //Create new user class at logon
        //Verify login type **extend to include password check here**
        user.loginVerify(loginSelect.getValue(), passwordInput.getText());
        if(user.getLoginType().equalsIgnoreCase("Viewer")) {
            createViewer(event);    }
        else if(user.getLoginType().equalsIgnoreCase("Admin")) {
            createAdmin(event);     }
        else if(user.getLoginType().equalsIgnoreCase("Error")) {
            popupWindow("Error", "Please enter correct login details", "OK");
        }
    }
    /**
     * Creates the 'Admin' interface for the club secretary user.
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
     * Creates the 'Viewer' interface for the player users.
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
    protected void popupWindow(String title, String message, String buttonText)    {
        //Initialise scene
        Stage popUpWindow=new Stage();
        popUpWindow.getIcons().add(new Image(Main.class.getResourceAsStream("ErrorIcon.png")));
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        //Add labels and button text
        popUpWindow.setTitle(title);
        Label messageLabel= new Label(message);
        Button OK= new Button(buttonText);
        //set logic and layout
        OK.setOnAction(e -> popUpWindow.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(messageLabel, OK);
        layout.setAlignment(Pos.CENTER);
        //Open scene
        Scene window= new Scene(layout, 200, 100);
        popUpWindow.setScene(window);
        popUpWindow.showAndWait();
    }
    /**
     * Called from Admin or Viewer scenes. Returns user to the login screen.
     * @param event
     * @throws IOException 
     */
    public void logout(ActionEvent event) throws IOException {
        Parent viewerParent = FXMLLoader.load(getClass().getResource("User.fxml"));
        Scene viewerScene = new Scene(viewerParent);
        //Recalls login screen scene information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewerScene);
        window.setTitle("Login");
        window.show();
    }
    
}