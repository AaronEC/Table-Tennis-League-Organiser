package main;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *<h1>League Data Structure Class</h1>
 * This class contains the data structures and associated methods for creating a
 * league.
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 * @serial 8604642400555460345L
 */
public class League implements Serializable{
    
    private static final long serialVersionUID = 8604642400555460345L;
    private final ArrayList<Team> teams;
    private ArrayList<Fixture> fixtures;
    private String name;
    private int teamsCount;
    private int fixturesCount;
    
    /**
     * Class constructor specifying name of <code>League</code>.
     * @param name 
     */
    public League(String name)    {
        this.name = name;
        this.teams = new ArrayList<>();
        this.teamsCount = 0;
        this.fixturesCount = 0;
        this.fixtures = new ArrayList<>();
    }
    
    /* Bespoke Methods */

    public void resetFixtures() {
        this.fixtures.clear();
    }
    
    void appendFixtures(ArrayList<Fixture> fixtures) {
        for (Fixture fixture: fixtures) {
            this.fixtures.add(fixture);
        }
    }
    /**
     * Adds <code>Team</code> to this <code>League</code>
     * @param team Team to be added.
     */
    void addTeam(Team team) {
        System.out.println("Team added: " + team.getName());
        teams.add(team);
        countTeams();
    }
    /**
     * Overload method for adding a blank <code>Team</code> with no 
     * <code>players</code>.
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
    void addFixture(Fixture fixture) {
        fixtures.add(0, fixture);
    }
    
    void removeFixture(Fixture fixture) {
        fixtures.remove(fixture);
    }
    /**
     * Updates <code>teamsCount</code> by counting all <code>Team</code> 
     * objects in this <code>League</code>.
     */
    void countTeams() {
        if (teams == null) { teamsCount = 0; }
        else { teamsCount = teams.size(); }
    }
    /**
     * Updates the <code>fixturesCount</code> by counting all 
     * <code>Fixture</code> objects in this <code>League</code>.
     */
    void countFixtures()    {
        if (fixtures == null) { fixturesCount = 0; }
        else { fixturesCount = fixtures.size(); }
    }

    /* Getters */

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
    
    /* Setters */
    
    void setName(String name) {
        System.out.println("Changed league name to " + this.name);
        this.name = name;
    }
    
    void setFixtures(ArrayList<Fixture> fixtures) {
        this.fixtures = fixtures;
    }    
}
