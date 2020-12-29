package main;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Landing point for all users, from here they will log in as either Admin or 
 * User class.
 * @author Aaron
 */
public class User implements Serializable{
    
    private String userId;
    private String password;
    private String loginType;
    private ArrayList<League> leagues = new ArrayList<League>();
    private final String fileName = "data.bin";

    /**
     * Checks username and password and verifies if login is an Admin or Viewer,
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

    public String getFileName() {
        return fileName;
    }
    
    String getLoginType() {
        return loginType;
    }
    
    protected void logout()    {
        loginType = null;
    } 

    private void createTimer() {

    }

    public void setLeagues(ArrayList<League> leagues) {
        this.leagues = leagues;
    }

    public void addLeague(String name) {
        this.leagues.add(new League(name));
        System.out.println(leagues.get(leagues.size() - 1).getName() + " added.");
    }
    
    public void removeLeague(League input) {
        this.leagues.remove(input);
        System.out.println("Deleted league: " + input);
    }
    /**
     * Returns all leagues as an ArrayList of League class.
     * @return 
     */
    ArrayList<League> getLeagues() {
        return leagues;
    }
}
