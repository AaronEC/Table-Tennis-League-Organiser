package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Class which implements all high level Admin functions
 * @author Aaron
 */
public class Admin extends Viewer {
        
    void saveLeagues() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(super.getFileName());
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(getLeagues());
        oos.close();
        fos.close();
        System.out.println("Leagues Saved to: " + super.getFileName());
    }
    
    void loadLeagues() throws FileNotFoundException, IOException, ClassNotFoundException   {
        FileInputStream fis = new FileInputStream(super.getFileName());
        ObjectInputStream ois = new ObjectInputStream(fis);
        setLeagues((ArrayList<League>) ois.readObject());
        System.out.println("Leagues loaded from: " + super.getFileName());
    }

    public void addTeam(League league, String name) {
        System.out.println("Adding Team " + name + " to League " + league.getName());
        league.addTeam(name);
    }
    
    public void removeTeam(League league, Team team) {
        System.out.println("Removing team "  + team.getName() + " from league "+ league.getName());
        league.removeTeam(team);
    }
    
    public void addPlayer(Team team, String name) {
        System.out.println("Adding Player " + name + " to Team " + team.getName());
        team.addPlayer(new Player(name));
    }
    
    public void removePlayer(Team team, Player player) {
        System.out.println("Removing player "  + player.getName() + " from team "+ team.getName());
        team.removePlayer(player);
    }
    
    public void updateVenue(Team team, String name)    {
        team.setVenue(name);
    }

    void modifyScoreSheet(League league, Fixture fixture) {

    }

    void generateFixtures(League league) {

    }

    void saveLeagueToDatabase(League league) {

    }

    void registerPlayer(String name, Team team, League league) {

    }
    
    void countTeams()   {
        for (League temp : getLeagues()) {
            temp.countTeams();
        }
    }
    void countPlayers()   {
        for (League league : getLeagues()) {
            for (Team team : league.getTeams())  {
                team.countPlayers();
            }
        }
    }
    
    void countFixtures()   {
        for (League temp : getLeagues()) {
            temp.countFixtures();
        }
    }
}
