package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;

public class UserController {
    /*
    @FXML
    private Label myMessage;
    public void generateRandom(ActionEvent event) {
            Random rand = new Random();
            int myRand = rand.nextInt(50) + 1;
            myMessage.setText(Integer.toString(myRand));
            //System.out.println(Integer.toString(myRand));
    }
    */

    @FXML private TextArea userInput;
    @FXML private TextArea passwordInput;

    /**
    * This method opens the main program scene with the user logged in as 
    * either a 'viewer' or an 'admin'
    * @param event 
    */
    public void login(ActionEvent event) throws IOException {

        System.out.println("User: " + userInput.getText() + 
                "\nPassword: " + passwordInput.getText());
        
        Parent viewerParent = FXMLLoader.load(getClass().getResource("Viewer.fxml"));
        Scene viewerScene = new Scene(viewerParent);
        
        //Gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(viewerScene);
        window.setTitle("Viewer");
        window.show();
    }
}
