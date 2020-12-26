package main;

/**
 * Landing point for users, from here they will log in as Admin or User
 * @author Aaron
 */
public class User   {
    
    private String userId;

    private String password;

    private String loginType;

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
    }
    
    String getLoginType() {
        return loginType;
    }

    private void createTimer() {

    }
}
