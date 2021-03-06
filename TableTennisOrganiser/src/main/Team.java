package main;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *<h1>Team Data Structure Class</h1>
 * This class contains the data structure for each team.
 * @author  Aaron Cardwell 13009941
 * @version 0.1
 * @since 18/12/2020
 */
public class Team implements Serializable{
    
    private static final long serialVersionUID = 8604642400555460346L;
    private final ArrayList<Player> teamPlayers;
    private ArrayList<Team> hasPlayed;
    private String name;
    private String venue;
    private int rank;
    private int points;
    private int matchesPlayed;
    private int matchesWon;
    private int playersCount;

    public Team(String name)    {
    
        this.name = name;
        this.venue = "Please Enter a Venue";
        this.rank = 0;
        this.matchesPlayed = 0;
        this.matchesWon = 0;
        this.points = 0;
        this.teamPlayers = new ArrayList<>();
        this.hasPlayed = new ArrayList<Team>();
        this.playersCount = 0;
    }
    /**
     * Should be run when class object is created, to load data into class data 
     * structures from database.
     * @throws IOException 
     */
    void initialize() throws IOException {

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
        countPlayers();
    }
    
    void removePlayer(Player player)  {
        this.teamPlayers.remove(player);
        countPlayers();
    }

    ArrayList<Player> getPlayers() {
        return this.teamPlayers;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public void incrementMatchesWon() {
        this.matchesWon++;
    }
    
    public void incrementMatchesPlayed() {
        this.matchesPlayed++;
    }

    public int getMatchesLost() {
        return matchesPlayed - matchesWon;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void addHasPlayed(Team team) {
        this.hasPlayed.add(team);
    }
    
    public void resetHasPlayed() {
        this.hasPlayed.clear();
    }

    public ArrayList<Team> getHasPlayed() {
        return hasPlayed;
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

    public void addPoints(int points) {
        this.points += points;
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
    
    public void resetStats() {
        this.matchesPlayed = 0;
        this.matchesWon = 0;
        this.points = 0;
    }
}
