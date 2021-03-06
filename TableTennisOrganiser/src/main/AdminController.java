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
 * Controls FMXL GUI elements for Admin class.
 * Sub sections:
 * 1. Leagues Tab Methods
 * 2. Teams Tab Methods
 * 3. Player/Venue Methods (Teams Tab)
 * 4. Fixtures Tab Methods
 * 5. Score Sheet Methods (Fixtures Tab)
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
 */
public class AdminController extends UserController implements Initializable{
    
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
    @FXML protected TableColumn <Team, Integer> tableViewTeamsTabPlayersColumn;
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
     * initialises the UI elements and associated class data structures.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            admin.loadLeagues();
        } catch (ClassNotFoundException ex) {
            System.err.println("Admin class creation error");
        }
        admin.countFixtures();
        initializeLeaguesTab();
        initializeTeamsTab();
        initializeFixturesTab();
    }
    
    /****************************/
    /** 1. Leagues Tab Methods **/
    /****************************/
    
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
    
    /**************************/
    /** 2. Teams Tab Methods **/
    /**************************/
    
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
                displayTeamInfo(teamSelection);
            }
        });
        tableViewTeamsTab.getSelectionModel().selectFirst();
    }
    /**
     * Updates the TableView in the 'Teams' tab. For use after adding or 
     * removing players or teams.
     */
    public void updateTeamsTableView() {
        tableViewTeamsTab.getItems().clear();
        String leagueChoiceBoxTeamsTabSelection = choiceBoxTeamsTabLeague.getSelectionModel().getSelectedItem();
        ArrayList<League> leagues = admin.getLeagues();
        if (leagueChoiceBoxTeamsTabSelection!=null) {
            leagues.stream().filter(temp -> (leagueChoiceBoxTeamsTabSelection.equals(temp.getName()))).forEachOrdered(temp -> {
                leagueSelectionTeamsTab = temp;
                System.out.println("League selected in teams tab: " + temp.getName());
            });
            tableViewTeamsTab.setItems(listTeams(leagueSelectionTeamsTab.getTeams()));
        }
        else {
            //leagueChoiceBoxTeamsTab.setValue("No Leagues Added");
            System.err.println("No leagues for choice box");
        }
    }
    /**
     * Updates the league choice box on the teams tab with all leagues in 
     * the database.
     */
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
     * labelTeamsTabTeamInfoVariables) with the selected teams information.
     * @param team Selected team in tableViewTeamsTab.
     */
    public void displayTeamInfo(Team team)   {
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
                "Team Overview:\nName:\t\t\t\t\t" + team.getName() +
                "\nHome Venue:\t\t\t\t" + team.getVenue() +
                "\nPlayers:\t\t\t\t\t" + names.toString().strip() +
                "\n\nTeam Statistics:\nMatches Won:\t\t\t\t" + team.getMatchesWon() +
                "\nMatches Lost:\t\t\t\t" + team.getMatchesLost() +
                "\nMatches Played:\t\t\t" + team.getMatchesPlayed()
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

    //Converts Team ArrayList to Observable list, for TableView.
    private ObservableList<Team> listTeams(ArrayList<Team> input)    {
        ObservableList<Team> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    /*****************************/
    /** 3. Player/Venue Methods **/
    /*****************************/
    
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
    
    public void generateTeamStatsManual() {
        admin.generateTeamStats(leagueSelectionTeamsTab);
        popupWindowSuccess("Team Stats Generated!", "Updated statistics for all teams.", "Press OK to continue.");
    }
    
    /**
     * Updates the selected team's (in tableViewTeamsTab Table View) venue 
     * string with the input from textFieldTeamsTabPlayerCreateName text field.
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
     * initialises the UI elements and data structures in 'Fixtures' tab.
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
        
        //Listener for League selection in Fixtures Tab.
        choiceBoxFixturesTabLeague.setOnAction((event) -> {
            admin.getLeagues().forEach(league -> {
                if (league.getName() == choiceBoxFixturesTabLeague.getSelectionModel().getSelectedItem()) {
                    leagueSelectionFixturesTab = league;
                    System.out.println("League selected in fixures tab: " + league.getName());
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
                    System.out.println("Home Team Selection: " + team.getName());
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
                    System.out.println("Away Team Selection: " + team.getName());
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
        
        //Listener for when a Fixture is selected in TableView.
        tableViewFixturesTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fixtureSelection = tableViewFixturesTab.getSelectionModel().getSelectedItem();
                System.out.println("Fixture Selected: " + fixtureSelection.getTeams());
                updateFixtureInfo(fixtureSelection);
            }
        });
        tableViewFixturesTab.getSelectionModel().selectFirst();
        
    }
    
    public void createFixture() {
        fixtureSelection = admin.createFixture(leagueSelectionFixturesTab);
        updateFixturesTableView();
        tableViewFixturesTab.getSelectionModel().selectFirst();
        choiceBoxFixturesTabAwayTeam.setValue("");
    }
    
    /**
     * Updates the league choice box on the fixtures tab with all leagues in 
     * the database.
     */
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
    
    /**
     * Updates the TableView in the 'Teams' tab. For use after adding or 
     * removing players or teams.
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
     * This generates a league style set of fixtures for the selected league in
     * the fixtures tab league selection box using the following rule set:
     *  - Each team will play every other team in the league.
     *  - No team will play more than once a week.
     *  - Each team only plays another team once.
     *  - If there are an odd number of teams in the league, a 'bye' will be 
     *  assigned once per week, where it is not possible for that team to play.
     * 
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
            System.out.println("League Selected: " + leagueSelectionFixturesTab.getName());
            leagueSelectionFixturesTab.resetFixtures();
            admin.generateFixtures(leagueSelectionFixturesTab, homeAway, "home");
            admin.saveLeagues();
            updateFixturesTableView();
            updateLeaguesTableView();
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
     * Deletes ALL fixtures from the league selected in the fixtures tab league
     * selection box.
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
     * Deletes a single Fixture from the selected league in the ChoiceBox. Gets
     * the Fixture from the TableView selection.
     * @param event 
     */
    public void removeFixture(ActionEvent event) {
        if (popupWindowChoice("Delete Fixture?", "This cannot be undone!", "Are you sure you wish to delete this fixture?")) {
            admin.removeFixture(leagueSelectionTeamsTab, fixtureSelection);   
            updateFixturesTableView();
            admin.saveLeagues();
        }
    }

    public void setWeek(ActionEvent event) {
        try {
            fixtureSelection.setWeek(Integer.parseInt(textFieldFixturesTabWeek.getText()));
        } catch (Exception e) {
            popupWindow("Incorrect Input", "Please enter a number.", "Press OK to continue.");
        }
        updateFixturesTableView();
        admin.saveLeagues();
    }
    
    public void setFixtureVenue(ActionEvent event) {
        admin.setFixtureVenue(fixtureSelection, textFieldFixturesTabVenue.getText());
        updateFixturesTableView();
        admin.saveLeagues();
    }
    
    public void setPlayed(ActionEvent event) {
        fixtureSelection.setPlayed();
        updateFixturesTableView();
        admin.saveLeagues();
    }

    
    //Converts Fixture ArrayList to Observable list, for display in TableView.
    private ObservableList<Fixture> listFixtures(ArrayList<Fixture> input)    {
        ObservableList<Fixture> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    /****************************/
    /** 5. Score Sheet Methods **/
    /****************************/
    
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
        System.out.println("Cleared players");
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
     * Updates the Home and Away Teams Choice Box in the score sheet section of 
     * the teams tab.
     * @param fixture The fixture to get the teams from.
     */
    public void updateTeamsChoiceBoxes(Fixture fixture) {
        homeTeamSelectionFixturesTab = fixture.getHomeTeam();
        awayTeamSelectionFixturesTab = fixture.getAwayTeam();
        try {   
            fixture.getHomeTeam().countPlayers();
            System.out.println("Home team Players no: " + fixture.getHomeTeam().getPlayersCount());
            fixture.getAwayTeam().countPlayers();
            System.out.println("Away team Players no: " + fixture.getAwayTeam().getPlayersCount());
        //Home team.
        System.out.println("Home team: " + fixture.getHomeTeam().getName());
        leagueSelectionFixturesTab.getTeams().forEach(team -> {
            choiceBoxFixturesTabHomeTeam.getItems().add(team.getName());
            if (team.getName() == fixture.getHomeTeam().getName()) {
                choiceBoxFixturesTabHomeTeam.getSelectionModel().select(fixture.getHomeTeam().getName());
            }
        });
        
        //Away team.
        //First we check for a Bye (this can only ever be the away team)
        System.out.println("Away team: " + fixture.getAwayTeam().getName());
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
            System.out.println("Fixture data incomplete (requires user input)");
        }
    }
    
    public void updateHomePlayers() {
        //Player 1
        homeTeamSelectionFixturesTab.getPlayers().forEach(player -> {
            choiceBoxFixturesTabHomePlayer1.getItems().add(player.getName());
        });
        choiceBoxFixturesTabHomePlayer1.getSelectionModel().select(fixtureSelection.getPlayerSelections()[0]);
        //Player 2
        homeTeamSelectionFixturesTab.getPlayers().forEach(player -> {
            choiceBoxFixturesTabHomePlayer2.getItems().add(player.getName());
        });
        choiceBoxFixturesTabHomePlayer2.getSelectionModel().select(fixtureSelection.getPlayerSelections()[1]);
    }
    
    public void updateAwayPlayers() {
        //Player 1
        awayTeamSelectionFixturesTab.getPlayers().forEach(player -> {
            choiceBoxFixturesTabAwayPlayer1.getItems().add(player.getName());
        });
        choiceBoxFixturesTabAwayPlayer1.getSelectionModel().select(fixtureSelection.getPlayerSelections()[2]);
        //Player 2
        awayTeamSelectionFixturesTab.getPlayers().forEach(player -> {
            choiceBoxFixturesTabAwayPlayer2.getItems().add(player.getName());
        });
        choiceBoxFixturesTabAwayPlayer2.getSelectionModel().select(fixtureSelection.getPlayerSelections()[3]);
    }
    
    public void noPlayersErrorCheckHome(MouseEvent mouseEvent) {
        if (homeTeamSelectionFixturesTab.getPlayersCount() < 2) {
        popupWindow("No Players", "No players in team " + homeTeamSelectionFixturesTab.getName(), "Please add more players in teams tab to create a fixture");
        } 
    }
    
    public void noPlayersErrorCheckAway(MouseEvent mouseEvent) {
        if (awayTeamSelectionFixturesTab.getPlayersCount() < 2) {
        popupWindow("No Players", "No players in team " + awayTeamSelectionFixturesTab.getName(), "Please add more players in teams tab to create a fixture");
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
        admin.modifyScoreSheet(fixtureSelection, scores);
        admin.generateTeamStats(leagueSelectionTeamsTab);
        admin.saveLeagues();
        updateTeamsTableView();
        try {
            resultsText.setText("Winner: " 
                        + fixtureSelection.calculateWinner().getName()
                        + "\nScore: "
                        + fixtureSelection.getResult());
        } catch (NullPointerException e) {
            System.err.println("Unable to determine winner: check input data");
            resultsText.setText("Please add more\nscores");
        }
    }
    
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
        System.out.println(scores);
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
     * Checks if String is empty. ONLY used by GUI controllers for error checking
     * user text inputs before processing.
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

