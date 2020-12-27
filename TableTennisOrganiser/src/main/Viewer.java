package main;

import java.io.IOException;

/**
 * Class which implements all high level Viewer functions
 * @author Aaron
 */
public class Viewer extends Timer {

    private String viewerName;

    void startViewer() throws IOException {
        System.out.println("Viewer Created");
        initializeLeagues();
    }

    protected void viewFixtures(League league) {

    }

    protected void viewTeamStats(League league) {

    }

    protected void viewTeamRankings(League league) {

    }

    protected void viewMatchScore(League league, Fixture fixture) {

    }

    protected void viewTeams(League league) {

    }
}
