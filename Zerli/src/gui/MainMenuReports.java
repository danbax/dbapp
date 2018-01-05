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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuReports extends GUIcontroller  {
	
	@FXML Text helloText;
	
	
	@FXML
	public void onLogout(MouseEvent event)  throws Exception {
		// Logout
		logout();
	}
	
	@FXML
	public void complains(MouseEvent event)  throws Exception {
		loadFxml("ReportComplains.fxml");
	}
	
	@FXML
	public void orders(MouseEvent event)  throws Exception {
		loadFxml("ReportOrders.fxml");
	}
	
	@FXML
	public void satisfaction(MouseEvent event)  throws Exception {
		
	}
	
	@FXML
	public void revenue(MouseEvent event)  throws Exception {
		// Move to 
		loadFxml("ReportRevenues.fxml");
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
