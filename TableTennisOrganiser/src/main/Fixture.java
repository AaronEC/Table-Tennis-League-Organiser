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
    private String homeTeamName;
    private String awayTeamName;
    private char played;
    private String separator;
    private ArrayList<String> singlesScores;
    private ArrayList<String> doublesScores;

    public Fixture(Team homeTeam, Team awayTeam, int week, String venue) {
        
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.venue = setVenue(venue);
        this.homeTeamName = homeTeam.getName();
        this.awayTeamName = awayTeam.getName();
        this.week = week + 1;
        this.teams = homeTeam.getName() + " vs " + awayTeam.getName();
        this.played = 'N';
        this.separator = "Vs";
    }

    public String setVenue(String venue) {
        if ("home".equals(venue)) {
            return homeTeam.getVenue();
        } else if ("away".equals(venue)){
        return awayTeam.getVenue();
        } else {
            System.err.println("Venue Error");
            return null;
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
        for (String game : singlesScores) {
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
        System.out.println(homeSets);
        System.out.println(awaySets);
        
        if (homeSets > awaySets) {
            return homeTeam;
        } else if (awaySets > homeSets) {
            return awayTeam;
        } else {
            return null;
        }
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public void setPlayed(char played) {
        this.played = played;
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

    public void setSinglesScores(ArrayList<String> singlesScores) {
        this.singlesScores = singlesScores;
    }

    public void setDoublesScores(ArrayList<String> doublesScores) {
        this.doublesScores = doublesScores;
    }

    public ArrayList<String> getSinglesScores() {
        return singlesScores;
    }

    public ArrayList<String> getDoublesScores() {
        return doublesScores;
    }
    
}
