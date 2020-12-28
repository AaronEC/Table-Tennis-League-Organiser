package main;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Not yet implemented
 * @author Aaron
 */
public class Team implements Serializable{

    public String name;
    public int rank;
    public int points;

    private ArrayList<String> players = new ArrayList<String>();
    
    private String venue;
    private int matchesPlayed;
    private int matchesWon;
    private int matchesLost;
    private int matchesDrawn;

    public Team(String name)    {
    
        this.name = name;
//        this.rank = 0;
//        this.venue = venue;
//        this.matchesWon = wins;
//        this.matchesLost = losses;
//        this.matchesDrawn = draws;
        this.points = 0;
        //this.players = players;
        //this.venue = venue;
    }
    /**
     * Should be run when class object is created, to load data into class data 
     * structures from database.
     * @throws IOException 
     */
    void initialize() throws IOException {
        //System.out.println("Initializing league 1");
        loadPlayers();
        calculatePoints();
    }

    void addPlayer(String name) {
        players.add(name);
    }

    ArrayList<String> getPlayers() {
            return players;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getRank() {
        return this.rank;
    }

    public int getPoints() {
        return this.points;
    }

    public String getVenue() {
        return this.venue;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getMatchesLost() {
        return matchesLost;
    }

    public int getMatchesDrawn() {
        return matchesDrawn;
    }

    public void setMatchesLost(int matchesLost) {
        this.matchesLost = matchesLost;
    }

    public void setMatchesDrawn(int matchesDrawn) {
        this.matchesDrawn = matchesDrawn;
    }
    
    private int calculatePoints() {
        return ((this.matchesWon * 3) + (this.matchesDrawn * 1));
    }
    /**
     * Loads players from database file "players.csv" and stores them to 
     * ArrayList<String> players.
     * @throws IOException 
     */
    void loadPlayers() throws IOException {
        ArrayList<String> database = Main.loadFile("players.csv");
        for(int i = 0; i < database.size(); i++)    {
            String newPlayer = database.get(i);
            if(newPlayer.contains(name))    {
                List<String> playerNames = Arrays.asList(newPlayer.split("\\s*,\\s*"));
                for(int j = 1; j < playerNames.size(); j++) {
                    addPlayer(playerNames.get(j));
                }
            }
        }
    System.out.println("PLAYERS: " + getPlayers().toString());
    }
}
