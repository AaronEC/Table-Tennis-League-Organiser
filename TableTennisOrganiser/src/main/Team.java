package main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *<h1>Team Data Structure Class</h1>
 * This class contains the data structure for each team.
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 18/12/2020
 * @serial 8604642400555460346L
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
    private int setsPlayed;
    private int setsWon;

    /**
     * Class constructor specifying name of <code>Team</code>
     * @param name 
     */
    public Team(String name)    {
    
        this.name = name;
        this.venue = "Please Enter a Venue";
        this.rank = 0;
        this.matchesPlayed = 0;
        this.matchesWon = 0;
        this.setsPlayed = 0;
        this.setsWon = 0;
        this.points = 0;
        this.teamPlayers = new ArrayList<>();
        this.hasPlayed = new ArrayList<>();
        this.playersCount = 0;
    }
    
    /* Bespoke Methods */
    
    public void countPlayers()   {
        if (teamPlayers == null) { this.playersCount = 0; }
        else { this.playersCount = teamPlayers.size(); }
    }

    public void addPlayer(Player player) {
        teamPlayers.add(player);
        countPlayers();
    }
    
    public void removePlayer(Player player)  {
        this.teamPlayers.remove(player);
        countPlayers();
    }

    public void incrementMatchesWon() {
        this.matchesWon++;
    }
    
    public void incrementMatchesPlayed() {
        this.matchesPlayed++;
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

    public void addPoints(int points) {
        this.points += points;
    }
    
    /**
     * Resets all stats (matches/sets) to 0.
     */
    public void resetStats() {
        this.matchesPlayed = 0;
        this.matchesWon = 0;
        this.points = 0;
        this.setsPlayed = 0;
        this.setsWon = 0;
    }
    
    /* Getters */
    
    public int getPlayersCount() {
        return playersCount;
    }
    
    public ArrayList<Player> getPlayers() {
        return this.teamPlayers;
    }
    
    public int getMatchesLost() {
        return matchesPlayed - matchesWon;
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

    public int getSetsPlayed() {
        return setsPlayed;
    }

    public int getSetsWon() {
        return setsWon;
    }
    
    /* Setters */
        
    public void setName(String name) {
        this.name = name;
    }
    
    public void setVenue(String venue) {
        this.venue = venue;
    }
    
    /**
     * Increases <code>setsPlayed</code> by the passed amount.
     * @param setsPlayed 
     */
    public void setSetsPlayed(int setsPlayed) {
        this.setsPlayed += setsPlayed;
    }
    
    /**
     * Increases <code>setsWon</code> by the passed amount
     * @param setsWon 
     */
    public void setSetsWon(int setsWon) {
        this.setsWon += setsWon;
    }
    
    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
}
