package main;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *<h1>User Logic Super Class</h1>
 * Landing point for all users, from here they will log in as either Admin or 
 * User class. Contains high level methods used by all users (including login).
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public class User implements Serializable{
    
    private String userId;      //Not currently implimented
    private String password;    //Not currently implimented
    private String loginType;
    private ArrayList<League> leagues = new ArrayList<League>();
    private final String fileName = "data.bin";

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

    protected void createTimer() {

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
