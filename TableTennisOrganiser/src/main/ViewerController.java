package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <h1>Viewer GUI Controller Class</h1>
 * Controls FMXL GUI elements for Viewer class.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
 */
public class ViewerController extends AdminController implements Initializable{
    
    
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
        } catch (IOException ex) {
            Logger.getLogger(ViewerController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        
    }
    
    public void start() throws IOException
    {
        //Create new viewer class at logon
        
    }

}
