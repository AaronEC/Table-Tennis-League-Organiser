package main;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * <h1>Admin GUI Controller Class</h1>
 * Controls FMXL GUI elements for Admin class.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
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
    @FXML private ChoiceBox<String> leagueChoiceBoxTeamsTab;
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
    
    /** Fixtures Tab **/
    @FXML private ChoiceBox<String> leagueChoiceBoxFixturesTab;
    @FXML private TableView <Fixture> fixtureTableAdmin;
    @FXML private TableColumn <Fixture, String> fixtureWeekAdmin;
    @FXML private TableColumn <Fixture, Integer> fixtureTeamsAdmin;
    @FXML private TableColumn <Fixture, Integer> fixturePlayedAdmin;
    @FXML private Label fixtureInfoLabels;
    @FXML private Label fixtureInfoVariables;
    
    /** Class Variables **/
    private final Admin admin = new Admin();
    private League leagueSelectionTeamsTab;
    private League leagueSelectionFixturesTab;
    private Team teamSelection;
    private Fixture fixtureSelection;

    /**
     * initialises the UI elements and associated class data structures.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            admin.loadLeagues();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        admin.countFixtures();
        initializeLeaguesTab();
        initializeTeamsTab();
        initializeFixturesTab();
    }
    
    /*************************/
    /** Leagues Tab Methods **/
    /*************************/
    
    /**
     * initialises the UI elements and data structures in 'Leagues' tab.
     */
    public void initializeLeaguesTab()  {
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
    /**
     * Refreshes the League TableView JavaFX element. For use after adding or 
     * removing a league OR team.
     */
    public void updateLeaguesTableView() {
        leagueTableAdmin.getItems().clear();
        leagueTableAdmin.setItems(listLeagues(admin.getLeagues()));
    }
    /**
     * Adds a League. Reads league name from leagueNameIn JavaFX TextField.
     * @param event Add League button press OR LeagueNameIn text field return.
     */
    public void addLeague(ActionEvent event) {
        if (isEmptyError(leagueNameIn.getText()))   {  return;  }
        admin.addLeague(leagueNameIn.getText());
        leagueNameIn.clear();
        leagueChoiceBoxTeamsTab.getSelectionModel().clearSelection();
        admin.saveLeagues();
        teamTableAdmin.getSelectionModel().selectFirst();
        updateLeaguesTableView();
        updateleagueChoiceBoxTeamsTab();
        updateleagueChoiceBoxFixturesTab();
        updateTeamsTableView();
    }
    /**
     * Changes a selected League's name. Selection is from leagueTableAdmin
     * TableView JavaFX element.
     * @param event Change Name button press OR leagueEditName text field return
     */
    public void editLeagueName(ActionEvent event) {
        if (isEmptyError(leagueEditName.getText()))   {  return;  }
        if (leagueTableAdmin.getSelectionModel().getSelectedItem() == null) {popupWindow("Error", "No league Selected.","Please select a league to change name."); return; }
        leagueTableAdmin.getSelectionModel().getSelectedItem().setName(leagueEditName.getText());
        leagueEditName.clear();
        admin.saveLeagues();
        updateLeaguesTableView();
        updateleagueChoiceBoxTeamsTab();
    }
    /**
     * Deletes a League. Gets league selection from the League TableView in 
     * League tab. Includes confirmation pop-up window.
     * @param event Delete league button press.
     */
    public void deleteLeague(ActionEvent event) {
        League selection = leagueTableAdmin.getSelectionModel().getSelectedItem();
        //Check input is present
        if (selection == null) { 
            popupWindow(); 
            return; }
        //Confirm delete
        if (popupWindowChoice(("Delete " + selection.getName() +"?"), "WARNING: This Action Cannot be Undone!", "This will delete all teams, players & fixtures in this league!"))   {
            admin.removeLeague(selection);
            admin.saveLeagues();
            updateLeaguesTableView();
            updateleagueChoiceBoxTeamsTab();
            updateleagueChoiceBoxFixturesTab();
        }
    }
    
    //Converts League ArrayList to Observable list, for TableView.
    private ObservableList<League> listLeagues(ArrayList<League> input)    {
        ObservableList<League> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    /***********************/
    /** Teams Tab Methods **/
    /***********************/
    
    /**
     * initialises the UI elements and data structures in 'Teams' tab.
     */
    public void initializeTeamsTab() {
        //Set values for TableView
        teamNameAdmin.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamPointsAdmin.setCellValueFactory(new PropertyValueFactory<>("points"));
        teamPlayersAdmin.setCellValueFactory(new PropertyValueFactory<>("playersCount"));
        
        //Populate UI elements with data
        updateleagueChoiceBoxTeamsTab();
        updateTeamsTableView();
        
        //Listener for League selection in Choice Box.
        leagueChoiceBoxTeamsTab.setOnAction((event) -> {
            updateTeamsTableView();
            teamInfoLabels.setText("");
            teamInfoVariables.setText("");
            teamTableAdmin.getSelectionModel().selectFirst();
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
    /**
     * Updates the teamTableAdmin TableView in the 'Teams' tab. For use after 
     * adding or removing players or teams.
     */
    public void updateTeamsTableView() {
        teamTableAdmin.getItems().clear();
        String leagueChoiceBoxTeamsTabSelection = leagueChoiceBoxTeamsTab.getSelectionModel().getSelectedItem();
        ArrayList<League> leagues = admin.getLeagues();
        if (leagueChoiceBoxTeamsTabSelection!=null) {
            leagues.stream().filter(temp -> (leagueChoiceBoxTeamsTabSelection.equals(temp.getName()))).forEachOrdered(temp -> {
                leagueSelectionTeamsTab = temp;
            });
            teamTableAdmin.setItems(listTeams(leagueSelectionTeamsTab.getTeams()));
        }
        else {
            leagueChoiceBoxTeamsTab.setValue("No Leagues Added");
        }
    }
    //Populate League choice box from League ArrayList
    public void updateleagueChoiceBoxTeamsTab() {
        leagueChoiceBoxTeamsTab.getItems().clear();
        admin.viewLeagues().forEach(choices -> {
            leagueChoiceBoxTeamsTab.getItems().add(choices);
        });
        try{
            leagueChoiceBoxTeamsTab.setValue(admin.viewLeagues().get(0));
            System.out.println("Selected League " + admin.viewLeagues().get(0) + " in choice box");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("No leagues to load");            
        }
    }
    
    /**
     * Adds a Team to selected League. League selection comes from 
     * leagueChoiceBoxTeamsTab in 'Teams' tab.
     * @param event Add Team button press OR teamNameIn TextField return.
     */
    public void addTeam(ActionEvent event) {
        if (isEmptyError(teamNameIn.getText()))   {  return;  }
        admin.addTeam(leagueSelectionTeamsTab, teamNameIn.getText());
        teamNameIn.clear();
        admin.saveLeagues();
        updateTeamsTableView();
        updateLeaguesTableView();
    }
    /**
     * Changes a selected Team's name. Team selection is from teamTableAdmin
     * TableView JavaFX element in 'Teams' tab.
     * @param event Rename Team button press OR teamEditName text field return.
     */
    public void editTeamName(ActionEvent event) {
        if (isEmptyError(teamEditName.getText()))   {  return;  }
        teamTableAdmin.getSelectionModel().getSelectedItem().setName(teamEditName.getText());
        
        teamEditName.clear();
        admin.saveLeagues();
        updateTeamsTableView();
    }
    /**
     * Deletes a Team. Team selection is from teamTableAdmin TableView JavaFX 
     * element in 'Teams' tab. Includes confirmation pop-up window.
     * @param event Delete Selected Team button press.
     */
    public void deleteTeam(ActionEvent event) {
        Team selection = teamTableAdmin.getSelectionModel().getSelectedItem();
        if (selection == null) { popupWindow(); return;}
        if (popupWindowChoice(("Delete " + selection.getName() +"?"), "WARNING: This Action Cannot be Undone!", "This will also delete all players in this team!"))   {
            admin.removeTeam(leagueSelectionTeamsTab, selection);
            admin.saveLeagues();
            updateTeamsTableView();
            updateLeaguesTableView();
        }
    }
    /**
     * Updates the two team information labels (teamInfoLabels and 
     * teamInfoVariables) with the selected teams information.
     * @param team Selected team in teamTableAdmin.
     */
    public void displayTeamInfo(Team team)   {
        ArrayList<Player> players = team.getPlayers();
        ArrayList<String> names = new ArrayList<>();
        if (players != null) {
            players.forEach(player -> {
                names.add(player.getName());
            });
        }
        
        teamInfoLabels.setText("""
                Name:
                Matches Played:
                Matches Won:
                Matches Lost:
                Matches Drawn:
                Total Points:
                Home Venue:
                Players:"""); 
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
        team.getPlayers().forEach(chosenPlayer -> {
            playerChoiceBox.getItems().add(chosenPlayer.getName());
        });
        if (!team.getPlayers().isEmpty()) {
            playerChoiceBox.setValue(team.getPlayers().get(0).getName());
        }
        else {
        playerChoiceBox.setValue("No Players in Team");
        }
    }
    /**
     * Updates the selected team's (in teamTableAdmin Table View) venue string
     * with the input from venueNameIn text field.
     * @param event Update Venue button press OR venueNameIn text field return.
     */
    public void updateVenue(ActionEvent event) {
        if (isEmptyError(venueNameIn.getText()))   {  return;  }
        admin.updateVenue(teamSelection, venueNameIn.getText());
        displayTeamInfo(teamSelection);
        venueNameIn.clear();
        admin.saveLeagues();
    }
    //Converts Team ArrayList to Observable list, for TableView.
    private ObservableList<Team> listTeams(ArrayList<Team> input)    {
        ObservableList<Team> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    /********************/
    /** Player Methods **/
    /********************/
    
    /**
     * Adds a Player to selected Team. Player name comes from playerNameIn
     * TextField in 'Teams' tab.
     * @param event Add New Player button OR playerNameIn TextField return.
     */
    public void addPlayer(ActionEvent event) {
        if (isEmptyError(playerNameIn.getText()))   {  return;  }
        admin.addPlayer(teamSelection, playerNameIn.getText());
        teamNameIn.clear();
        admin.saveLeagues();
        displayTeamInfo(teamSelection);
        updateTeamsTableView();
        playerNameIn.clear();
    }
    /**
     * Deletes a Player from selected Team. Team selection comes from 
     * playerChoiceBox in 'Teams' tab. Includes pop-up window confirmation.
     * @param event Delete Player button.
     */
    public void deletePlayer(ActionEvent event) {
        Player playerSelection = null;
        for (Player player : teamSelection.getPlayers()) {
            if (playerChoiceBox.getSelectionModel().getSelectedItem().equals(player.getName())) {
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
    
    /**************************/
    /** Fixtures Tab Methods **/
    /**************************/
    
    public void initializeFixturesTab() {
        //Set values for TableView
        fixtureWeekAdmin.setCellValueFactory(new PropertyValueFactory<>("week"));
        fixtureTeamsAdmin.setCellValueFactory(new PropertyValueFactory<>("teams"));
        fixturePlayedAdmin.setCellValueFactory(new PropertyValueFactory<>("played"));
        
        //Populate UI elements with data
        updateleagueChoiceBoxFixturesTab();
        updateFixturesTableView();
        
        //Listener for League selection in Choice Box.
        leagueChoiceBoxFixturesTab.setOnAction((event) -> {
            updateFixturesTableView();
            fixtureInfoLabels.setText("");
            fixtureInfoVariables.setText("");
            fixtureTableAdmin.getSelectionModel().selectFirst();
        });
        
        //Listener for when a Fixture is selected in TableView.
        fixtureTableAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fixtureSelection = fixtureTableAdmin.getSelectionModel().getSelectedItem();
                System.out.println(fixtureSelection.getTeams());
                //displayFixtureInfo(fixtureSelection);
            }
        });
        fixtureTableAdmin.getSelectionModel().selectFirst();
    }
    
    public void updateleagueChoiceBoxFixturesTab() {
        leagueChoiceBoxFixturesTab.getItems().clear();
        admin.viewLeagues().forEach(choices -> {
            leagueChoiceBoxFixturesTab.getItems().add(choices);
        });
        try{
            leagueChoiceBoxFixturesTab.setValue(admin.viewLeagues().get(0));
            System.out.println("Selected League " + admin.viewLeagues().get(0) + " in choice box");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("No leagues to load");            
        }
    }
    
    public void updateFixturesTableView() {
        fixtureTableAdmin.getItems().clear();
        String leagueChoiceBoxFixturesTabSelection = leagueChoiceBoxFixturesTab.getSelectionModel().getSelectedItem();
        ArrayList<League> leagues = admin.getLeagues();
        if (leagueChoiceBoxFixturesTabSelection!=null) {
            leagues.stream().filter(temp -> (leagueChoiceBoxFixturesTabSelection.equals(temp.getName()))).forEachOrdered(temp -> {
                leagueSelectionFixturesTab = temp;
            });
            fixtureTableAdmin.setItems(listFixtures(leagueSelectionFixturesTab.getFixtures()));
        }
        else {
            leagueChoiceBoxTeamsTab.setValue("No Leagues Added");
        }
    }
    
    public void generateFixtures(ActionEvent event) {
        if (popupWindowChoice("Overwrite " + leagueSelectionFixturesTab.getName() + " fixtures?", "This will replace ALL current fixtures in this league", "Are you sure?")) {
            System.out.println("League Selected: " + leagueSelectionFixturesTab.getName());
            admin.generateFixtures(leagueSelectionFixturesTab);
            updateFixturesTableView();
        }
    }
    
    //Converts Fixture ArrayList to Observable list, for TableView.
    private ObservableList<Fixture> listFixtures(ArrayList<Fixture> input)    {
        ObservableList<Fixture> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    
    
    /**
     * Checks if String is empty. ONLY used by UI controllers for checking user 
     * inputs before processing.
     * @param in String
     * @return True - Empty String (creates pop up error window), 
     * False - Not Empty String.
     */
    protected boolean isEmptyError(String in) {
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

