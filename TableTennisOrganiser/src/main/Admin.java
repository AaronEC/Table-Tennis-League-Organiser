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
        try {
            FileOutputStream fos = new FileOutputStream(super.getFileName());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(getLeagues());
            oos.close();
            fos.close();
            System.out.println("Leagues Saved to: " + super.getFileName());
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: File " + super.getFileName() + 
                    "not found");
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
                    "not found");               
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
     * @param league
     * @param name 
     */
    public void addPlayer(Team team, String name) {
        System.out.println("Adding Player " + name + " to Team " + team.getName());
        team.addPlayer(new Player(name));
    }
    /**
     * Removes a 'Player' object from the 'Team' object passed to the function.
     * @param league
     * @param team 
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

    void modifyScoreSheet(League league, Fixture fixture) {

    }

    void generateFixtures(League league) {

    }

    void saveLeagueToDatabase(League league) {

    }

    void registerPlayer(String name, Team team, League league) {

    }
    /**
     * Force updates ALL fixturesCount int parameters in ALL 'Team' objects 
     * currently assigned to the Admin user. For use after adding or removing
     * fixtures, to update Teams TableView fixtures column.
     */
    void countFixtures()   {
        for (League temp : getLeagues()) {
            temp.countFixtures();
        }
    }
}
