package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Not yet implemented
 * @author Aaron
 */
public class Team {

    private String name ;

    private ArrayList<String> players = new ArrayList<String>();
    
    private String venue;

    //private int matchesPlayed;

    //private int matchesWon;

    //private int setsWon;
    
    public Team(String name)    {
    
        this.name = name;
//        this.players = players;
//        this.venue = venue;
    
    }
    
    void initialize() throws FileNotFoundException {
        //System.out.println("Initializing league 1");
        loadPlayers();
        
    }

    void addPlayer(String name) {
        players.add(name);
    }

    void setPlayers(String name) {

    }

    void setMatchesPlayed(String name) {

    }

    void setMatchesWon(String name) {

    }

    void setSetsWon(String name) {

    }
    
    void setVenue(String name) {

    }

    void getVenue(String name) {

    }

    String getName() {
            return name;
    }

    ArrayList<String> getPlayers() {
            return players;
    }

    int getMatchesPlayed() {
            return 0;
    }

    int getMatchesWon() {
            return 0;
    }

    int getSetsWon() {
            return 0;
    }
    
    void loadPlayers() throws FileNotFoundException  {
        Scanner s = new Scanner(new File("players.csv"));
        ArrayList<String> database = new ArrayList<String>();
        while (s.hasNextLine()){
                database.add(s.nextLine().replaceAll("[{}]", ""));
        }
        s.close();
        for(int i = 0; i < database.size(); i++)    {
            String newPlayer = database.get(i).toString();
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
