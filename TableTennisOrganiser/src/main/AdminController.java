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
 * Controls FMXL GUI elements for Admin class
 * @author Aaron
 */
public class AdminController extends UserController implements Initializable{
    
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
        //Create and initialise Admin object
        Admin admin = new Admin();
        
    }
}

