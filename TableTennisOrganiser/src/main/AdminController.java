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
    
    /** Leagues Tab **/
    @FXML private TextField leagueEditName;
    @FXML private TextField leagueNameIn;
    @FXML private Button addleague;
    @FXML private Button changeleagueName;
    @FXML private TableView <League> leagueTableAdmin;
    @FXML private TableColumn <League, String> leagueNameAdmin;
    @FXML private TableColumn <League, Integer> leagueTeamsAdmin;
    
    /** Teams Tab **/
    @FXML private ChoiceBox<String> leagueChoiceBox;
    @FXML private TextField teamEditName;
    @FXML private TextField teamNameIn;
    @FXML private Button addteam;
    @FXML private Button changeteamName;
    @FXML private TableView <Team> teamTableAdmin;
    @FXML private TableColumn <Team, String> teamNameAdmin;
    @FXML private TableColumn <Team, Integer> teamPointsAdmin;
    
    private Admin admin = new Admin();
    League leagueSelection = null;

    /**
     * initialises the UI elements and associated class data structures with
     * data from the database files.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            admin.loadLeagues();
            admin.countTeams();
            initializeLeaguesTab();
            initializeTeamsTab();
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /** Leagues Tab **/
    public void initializeLeaguesTab() throws IOException  {
        leagueTeamsAdmin.setCellValueFactory(new PropertyValueFactory<>("teamsCount"));
        leagueNameAdmin.setCellValueFactory(new PropertyValueFactory<>("name"));

        updateLeaguesTableView();

        //Listener to show team stats when a team is selected in TableView.
        leagueTableAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                League selected = leagueTableAdmin.getSelectionModel().getSelectedItem();
                System.out.println(selected.getName());
            }
        });
    }
    
    public void updateLeaguesTableView() throws IOException {
        leagueTableAdmin.getItems().clear();
        leagueTableAdmin.setItems(listLeagues(admin.getLeagues()));
    }
    
    public void addLeague(ActionEvent event) throws IOException    {
        if (isEmptyError(leagueNameIn.getText()))   {  return;  }
        admin.addLeague(leagueNameIn.getText());
        leagueNameIn.clear();
        admin.saveLeagues();
        updateLeaguesTableView();
    }
    
    public void editLeagueName(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException  {
        if (isEmptyError(leagueEditName.getText()))   {  return;  }
        leagueTableAdmin.getSelectionModel().getSelectedItem().setName(leagueEditName.getText());
        leagueNameIn.clear();
        admin.saveLeagues();
        updateLeaguesTableView();
    }
    
    public void deleteLeague(ActionEvent event) throws IOException    {
        League selection = leagueTableAdmin.getSelectionModel().getSelectedItem();
        if (selection == null) { popupWindow(); return;}
        if (popupWindowChoice(("Delete " + selection.getName() +"?"), "WARNING: This Action Cannot be Undone!", "This will delete all teams, players & fixtures in this league!"))   {
            admin.removeLeague(selection);
            admin.saveLeagues();
            updateLeaguesTableView();
        }
    }
    
    private ObservableList<League> listLeagues(ArrayList<League> input)    {
        ObservableList<League> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    /** Teams Tab **/
    public void initializeTeamsTab() throws IOException {
        leagueChoiceBox.getSelectionModel().selectFirst();
        teamNameAdmin.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamPointsAdmin.setCellValueFactory(new PropertyValueFactory<>("points"));
        leagueChoiceBox.getItems().clear();
        for (String choices : admin.viewLeagues())  {
            leagueChoiceBox.getItems().add(choices);
        }
        if (admin.viewLeagues().get(0) != null) {
            leagueChoiceBox.setValue(admin.viewLeagues().get(0));
        }
        
        //Listener for League selection in Choice Box.
        leagueChoiceBox.setOnAction((event) -> {
            try {
                updateTeamsTableView();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        updateTeamsTableView();
    }
    
        
    public void updateTeamsTableView() throws IOException {
        teamTableAdmin.getItems().clear();
        String leagueChoiceBoxSelection = leagueChoiceBox.getSelectionModel().getSelectedItem();
        ArrayList<League> leagues = admin.getLeagues();
        if (leagueChoiceBoxSelection!=null) {
            for (League temp : leagues) {
                if (leagueChoiceBoxSelection.equals(temp.getName()))
                {
                    leagueSelection = temp;
                    //System.out.println("League is " + temp.getName());
                }
            }
            teamTableAdmin.setItems(listTeams(leagueSelection.getTeams()));
        }
    }
        
    public void addTeam(ActionEvent event) throws IOException    {
        if (isEmptyError(teamNameIn.getText()))   {  return;  }
        admin.addTeam(leagueSelection, teamNameIn.getText());
        System.out.println("Adding team: " + teamNameIn.getText());
        teamNameIn.clear();
        countTeams();
        admin.saveLeagues();
        updateTeamsTableView();
    }
    
    public void editTeamName(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException  {
        if (isEmptyError(teamEditName.getText()))   {  return;  }
        teamTableAdmin.getSelectionModel().getSelectedItem().setName(teamEditName.getText());
        teamNameIn.clear();
        admin.saveLeagues();
        updateTeamsTableView();
    }
    
    public void deleteTeam(ActionEvent event) throws IOException    {
        Team selection = teamTableAdmin.getSelectionModel().getSelectedItem();
        if (selection == null) { popupWindow(); return;}
        if (popupWindowChoice(("Delete " + selection.getName() +"?"), "WARNING: This Action Cannot be Undone!", "This will also delete all players in this team!"))   {
            admin.removeTeam(leagueSelection, selection);
            countTeams();
            admin.saveLeagues();
            updateTeamsTableView();
        }
    }
    
    private ObservableList<Team> listTeams(ArrayList<Team> input)    {
        ObservableList<Team> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    /** Other Methods**/
    void countTeams() throws IOException   {
        admin.countTeams();
        updateLeaguesTableView();
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

