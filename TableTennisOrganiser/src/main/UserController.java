package main;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;

/**
 * <h1>User GUI Controller Class</h1>
 * Handles the creation of User super class, as well as controlling FMXL GUI 
 * elements for the user login page.
 * 
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public class UserController extends User implements Initializable{

    @FXML private PasswordField passwordInput;
    @FXML private ChoiceBox<String> loginSelect;
    protected final Admin admin = new Admin();
    /**
     * Initialises the UI elements (login window) and <code>User</code> data 
     * structures.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginSelect.getItems().add("Secretary");
        loginSelect.getItems().add("Player");
        loginSelect.getItems().add("incorrect password test");
        loginSelect.setValue("Secretary");
    }
    
    /**
    * Handles login events and creation of <code>AdminController</code> or 
    * <code>UserController</code> interfaces.
    * @param event 
    * @throws IOException File will always be present
    */
    public void login(ActionEvent event) throws IOException {
        //Verify login type **extend to include password check here**
        admin.loginVerify(loginSelect.getValue(), passwordInput.getText());
        if(admin.getLoginType().equalsIgnoreCase("Viewer")) {
            createViewer(event);
        }
        else if(admin.getLoginType().equalsIgnoreCase("Admin")) {
            createAdmin(event);
        }
        else if(admin.getLoginType().equalsIgnoreCase("Error")) {
            popupWindow("Error", "Incorrect Login", "Please enter correct login details.");
        }
    }
    /**
     * Creates the <code>AdminController</code> interface for the admin user.
     * @param event
     * @throws IOException File will always be present
     */
    protected void createAdmin(ActionEvent event) throws IOException {
        System.out.println("Launching Admin Scene");
        Parent adminParent = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene adminScene = new Scene(adminParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.setTitle("Secretary View");
        window.show();
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (popupWindowChoice("Exit Program?", "Are you sure you want to exit?", "Unsaved changes will be lost...")) {
                    System.out.println("THREAD: Shutdown.");
                }
            }
        });             
    }
    /**
     * Creates the <code>ViewerController</code> interface for the viewer user.
     * @param event
     * @throws IOException File will always be present
     */
    protected void createViewer(ActionEvent event) throws IOException    {
        System.out.println("Launching Viewer Scene");
        Parent viewerParent = FXMLLoader.load(getClass().getResource("Viewer.fxml"));
        Scene viewerScene = new Scene(viewerParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewerScene);
        window.setTitle("Player View");
        window.show();
    }
    /**
     * Creates a pop up error window with the passed text.
     * @param title Popup window title.
     * @param messageLarge Top message.
     * @param messageSmall Bottom message.
     */
    protected void popupWindow(String title, String messageLarge, String messageSmall)    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(messageLarge);
        alert.setContentText(messageSmall);
        alert.showAndWait();
    }
    /**
     * Creates a default popup window with error for missing text input.
     */
    protected void popupWindow()    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Error");
        alert.setHeaderText("No value entered");
        alert.setContentText("Please input a value");
        alert.showAndWait();
    }
    /**
     * Creates a default popup information window passed text.
     */
    protected void popupWindowSuccess(String title, String messageLarge, String messageSmall)    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(messageLarge);
        alert.setContentText(messageSmall);
        alert.showAndWait();
    }
    /**
     * Creates a pop up window with the specified text. The window contains 
     * "OK" and "Cancel" options. Returns true if "OK" is selected by user, 
     * false if "Cancel" is selected.
     * @param title Popup window title.
     * @param messageLarge Top message.
     * @param messageSmall Bottom message.
     * @return OK - true, Cancel - False.
     */
    protected boolean popupWindowChoice(String title, String messageLarge, String messageSmall)    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(messageLarge);
        alert.setContentText(messageSmall);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
    /**
     * Displays a pop-up window with user information on fixture generation.
     */
    protected void popupWindowInformation() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Generate Fixtures");
        alert.setHeaderText("Click to generate fixtures using these rules:");
        String s ="""
                - Each team will play every other team in the league.
                - No team will play more than once a week.
                - Each team only plays another team once.
                - If there are an odd number of teams in the league,
                  a 'bye' will be assigned once per week, where it is 
                  not possible for that team to play.
                  
                - If "Generate home AND away fixtures" is selected,
                  every team will play every other team TWICE in both
                  a HOME and AWAY match (doubles the fixtures).
            """;
        alert.setContentText(s);
        alert.show();
    }
    /**
     * Returns user to the login screen.
     * @param event
     * @throws IOException 
     */
    public void logout(ActionEvent event) throws IOException {
        admin.saveLeagues();
        Parent viewerParent = FXMLLoader.load(getClass().getResource("User.fxml"));
        Scene viewerScene = new Scene(viewerParent);
        //Recalls login screen scene information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewerScene);
        window.setTitle("Login");
        window.show();
    }
    
}