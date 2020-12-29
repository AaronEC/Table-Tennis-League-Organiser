package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *<h1>League Data Structure Class</h1>
 * This class contains the data structure for each league.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 06/12/2020
 */
public class League implements Serializable{
    
    private static final long serialVersionUID = 8604642400555460345L;
    private final ArrayList<Team> teams;
    private final ArrayList<Fixture> fixtures;
    private String name;
    private int teamsCount;
    private int fixturesCount;
    
    public League(String name)    {
        this.name = name;
        this.teams = new ArrayList<>();
        this.teamsCount = 0;
        this.fixturesCount = 0;
        this.fixtures = new ArrayList<>();
    }
    /**
     * Currently not used.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    void initialize() throws FileNotFoundException, IOException {
        //loadTeams();
    }

    void setName(String name) {
        System.out.println("Changed league name to " + this.name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    ArrayList<Team> getTeams() {
        return this.teams;
    }
    
    ArrayList<Fixture> getFixtures() {
        return fixtures;
    }

    public int getFixturesCount() {
        return fixturesCount;
    }

    public int getTeamsCount() {
        return teamsCount;
    }

    void setFixtures(Fixture fixtures) {

    }
    /**
     * Adds team of object type Team to League.
     * @param team Team to be added.
     */
    void addTeam(Team team) {
        System.out.println("Team added: " + team.getName());
        teams.add(team);
        countTeams();
    }
    /**
     * Overload method for adding a blank team with no players.
     * @param name Name of team to be added.
     */
    void addTeam(String name) {
        teams.add(new Team(name));
        countTeams();
    }
    /**
     * Deletes a team from this League.
     * Calls countTeams() to update teamsCount(int).
     * @param team Team to be removed.
     */
    void removeTeam(Team team)  {
        System.out.println("Removed team: " + team.getName());
        teams.remove(team);
        countTeams();
    }
    /**
     * Updates the teamsCount(int) by counting all teams in this League.
     */
    void countTeams() {
        if (teams == null) { teamsCount = 0; }
        else { teamsCount = teams.size(); }
    }
    /**
     * Updates the fixturesCount(int) by counting all fixtures in this League.
     */
    void countFixtures()    {
        if (fixtures == null) { fixturesCount = 0; }
        else { fixturesCount = fixtures.size(); }
    }
}
