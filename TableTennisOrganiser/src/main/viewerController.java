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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

/**
 * Controls FMXL GUI elements for Viewer class
 * @author Aaron
 */
public class ViewerController extends UserController implements Initializable{
   
    @FXML private ListView<String> teamView;
    @FXML private ChoiceBox<String> leagueChoiceBox;
    /**
     * initialises the UI elements and associated class data structures with
     * data from the database files.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Viewer viewer = new Viewer();   //Create object of class to control
        //Load lsit view with team names.
        try {
            viewer.startViewer();
            start();
            teamView.getItems().addAll(viewer.viewTeams());
            //Add listener for Teams view selections
            teamView.getSelectionModel().selectedItemProperty().addListener(
                    (ObservableValue<? extends String> observable, 
                            String oldValue, String newValue) -> {
                System.out.println("Selected item: " + newValue);
            });
        } catch (IOException ex) {
            Logger.getLogger(ViewerController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        //Populate league choicebox with league names.
        ArrayList<String> leagueNames = viewer.viewLeagues();
        for (String choices : leagueNames)  {
            leagueChoiceBox.getItems().add(choices);
            leagueChoiceBox.setValue(choices);
        }
    }
    
    public void start() throws IOException
    {
        //Create new viewer class at logon
        
    }
}
