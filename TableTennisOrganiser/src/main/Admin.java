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
        System.out.println("Generating fixtures for " + league.getTeamsCount() + " teams.");
        System.out.println("Teams in league: " + league.getTeams().toString());
//How many teamsCount are in the league?
        int teamsCount = league.getTeamsCount();

        
        //If odd number of teamsCount then add a bye
        boolean bye = false;
        if (teamsCount % 2 == 1) {
            bye = true;
            league.addTeam("*Bye*");
            teamsCount++;
        }
        
        //Create 2d array of fixtures
        int totalWeeks = teamsCount - 1;
        int matchesPerWeek = teamsCount / 2;
        String[][] rounds = new String[totalWeeks][matchesPerWeek];
        ArrayList<Fixture> fixtures = new ArrayList<>();
        
        for (int round = 0; round < totalWeeks; round++) {
            for (int match = 0; match < matchesPerWeek; match++) {
                int home = (round + match) % (teamsCount - 1);
                int away = (teamsCount - 1 - match + round) % (teamsCount - 1);
                // Last team stays in the same place while the others
                // rotate around it.
                if (match == 0) {
                    away = teamsCount - 1;
                }
                // Add one so teamsCount are number 1 to teamsCount not 0 to teamsCount - 1
                // upon display.
                //rounds[round][match] = (home + 1) + " v " + (away + 1);
                fixtures.add(new Fixture(league.getTeams().get(home), league.getTeams().get(away), round));
            }
        }
        if (bye == true) {
            league.getTeams().remove(teamsCount - 1);
        }
        league.setFixtures(fixtures);
        league.countFixtures();
        
        
        
//        // Interleave so that home and away games are fairly evenly dispersed.
//        String[][] interleaved = new String[totalWeeks][matchesPerWeek];
//        
//        int evn = 0;
//        int odd = (teamsCount / 2);
//        for (int i = 0; i < rounds.length; i++) {
//            if (i % 2 == 0) {
//                interleaved[i] = rounds[evn++];
//            } else {
//                interleaved[i] = rounds[odd++];
//            }
//        }
//        
//        rounds = interleaved;
//
//        // Last team can't be away for every game so flip them
//        // to home on odd rounds.
//        for (int round = 0; round < rounds.length; round++) {
//            if (round % 2 == 1) {
//                rounds[round][0] = flip(rounds[round][0]);
//            }
//        }
//        
//        // Display the fixtures        
//        for (int i = 0; i < rounds.length; i++) {
//            System.out.println("Round " + (i + 1));
//            System.out.println(Arrays.asList(rounds[i]));
//            System.out.println();
//        }
//        
//        System.out.println();
//        
//        if (bye) {
//            System.out.println("Matches against team " + teamsCount + " are byes.");
//        }
//        
//        System.out.println("Use mirror image of these rounds for "
//            + "return fixtures.");
    }

    private static String flip(String match) {
        String[] components = match.split(" v ");
        return components[1] + " v " + components[0];
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

    void deleteFixtures(League league) {
        league.resetFixtures();
        league.countFixtures();
    }
}
