package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import entity.Request;
import entity.Shop;
import enums.Actions;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuNetworkManager extends Application implements Initializable  {
	
	@FXML Text helloText;
	@FXML ComboBox<Shop> shop1cmb;
	@FXML ComboBox<Shop> shop2cmb;

	public static Shop shop1;
	public static Shop shop2;
	int howMuchComboBox=0;
	
	@FXML
	public void onLogout(MouseEvent event)  throws Exception {
		// Logout
		Request req = new Request();
		req.setAction(Actions.Logout);
		req.setValue(LoginController.myUser);
		Client.clientConn.handleMessageFromClientUI(req);	
		
		// Move to loginForm
		GUIcontroller guic = new GUIcontroller();
		guic.loadFxml("loginForm.fxml");
	}
	
	@FXML
	public void complains(MouseEvent event)  throws Exception {
		if(howMuchComboBox == 1) {
			GUIcontroller guic = new GUIcontroller();
			try {
				guic.loadFxml("ReportComplains.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(howMuchComboBox == 2) {
			// 2 shop view
			
		}
	}
	
	@FXML
	public void orders(MouseEvent event)  throws Exception {
		if(howMuchComboBox == 1) {
			GUIcontroller guic = new GUIcontroller();
			try {
				guic.loadFxml("ReportOrders.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(howMuchComboBox == 2) {
			// 2 shop view
			
		}
	}
	
	@FXML
	public void satisfaction(MouseEvent event)  throws Exception {
		if(howMuchComboBox == 1)
		{
			
		}
		if(howMuchComboBox == 2) {
			
		}
	}
	
	@FXML
	public void revenue(MouseEvent event)  throws Exception {
		if(howMuchComboBox == 1)
		{
		// Move to 
		GUIcontroller guic = new GUIcontroller();
				try {
					guic.loadFxml("ReportRevenues.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(howMuchComboBox == 2) {
			// 2 shop view
			
		}
	}
	
	@FXML
	public void changeQ(ActionEvent event) throws Exception {
		/* determine which shop comboBox selected */
		boolean is1 = this.shop1cmb.getSelectionModel().isEmpty();
		boolean is2 = this.shop2cmb.getSelectionModel().isEmpty();
		howMuchComboBox = howMuchComboSelected();
		if(howMuchComboBox == 1)
		{
			if(is1)
				shop1 = this.shop1cmb.getSelectionModel().getSelectedItem(); 
			if(is2)
				shop1 = this.shop2cmb.getSelectionModel().getSelectedItem(); 
			LoginController.shop = shop1;
		}
		if(howMuchComboBox == 2)
		{
			shop1 = this.shop1cmb.getSelectionModel().getSelectedItem(); 
			shop2 = this.shop2cmb.getSelectionModel().getSelectedItem(); 
		}
		
	}
	

	public int howMuchComboSelected()
	{
		/* how much comboBox selected */
		boolean isq = this.shop1cmb.getSelectionModel().isEmpty();
		boolean isy = this.shop2cmb.getSelectionModel().isEmpty();
		if(isy==false && isq==false) // checks if 2 selected
			return 2;
		if(isy==true && isq==true) // checks if 2 selected
			return 0;
		if(isy==true || isq==true) // checks if 2 selected
			return 1;
		
		return 0;
	}
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/MenuNetworkManager.fxml"));
			
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
			
			// set list of shops
			this.shop1cmb.setItems(ClientIpSetController.observableList);
			this.shop2cmb.setItems(ClientIpSetController.observableList);
			
		}

}
