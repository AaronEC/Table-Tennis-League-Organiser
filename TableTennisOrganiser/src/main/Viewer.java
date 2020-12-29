package main;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class which implements all high level Viewer functions
 * @author Aaron
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
     * Returns names of leagues as an ArrayList<String>.
     * @return 
     */
    protected ArrayList<String> viewLeagues() {
        ArrayList<String> leagueNames = new ArrayList<>();
        ArrayList<League> leagues = getLeagues();
            for (League tempLeagues : leagues) {
                leagueNames.add(tempLeagues.getName());
            }
        return leagueNames;
    }   
}
