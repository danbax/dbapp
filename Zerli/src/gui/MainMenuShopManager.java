package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import entity.Request;
import enums.Actions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuShopManager extends GUIcontroller  {
	
	@FXML Text helloText;
	
	
	@FXML
	public void onLogout(MouseEvent event)  throws Exception {
		// Logout
		logout();
	}
	
	@FXML
	public void authorize(MouseEvent event)  throws Exception {
		// Move to 
		loadFxml("AuthorizeUsers.fxml");
	}
	
	@FXML
	public void deals(MouseEvent event)  throws Exception {
		// move to deals
		loadFxml("UpdateDeals.fxml");
	}
	
	@FXML
	public void reports(MouseEvent event)  throws Exception {
		// move to reports
		loadFxml("MenuReport.fxml");
	}
	


		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			if(LoginController.myUser != null)
			{
				// add hello text
				helloText.setText("Hello, "+LoginController.myUser.getUsername());
			}
		}
	
}
