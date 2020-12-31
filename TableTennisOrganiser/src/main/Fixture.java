package main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Not yet implemented
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

    public Fixture(Team homeTeam, Team awayTeam, int week, String venue) {
        
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.venue = setVenue(venue);
        this.homeTeamName = homeTeam.getName();
        this.awayTeamName = awayTeam.getName();
        this.week = week + 1;
        this.teams = homeTeam.getName() + " vs " + awayTeam.getName();
        this.played = 'n';
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

    Team calculateWinner(DoublesSet[] doublesSet, SinglesSet[] singlesSet) {
            return null;
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
}
