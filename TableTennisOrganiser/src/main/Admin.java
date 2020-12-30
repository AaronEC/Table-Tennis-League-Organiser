package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * <h1>Admin Logic Class</h1>
 * Class which implements all logic for Admin functions and use cases.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
 */
public class Admin extends Viewer {
    /**
     * Saves ALL leagues currently created by the Admin user to a serialized
     * .bin file. Gets file name from super class 'User'.
     * @param super.fileName
     */
    void saveLeagues() {
        System.out.println("Saving leagues to file " + super.getFileName());
        try (FileOutputStream fos = new FileOutputStream(super.getFileName()); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
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
     * super class 'User'.
     * @param super.fileName
     * @throws ClassNotFoundException (there will always be a class here)
     */
    void loadLeagues() throws ClassNotFoundException {
        System.out.println("Loading leagues from file " + super.getFileName());
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
     * Adds a 'Team' object to the 'League' object which is passed to the 
     * function, assigns the new team the 'name' passed to function.
     * @param league
     * @param name 
     */
    public void addTeam(League league, String name) {
        System.out.println("Adding Team " + name + " to League " + league.getName());
        league.addTeam(name);
    }
    /**
     * Removes a 'Team' object from the 'League' object passed to the function.
     * @param league
     * @param team 
     */
    public void removeTeam(League league, Team team) {
        System.out.println("Removing team "  + team.getName() + " from league "+ league.getName());
        league.removeTeam(team);
    }
    /**
     * Adds a 'Player' object to the 'Team' object which is passed to the 
     * function, assigns the new player the 'name' passed to function.
     * @param team
     * @param name
     */
    public void addPlayer(Team team, String name) {
        System.out.println("Adding Player " + name + " to Team " + team.getName());
        team.addPlayer(new Player(name));
    }
    /**
     * Removes a 'Player' object from the 'Team' object passed to the function.
     * @param team 
     * @param player
     */
    public void removePlayer(Team team, Player player) {
        System.out.println("Removing player "  + player.getName() + " from team "+ team.getName());
        team.removePlayer(player);
    }
    /**
     * Updates the 'Team' objects 'venue' string with the name passed.
     * @param team
     * @param name 
     */
    public void updateVenue(Team team, String name)    {
        team.setVenue(name);
    }
    /**
     * Generates and REPLACES all fixtures for the league passed.
     * @param league 
     */
    void generateFixtures(League league) {
        System.out.println(league.getTeamsCount());
        System.out.println(league.getTeamsCount() % 2);
        ArrayList<Team> notPlayed = new ArrayList<>();
        //Reset all fixtures in league
        league.resetFixtures();
        //Reset the list of teams each team has already played
        for (Team tempTeam : league.getTeams()) {
            tempTeam.resetHasPlayed();
        }
        //Check if there is an odd number of teams
        if (league.getTeamsCount() % 2 == 1) {
            
        }
        //Calculate how many weeks will be needed to play all fixtures
        int weeks = league.getTeamsCount() / 2;
        System.out.println("Weeks " + weeks);
        //Make each team play all other teams only once, and never play more
        //than one game a week.
        for (int i = 0; i <= weeks; i++) {
            System.out.println("Week " + i);
            notPlayed.clear();
            for (Team team : league.getTeams()) {
                notPlayed.add(team);
                //System.out.println("Not Played " + team.getName());
            }    
            //Every team in the league
            for (Team homeTeam : league.getTeams()) {
                System.out.println("Home Team: " + homeTeam.getName());
                //Can potentially play every team
                for (Team awayTeam : league.getTeams()) {
                    System.out.println("Away Team: " + awayTeam.getName());
                    //IF they are not the same team
                    if (homeTeam.getName() != awayTeam.getName()) {
                        //AND they haven't already played a game that week
                        if (notPlayed.contains(homeTeam) && notPlayed.contains(awayTeam)) {
                            //AND they haven't already played eachother in the league
                            if (!homeTeam.getHasPlayed().contains(awayTeam) && (!awayTeam.getHasPlayed().contains(homeTeam))) {
                                league.addFixture(new Fixture(homeTeam, awayTeam, i + 1));
                                notPlayed.remove(homeTeam);
                                notPlayed.remove(awayTeam);
                                System.out.println(homeTeam.getName() + " & " + awayTeam.getName() + " have played.");
                                homeTeam.addHasPlayed(awayTeam);
                                awayTeam.addHasPlayed(homeTeam); 
                            } else { System.out.println("These teams have already played eachother"); }
                        } else { System.out.println("Teams have already played this week"); }
                    }
                    else { System.out.println("Team is the same"); }
                }
            }
        }
        
        for (Fixture fixture : league.getFixtures()) {
//            System.out.println("Week " + fixture.getWeek());
//            System.out.println(fixture.getTeams());
        }

    }
    
    void modifyScoreSheet(League league, Fixture fixture) {

    }

    void saveLeagueToDatabase(League league) {

    }

    void registerPlayer(String name, Team team, League league) {

    }
    /**
     * Force updates ALL fixturesCount int parameters in ALL 'Team' objects 
     * currently assigned to the Admin user. For use after adding or removing
     * fixtures, to update Teams TableView fixtures column.
     * **WILL BE REMOVED FOR EFFICIENCY REASONS EVENTUALLY**
     */
    void countFixtures()   {
        getLeagues().forEach(temp -> {
            temp.countFixtures();
        });
    }
}
