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

    String loginVerify(String userName, String password) {
            return null;
    }

    private void createAdmin(String adminName) {

    }

    private void createViewer(String viewerName) {

    }

    private void createTimer() {

    }

}
