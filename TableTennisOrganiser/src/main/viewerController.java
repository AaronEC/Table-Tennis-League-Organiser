package main;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;

public class ViewerController {
   
    /**
    * This method logs the user out and opens the login scene
    * @param event 
    */
    public void logout(ActionEvent event) throws IOException {
        
        Parent viewerParent = FXMLLoader.load(getClass().getResource("User.fxml"));
        Scene viewerScene = new Scene(viewerParent);
        
        //Gets the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(viewerScene);
        window.setTitle("Viewer");
        window.show();
    }
}
