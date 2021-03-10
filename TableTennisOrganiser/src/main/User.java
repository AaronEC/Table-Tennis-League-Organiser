package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *<h1>User Logic Super Class</h1>
 * Landing point for all users, from here they will log in as either Admin or 
 * User class. Contains high level methods used by all users (including login,
 * generating stats and creating threads.).
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public abstract class User implements Serializable{
    
    private String userId;      //Not currently implimented
    private String password;    //Not currently implimented
    private String loginType;
    private ArrayList<League> leagues = new ArrayList<League>();
    private final String fileName = "data.bin";
    
    
    /**
     * Saves ALL leagues currently created by the Admin user to a serialized
     * .bin file. Gets file name from super class 'User'.
     * @param super.fileName
     */
    void saveLeaguesData() {
        try (FileOutputStream fos = new FileOutputStream(fileName); 
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(getLeagues());
            System.out.println("Leagues Saved to: " + fileName);
        } catch (FileNotFoundException ex) {
            System.err.println(fileName + " not found");
        } catch (IOException ex) {
            System.err.println("ERROR: Unable to create output file");
        }
    } 
    /**
     * Loads ALL leagues from a serialized .bin file. Gets file name from 
     * super class <code>User</code> getFileName().
     * @param super.fileName
     * @throws ClassNotFoundException (there will always be a class here)
     */
    void loadLeagues() throws ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            setLeagues((ArrayList<League>) ois.readObject());
            System.out.println("Leagues loaded from: " + fileName);
            // Create thread for auto stat update (now league data is loaded).
            startThreadedTimer(100);
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: File " + fileName + 
                    " not found");               
        } catch (IOException ex) {
            System.err.println("ERROR: Incompatible input file loaded");
        }
    }

    /**
     * Checks username and password and verifies if login is an Admin or Viewer.
     * Launches Admin or Viewer FXML GUI respectively.
     * TODO: - Error on incorrect login
     *       - Password verification
     * @param userName
     * @param password 
     */
    protected void loginVerify(String userName, String password) {
        System.out.println("User: " + userName + 
            "\nPassword: " + password);
        if (userName.equalsIgnoreCase("Secretary"))    {
            System.out.println("Admin Login");
            loginType = "Admin";
        }
        else if (userName.equalsIgnoreCase("Player"))    {
            System.out.println("Viewer Login");
            loginType = "Viewer";
        }
        else    {
            System.out.println("Incorrect login details");
            loginType = "Error";
        }
    }
    protected void logout()    {
        loginType = null;
    }
    /**
     * Starts <code>Timer</code> on a new thread, the <code>Timer</code> auto
     * generates team stats for ALL teams in database, once every 
     * <code>seconds</code> interval.
     * @param seconds
     */
    public void startThreadedTimer(int seconds) {
        Thread t1 = new Thread(new Timer(seconds * 1000));
        // Daemon thread because it's an unimportant background process.
        t1.setDaemon(true);
        t1.start();
    }
    /**
     * Generates stats for each <code>Team</code> in the passed 
     * <code>League</code>.
     * @param league 
     */
    protected void generateTeamStats(League league) {
        for (Team team : league.getTeams()) {
            team.resetStats();
        }

        for (Fixture fixture : league.getFixtures()) {
            fixture.calculateWinner();
            Team winner = fixture.getWinner();
            Team loser = fixture.getLoser();

            if (winner != null) {
                winner.incrementMatchesWon();
                winner.incrementMatchesPlayed();
                winner.addPoints(3);
                winner.setSetsWon(fixture.getWinnerSets());
                winner.setSetsPlayed(fixture.getWinnerSets()+fixture.getLoserSets());
            }
            if (loser != null) {
                loser.incrementMatchesPlayed();
                loser.addPoints(1);
                loser.setSetsWon(fixture.getLoserSets());
                loser.setSetsPlayed(fixture.getLoserSets()+fixture.getWinnerSets());
            }
        }
    }
    /**
     * Overload method for generating stats for ALL <code>League</code> objects 
     * in <code>leagues</code> array.
     */
    protected void generateTeamStats() {
        try {
            for (League league : leagues) {
                System.out.println(league);
                for (Team team : league.getTeams()) {
                    team.resetStats();
                }

                for (Fixture fixture : league.getFixtures()) {
                    fixture.calculateWinner();
                    Team winner = fixture.getWinner();
                    Team loser = fixture.getLoser();

                    if (winner != null) {
                        winner.incrementMatchesWon();
                        winner.incrementMatchesPlayed();
                        winner.addPoints(3);
                        winner.setSetsWon(fixture.getWinnerSets());
                        winner.setSetsPlayed(fixture.getWinnerSets()+fixture.getLoserSets());
                    }
                    if (loser != null) {
                        loser.incrementMatchesPlayed();
                        loser.addPoints(1);
                        loser.setSetsWon(fixture.getLoserSets());
                        loser.setSetsPlayed(fixture.getLoserSets()+fixture.getWinnerSets());
                    }
                }
            }
            System.out.println("Stats successfully generated!");
        } catch (NullPointerException e) {
            System.err.println("No leagues to generate stats for.");
        }
    }
    
    /**
     * Creates and adds <code>League</code> to <code>leagues</code> with name 
     * <code>name</code>.
     * @param name 
     */
    public void addLeague(String name) {
        this.leagues.add(new League(name));
        System.out.println(leagues.get(leagues.size() - 1).getName() + " added.");
    }
    /**
     * Removes <code>League</code> from <code>leagues</code>.
     * @param input 
     */
    public void removeLeague(League input) {
        this.leagues.remove(input);
        System.out.println("Deleted league: " + input);
    }
    
    /* Getters */
    
    /**
     * Returns <code>filename</code>, the file name of the serial .bin database.
     * @return 
     */
    public String getFileName() {
        return fileName;
    }
    
    public String getLoginType() {
        return loginType;
    }
    /**
     * Returns <code>leagues</code> ArrayList of <code>League</code> objects.
     * @return 
     */
    public ArrayList<League> getLeagues() {
        return leagues;
    }    
    
    /* Setters */
    
    public void setLeagues(ArrayList<League> leagues) {
        this.leagues = leagues;
    }
}
