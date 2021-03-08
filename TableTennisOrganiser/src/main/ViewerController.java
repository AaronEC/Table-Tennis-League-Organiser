package main;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <h1>Viewer GUI Controller Class</h1>
 * Controls FMXL GUI elements for Viewer scene.
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public class ViewerController extends AdminController implements Initializable{
    
    @FXML private Label homeTeam, awayTeam, venue;
    @FXML private Label homePlayer1, homePlayer2, awayPlayer1, awayPlayer2;

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
            System.err.println("Viewer class creation error");
        }
        initializeTeamsTab();
        initializeFixturesTab();
        
        //Listeners for teams tab.
        //Listener for League selection in teams tab Choice Box.
        choiceBoxTeamsTabLeague.setOnAction((event) -> {
            updateTeamsTableView();
            labelTeamsTabTeamInfoLabels.setText("");
            tableViewTeamsTab.getSelectionModel().selectFirst();
        });
        //Listener for when a Team is selected in teams tab TableView.
        tableViewTeamsTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                teamSelection = tableViewTeamsTab.getSelectionModel().getSelectedItem();
                displayTeamInfo(teamSelection);
            }
        });
        //Listeners for fixtures tab.
        //Listener for League selection in Fixtures Tab.
        choiceBoxFixturesTabLeague.setOnAction((event) -> {
            admin.getLeagues().forEach(league -> {
                if (league.getName() == choiceBoxFixturesTabLeague.getSelectionModel().getSelectedItem()) {
                    leagueSelectionFixturesTab = league;
                    updateFixturesTableView();
                }
            });
            tableViewFixturesTab.getSelectionModel().selectFirst();
        });
        //Listener for when a Fixture is selected in fixtures tab TableView.
        tableViewFixturesTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fixtureSelection = tableViewFixturesTab.getSelectionModel().getSelectedItem();
                updateFixtureInfo(fixtureSelection);
            }
        });
        
        //Auto select first item in tables (looks nicer and can prevent some 
        //null pointer exceptions, but they should be handled anyway).
        tableViewTeamsTab.getSelectionModel().selectFirst();
        tableViewFixturesTab.getSelectionModel().selectFirst();
        
    }
    
    /**
     * Updates <code>labelTeamsTabTeamInfoLabels</code> with information on the 
     * team selected in <code>tableViewTeamsTab</code>.
     * @param team Selected team
     * Override - to avoid updating unnecessary UI elements.
     */
    @Override
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
    }
    
    
    /*****************************/
    /** 4. Fixtures Tab Methods **/
    /*****************************/
    
    /**
     * initialises the UI elements and data structures in 'Fixtures' tab.
     * Override - to avoid updating unnecessary UI elements.
     */
    @Override
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
     * Clears and updates all UI elements in the score sheet to match the 
     * <code>Fixture</code> selected in <code>tableViewFixturesTab</code>.
     * @param fixture 
     * Override - to avoid updating unnecessary UI elements.
     */
    @Override
    public void updateFixtureInfo(Fixture fixture) {
        
        homeTeam.setText(fixture.getHomeTeam().getName());
        awayTeam.setText(fixture.getAwayTeam().getName());
        venue.setText(fixture.getVenue());
        try {
            homePlayer1.setText(fixture.getHomeTeam().getPlayers().get(fixture.getPlayerSelections()[0]).getName());
            homePlayer2.setText(fixture.getHomeTeam().getPlayers().get(fixture.getPlayerSelections()[1]).getName());
        } catch (IndexOutOfBoundsException e) {
            homePlayer1.setText("None");
            homePlayer2.setText("None");
        }
        try {
        awayPlayer1.setText(fixture.getAwayTeam().getPlayers().get(fixture.getPlayerSelections()[2]).getName());
        awayPlayer2.setText(fixture.getAwayTeam().getPlayers().get(fixture.getPlayerSelections()[3]).getName());
        } catch (IndexOutOfBoundsException e) {
            awayPlayer1.setText("None");
            awayPlayer2.setText("None");
        }
        updateScoreSheetScores(fixture);
    }

    /**
     * Updates all score elements in the score sheet with <code>scores</code> 
     * from passed <code>Fixture</code>.
     * @param fixture 
     */
    public void updateScoreSheetScores(Fixture fixture) {
        // Get nodes from GridPane
        ObservableList<Node> currentGrid = scoreGrid.getChildren();
        // Empty list for return
        ObservableList<Node> newGrid = FXCollections.observableArrayList();
        // Get any saved scores for this fixture
        ArrayList<String> scores = fixture.getScores();
        
        // Create list of scores to populate GridPane (n/a if none saved)
        int i = 0;
        for (Node node : currentGrid) {
            Label text = new Label();
            if (scores != null) {
                Collections.replaceAll(scores, "", "0:0");
                text.setText("    " + scores.get(i));
                Collections.replaceAll(scores, "0:0", "");
            } else {
                text.setText("    n/a");
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
}
