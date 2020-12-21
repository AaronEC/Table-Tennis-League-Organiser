package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

public class UserController {
	/*
        @FXML
	private Label myMessage;
	public void generateRandom(ActionEvent event) {
		Random rand = new Random();
		int myRand = rand.nextInt(50) + 1;
		myMessage.setText(Integer.toString(myRand));
		//System.out.println(Integer.toString(myRand));
	}
        */
	
	@FXML
	private TextArea userInput;
        @FXML
        private TextArea passwordInput;
	public void login(ActionEvent event) {
		
            System.out.println("User: " + userInput.getText() + 
                    "\nPassword: " + passwordInput.getText());

	}

}
