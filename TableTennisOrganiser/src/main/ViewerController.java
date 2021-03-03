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
public class ViewerController extends AdminController implements Initializable{

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
        
    }
    
    /**
     * Updates the two team information labels (labelTeamsTabTeamInfoLabels and 
     * labelTeamsTabTeamInfoVariables) with the selected teams information.
     * @param team Selected team in tableViewTeamsTab.
     */
    @Override
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
                Total Points:
                Home Venue:
                Players:"""); 
        labelTeamsTabTeamInfoVariables.setText(
                team.getName() + "\n" +
                team.getMatchesPlayed() + "\n" +
                team.getMatchesWon() + "\n" +
                team.getMatchesLost()  + "\n" +
                team.getPoints()  + "\n" + 
                team.getVenue() + "\n" + 
                names.toString()
                );
       
    }

}
