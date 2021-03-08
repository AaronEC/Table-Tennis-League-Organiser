package main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Fixture Data Class</h1>
 * Contains all data structures needed for creating a fixture. Also contains
 * all methods to update an process this data.
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 * @serial 8604642400555460347L8604642400555460347L
 */
public class Fixture implements Serializable{

    private static final long serialVersionUID = 8604642400555460347L;
    private int week;
    private String teams;
    private String venue;
    private Team homeTeam;
    private Team awayTeam;
    private Team winner;
    private Team loser;
    private String homeTeamName;
    private String awayTeamName;
    private char played;
    private final String separator;
    private ArrayList<String> scores;
    private String result;
    private int[] playerSelections;

    /**
     * Class constructor specifying home team, away team, week of fixture and
     * venue name.
     * @param homeTeam
     * @param awayTeam
     * @param week
     * @param venue 
     */
    public Fixture(Team homeTeam, Team awayTeam, int week, String venue) {
        
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.venue = setVenue(venue);
        this.homeTeamName = getName(homeTeam);
        this.awayTeamName = getName(awayTeam);
        this.week = week + 1;
        this.teams = getName(homeTeam) + " vs " + getName(awayTeam);
        this.played = 'N';
        this.separator = "Vs";
        this.playerSelections = new int[]{0,1,0,1};
    }

    /**
     * Calculates the winner of this <code>Fixture</code>. 
     * Returns the winning <code>Team</code>.
     * Returns null if unable to calculate winner (this shouldn't happen with
     * correct inputs).
     * @return 
     */
    public Team calculateWinner() {
        int homeGames = 0;
        int awayGames = 0;
        int homeSets = 0;
        int awaySets = 0;
        int index = 0;
        try {
            for (String game : scores) {
                if (game != "") {
                    String[] score = game.split(":");
                    if (Integer.parseInt(score[0]) > Integer.parseInt(score[1])) {
                        homeGames++;
                    } else if (Integer.parseInt(score[1]) > Integer.parseInt(score[0])) {
                        awayGames++;
                    }
                    index++;
                    if (index == 3) {
                        if (homeGames > awayGames) {
                            homeSets++;
                        } else if (homeGames < awayGames) {
                            awaySets++;
                        }
                        homeGames = 0;
                        awayGames = 0;
                        index = 0;
                    }
                }
            }

            result = String.format("%d : %d", homeSets, awaySets);
            homeTeam.setSetsWon(homeSets);
            homeTeam.setSetsPlayed(homeSets + awaySets);
            awayTeam.setSetsWon(awaySets);
            awayTeam.setSetsPlayed(awaySets + homeSets);

            if (homeSets > awaySets) {
                winner = homeTeam;
                loser = awayTeam;
            } else if (awaySets > homeSets) {
                winner = awayTeam;
                loser = homeTeam;
            } else {
                winner = null;
            }
            return winner;      
        } catch (NullPointerException e) {
            return null; 
        }
    }
    
    /* Getters */
    
    public String getName(Team team) {
        if (team != null) {
            return team.getName();
        } else {
            return "";
        }
    }

    public String getSeparator() {
        return separator;
    }

    public int getWeek() {
        return week;
    }

    public String getTeams() {
        return teams;
    }

    public char getPlayed() {
        return played;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public String getVenue() {
        return venue;
    }

    public ArrayList<String> getScores() {
        return scores;
    }

    public String getResult() {
        return result;
    }

    public Team getWinner() {
        return winner;
    }

    public Team getLoser() {
        return loser;
    }
    /**
     * Returns an integer array of index numbers. These match the index of the
     * selected <code>Player</code> in their respective teams 
     * <code>players</code> array. If the team in this <code>Fixture</code> does 
     * not have any players, this returns an array of all -1, which will set the 
     * ChoiceBox to blank.
     * @return (int)Array
     */
    public int[] getPlayerSelections() {
        if (playerSelections != null) {
            return playerSelections;
        } else {
            return new int[]{-1,-1,-1,-1};
        }
    }
    
    /* Setters */
    
    /**
     * Sets <code>venue</code> to either the string passed OR:
     * - To home teams venue if passed "home".
     * - To away teams venue if passed "away".
     * @param venue
     * @return Venue name as String
     */
    public String setVenue(String venue) {
        if ("home".equals(venue)) {
            return homeTeam.getVenue();
        } else if ("away".equals(venue)){
            return awayTeam.getVenue();
        } else {
            this.venue = venue;
            return venue;
        }
    }
    
    public void setWeek(int week) {
        this.week = week;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }
    /**
     * Toggles <code>played</code> between 'Y' and 'N'.
     */
    public void setPlayed() {
        if (this.played == 'Y') {
            this.played = 'N';
        } else {
            this.played = 'Y';
        }
    }
    /**
     * Overload method for specifying exact property of <code>played</code>.
     * @param in 
     */
    public void setPlayed(boolean in) {
        if (in == true) {
            this.played = 'Y';
        } else {
            this.played = 'N';
        }
    }
    
    public void setScores(ArrayList<String> scores) {
        this.scores = scores;
    }
    /**
     * Changes the <code>Player</code> selection at <code>inde</code> to 
     * <code>value</code>.
     * @param index
     * @param value 
     */
    public void setPlayerSelections(int index, int value) {
        if (value != -1) {
            this.playerSelections[index] = value;
        }
    }    
    /**
     * Also updates <code>homeTeamName</code> to <code>homeTeam.name</code>
     * @param homeTeam 
     */
    public void setHomeTeam(Team homeTeam) {
        this.homeTeamName = homeTeam.getName();
        this.homeTeam = homeTeam;
    }
    /**
     * Also updates <code>awayTeamName</code> to <code>awayTeam.name</code>
     * @param awayTeam 
     */
    public void setAwayTeam(Team awayTeam) {
        this.awayTeamName = awayTeam.getName();
        this.awayTeam = awayTeam;
    }
    
    
    
}
