package main;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>Viewer Logic Class</h1>
 * Class which implements all logic for Viewer functions and use cases.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
 */
public class Viewer extends Timer {

    private String viewerName;

    
    void startViewer() throws IOException {
        System.out.println("Viewer Created");
    }

    protected void viewFixtures(League league) {

    }

    protected void viewTeamStats(League league) {

    }

    protected void viewTeamRankings(League league) {

    }

    protected void viewMatchScore(League league, Fixture fixture) {

    }

    protected ArrayList<Team> viewTeams(League input) {
        return null;   
    }
    /**
     * Returns names of leagues as an ArrayList of strings.
     * @return 
     */
    protected ArrayList<String> viewLeagues() {
        ArrayList<String> leagueNames = new ArrayList<>();
        ArrayList<League> leagues = getLeagues();
        leagues.forEach(tempLeagues -> {
            leagueNames.add(tempLeagues.getName());
        });
        return leagueNames;
    }   
}
