package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * <h1>Admin Logic Class</h1>
 * Class which implements all logic for <code>Admin</code> functions and use 
 * cases.
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public class Admin extends Timer {
    /**
     * Saves ALL leagues currently created by the Admin user to a serialized
     * .bin file. Gets file name from super class 'User'.
     * @param super.fileName
     */
    void saveLeagues() {
        try (FileOutputStream fos = new FileOutputStream(super.getFileName()); 
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(getLeagues());
            System.out.println("Leagues Saved to: " + super.getFileName());
        } catch (FileNotFoundException ex) {
            System.err.println(super.getFileName() + " not found");
        } catch (IOException ex) {
            System.err.println("ERROR: Incorrect output");
        }
    } 
    /**
     * Loads ALL leagues from a serialized .bin file. Gets file name from 
     * super class <code>User</code> getFileName().
     * @param super.fileName
     * @throws ClassNotFoundException (there will always be a class here)
     */
    void loadLeagues() throws ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(super.getFileName());
            ObjectInputStream ois = new ObjectInputStream(fis);
            setLeagues((ArrayList<League>) ois.readObject());
            System.out.println("Leagues loaded from: " + super.getFileName());
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: File " + super.getFileName() + 
                    " not found");               
        } catch (IOException ex) {
            System.err.println("ERROR: Incorrect input loaded");
        }
    }
    /**
     * Adds a <code>Team</code> object to the <code>League</code> object which 
     * is passed to the function, assigns the new <code>Team</code> the 
     * <code>name</code> passed to function.
     * @param league
     * @param name 
     */
    public void addTeam(League league, String name) {
        System.out.println("Adding Team " + name + " to League " + league.getName());
        league.addTeam(name);
    }
    /**
     * Removes a <code>Team</code> object from the <code>League</code> object 
     * passed to the function.
     * @param league
     * @param team 
     */
    public void removeTeam(League league, Team team) {
        System.out.println("Removing team "  + team.getName() + " from league "+ league.getName());
        league.removeTeam(team);
    }
    /**
     * Adds a <code>Player</code> object to the <code>Team</code> object which is passed to the 
     * function, assigns the new <code>Player</code> the <code>name</code> 
     * passed to function.
     * @param team
     * @param name
     */
    public void addPlayer(Team team, String name) {
        System.out.println("Adding Player " + name + " to Team " + team.getName());
        team.addPlayer(new Player(name));
    }
    /**
     * Removes a <code>Player</code> object from the <code>Team</code> object 
     * passed to the function.
     * @param team 
     * @param player
     */
    public void removePlayer(Team team, Player player) {
        System.out.println("Removing player "  + player.getName() + " from team "+ team.getName());
        team.removePlayer(player);
    }
    /**
     * Updates the <code>Team</code> objects <code>venue</code> string with the 
     * <code>name</code> passed.
     * @param team
     * @param name 
     */
    public void updateVenue(Team team, String name)    {
        team.setVenue(name);
    }
    /**
     * Generates and REPLACES all <code>fixtures</code> for the 
     * <code>League</code> passed. If <code>homeAndAway</code> is set to true, 
     * generates <code>fixtures</code> where each <code>Team</code> plays each 
     * other <code>Team</code> in BOTH a home AND away <code>Fixture</code>.
     * @param league 
     */
    void generateFixtures(League league, boolean homeAndAway, String venue) {
        league.countTeams();
        System.out.println("Generating fixtures for " + league.getTeamsCount() + " teams.");
        //How many teamsCount are in the league?
        int teamsCount = league.getTeamsCount();
        int increment;
        if (venue == "away") {
            increment = league.getTeamsCount();
        } else {
            increment = 0;
        }
        
        //If odd number of teamsCount then add a bye
        boolean bye = false;
        if (teamsCount % 2 == 1) {
            bye = true;
            league.addTeam("*Bye*");
            league.getTeams().get(teamsCount).setVenue("*Bye*");
            teamsCount++;
        }
        
        //Create 2d array of fixtures
        int totalWeeks = teamsCount - 1;
        int fixturesPerWeek = teamsCount / 2;
        ArrayList<Fixture> fixtures = new ArrayList<>();
        //For each week needed
        for (int week  = 0; week < totalWeeks; week++) {
            //Find each team for fixture using array index aritmatic
            for (int fixture = 0; fixture < fixturesPerWeek; fixture++) {
                int home = (week + fixture) % (teamsCount - 1);
                int away = (teamsCount - 1 - fixture + week) % (teamsCount - 1);
                // Other teams rotate around last team
                if (fixture == 0) {
                    away = teamsCount - 1;
                }
                //Add fixture to temporary array
                fixtures.add(new Fixture(league.getTeams().get(home), league.getTeams().get(away), (week + increment), venue));
            }
        }
        //Remove 'bye' team from league as no longer needed
        if (bye == true) {
            league.getTeams().remove(teamsCount - 1);
        }
        //Update League object with new fixtures
        league.appendFixtures(fixtures);
        league.countFixtures();
        //Recursively run again if home and away fixtures are requested.
        if(homeAndAway) {
            generateFixtures(league, false, "away");
        }
    }
    
    /**
     * Updates the score sheet for the given <code>Fixture</code>. 
     * <code>scores</code> must be passed as an array of strings in the 
     * following format:
     * (int)home score : (int)away score
     * 4x3 sets of singles games
     * 1x3 sets doubles games
     * @param fixture 
     */
    void modifyScoreSheet(Fixture fixture, ArrayList<String> scores) {
        fixture.setScores(scores);
    }
    
    /**
     * Force updates ALL <code>fixturesCount</code> int parameters in ALL 
     * <code>Team</code> objects currently assigned to the <code>Admin</code> 
     * class. For use after adding or removing <code>fixtures</code>, to update 
     * teamsTableView fixtures column.
     */
    void countFixtures()   {
        getLeagues().forEach(league -> {
            league.countFixtures();
        });
    }
    /**
     * Adds a new blank <code>fixture</code> to the passed <code>league</code>.
     * @param league
     * @return Fixture
     */
    Fixture createFixture(League league) {
        Team team = new Team("None");
        Fixture fixture = new Fixture(team, team, 0, "None");
        league.addFixture(fixture);
        return fixture;
    }
    /**
     * Removes all <code>fixtures</code> from <code>League</code>.
     * @param league 
     */
    void deleteFixtures(League league) {
        league.resetFixtures();
        league.countFixtures();
    }
    /**
     * Removes a single <code>Fixture</code> from a <code>League</code>
     * @param league
     * @param fixture 
     */
    void removeFixture(League league, Fixture fixture) {
        league.removeFixture(fixture);
        league.countFixtures();
    }

    void setFixtureVenue(Fixture fixture, String venue) {
        fixture.setVenue(venue);
    }
    /**
     * Returns names of all <code>Leagues</code> in database.
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
