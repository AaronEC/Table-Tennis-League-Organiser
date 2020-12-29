package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * <h1>Viewer GUI Controller Class</h1>
 * Controls FMXL GUI elements for Viewer class.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
 */
public class ViewerController extends UserController implements Initializable{
    
    @FXML private Label teamInfoLabels;
    @FXML private Label teamInfoData;
    @FXML private Label teamStatsLabels;
    @FXML private Label teamStatsData;
    @FXML private ChoiceBox<String> leagueChoiceBoxTeams;
    @FXML private TableView <Team> leagueTable;
    @FXML private TableColumn <Team, String> leagueRank;
    @FXML private TableColumn <Team, String> leagueName;
    @FXML private TableColumn <Team, String> leaguePoints;
    
    /**
     * initialises the UI elements and associated class data structures with
     * data from the database files.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Viewer viewer = new Viewer();   //Create object of class to control
        //Load lsit view with team names.
        try {
            viewer.startViewer();
            start();
        } catch (IOException ex) {
            Logger.getLogger(ViewerController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        //Populate league choicebox with league names.
        ArrayList<String> leagueNames = viewer.viewLeagues();
        for (String choices : leagueNames)  {
            leagueChoiceBoxTeams.getItems().add(choices);
            leagueChoiceBoxTeams.setValue(choices);
        }
        //Populate TableView with League data.
        leagueName.setCellValueFactory(new PropertyValueFactory<>("name"));
        leagueRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        leaguePoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        //**Needs to be updated to support multiple Leagues**
        leagueTable.setItems(listTeams(viewer.getLeagues().get(0)));
        //Listener to show team stats when a team is selected in TableView.
        leagueTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Team selected = leagueTable.getSelectionModel().getSelectedItem();
                System.out.println(selected.getName());
                showTeamInfo(selected);
            }
        });
        leagueTable.getSelectionModel().selectFirst();
    }
    
    public void start() throws IOException
    {
        //Create new viewer class at logon
        
    }
    
    private void showTeamInfo(Team input)   {
        teamInfoLabels.setText("Team Name: \nHome Venue: \nPlayers: ");
        teamInfoData.setText(input.getName() + "\n" + input.getVenue() + "\n" +
                input.getPlayers().toString());
        teamStatsLabels.setText("Matches Won: \nMatches Lost: \nMatches Drawn: ");
        teamStatsData.setText(input.getMatchesWon() + "\n" + 
                input.getMatchesLost() + "\n" + input.getMatchesDrawn());
    }
    /**
     * Converts a League class object into and ObservableList<Teams> for use 
     * within the TableView GUI.
     * @param input
     * @return 
     */
    private ObservableList<Team> listTeams(League input)    {
        ObservableList<Team> output = FXCollections.observableArrayList(input.getTeams());
        return output;
    }
}
