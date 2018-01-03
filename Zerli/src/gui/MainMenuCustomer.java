package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import client.Request;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuCustomer extends Application implements Initializable  {
	public static MainMenuCustomer last;
	@FXML Text helloText;
	@FXML Text numberOfItems;	
	
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
	public void catalog(MouseEvent event)  throws Exception {
		Request req = new Request();
		req.setAction(Actions.Logout);
		req.setValue(LoginController.myUser);
		Client.clientConn.handleMessageFromClientUI(req);	
		
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUIcontroller guic = new GUIcontroller();
				try {
					guic.loadFxml("Catalog.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
				}
			}
			
			});
		
	}
	
	@FXML
	public void customMade(MouseEvent event)  throws Exception {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUIcontroller guic = new GUIcontroller();
				try {
					guic.loadFxml("CustomOrder.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			});
	}
	
	public void updateCountItems(int count)
	{
		this.numberOfItems.setText(count + " Items");
	}
	
	@FXML
	public void onNumberOfItems(MouseEvent event)  throws Exception {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUIcontroller guic = new GUIcontroller();
				try {
					guic.loadFxml("Cart.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			});
	}
	
	@FXML
	public void updateAccount(MouseEvent event)  throws Exception {
		Request req = new Request();
		req.setAction(Actions.Logout);
		req.setValue(LoginController.myUser);
		Client.clientConn.handleMessageFromClientUI(req);	
		
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUIcontroller guic = new GUIcontroller();
				try {
					guic.loadFxml("UpdateMyUserData.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			});
		
	}
	
	@FXML
	public void orderHistory(MouseEvent event)  throws Exception {
		Request req = new Request();
		req.setAction(Actions.Logout);
		req.setValue(LoginController.myUser);
		Client.clientConn.handleMessageFromClientUI(req);	
		
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUIcontroller guic = new GUIcontroller();
				try {
					guic.loadFxml("OrdersHistory.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			});
	}
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/MenuCustomers.fxml"));
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);  
			
			primaryStage.show();
			
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			
			last = this;
			if(LoginController.myUser != null)
			{
				// add hello text
				helloText.setText("Hello, "+LoginController.myUser.getUsername());
			}
			
			Request req = new Request();
			
			
			// update count items
			req.setAction(Actions.GetMyCartCountItems);
			req.setValue(LoginController.myUser);
			Client.clientConn.handleMessageFromClientUI(req);
			
			
			
			req.setAction(Actions.Logout);
			req.setValue(LoginController.myUser);
			Client.clientConn.handleMessageFromClientUI(req);	
			
		}
	
}
