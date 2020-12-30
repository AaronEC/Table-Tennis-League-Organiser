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
    private Team homeTeam;
    private Team awayTeam;
    private char played;

    public Fixture(Team homeTeam, Team awayTeam) {
        
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.week = 0;
        this.teams = homeTeam.getName() + " vs " + awayTeam.getName();
        this.played = 'n';
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
    

    

}
