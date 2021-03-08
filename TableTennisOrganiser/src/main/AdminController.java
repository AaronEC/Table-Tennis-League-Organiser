package main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
/**
 * <h1>Admin GUI Controller Class</h1>
 * Controls FMXL GUI elements for <code>Admin</code> class.
 * Sub sections:
 * 1. Leagues Tab Methods
 * 2. Teams Tab Methods
 * 3. Player/Venue Methods (Teams Tab)
 * 4. Fixtures Tab Methods
 * 5. Score Sheet Methods (Fixtures Tab)
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public class AdminController extends UserController implements Initializable{
    
//These are PROTECTED as ViewerController sub-class requires access too.
    
    /** Leagues Tab **/
    @FXML protected TextField textFieldLeaguesTabUpdateName;
    @FXML protected TextField textFieldLeaguesTabCreateName;
    @FXML protected TableView <League> tableViewLeaguesTab;
    @FXML protected TableColumn <League, String> tableViewLeaguesTabNameColumn;
    @FXML protected TableColumn <League, Integer> tableViewLeaguesTabTeamsColumn;
    @FXML protected TableColumn <League, Integer> tableViewLeaguesTabFixturesColumn;
    
    /** Teams Tab **/
    @FXML protected ChoiceBox<String> choiceBoxTeamsTabLeague;
    @FXML protected ChoiceBox<String> choiceBoxTeamsTabPlayer;
    @FXML protected TextField textFieldTeamsTabTeamUpdateName;
    @FXML protected TextField textFieldTeamsTabTeamCreateName;
    @FXML protected TextField textFieldTeamsTabVenueUpdateName;
    @FXML protected TextField textFieldTeamsTabPlayerCreateName;
    @FXML protected TableView <Team> tableViewTeamsTab;
    @FXML protected TableColumn <Team, String> tableViewTeamsTabNameColumn;
    @FXML protected TableColumn <Team, Integer> tableViewTeamsTabPointsColumn;
    @FXML protected TableColumn <Team, Integer> tableViewTeamsTabPlayedColumn;
    @FXML protected TableColumn <Team, Integer> tableViewTeamsTabSetsColumn;
    @FXML protected Label labelTeamsTabTeamInfoLabels;
    @FXML protected Label labelTeamsTabTeamInfoVariables;
    
    /** Fixtures Tab **/
    @FXML protected ChoiceBox<String> choiceBoxFixturesTabLeague;
    @FXML protected TableView <Fixture> tableViewFixturesTab;
    @FXML protected TableColumn <Fixture, String> tableViewFixturesTabWeekColumn;
    @FXML protected TableColumn <Fixture, Integer> tableViewFixturesTabHomeColumn;
    @FXML protected TableColumn <Fixture, String> tableViewFixturesTabVsColumn;
    @FXML protected TableColumn <Fixture, Integer> tableViewFixturesTabAwayColumn;
    @FXML protected TableColumn <Fixture, Integer> tableViewFixturesTabVenueColumn;
    @FXML protected TableColumn <Fixture, Integer> tableViewFixturesTabPlayedColumn;
    @FXML protected CheckBox checkBoxFixturesTabHomeAway;
    
    /** Score Sheet **/
    @FXML protected ChoiceBox<String> choiceBoxFixturesTabHomeTeam;
    @FXML protected ChoiceBox<String> choiceBoxFixturesTabAwayTeam;
    @FXML protected ChoiceBox<String> choiceBoxFixturesTabHomePlayer1;
    @FXML protected ChoiceBox<String> choiceBoxFixturesTabHomePlayer2;
    @FXML protected ChoiceBox<String> choiceBoxFixturesTabAwayPlayer1;
    @FXML protected ChoiceBox<String> choiceBoxFixturesTabAwayPlayer2;
    @FXML protected CheckBox checkBoxFixturesTabPlayed;
    @FXML protected TextField textFieldFixturesTabWeek;
    @FXML protected TextField textFieldFixturesTabVenue;
    @FXML protected TextArea resultsText;
    
    /** Score Grid Panes **/
    @FXML protected GridPane scoreGrid = new GridPane();
    
    /** Class Variables **/
    protected League leagueSelectionTeamsTab;
    protected League leagueSelectionFixturesTab;
    protected Team homeTeamSelectionFixturesTab;
    protected Team awayTeamSelectionFixturesTab;
    protected Team teamSelection;
    protected Fixture fixtureSelection;

    /**
     * Initialises the UI elements and <code>Admin</code> data structures.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load leagues from searalised .bin file.
        try {
            admin.loadLeagues();
        } catch (ClassNotFoundException ex) {
            System.err.println("Admin class creation error");
        }
        admin.countFixtures();
        
        // Initialise the UI elements of each tab controlled by this class.
        initializeLeaguesTab();
        initializeTeamsTab();
        initializeFixturesTab();
        
        // Initialize listeners (Used for mouse-click and selection events)
        
        //Listener for League selection in teams tab Choice Box.
        choiceBoxTeamsTabLeague.setOnAction((event) -> {
            updateTeamsTableView();
            labelTeamsTabTeamInfoLabels.setText("");
            tableViewTeamsTab.getSelectionModel().selectFirst();
        });
        //Listener for when a Team is selected in teams tab TableView.
        tableViewTeamsTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("New team selected");
                teamSelection = tableViewTeamsTab.getSelectionModel().getSelectedItem();
                displayTeamInfo(teamSelection);
            }
        });
        //Listener for League selection in Fixtures Tab choice box.
        choiceBoxFixturesTabLeague.setOnAction((event) -> {
            admin.getLeagues().forEach(league -> {
                if (league.getName() == choiceBoxFixturesTabLeague.getSelectionModel().getSelectedItem()) {
                    leagueSelectionFixturesTab = league;
                    //System.out.println("League selected in fixures tab: " + league.getName());
                    updateFixturesTableView();
                }
            });
            tableViewFixturesTab.getSelectionModel().selectFirst();
        });
        
        //Listener for Home Team selection in Choice Box.
        choiceBoxFixturesTabHomeTeam.setOnAction((event) -> {
            leagueSelectionTeamsTab.getTeams().forEach(team -> {
                if (team.getName().equals(choiceBoxFixturesTabHomeTeam.getSelectionModel().getSelectedItem())) {
                    homeTeamSelectionFixturesTab = team;
                    //System.out.println("Home Team Selection: " + team.getName());
                    fixtureSelection.setHomeTeam(team);
                    updateFixturesTableView();
                }
            });
        });
        //Listener for Away Team selection in Choice Box.
        choiceBoxFixturesTabAwayTeam.setOnAction((event) -> {
            leagueSelectionTeamsTab.getTeams().forEach(team -> {
                if (team.getName().equals(choiceBoxFixturesTabAwayTeam.getSelectionModel().getSelectedItem())) {
                    awayTeamSelectionFixturesTab = team;
                    //System.out.println("Away Team Selection: " + team.getName());
                    fixtureSelection.setAwayTeam(team);
                    updateFixturesTableView();
                }
            });
        });
        //Listener for Home Player 1 ChoiceBox.
        choiceBoxFixturesTabHomePlayer1.setOnAction((event) -> {
            int value = choiceBoxFixturesTabHomePlayer1.getSelectionModel().getSelectedIndex();
            fixtureSelection.setPlayerSelections(0, value);
        });
        //Listener for Home Player 2 ChoiceBox.
        choiceBoxFixturesTabHomePlayer2.setOnAction((event) -> {
            int value = choiceBoxFixturesTabHomePlayer2.getSelectionModel().getSelectedIndex();
            fixtureSelection.setPlayerSelections(1, value);
        });
        //Listener for Away Player 1 ChoiceBox.
        choiceBoxFixturesTabAwayPlayer1.setOnAction((event) -> {
            int value = choiceBoxFixturesTabAwayPlayer1.getSelectionModel().getSelectedIndex();
            fixtureSelection.setPlayerSelections(2, value);
        });
        //Listener for Away Player 2 ChoiceBox.
        choiceBoxFixturesTabAwayPlayer2.setOnAction((event) -> {
            int value = choiceBoxFixturesTabAwayPlayer2.getSelectionModel().getSelectedIndex();
            fixtureSelection.setPlayerSelections(3, value);
        });
        
        //Listener for when a Fixture is selected in fixtures tab TableView.
        tableViewFixturesTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fixtureSelection = tableViewFixturesTab.getSelectionModel().getSelectedItem();
                //System.out.println("Fixture Selected: " + fixtureSelection.getTeams());
                updateFixtureInfo(fixtureSelection);
            }
        });
        
        //Auto select first item in tables (looks nicer and can prevent some 
        //null pointer exceptions, but they should be handled anyway).
        tableViewTeamsTab.getSelectionModel().selectFirst();
        tableViewFixturesTab.getSelectionModel().selectFirst();
    }
    
    /****************************/
    /** 1. Leagues Tab Methods **/
    /****************************/
    
    /**
     * initialises the UI elements in leagues tab.
     */
    public void initializeLeaguesTab()  {
        tableViewLeaguesTabFixturesColumn.setCellValueFactory(new PropertyValueFactory<>("fixturesCount"));
        tableViewLeaguesTabTeamsColumn.setCellValueFactory(new PropertyValueFactory<>("teamsCount"));
        tableViewLeaguesTabNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        updateLeaguesTableView();
    }
    /**
     * Refreshes the League TableView JavaFX element. For use after adding or 
     * removing a <code>League</code> OR <code>Team</code>.
     */
    public void updateLeaguesTableView() {
        tableViewLeaguesTab.getItems().clear();
        tableViewLeaguesTab.setItems(listLeagues(admin.getLeagues()));
    }
    /**
     * Adds a <code>League</code> with <code>name</code> from 
     * <code>textFieldLeaguesTabCreateName</code>.
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
     * Changes a selected <code>League</code> object's <code>name</code> 
     * variable with input from <code>textFieldLeaguesTabUpdateName</code>.
     * @param event Change Name button press OR textFieldLeaguesTabUpdateName 
     * text field return
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
     * Deletes the <code>League</code> currently selected in the 
     * <code>tableViewLeaguesTab</code>. Includes confirmation pop-up window.
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
    
    /**************************/
    /** 2. Teams Tab Methods **/
    /**************************/
    
    /**
     * Initialises the UI elements in teams tab.
     */
    public void initializeTeamsTab() {
        //Set values for TableView
        tableViewTeamsTabNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewTeamsTabPointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        tableViewTeamsTabPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("matchesPlayed"));
        tableViewTeamsTabSetsColumn.setCellValueFactory(new PropertyValueFactory<>("setsWon"));
        
        //Populate UI elements with data
        updateleagueChoiceBoxTeamsTab();
        updateTeamsTableView();
    }
    
    /**
     * Updates/refreshes <code>tableViewTeamsTab</code> in the teams tab.
     */
    public void updateTeamsTableView() {
        tableViewTeamsTab.getItems().clear();
        String leagueChoiceBoxTeamsTabSelection = choiceBoxTeamsTabLeague.getSelectionModel().getSelectedItem();
        ArrayList<League> leagues = admin.getLeagues();
        if (leagueChoiceBoxTeamsTabSelection!=null) {
            leagues.stream().filter(temp -> (leagueChoiceBoxTeamsTabSelection.equals(temp.getName()))).forEachOrdered(temp -> {
                leagueSelectionTeamsTab = temp;
                //System.out.println("League selected in teams tab: " + temp.getName());
            });
            tableViewTeamsTab.setItems(listTeams(leagueSelectionTeamsTab.getTeams()));
        }
        else {
            //leagueChoiceBoxTeamsTab.setValue("No Leagues Added");
            System.err.println("No leagues for choice box");
        }
    }
    /**
     * Updates/refreshes <code>choiceBoxTeamsTabLeague</code> in the teams tab.
     */
    public void updateleagueChoiceBoxTeamsTab() {
        choiceBoxTeamsTabLeague.getItems().clear();
        admin.viewLeagues().forEach(choices -> {
            choiceBoxTeamsTabLeague.getItems().add(choices);
        });
        try{
            choiceBoxTeamsTabLeague.setValue(admin.viewLeagues().get(0));
            //System.out.println("Selected League " + admin.viewLeagues().get(0) + " in choice box");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("No leagues to load");            
        }
    }
    
    /**
     * Adds a <code>Team</code> to <code>League</code> currently selected in
     * <code>leagueChoiceBoxTeamsTab</code>.
     * @param event Add Team button press OR textFieldTeamsTabTeamCreateName 
     * TextField return.
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
     * Changes a <code>Team</code> objects <code>name</code> variable selected 
     * in <code>tableViewTeamsTab</code>.
     * @param event Rename Team button press OR textFieldTeamsTabTeamUpdateName 
     * text field return.
     */
    public void updateTeamName(ActionEvent event) {
        if (isEmptyError(textFieldTeamsTabTeamUpdateName.getText()))   {  
            return;  
        }
        tableViewTeamsTab.getSelectionModel().getSelectedItem().setName(textFieldTeamsTabTeamUpdateName.getText());     
        textFieldTeamsTabTeamUpdateName.clear();
        admin.saveLeagues();
        updateTeamsTableView();
    }
    /**
     * Deletes the <code>Team</code> selected in <code>teamTableAdmin</code>. 
     * Includes confirmation pop-up window.
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
     * Updates the two team information labels 
     * (<code>labelTeamsTabTeamInfoLabels</code> and 
     * <code>labelTeamsTabTeamInfoVariables</code>) with <code>Team</code> info.
     * @param team Selected team in <code>tableViewTeamsTab</code>.
     */
    public void displayTeamInfo(Team team)   {
        System.out.println("main.AdminController.displayTeamInfo()");
        ArrayList<Player> players = team.getPlayers();
        String names = "";
        int length = 0;
        if (players != null) {
            for (Player player : players) {
                names += " " + player.getName();
                if (length < players.size()-1) {
                    names += ",";
                } else {
                    names += ".";
                }
                length++;
            }
        }
        labelTeamsTabTeamInfoLabels.setText(
                "Team Overview:\nName:" + tabs(5) + team.getName() +
                "\nHome Venue:" + tabs(4) + team.getVenue() +
                "\nPlayers:" + tabs(5) + names.toString().strip() +
                "\n\nTeam Statistics:\nMatches Won:" + tabs(4) + team.getMatchesWon() +
                "\nMatches Lost:" + tabs(4) + team.getMatchesLost() +
                "\nMatches Played:" + tabs(3) + team.getMatchesPlayed() +
                "\nSets Won:" + tabs(4) + team.getSetsWon() +
                "\nSets Lost:" + tabs(5) + (team.getSetsPlayed() - team.getSetsWon()) +
                "\nSets Played:" + tabs(4) + team.getSetsPlayed()
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
     * Converts <code>Team</code> ArrayList to <code>Team</code> Observable list
     * @param input
     * @return 
     */
    private ObservableList<Team> listTeams(ArrayList<Team> input)    {
        ObservableList<Team> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    /*****************************/
    /** 3. Player/Venue Methods **/
    /*****************************/
    
    /**
     * Adds a <code>Player</code> to selected <code>Team</code> in
     * <code>tableViewTeamsTab</code>.
     * @param event Add New Player button OR textFieldTeamsTabVenueUpdateName 
     * TextField return.
     */
    public void createPlayer(ActionEvent event) {
        if (isEmptyError(textFieldTeamsTabPlayerCreateName.getText()))   {  
            return;  
        }
        admin.addPlayer(teamSelection, textFieldTeamsTabPlayerCreateName.getText());
        textFieldTeamsTabPlayerCreateName.clear();
        admin.saveLeagues();
        displayTeamInfo(teamSelection);
        updateTeamsTableView();
        textFieldTeamsTabPlayerCreateName.clear();
    }
    /**
     * Deletes a <code>Player</code> from selected <code>Team</code> in
     * <code>playerChoiceBox</code>. Includes pop-up window confirmation.
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
    /**
     * Manually calls generateTeamStats(). Updates UI and saves after.
     */
    public void generateTeamStatsManual() {
        admin.generateTeamStats(leagueSelectionTeamsTab);
        updateTeamsTableView();
        admin.saveLeagues();
        popupWindowSuccess("Team Stats Generated!", "Updated statistics for all teams.", "Press OK to continue.");
    }
    
    /**
     * Updates the selected <code>Team</code> in <code>tableViewTeamsTab</code> 
     * <code>venue</code> string with the input from 
     * <code>textFieldTeamsTabPlayerCreateName</code> text field.
     * @param event Update Venue button press OR 
     * textFieldTeamsTabPlayerCreateName text field return.
     */
    public void updateVenue(ActionEvent event) {
        if (isEmptyError(textFieldTeamsTabVenueUpdateName.getText()))   {  return;  }
        admin.updateVenue(teamSelection, textFieldTeamsTabVenueUpdateName.getText());
        displayTeamInfo(teamSelection);
        textFieldTeamsTabVenueUpdateName.clear();
        admin.saveLeagues();
    }
    
    /*****************************/
    /** 4. Fixtures Tab Methods **/
    /*****************************/
    
    /**
     * initialises the UI elements in fixtures tab.
     */
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
    }
    
    /**
     * Creates a blank <code>Fixture</code> in the <code>League</code> selected 
     * in <code>leagueSelectionFixturesTab</code>.
     */
    public void createFixture() {
        fixtureSelection = admin.createFixture(leagueSelectionFixturesTab);
        updateFixturesTableView();
        tableViewFixturesTab.getSelectionModel().selectFirst();
        choiceBoxFixturesTabAwayTeam.setValue("");
    }
    
    /**
     * Updates <code>choiceBoxFixturesTabLeague</code> with all 
     * <code>League</code> objects in the database.
     */
    public void updateLeagueChoiceBoxFixturesTab() {
        choiceBoxFixturesTabLeague.getItems().clear();
        admin.viewLeagues().forEach(choices -> {
            choiceBoxFixturesTabLeague.getItems().add(choices);
        });
        try{
            choiceBoxFixturesTabLeague.setValue(admin.viewLeagues().get(0));
            //System.out.println("Selected League " + admin.viewLeagues().get(0) + " in choice box");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("No leagues to load");            
        }
    }
    
    /**
     * Updates/refreshes <code>tableViewFixturesTab</code>.
     */
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
            choiceBoxFixturesTabLeague.setValue("No Leagues Added");
        }
        tableViewFixturesTab.getSelectionModel().select(fixtureSelection);
    }
    
    /**
     * This generates a league style set of fixtures for the <code>League</code>
     * selected in <code>choiceBoxFixturesTabLeague</code>.
     *  - Each team will play every other team in the league.
     *  - No team will play more than once a week.
     *  - Each team only plays another team once.
     *  - If there are an odd number of teams in the league, a 'bye' will be 
     *  assigned once per week, where it is not possible for that team to play.
     *  - If "Generate home AND away fixtures" is selected, every team will play
     * every other team in both a HOME and AWAY match (doubles the fixtures).
     * @param event Generate Fixtures GUI button press.
     */
    public void generateFixtures(ActionEvent event) {
        boolean homeAway = false;
        if (checkBoxFixturesTabHomeAway.isSelected()) {
            homeAway = true;
        }
        if (leagueSelectionFixturesTab.getTeamsCount() < 2) {
            popupWindow("Error", "Not enough teams!", "Please add 2 or more teams to generate fixtures.");
        }
        if (popupWindowChoice("Overwrite " + leagueSelectionFixturesTab.getName() + " fixtures?", "This will replace ALL current fixtures in this league", "Are you sure?")) {
            //System.out.println("League Selected: " + leagueSelectionFixturesTab.getName());
            leagueSelectionFixturesTab.resetFixtures();
            admin.generateFixtures(leagueSelectionFixturesTab, homeAway, "home");
            admin.generateTeamStats(leagueSelectionFixturesTab);
            admin.saveLeagues();
            updateFixturesTableView();
            updateTeamsTableView();
        }
    }
    /**
     * Pops up a window to explain to the user in more detail how the league 
     * fixtures are generated and what rules the algorithm follows.
     * @param event Help GUI button press.
     */
    public void fixturesHelp(ActionEvent event) {
        popupWindowInformation();
    }
    
    /**
     * Removes ALL <code>fixures</code> from the <code>League</code> selected in
     * <code>choiceBoxFixturesTabLeague</code>.
     * @param event Delete ALL Fixtures GUI button press.
     */
    public void deleteFixtures(ActionEvent event) {
        if (popupWindowChoice("Delete " + leagueSelectionFixturesTab.getName() + " fixtures?", "This will DELETE ALL current fixtures in this league", "Are you sure?")) {
        admin.deleteFixtures(leagueSelectionFixturesTab);
        admin.saveLeagues();
        updateFixturesTableView();
        updateLeaguesTableView();
        }
    }
    /**
     * Removes a single <code>Fixture</code> selected in 
     * <code>tableViewFixturesTab</code>.
     * @param event 
     */
    public void removeFixture(ActionEvent event) {
        if (popupWindowChoice("Delete Fixture?", "This cannot be undone!", "Are you sure you wish to delete this fixture?")) {
            admin.removeFixture(leagueSelectionTeamsTab, fixtureSelection);   
            updateFixturesTableView();
            admin.saveLeagues();
        }
    }
    /**
     * Sets <code>Fixture.week</code> selected in 
     * <code>tableViewFixturesTab</code> to 
     * <code>textFieldFixturesTabWeek</code>. Includes error check (must be int)
     * @param event 
     */
    public void setWeek(ActionEvent event) {
        try {
            fixtureSelection.setWeek(Integer.parseInt(textFieldFixturesTabWeek.getText()));
        } catch (Exception e) {
            popupWindow("Incorrect Input", "Please enter a number.", "Press OK to continue.");
        }
        updateFixturesTableView();
        admin.saveLeagues();
    }
    
    /**
     * Sets <code>Fixture.venue</code> selected in 
     * <code>tableViewFixturesTab</code> to 
     * <code>textFieldFixturesTabWeek</code>
     * @param event 
     */
    public void setFixtureVenue(ActionEvent event) {
        admin.setFixtureVenue(fixtureSelection, textFieldFixturesTabVenue.getText());
        updateFixturesTableView();
        admin.saveLeagues();
    }
    /**
     * Toggles <code>Fixture.played</code> between 'Y' and 'N'.
     * @param event 
     */
    public void setPlayed(ActionEvent event) {
        fixtureSelection.setPlayed();
        updateFixturesTableView();
        admin.saveLeagues();
    }

    /**
     * Converts <code>Fixture</code> ArrayList to <code>Fixture</code> 
     * Observable list.
     * @param input
     * @return 
     */
    private ObservableList<Fixture> listFixtures(ArrayList<Fixture> input)    {
        ObservableList<Fixture> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    /****************************/
    /** 5. Score Sheet Methods **/
    /****************************/
    
    /**
     * Clears and updates all UI elements in the score sheet to match the 
     * <code>Fixture</code> selected in <code>tableViewFixturesTab</code>.
     * @param fixture 
     */
    public void updateFixtureInfo(Fixture fixture) {
        //Clear player choice boxes (to avoid duplication)
        choiceBoxFixturesTabHomePlayer1.getItems().clear();
        choiceBoxFixturesTabHomePlayer2.getItems().clear();
        choiceBoxFixturesTabAwayPlayer1.getItems().clear();
        choiceBoxFixturesTabAwayPlayer1.setValue("");
        choiceBoxFixturesTabAwayPlayer2.getItems().clear();
        choiceBoxFixturesTabAwayPlayer2.setValue("");
        choiceBoxFixturesTabAwayTeam.getItems().clear();
        choiceBoxFixturesTabHomeTeam.getItems().clear();
        //System.out.println("Cleared players");
        //Update fixture information top ribbon
        textFieldFixturesTabWeek.setText(String.valueOf(fixture.getWeek()));
        textFieldFixturesTabVenue.setText(fixture.getVenue());
        if (fixture.getPlayed() == 'Y') { 
            checkBoxFixturesTabPlayed.setSelected(true); 
        } else {
            checkBoxFixturesTabPlayed.setSelected(false);
        }
        //Update & populate choice boxes
        updateTeamsChoiceBoxes(fixture);
        updateScoreSheetScores(fixture);
        //update teams variables
        
    }
    /**
     * Updates <code>choiceBoxFixturesTabHomeTeam</code> and 
     * <code>choiceBoxFixturesTabAwayTeam</code> to teams in <code>Fixture</code>
     * @param fixture The fixture to get the teams from.
     */
    public void updateTeamsChoiceBoxes(Fixture fixture) {
        homeTeamSelectionFixturesTab = fixture.getHomeTeam();
        awayTeamSelectionFixturesTab = fixture.getAwayTeam();
        try {   
            fixture.getHomeTeam().countPlayers();
            //System.out.println("Home team Players no: " + fixture.getHomeTeam().getPlayersCount());
            fixture.getAwayTeam().countPlayers();
            //System.out.println("Away team Players no: " + fixture.getAwayTeam().getPlayersCount());
        //Home team.
        //System.out.println("Home team: " + fixture.getHomeTeam().getName());
        leagueSelectionFixturesTab.getTeams().forEach(team -> {
            choiceBoxFixturesTabHomeTeam.getItems().add(team.getName());
            if (team.getName() == fixture.getHomeTeam().getName()) {
                choiceBoxFixturesTabHomeTeam.getSelectionModel().select(fixture.getHomeTeam().getName());
            }
        });
        
        //Away team.
        //First we check for a Bye (this can only ever be the away team)
        //System.out.println("Away team: " + fixture.getAwayTeam().getName());
        if (fixture.getAwayTeam().getName().equals("*Bye*")) {
            choiceBoxFixturesTabAwayTeam.setValue("*Bye*");
            choiceBoxFixturesTabAwayPlayer1.setValue("*Bye*");
            choiceBoxFixturesTabAwayPlayer2.setValue("*Bye*");
            
        } 
        //If not a Bye match, set and display the away team.
        else {
            leagueSelectionFixturesTab.getTeams().forEach(team -> {
                choiceBoxFixturesTabAwayTeam.getItems().add(team.getName());
                if (team.getName() == fixture.getAwayTeam().getName()) {
                    choiceBoxFixturesTabAwayTeam.getSelectionModel().select(fixture.getAwayTeam().getName());
                }
            });
        }
        updateHomePlayers();
        updateAwayPlayers();
        } catch (NullPointerException ex) {
            //System.out.println("Fixture data incomplete (requires user input)");
        }
    }
    
    /**
     * Updates <code>choiceBoxFixturesTabHomePlayer1</code> and 
     * <code>choiceBoxFixturesTabHomePlayer2</code> to display the players
     * saved for the currently selected <code>Fixture</code> in 
     * <code>tableViewFixturesTab</code>.
     */
    public void updateHomePlayers() {
        //Player 1
        if (choiceBoxFixturesTabHomePlayer1.getItems().size() < homeTeamSelectionFixturesTab.getPlayers().size()) {
            homeTeamSelectionFixturesTab.getPlayers().forEach(player -> {
                choiceBoxFixturesTabHomePlayer1.getItems().add(player.getName());
            });
        }
        choiceBoxFixturesTabHomePlayer1.getSelectionModel().select(fixtureSelection.getPlayerSelections()[0]);
        //Player 2
        if (choiceBoxFixturesTabHomePlayer2.getItems().size() < homeTeamSelectionFixturesTab.getPlayers().size()) {
            homeTeamSelectionFixturesTab.getPlayers().forEach(player -> {
                choiceBoxFixturesTabHomePlayer2.getItems().add(player.getName());
            });
        }
        choiceBoxFixturesTabHomePlayer2.getSelectionModel().select(fixtureSelection.getPlayerSelections()[1]);
    }
    /**
     * Updates <code>choiceBoxFixturesTabAwayPlayer1</code> and 
     * <code>choiceBoxFixturesTabAwayPlayer2</code> to display the players
     * saved for the currently selected <code>Fixture</code> in 
     * <code>tableViewFixturesTab</code>.
     */
    public void updateAwayPlayers() {
        //Player 1
        if (choiceBoxFixturesTabAwayPlayer1.getItems().size() < homeTeamSelectionFixturesTab.getPlayers().size()) {
            awayTeamSelectionFixturesTab.getPlayers().forEach(player -> {
                choiceBoxFixturesTabAwayPlayer1.getItems().add(player.getName());
            });
        }
        choiceBoxFixturesTabAwayPlayer1.getSelectionModel().select(fixtureSelection.getPlayerSelections()[2]);
        //Player 2
        if (choiceBoxFixturesTabAwayPlayer2.getItems().size() < homeTeamSelectionFixturesTab.getPlayers().size()) {
            awayTeamSelectionFixturesTab.getPlayers().forEach(player -> {
                choiceBoxFixturesTabAwayPlayer2.getItems().add(player.getName());
            });
        }
        choiceBoxFixturesTabAwayPlayer2.getSelectionModel().select(fixtureSelection.getPlayerSelections()[3]);
    }
    /**
     * Error check for missing players in <code>homeTeamSelectionFixturesTab</code>.
     * @param mouseEvent 
     */
    public void noPlayersErrorCheckHome(MouseEvent mouseEvent) {
        try {
            if (homeTeamSelectionFixturesTab.getPlayersCount() < 2) {
            popupWindow("No Players", "No players in team " + homeTeamSelectionFixturesTab.getName(), "Please add more players in teams tab to create a fixture");
            } 
        } catch (NullPointerException e) {
            System.err.println("No fixture selected");
            popupWindowSuccess("No Fixture Selected!", "Please select a fixture before slecting players", "Press OK to continue...");
        }
    }
    /**
     * Error check for missing players in <code>awayTeamSelectionFixturesTab</code>.
     * @param mouseEvent 
     */
    public void noPlayersErrorCheckAway(MouseEvent mouseEvent) {
        try {
            if (awayTeamSelectionFixturesTab.getPlayersCount() < 2) {
            popupWindow("No Players", "No players in team " + awayTeamSelectionFixturesTab.getName(), "Please add more players in teams tab to create a fixture");
            } 
        } catch (NullPointerException e) {
            System.err.println("No fixture selected");
            popupWindowSuccess("No Fixture Selected!", "Please select a fixture before slecting players", "Press OK to continue...");
        }
    }
    
    /* Score Calculation Methods */
    
    /**
     * Calculates the final scores (including winner/loser and points assigned)
     * from the game scores which have been entered by the user into the score
     * sheet on the fixtures tab.
     * @param event Calculate Scores button press on fixtures GUI tab
     */
    public void calculateScores(ActionEvent event) {
        // Data arrays for storing scores
        ArrayList<String> scores = new ArrayList<>();
        
        // Create Array of singles scores from user input
        ObservableList<Node> sChildren = scoreGrid.getChildren();
        for(Node node : sChildren) {
            TextField score = (TextField) node;
            // Error check input
            if (score.getText().matches("[0-9]+[:][0-9]+") || "".equals(score.getText())) {
                scores.add(score.getText());
            } else {
                popupWindow("Invalid Input", "Please use 'Home Score : Away Score' format", "E.G. 9:11 or 11:0");
                return;
            }
        }
        
        // Call Admin class logic to update Fixture scores
        try {
            admin.modifyScoreSheet(fixtureSelection, scores);
        } catch (NullPointerException e) {
            System.err.println("No fixture selected");
            popupWindow("No Fixture Selected!", "Please select a fixture before editing a score sheet", "Press OK to continue...");
            return;
        }
        fixtureSelection.calculateWinner();
        try {
            resultsText.setText("Winner: " 
                        + fixtureSelection.getWinner().getName()
                        + "\nScore: "
                        + fixtureSelection.getResult());
            fixtureSelection.setPlayed(true);
            checkBoxFixturesTabPlayed.setSelected(true);
            tableViewFixturesTab.getSelectionModel().clearSelection();
            tableViewFixturesTab.getSelectionModel().select(fixtureSelection);
        } catch (NullPointerException e) {
            System.err.println("Unable to determine winner: check score sheet input data");
            resultsText.setText("Please add more\nscores");
            return;
        }
        updateTeamsTableView();
        admin.generateTeamStats(leagueSelectionTeamsTab);
        admin.saveLeagues();
    }
    
    /**
     * Updates the score elements of the score sheet to reflect the 
     * <code>Fixture</code> passed.
     * @param fixture 
     */
    public void updateScoreSheetScores(Fixture fixture) {
        // Get nodes from GridPane
        ObservableList<Node> currentGrid = scoreGrid.getChildren();
        // Empty list for return
        ObservableList<Node> newGrid = FXCollections.observableArrayList();
        // Get any saved scores for this fixture
        ArrayList<String> scores = fixture.getScores();
        
        // Create list of scores to populate GridPane (Blank if none saved)
        int i = 0;
        for (Node node : currentGrid) {
            TextField text = new TextField();
            if (scores != null) {
                text.setText(scores.get(i));
            } else {
                text.setText("");
            }
            node = (Node) text;
            newGrid.add(node);
            i++;
        }
        // Clear current GridPane
        scoreGrid.getChildren().clear();
        
        // Populate GridPane with previously loaded scores
        int row = 0;
        int col = 0;
        for (Node node : newGrid) {
            scoreGrid.add(node, col, row);
            row++;
            if (row % 6 == 0) {
                col++;
                row = 0;
            }
        }
        
        // Update results box
        //System.out.println(scores);
        if (scores != null) {
            try {
                resultsText.setText("Winner: " 
                        + fixtureSelection.calculateWinner().getName()
                        + "\nScore: "
                        + fixtureSelection.getResult());
            } catch (NullPointerException e) {
                System.err.println("Unable to determine winner: check input data");
            }
        } else {
            resultsText.setText("");
        }
    }
    
    /**
     * Checks if String is empty. ONLY used by GUI controllers for error 
     * checking user text inputs before processing.
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
    
    /**
     * Recursively returns the amount of tabs requested as a string of "\t".
     * Used for formatting.
     * @param size
     * @return 
     */
    public String tabs(int size) {
        String tabs = "";
        while (size > 0) {
            tabs += "\t";
            tabs(--size);
        }
        return tabs;
    }

}

