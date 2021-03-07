package main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for holding all attributes of a fixture.
 * @author Aaron
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
    
    public String getName(Team team) {
        if (team != null) {
            return team.getName();
        } else {
            return "";
        }
    }

    public String setVenue(String venue) {
        if ("home".equals(venue)) {
            return homeTeam.getVenue();
        } else if ("away".equals(venue)){
            return awayTeam.getVenue();
        } else {
            System.err.println("VENUE: " + venue);
            this.venue = venue;
            return venue;
        }
    }
    /**
     * Calculates the winner of this fixture and returns the Team object.
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
        
        if (homeSets > awaySets) {
            winner = homeTeam;
            loser = awayTeam;
        } else if (awaySets > homeSets) {
            winner = awayTeam;
            loser = homeTeam;
        } else {
            return null;
        }
        System.out.println("Fixture: " + winner.getName());
        return winner;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }
    /**
     * Call to toggle played status between Y and N.
     */
    public void setPlayed() {
        if (this.played == 'Y') {
            this.played = 'N';
        } else {
            this.played = 'Y';
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

    public void setScores(ArrayList<String> scores) {
        this.scores = scores;
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
     * selected player in their respective teams Player array. If the team in
     * this fixture does not have any players, this returns an array of all -1,
     * which will set the ChoiceBox to blank.
     * @return (int)Array
     */
    public int[] getPlayerSelections() {
        if (playerSelections != null) {
            return playerSelections;
        } else {
            return new int[]{-1,-1,-1,-1};
        }
    }
    /**
     * Change the player selection at 'index' to 'value'.
     * @param index
     * @param value 
     */
    public void setPlayerSelections(int index, int value) {
        if (value != -1) {
            this.playerSelections[index] = value;
        }
    }    

    public void setHomeTeam(Team homeTeam) {
        this.homeTeamName = homeTeam.getName();
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeamName = awayTeam.getName();
        this.awayTeam = awayTeam;
    }
    
    
    
}
