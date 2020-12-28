package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * This class contains the data structure for each league.
 * @author Aaron
 */
public class League implements Serializable{
    
    private static final long serialVersionUID = 8604642400555460345L;
    private String name;
    private int teamsCount;
    ArrayList<Team> teams = new ArrayList<Team>();
    //private ArrayList<Fixture> fixture;

    public League(String name)    {
        this.name = name;
    }
    /**
     * Should be run when class object is created, to load data into class data 
     * structures from database. Otherwise will be an empty class.
     * @throws FileNotFoundException 
     */
    void initialize() throws FileNotFoundException, IOException {
        //loadTeams();
    }
        
    void setName(String name) {
        System.out.println("Changed name");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    ArrayList<Team> getTeams() {
        return this.teams;
    }

    Fixture[] getFixtures() {
        return null;
    }

    public int getTeamsCount() {
        return teamsCount;
    }

    void setFixtures(Fixture fixtures) {

    }

    void addTeam(Team input) {
        System.out.println("Team added: " + input.getName());
        this.teams.add(input);
    }
    /**
     * Overload method for adding a blank team with no players.
     * @param input 
     */
    void addTeam(String input) {
        Team newTeam = new Team(input);
        this.teams.add(newTeam);
    }
    
    void removeTeam(Team team)  {
        this.teams.remove(team);
    }

    void countTeams() {
        this.teamsCount = teams.size();
    }
    /**
     * Loads teams from database file "teams.csv" and stores them to 
     * ArrayList<Team> teams.
     * ONLY loads team names, players must be added later by user or from 
     * database.
     * @throws IOException 
     */
//    void loadTeams() throws FileNotFoundException, IOException    {
//        ArrayList<String> database = Main.loadFile("teams.csv");
//        for(String temp : database)    {
//            List<String> tempTeam = new ArrayList<String>(Arrays.asList(temp.split(",")));
//            tempTeam.forEach(System.out::println);
//            Team newTeam = new Team(tempTeam.get(0), tempTeam.get(1), Integer.parseInt(tempTeam.get(2)), Integer.parseInt(tempTeam.get(3)), Integer.parseInt(tempTeam.get(4)));
//            newTeam.initialize();
//            teams.add(newTeam);         
//        }
//    }
}
