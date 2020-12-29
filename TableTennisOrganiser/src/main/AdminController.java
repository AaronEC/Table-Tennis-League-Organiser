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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    @FXML private TableView <League> leagueTableAdmin;
    @FXML private TableColumn <League, String> leagueNameAdmin;
    @FXML private TableColumn <League, Integer> leagueTeamsAdmin;
    @FXML private TableColumn <League, Integer> leagueFixturesAdmin;
    
    /** Teams Tab **/
    @FXML private ChoiceBox<String> leagueChoiceBox;
    @FXML private ChoiceBox<String> playerChoiceBox;
    @FXML private TextField teamEditName;
    @FXML private TextField teamNameIn;
    @FXML private TextField playerNameIn;
    @FXML private TextField venueNameIn;
    @FXML private TableView <Team> teamTableAdmin;
    @FXML private TableColumn <Team, String> teamNameAdmin;
    @FXML private TableColumn <Team, Integer> teamPointsAdmin;
    @FXML private TableColumn <Team, Integer> teamPlayersAdmin;
    @FXML private Label teamInfoLabels;
    @FXML private Label teamInfoVariables;
    
    private Admin admin = new Admin();
    private League leagueSelection;
    private Team teamSelection;

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
            //admin.countPlayers();
            admin.countFixtures();
            initializeLeaguesTab();
            initializeTeamsTab();
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /*************************/
    /** Leagues Tab Methods **/
    /*************************/
    public void initializeLeaguesTab() throws IOException  {
        leagueFixturesAdmin.setCellValueFactory(new PropertyValueFactory<>("fixturesCount"));
        leagueTeamsAdmin.setCellValueFactory(new PropertyValueFactory<>("teamsCount"));
        leagueNameAdmin.setCellValueFactory(new PropertyValueFactory<>("name"));

        updateLeaguesTableView();

        //Listener for when a League is selected in TableView.
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
    /***********************/
    /** Teams Tab Methods **/
    /***********************/
    public void initializeTeamsTab() throws IOException {
        teamNameAdmin.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamPointsAdmin.setCellValueFactory(new PropertyValueFactory<>("points"));
        teamPlayersAdmin.setCellValueFactory(new PropertyValueFactory<>("playersCount"));
        
        //Populate League choice box from League ArrayList
        leagueChoiceBox.getItems().clear();
        for (String choices : admin.viewLeagues())  {
            leagueChoiceBox.getItems().add(choices);
        }
        if (admin.viewLeagues().get(0) != null) {
            leagueChoiceBox.setValue(admin.viewLeagues().get(0));
        }
        updateTeamsTableView();
        
        //Listener for League selection in Choice Box.
        leagueChoiceBox.setOnAction((event) -> {
            try {
                updateTeamsTableView();
                teamInfoLabels.setText("");
                teamInfoVariables.setText("");
                teamTableAdmin.getSelectionModel().selectFirst();
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Listener for when a Team is selected in TableView.
        teamTableAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                teamSelection = teamTableAdmin.getSelectionModel().getSelectedItem();
                System.out.println(teamSelection.getName());
                displayTeamInfo(teamSelection);
            }
        });
        teamTableAdmin.getSelectionModel().selectFirst();
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
                }
            }
            teamTableAdmin.setItems(listTeams(leagueSelection.getTeams()));
        }
    }
        
    public void addTeam(ActionEvent event) throws IOException    {
        if (isEmptyError(teamNameIn.getText()))   {  return;  }
        admin.addTeam(leagueSelection, teamNameIn.getText());
        teamNameIn.clear();
        admin.saveLeagues();
        updateTeamsTableView();
        updateLeaguesTableView();
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
            admin.saveLeagues();
            updateTeamsTableView();
            updateLeaguesTableView();
        }
    }
    
    public void displayTeamInfo(Team team)   {
        ArrayList<Player> players = team.getPlayers();
        ArrayList<String> names = new ArrayList<>();
        if (players != null) {
            for(Player player : players) {
                names.add(player.getName());
            }
        }
        
        teamInfoLabels.setText("Name:\nMatches Played:\nMatches Won:\n"
                + "Matches Lost:\nMatches Drawn:\nTotal Points:\nHome Venue:"
                + "\nPlayers:" ); 
        teamInfoVariables.setText(
                team.getName() + "\n" +
                team.getMatchesPlayed() + "\n" +
                team.getMatchesWon() + "\n" +
                team.getMatchesLost()  + "\n" +
                team.getMatchesLost()  + "\n" +
                team.getPoints()  + "\n" + 
                team.getVenue() + "\n" + 
                names.toString()
                );
        
        //Populate Player ChoiceBox
        playerChoiceBox.getItems().clear();
            for (Player chosenPlayer : team.getPlayers())  {
                playerChoiceBox.getItems().add(chosenPlayer.getName());
            }
            if (!team.getPlayers().isEmpty()) {
                playerChoiceBox.setValue(team.getPlayers().get(0).getName());
            }
            playerChoiceBox.setValue("No Players in Team");
        
    }
    
    public void updateVenueName(ActionEvent event) throws IOException  {
        admin.updateVenue(teamSelection, venueNameIn.getText());
        displayTeamInfo(teamSelection);
        admin.saveLeagues();
    }
    
    private ObservableList<Team> listTeams(ArrayList<Team> input)    {
        ObservableList<Team> output = FXCollections.observableArrayList(input);
        return output;
    }
    /********************/
    /** Player Methods **/
    /********************/
    public void addPlayer(ActionEvent event) throws IOException    {
        if (isEmptyError(playerNameIn.getText()))   {  return;  }
        admin.addPlayer(teamSelection, playerNameIn.getText());
        teamNameIn.clear();
        admin.saveLeagues();
        displayTeamInfo(teamSelection);
        updateTeamsTableView();
        playerNameIn.clear();
    }
    
    public void deletePlayer(ActionEvent event) throws IOException    {
        Player playerSelection = null;
        for (Player player : teamSelection.getPlayers()) {
            if (player.getName() == playerChoiceBox.getSelectionModel().getSelectedItem()) {
                playerSelection = player;
            }
        }
        if (playerSelection == null) { popupWindow(); return;}
        if (popupWindowChoice(("Delete player " + playerSelection.getName() +
                "?"), "WARNING: This Action Cannot be Undone!", 
                ("Are you sure you want to delete player " + 
                playerSelection.getName() + "?")))   {
            admin.removePlayer(teamSelection, playerSelection);
            displayTeamInfo(teamSelection);
            updateTeamsTableView();
            admin.saveLeagues();
        }
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

