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

public class MainMenuReports extends Application implements Initializable  {
	
	@FXML Text helloText;
	
	
	@FXML
	public void onLogout(MouseEvent event)  throws Exception {
		// Logout
		Request req = new Request();
		req.setAction(Actions.Logout);
		req.setValue(LoginController.myUser);
		Client.clientConn.handleMessageFromClientUI(req);	
		
		// Move to loginForm
		GUIcontroller guic = new GUIcontroller();
		guic.loadFxml("MenuShopManager.fxml");
	}
	
	@FXML
	public void complains(MouseEvent event)  throws Exception {
		GUIcontroller guic = new GUIcontroller();
		try {
			guic.loadFxml("ReportComplains.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void orders(MouseEvent event)  throws Exception {
		GUIcontroller guic = new GUIcontroller();
		try {
			guic.loadFxml("ReportOrders.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void satisfaction(MouseEvent event)  throws Exception {
		
	}
	
	@FXML
	public void revenue(MouseEvent event)  throws Exception {
		// Move to 
		GUIcontroller guic = new GUIcontroller();
				try {
					guic.loadFxml("ReportRevenues.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/MenuReport.fxml"));
			
			Scene scene = new Scene(root);
			GUIcontroller.setCurrentScene(scene);
			GUIcontroller.setCurrentScene(scene);
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
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
