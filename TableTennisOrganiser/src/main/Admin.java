package main;

import java.io.IOException;

/**
 * Class which implements all high level Admin functions
 * @author Aaron
 */
public class Admin extends Viewer {

	private String adminName;

	void startAdmin() throws IOException {
            System.out.println("Admin Created");
            initializeLeagues();
	}

	private void createLeague(String name) {

	}

	private void addTeam(League league, Team team) {

	}

	private void modifyScoreSheet(League league, Fixture fixture) {

	}

	private void generateFixtures(League league) {

	}

	private void saveLeagueToDatabase(League league) {

	}

	private void registerPlayer(String name, Team team, League league) {

	}
}
