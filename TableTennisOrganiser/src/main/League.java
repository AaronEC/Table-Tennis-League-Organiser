package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains the data structure for each league.
 * @author Aaron
 */
public class League {
    
    private String name;
    ArrayList<Team> teams = new ArrayList<Team>();
    //private ArrayList<Fixture> fixture;

    public League(String name)    {
    
        this.name = name;
        //this.teams = new ArrayList<Team>();
    
    }
    
    void initialize() throws FileNotFoundException {
        loadTeams();
    }
        
    void setName(String name) {

    }

    String getName() {
            return this.name;
    }

    ArrayList<Team> getTeams() {
        //teams.forEach(System.out::println);
            return this.teams;
    }

    Fixture[] getFixtures() {
            return null;
    }

    void setFixtures(Fixture fixtures) {

    }

    void addTeam(Team input) {
        //System.out.println(input);

        //Team myObj = new Team(newTeam[0], newTeam[1], newTeam[2]);
        
        //teams.add(input);
    }
    /**
     * Overload method for adding a blank team with no players.
     * @param input 
     */
    void addTeam(String input) {
        Team team = new Team(input);
        teams.add(team);
    }

    void orderTeams() {

    }
    
    void loadTeams() throws FileNotFoundException    {
        Scanner s = new Scanner(new File("teams.csv"));
        ArrayList<String> database = new ArrayList<String>();
        while (s.hasNextLine()){
                database.add(s.nextLine().replaceAll("[{}]", ""));
        }
        s.close();
        for(int i = 0; i < database.size(); i++)    {
            Team newTeam = new Team(database.get(i).toString());
            teams.add(newTeam);
            System.out.println("TEAM: " + teams.get(i).getName());
            newTeam.initialize();
        }
    }
}
