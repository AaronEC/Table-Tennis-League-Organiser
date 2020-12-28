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

    String adminName;
        
    void saveLeagues(ArrayList<League> input) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(super.getFileName());
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(input);
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

    void addTeam(League league, Team team) {

    }

    void modifyScoreSheet(League league, Fixture fixture) {

    }

    void generateFixtures(League league) {

    }

    void saveLeagueToDatabase(League league) {

    }

    void registerPlayer(String name, Team team, League league) {

    }
}
