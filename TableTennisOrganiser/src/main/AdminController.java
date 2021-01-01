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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
/**
 * <h1>Admin GUI Controller Class</h1>
 * Controls FMXL GUI elements for Admin class.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
 */
public class AdminController extends UserController implements Initializable{
    
    /** Leagues Tab **/
    @FXML private TextField textFieldLeaguesTabUpdateName;
    @FXML private TextField textFieldLeaguesTabCreateName;
    @FXML private TableView <League> tableViewLeaguesTab;
    @FXML private TableColumn <League, String> tableViewLeaguesTabNameColumn;
    @FXML private TableColumn <League, Integer> tableViewLeaguesTabTeamsColumn;
    @FXML private TableColumn <League, Integer> tableViewLeaguesTabFixturesColumn;
    
    /** Teams Tab **/
    @FXML private ChoiceBox<String> choiceBoxTeamsTabLeague;
    @FXML private ChoiceBox<String> choiceBoxTeamsTabPlayer;
    @FXML private TextField textFieldTeamsTabTeamUpdateName;
    @FXML private TextField textFieldTeamsTabTeamCreateName;
    @FXML private TextField textFieldTeamsTabVenueUpdateName;
    @FXML private TextField textFieldTeamsTabPlayerCreateName;
    @FXML private TableView <Team> tableViewTeamsTab;
    @FXML private TableColumn <Team, String> tableViewTeamsTabNameColumn;
    @FXML private TableColumn <Team, Integer> tableViewTeamsTabPointsColumn;
    @FXML private TableColumn <Team, Integer> tableViewTeamsTabPlayersColumn;
    @FXML private Label labelTeamsTabTeamInfoLabels;
    @FXML private Label labelTeamsTabTeamInfoVariables;
    
    /** Fixtures Tab **/
    @FXML private ChoiceBox<String> choiceBoxFixturesTabLeague;
    @FXML private TableView <Fixture> tableViewFixturesTab;
    @FXML private TableColumn <Fixture, String> tableViewFixturesTabWeekColumn;
    @FXML private TableColumn <Fixture, Integer> tableViewFixturesTabHomeColumn;
    @FXML private TableColumn <Fixture, String> tableViewFixturesTabVsColumn;
    @FXML private TableColumn <Fixture, Integer> tableViewFixturesTabAwayColumn;
    @FXML private TableColumn <Fixture, Integer> tableViewFixturesTabVenueColumn;
    @FXML private TableColumn <Fixture, Integer> tableViewFixturesTabPlayedColumn;
    
    @FXML private CheckBox homeAndAway;
    
    /** Score Sheet **/
    @FXML private ChoiceBox<String> choiceBoxFixturesTabHomePlayer1;
    @FXML private ChoiceBox<String> choiceBoxFixturesTabHomePlayer2;
    
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
        tableViewLeaguesTabFixturesColumn.setCellValueFactory(new PropertyValueFactory<>("fixturesCount"));
        tableViewLeaguesTabTeamsColumn.setCellValueFactory(new PropertyValueFactory<>("teamsCount"));
        tableViewLeaguesTabNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        updateLeaguesTableView();

