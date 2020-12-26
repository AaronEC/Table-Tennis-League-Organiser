package main;

import java.net.URL;
import java.util.ResourceBundle;
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
        teamView.getItems().addAll("UWE", "Filton", "Page");
    }
    
    public void start()
    {

    }
}
