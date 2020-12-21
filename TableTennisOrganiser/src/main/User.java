package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class User extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root =FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
			primaryStage.setTitle("Login");
                        primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
