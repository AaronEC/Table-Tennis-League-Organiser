package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Landing point for users, from here they will log in as Admin or User
 * @author Aaron
 */
public class User   {
    
    private String userId;

    private String password;

    private String loginType;
    
    private ArrayList<League> leagues = new ArrayList<League>();

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
    protected void initializeLeagues() throws IOException {
        ArrayList<String> database = Main.loadFile("leagues.csv");
        
        leagues.add(new League(database.get(0)));
        System.out.println("League: " + leagues.get(0).getName());
        leagues.get(0).initialize();
    }
    
    String getLoginType() {
        return loginType;
    }
    
    protected void logout()    {
        loginType = null;
    } 

    private void createTimer() {

    }
    
    ArrayList<League> getLeagues() {
        return leagues;
    }
}
