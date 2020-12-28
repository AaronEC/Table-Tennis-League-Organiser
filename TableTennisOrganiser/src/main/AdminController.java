package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controls FMXL GUI elements for Admin class
 * @author Aaron
 */
public class AdminController extends UserController implements Initializable{
    
    @FXML private TextField leagueEditName;
    @FXML private TextField leagueNameIn;
    @FXML private Button addleague;
    @FXML private Button changeleagueName;
    @FXML private TableView <League> leagueTableAdmin;
    @FXML private TableColumn <League, String> leagueNameAdmin;
    @FXML private TableColumn <League, Integer> leagueTeamsAdmin;
    
    private Admin admin = new Admin();

    /**
     * initialises the UI elements and associated class data structures with
     * data from the database files.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        leagueTeamsAdmin.setCellValueFactory(new PropertyValueFactory<>("teamsCount"));
        leagueNameAdmin.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        try {
            admin.loadLeagues();
            updateLeaguesTableView();
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Listener to show team stats when a team is selected in TableView.
        leagueTableAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                League selected = leagueTableAdmin.getSelectionModel().getSelectedItem();
                System.out.println(selected.getName());
            }
        });
    }
    
    public void addLeague(ActionEvent event) throws IOException    {
        if (isEmptyError(leagueNameIn.getText()))   {  return;  }
        admin.addLeague(leagueNameIn.getText());
        leagueNameIn.clear();
        updateLeaguesTableView();
    }
    
    public void editLeagueName(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException  {
        if (isEmptyError(leagueEditName.getText()))   {  return;  }
        leagueTableAdmin.getSelectionModel().getSelectedItem().setName(leagueEditName.getText());
        leagueNameIn.clear();
        updateLeaguesTableView();
    }
    
    public void deleteLeague(ActionEvent event) throws IOException    {
        League selection = leagueTableAdmin.getSelectionModel().getSelectedItem();
        if (selection == null) { popupWindow(); return;}
        if (popupWindowChoice(("Delete " + selection.getName() +"?"), "WARNING: This Action Cannot be Undone!", "This will delete all teams, players & fixtures in this league!"))   {
            admin.removeLeague(selection);
            updateLeaguesTableView();
        }
    }
    
    public void updateLeaguesTableView() throws IOException {
        leagueTableAdmin.getItems().clear();
        leagueTableAdmin.setItems(listLeagues(admin.getLeagues()));
        admin.saveLeagues(admin.getLeagues());
    }
    
    private ObservableList<League> listLeagues(ArrayList<League> input)    {
        ObservableList<League> output = FXCollections.observableArrayList(input);
        return output;
    }

    boolean isEmptyError(String in) {
        boolean error;
        if (in.trim().length() > 0)   {
            error = false;
        }   else {
            popupWindow();
            error = true;
        }
        return error;
    }
}

