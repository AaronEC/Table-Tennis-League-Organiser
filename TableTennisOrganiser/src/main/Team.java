package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Not yet implemented
 * @author Aaron
 */
public class Team {

    public SimpleStringProperty name;
    public SimpleIntegerProperty rank;
    public SimpleIntegerProperty points;

    private ArrayList<String> players = new ArrayList<String>();
    
    private String venue;
    private int gamesWon;
    private int gamesPlayed;

    public Team(String name)    {
    
        this.name = new SimpleStringProperty(name);
        this.points = new SimpleIntegerProperty((int) (Math.random() * 49 + 1));
        this.rank = new SimpleIntegerProperty(0);
        this.venue = "Sports Hall";
        this.gamesWon = ((int)(Math.random() * 8 + 1));
        this.gamesPlayed = ((int)(Math.random() * 12 + 1));
        //this.players = players;
        //this.venue = venue;
    }
    /**
     * Should be run when class object is created, to load data into class data 
     * structures from database. Otherwise will be an empty class.
     * @throws IOException 
     */
    void initialize() throws IOException {
        //System.out.println("Initializing league 1");
        loadPlayers();
        
    }

    void addPlayer(String name) {
        players.add(name);
    }


    ArrayList<String> getPlayers() {
            return players;
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public String getName() {
        return name.get();
    }

    public int getRank() {
        return rank.get();
    }

    public int getPoints() {
        return points.get();
    }

    public String getVenue() {
        return venue;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
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
            if(newPlayer.contains(name.get()))    {
                List<String> playerNames = Arrays.asList(newPlayer.split("\\s*,\\s*"));
                for(int j = 1; j < playerNames.size(); j++) {
                    addPlayer(playerNames.get(j));
                }
            }
        }
    System.out.println("PLAYERS: " + getPlayers().toString());
    }
}
