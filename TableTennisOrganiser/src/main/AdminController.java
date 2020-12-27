package main;

import java.io.IOException;
import javafx.fxml.Initializable;

/**
 * Controls FMXL GUI elements for Admin class
 * @author Aaron
 */
public class AdminController extends ViewerController implements Initializable{
       
    @Override
    public void start() throws IOException
    {
        //Create new viewer class at logon
        Admin admin = new Admin();
        admin.startAdmin();
    }
}
