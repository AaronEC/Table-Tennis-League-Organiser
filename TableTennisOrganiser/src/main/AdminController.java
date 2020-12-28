package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controls FMXL GUI elements for Admin class
 * @author Aaron
 */
public class AdminController extends UserController implements Initializable{
    
    @FXML private ChoiceBox<String> leagueChoiceBox;
    @FXML private Button addLeague;
    @FXML private Button deleteLeague;
    @FXML private TextField leagueNameIn;
    @FXML private TableView <League> leagueTableAdmin;
    @FXML private TableColumn <League, String> leagueNameAdmin;
    @FXML private TableColumn <League, Integer> leagueTeamsAdmin;
    
    private Admin admin = new Admin();

    /**
     * initialises the UI elements and associated class data structures with
     * data from the database files.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Listener to show team stats when a team is selected in TableView.
        leagueTableAdmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                League selected = leagueTableAdmin.getSelectionModel().getSelectedItem();
                System.out.println(selected.getName());
            }
        });
    }
    
    public void addLeague(ActionEvent event) throws IOException    {
        admin.addLeague(leagueNameIn.getText());
        updateLeaguesTableView();
    }
    
    public void deleteLeague(ActionEvent event) throws IOException    {
        System.out.println("Deleting League: " + leagueTableAdmin.getSelectionModel().getSelectedItem());
        admin.removeLeague(leagueTableAdmin.getSelectionModel().getSelectedItem());
        updateLeaguesTableView();
    }
    
    public void updateLeaguesTableView() throws IOException {
        leagueTeamsAdmin.setCellValueFactory(new PropertyValueFactory<>("teamsCount"));
        leagueNameAdmin.setCellValueFactory(new PropertyValueFactory<>("name"));
        leagueTableAdmin.setItems(listLeagues(admin.getLeagues()));
        //saveAdmin();
    }
    
    private ObservableList<League> listLeagues(ArrayList<League> input)    {
        ObservableList<League> output = FXCollections.observableArrayList(input);
        return output;
    }
    
    public void saveAdmin() throws FileNotFoundException, IOException {
        String fileName = "data.bin";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(admin);
    }
}

