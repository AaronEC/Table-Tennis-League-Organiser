package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class contains the data structure for each league.
 * @author Aaron
 */
public class League {
    
    private SimpleStringProperty name;
    private SimpleIntegerProperty teamsCount;
    ArrayList<Team> teams = new ArrayList<Team>();
    //private ArrayList<Fixture> fixture;

    public League(String name)    {
        this.name = new SimpleStringProperty(name);
        this.teamsCount = countTeams();
    }
    /**
     * Should be run when class object is created, to load data into class data 
     * structures from database. Otherwise will be an empty class.
     * @throws FileNotFoundException 
     */
    void initialize() throws FileNotFoundException, IOException {
        loadTeams();
    }
        
    void setName(String name) {

    }

    public String getName() {
        return name.get();
    }

    ArrayList<Team> getTeams() {
            return this.teams;
    }

    Fixture[] getFixtures() {
            return null;
    }

    public int getTeamsCount() {
        return teamsCount.get();
    }

    void setFixtures(Fixture fixtures) {

    }

    void addTeam(Team input) {

    }
    /**
     * Overload method for adding a blank team with no players.
     * @param input 
     */
    void addTeam(String input) {
        //Team team = new Team(input);
        //teams.add(team);
        countTeams();
    }

    SimpleIntegerProperty countTeams() {
        return new SimpleIntegerProperty(teams.size());
    }
    /**
     * Loads teams from database file "teams.csv" and stores them to 
     * ArrayList<Team> teams.
     * ONLY loads team names, players must be added later by user or from 
     * database.
     * @throws IOException 
     */
    void loadTeams() throws FileNotFoundException, IOException    {
        ArrayList<String> database = Main.loadFile("teams.csv");
        for(String temp : database)    {
            List<String> tempTeam = new ArrayList<String>(Arrays.asList(temp.split(",")));
            tempTeam.forEach(System.out::println);
            Team newTeam = new Team(tempTeam.get(0), tempTeam.get(1), Integer.parseInt(tempTeam.get(2)), Integer.parseInt(tempTeam.get(3)), Integer.parseInt(tempTeam.get(4)));
            newTeam.initialize();
            teams.add(newTeam);         
        }
    }
}
