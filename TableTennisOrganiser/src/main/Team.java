package main;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Not yet implemented
 * @author Aaron
 */
public class Team implements Serializable{
    
    private static final long serialVersionUID = 8604642400555460346L;
    public String name;
    public int rank;
    public int points;
    private ArrayList<Player> teamPlayers;
    private String venue;
    private int matchesPlayed;
    private int matchesWon;
    private int matchesLost;
    private int matchesDrawn;
    private int playersCount;

    public Team(String name)    {
    
        this.name = name;
        this.venue = "Please Enter a Venue";
        this.rank = 0;
        this.matchesPlayed = 0;
        this.matchesWon = 0;
        this.matchesLost = 0;
        this.matchesDrawn = 0;
        this.points = 0;
        this.teamPlayers = new ArrayList<>();
        this.playersCount = 0;
    }
    /**
     * Should be run when class object is created, to load data into class data 
     * structures from database.
     * @throws IOException 
     */
    void initialize() throws IOException {
        //System.out.println("Initializing league 1");
        //loadPlayers();
        calculatePoints();
    }

    public int getPlayersCount() {
        return playersCount;
    }
    
    void countPlayers()   {
        if (teamPlayers == null) { this.playersCount = 0; }
        else { this.playersCount = teamPlayers.size(); }
    }

    void addPlayer(Player player) {
        teamPlayers.add(player);
    }
    
    void removePlayer(Player player)  {
        this.teamPlayers.remove(player);
    }

    ArrayList<Player> getPlayers() {
        return this.teamPlayers;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setMatchesLost(int matchesLost) {
        this.matchesLost = matchesLost;
    }

    public void setMatchesDrawn(int matchesDrawn) {
        this.matchesDrawn = matchesDrawn;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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
    
    private int calculatePoints() {
        return ((this.matchesWon * 3) + (this.matchesDrawn * 1));
    }
    /**
     * Loads players from database file "players.csv" and stores them to 
     * ArrayList<String> players.
     * @throws IOException 
     */
//    void loadPlayers() throws IOException {
//        ArrayList<String> database = Main.loadFile("players.csv");
//        for(int i = 0; i < database.size(); i++)    {
//            String newPlayer = database.get(i);
//            if(newPlayer.contains(name))    {
//                List<String> playerNames = Arrays.asList(newPlayer.split("\\s*,\\s*"));
//                for(int j = 1; j < playerNames.size(); j++) {
//                    addPlayer(playerNames.get(j));
//                }
//            }
//        }
//    System.out.println("PLAYERS: " + getPlayers().toString());
//    }
}
