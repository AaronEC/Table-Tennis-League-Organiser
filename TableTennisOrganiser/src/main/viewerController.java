package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * Controls FMXL GUI elements for Viewer class
 * @author Aaron
 */
public class ViewerController extends UserController implements Initializable{
   
    @FXML private ListView<String> teamView;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //Initialise the Teams view
            Viewer viewer = new Viewer();
            viewer.startViewer();
            start();
            teamView.getItems().addAll(viewer.viewTeams());
            //Add listener for Teams view selections
            teamView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                // Your action here
                System.out.println("Selected item: " + newValue);
            });
        } catch (IOException ex) {
            Logger.getLogger(ViewerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start() throws IOException
    {
        //Create new viewer class at logon
        
    }
}
