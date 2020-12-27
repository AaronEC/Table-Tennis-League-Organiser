package main;
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Contains main() and starts the program (launches GUI here). 
 * Also contains the package wide static methods such as loadFile().
 * @author Aaron
 */
public class Main extends Application {
    /**
     * Creates main login window.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Package wide static method to load a file from a CSV into an array, also
     * removes formatting used for this programs storage implementation.
     * @param fileName
     * @return ArrayList of lines in file, with formatting removed
     * @throws IOException 
     */
    public static ArrayList<String> loadFile(String fileName) throws IOException {
        Scanner s = new Scanner(new File(fileName));
        ArrayList<String> database = new ArrayList<String>();
        while (s.hasNextLine()){
            database.add(s.nextLine().replaceAll("[{}]", ""));
        }
        s.close();
        return database;      
    }
}
