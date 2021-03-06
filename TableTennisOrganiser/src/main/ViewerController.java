package main;

import java.net.URL;
import java.util.ArrayList;
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
 * Controls FMXL GUI elements for Viewer class.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
 */
public class ViewerController extends AdminController implements Initializable{
    
    @FXML private Label homeTeam, awayTeam, venue;
    @FXML private Label homePlayer1, homePlayer2, awayPlayer1, awayPlayer2;

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
        } catch (ClassNotFoundException ex) {
            System.err.println("Viewer class creation error");
        }
        initializeTeamsTab();
        initializeFixturesTab();
        
    }
    
    /**
     * Updates the two team information labels (labelTeamsTabTeamInfoLabels and 
     * labelTeamsTabTeamInfoVariables) with the selected teams information.
     * @param team Selected team in tableViewTeamsTab.
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
                "Team Overview:\nName:\t\t\t\t\t" + team.getName() +
                "\nHome Venue:\t\t\t\t" + team.getVenue() +
                "\nPlayers:\t\t\t\t\t" + names.toString().strip() +
                "\n\nTeam Statistics:\nMatches Won:\t\t\t\t" + team.getMatchesWon() +
                "\nMatches Lost:\t\t\t\t" + team.getMatchesLost() +
                "\nMatches Played:\t\t\t" + team.getMatchesPlayed()
        );
    }
    
    
    /*****************************/
    /** 4. Fixtures Tab Methods **/
    /*****************************/
    
    /**
     * initialises the UI elements and data structures in 'Fixtures' tab.
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
            System.out.println("No home players in fixture");
        }
        try {
        awayPlayer1.setText(fixture.getAwayTeam().getPlayers().get(fixture.getPlayerSelections()[2]).getName());
        awayPlayer2.setText(fixture.getAwayTeam().getPlayers().get(fixture.getPlayerSelections()[3]).getName());
        } catch (IndexOutOfBoundsException e) {
            awayPlayer1.setText("None");
            awayPlayer2.setText("None");
            System.out.println("No away players in fixture");
        }
        updateScoreSheetScores(fixture);
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
            Label text = new Label();
            if (scores != null) {
                text.setText("    " + scores.get(i));
            } else {
                text.setText("    0:0");
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
    
    
}
