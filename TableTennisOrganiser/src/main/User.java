package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class User extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
	
    public static void main(String[] args) {
        launch(args);
    }
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
        if (userName.equals("Admin"))    {
            System.out.println("Admin Login");
            loginType = "Admin";
        }
        else if (userName.equals("Viewer"))    {
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