        //Listener for when a League is selected in TableView.
        tableViewLeaguesTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                League selected = tableViewLeaguesTab.getSelectionModel().getSelectedItem();
                System.out.println(selected.getName());               
            }
        });
    }
    /**
     * Refreshes the League TableView JavaFX element. For use after adding or 
     * removing a league OR team.
     */
    public void updateLeaguesTableView() {
        tableViewLeaguesTab.getItems().clear();
        tableViewLeaguesTab.setItems(listLeagues(admin.getLeagues()));
    }
    /**
     * Adds a League. Reads league name from leagueNameIn JavaFX TextField.
     * @param event Add League button press OR LeagueNameIn text field return.
     */
    public void createLeague(ActionEvent event) {
        if (isEmptyError(textFieldLeaguesTabCreateName.getText()))   {  return;  }
        admin.addLeague(textFieldLeaguesTabCreateName.getText());
        textFieldLeaguesTabCreateName.clear();
        choiceBoxTeamsTabLeague.getSelectionModel().clearSelection();
        admin.saveLeagues();
        tableViewTeamsTab.getSelectionModel().selectFirst();
        updateleagueChoiceBoxTeamsTab();
        updateLeagueChoiceBoxFixturesTab();
        updateTeamsTableView();
        updateLeaguesTableView();
    }
    /**
     * Changes a selected League's name. Selection is from leagueTableAdmin
     * TableView JavaFX element.
     * @param event Change Name button press OR textFieldLeaguesTabUpdateName text field return
     */
    public void updateLeagueName(ActionEvent event) {
        if (isEmptyError(textFieldLeaguesTabUpdateName.getText()))   {  return;  }
        if (tableViewLeaguesTab.getSelectionModel().getSelectedItem() == null) {popupWindow("Error", "No league Selected.","Please select a league to change name."); return; }
        tableViewLeaguesTab.getSelectionModel().getSelectedItem().setName(textFieldLeaguesTabUpdateName.getText());
        textFieldLeaguesTabUpdateName.clear();
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
        League selection = tableViewLeaguesTab.getSelectionModel().getSelectedItem();
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
            updateLeagueChoiceBoxFixturesTab();
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
        tableViewTeamsTabNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewTeamsTabPointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        tableViewTeamsTabPlayersColumn.setCellValueFactory(new PropertyValueFactory<>("playersCount"));
        
        //Populate UI elements with data
        updateleagueChoiceBoxTeamsTab();
        updateTeamsTableView();
        
        //Listener for League selection in Choice Box.
        choiceBoxTeamsTabLeague.setOnAction((event) -> {
            updateTeamsTableView();
            labelTeamsTabTeamInfoLabels.setText("");
            labelTeamsTabTeamInfoVariables.setText("");
            tableViewTeamsTab.getSelectionModel().selectFirst();
        });
        
        //Listener for when a Team is selected in TableView.
        tableViewTeamsTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                teamSelection = tableViewTeamsTab.getSelectionModel().getSelectedItem();
                System.out.println(teamSelection.getName());
                displayTeamInfo(teamSelection);
            }
        });
        tableViewTeamsTab.getSelectionModel().selectFirst();
    }
    /**
     * Updates the teamTableAdmin TableView in the 'Teams' tab. For use after 
     * adding or removing players or teams.
     */
    public void updateTeamsTableView() {
        tableViewTeamsTab.getItems().clear();
        String leagueChoiceBoxTeamsTabSelection = choiceBoxTeamsTabLeague.getSelectionModel().getSelectedItem();
        ArrayList<League> leagues = admin.getLeagues();
        if (leagueChoiceBoxTeamsTabSelection!=null) {
            leagues.stream().filter(temp -> (leagueChoiceBoxTeamsTabSelection.equals(temp.getName()))).forEachOrdered(temp -> {
                leagueSelectionTeamsTab = temp;
            });
            tableViewTeamsTab.setItems(listTeams(leagueSelectionTeamsTab.getTeams()));
        }
        else {
            //leagueChoiceBoxTeamsTab.setValue("No Leagues Added");
        }
    }
    //Populate League choice box from League ArrayList
    public void updateleagueChoiceBoxTeamsTab() {
        choiceBoxTeamsTabLeague.getItems().clear();
        admin.viewLeagues().forEach(choices -> {
            choiceBoxTeamsTabLeague.getItems().add(choices);
        });
        try{
            choiceBoxTeamsTabLeague.setValue(admin.viewLeagues().get(0));
            System.out.println("Selected League " + admin.viewLeagues().get(0) + " in choice box");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("No leagues to load");            
        }
    }
    
    /**
     * Adds a Team to selected League. League selection comes from 
     * leagueChoiceBoxTeamsTab in 'Teams' tab.
     * @param event Add Team button press OR textFieldTeamsTabTeamCreateName TextField return.
     */
    public void createTeam(ActionEvent event) {
        if (isEmptyError(textFieldTeamsTabTeamCreateName.getText()))   {  return;  }
        admin.addTeam(leagueSelectionTeamsTab, textFieldTeamsTabTeamCreateName.getText());
        textFieldTeamsTabTeamCreateName.clear();
        admin.saveLeagues();
        updateTeamsTableView();
        updateLeaguesTableView();
        updateFixturesTableView();
    }
    /**
     * Changes a selected Team's name. Team selection is from teamTableAdmin
     * TableView JavaFX element in 'Teams' tab.
     * @param event Rename Team button press OR textFieldTeamsTabTeamUpdateName text field return.
     */
    public void updateTeamName(ActionEvent event) {
        if (isEmptyError(textFieldTeamsTabTeamUpdateName.getText()))   {  return;  }
        tableViewTeamsTab.getSelectionModel().getSelectedItem().setName(textFieldTeamsTabTeamUpdateName.getText());
        
        textFieldTeamsTabTeamUpdateName.clear();
        admin.saveLeagues();
        updateTeamsTableView();
    }
    /**
     * Deletes a Team. Team selection is from teamTableAdmin TableView JavaFX 
     * element in 'Teams' tab. Includes confirmation pop-up window.
     * @param event Delete Selected Team button press.
     */
    public void deleteTeam(ActionEvent event) {
        Team selection = tableViewTeamsTab.getSelectionModel().getSelectedItem();
        if (selection == null) { popupWindow(); return;}
        if (popupWindowChoice(("Delete " + selection.getName() +"?"), "WARNING: This Action Cannot be Undone!", "This will also delete all players in this team!"))   {
            admin.removeTeam(leagueSelectionTeamsTab, selection);
            admin.saveLeagues();
            updateTeamsTableView();
            updateLeaguesTableView();
        }
    }
    /**
     * Updates the two team information labels (labelTeamsTabTeamInfoLabels and 
 labelTeamsTabTeamInfoVariables) with the selected teams information.
     * @param team Selected team in tableViewTeamsTab.
     */
    public void displayTeamInfo(Team team)   {
        ArrayList<Player> players = team.getPlayers();
        ArrayList<String> names = new ArrayList<>();
        if (players != null) {
            players.forEach(player -> {
                names.add(player.getName());
            });
        }
        
        labelTeamsTabTeamInfoLabels.setText("""
                Name:
                Matches Played:
                Matches Won:
                Matches Lost:
                Matches Drawn:
                Total Points:
                Home Venue:
                Players:"""); 
        labelTeamsTabTeamInfoVariables.setText(
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
        choiceBoxTeamsTabPlayer.getItems().clear();
        team.getPlayers().forEach(chosenPlayer -> {
            choiceBoxTeamsTabPlayer.getItems().add(chosenPlayer.getName());
        });
        if (!team.getPlayers().isEmpty()) {
            choiceBoxTeamsTabPlayer.setValue(team.getPlayers().get(0).getName());
        }
        else {
        choiceBoxTeamsTabPlayer.setValue("No Players in Team");
        }
    }
    /**
     * Updates the selected team's (in tableViewTeamsTab Table View) venue string
 with the input from textFieldTeamsTabPlayerCreateName text field.
     * @param event Update Venue button press OR textFieldTeamsTabPlayerCreateName text field return.
     */
    public void updateVenue(ActionEvent event) {
        if (isEmptyError(textFieldTeamsTabVenueUpdateName.getText()))   {  return;  }
        admin.updateVenue(teamSelection, textFieldTeamsTabVenueUpdateName.getText());
        displayTeamInfo(teamSelection);
        textFieldTeamsTabVenueUpdateName.clear();
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
     * @param event Add New Player button OR textFieldTeamsTabVenueUpdateName TextField return.
     */
    public void createPlayer(ActionEvent event) {
        if (isEmptyError(textFieldTeamsTabPlayerCreateName.getText()))   {  return;  }
        admin.addPlayer(teamSelection, textFieldTeamsTabPlayerCreateName.getText());
        textFieldTeamsTabPlayerCreateName.clear();
        admin.saveLeagues();
        displayTeamInfo(teamSelection);
        updateTeamsTableView();
        textFieldTeamsTabPlayerCreateName.clear();
    }
    /**
     * Deletes a Player from selected Team. Team selection comes from 
     * playerChoiceBox in 'Teams' tab. Includes pop-up window confirmation.
     * @param event Delete Player button.
     */
    public void deletePlayer(ActionEvent event) {
        Player playerSelection = null;
        for (Player player : teamSelection.getPlayers()) {
            if (choiceBoxTeamsTabPlayer.getSelectionModel().getSelectedItem().equals(player.getName())) {
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
        tableViewFixturesTabWeekColumn.setCellValueFactory(new PropertyValueFactory<>("week"));
        tableViewFixturesTabHomeColumn.setCellValueFactory(new PropertyValueFactory<>("homeTeamName"));
        tableViewFixturesTabAwayColumn.setCellValueFactory(new PropertyValueFactory<>("awayTeamName"));
        tableViewFixturesTabVenueColumn.setCellValueFactory(new PropertyValueFactory<>("venue"));
        tableViewFixturesTabPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("played"));
        tableViewFixturesTabVsColumn.setCellValueFactory(new PropertyValueFactory<>("separator"));
        
        //Populate UI elements with data
        updateLeagueChoiceBoxFixturesTab();
        updateFixturesTableView();
        
        //Listener for League selection in Choice Box.
        choiceBoxFixturesTabLeague.setOnAction((event) -> {
            updateFixturesTableView();
            tableViewFixturesTab.getSelectionModel().selectFirst();
        });
        
        //Listener for when a Fixture is selected in TableView.
        tableViewFixturesTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fixtureSelection = tableViewFixturesTab.getSelectionModel().getSelectedItem();
                System.out.println(fixtureSelection.getTeams());
                //displayFixtureInfo(fixtureSelection);
            }
        });
        tableViewFixturesTab.getSelectionModel().selectFirst();
    }
    
    public void updateLeagueChoiceBoxFixturesTab() {
        choiceBoxFixturesTabLeague.getItems().clear();
        admin.viewLeagues().forEach(choices -> {
            choiceBoxFixturesTabLeague.getItems().add(choices);
        });
        try{
            choiceBoxFixturesTabLeague.setValue(admin.viewLeagues().get(0));
            System.out.println("Selected League " + admin.viewLeagues().get(0) + " in choice box");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("No leagues to load");            
        }
    }
    
    public void updateFixturesTableView() {
        tableViewFixturesTab.getItems().clear();
        String leagueChoiceBoxFixturesTabSelection = choiceBoxFixturesTabLeague.getSelectionModel().getSelectedItem();
        ArrayList<League> leagues = admin.getLeagues();
        if (leagueChoiceBoxFixturesTabSelection!=null) {
            leagues.stream().filter(temp -> (leagueChoiceBoxFixturesTabSelection.equals(temp.getName()))).forEachOrdered(temp -> {
                leagueSelectionFixturesTab = temp;
            });
            tableViewFixturesTab.setItems(listFixtures(leagueSelectionFixturesTab.getFixtures()));
        }
        else {
            //leagueChoiceBoxTeamsTab.setValue("No Leagues Added");
        }
    }
    
    public void generateFixtures(ActionEvent event) {
        boolean homeAway = false;
        if (homeAndAway.isSelected()) {
            homeAway = true;
        }
        if (leagueSelectionFixturesTab.getTeamsCount() < 2) {
            popupWindow("Error", "Not enough teams!", "Please add 2 or more teams to generate fixtures.");
        }
        if (popupWindowChoice("Overwrite " + leagueSelectionFixturesTab.getName() + " fixtures?", "This will replace ALL current fixtures in this league", "Are you sure?")) {
            System.out.println("League Selected: " + leagueSelectionFixturesTab.getName());
            leagueSelectionFixturesTab.resetFixtures();
            admin.generateFixtures(leagueSelectionFixturesTab, homeAway, "home");
            admin.saveLeagues();
            updateFixturesTableView();
            updateLeaguesTableView();
        }
    }
    
    public void fixturesHelp(ActionEvent event) {
        popupWindowInformation();
    }
    
    public void deleteFixtures(ActionEvent event) {
        if (popupWindowChoice("Delete " + leagueSelectionFixturesTab.getName() + " fixtures?", "This will DELETE ALL current fixtures in this league", "Are you sure?")) {
        admin.deleteFixtures(leagueSelectionFixturesTab);
        admin.saveLeagues();
        updateFixturesTableView();
        updateLeaguesTableView();
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

