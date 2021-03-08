package main;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 *<h1>Main Logic Class</h1>
 * Contains main() and starts the program (launches GUI from here). 
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 06/12/2020
 */
public class Main extends Application {
    /**
     * Creates main login window.
     * @param stage
     * @throws IOException Files will always be here
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
