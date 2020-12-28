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
        if (leagueNameIn.getText().trim().length() > 0)   {
            admin.addLeague(leagueNameIn.getText());
            updateLeaguesTableView();
        }   else {
            popupWindow("No Name", "Please enter a league name", "OK");
        }
    }
    
    public void editLeagueName(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException  {
        leagueTableAdmin.getSelectionModel().getSelectedItem().setName(leagueEditName.getText());
        updateLeaguesTableView();
    }
    
    public void deleteLeague(ActionEvent event) throws IOException    {
        System.out.println("Deleting League: " + leagueTableAdmin.getSelectionModel().getSelectedItem());
        admin.removeLeague(leagueTableAdmin.getSelectionModel().getSelectedItem());
        updateLeaguesTableView();
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
}

