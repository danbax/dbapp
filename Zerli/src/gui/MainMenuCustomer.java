package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import entity.Request;
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

public class MainMenuCustomer extends GUIcontroller  {
	public static MainMenuCustomer last;
	@FXML Text helloText;
	@FXML Text numberOfItems;	
	
	@FXML
	public void onLogout(MouseEvent event)  throws Exception {
		// Logout
		logout();
	}
	
	@FXML
	public void catalog(MouseEvent event)  throws Exception {
		loadFxml("Catalog.fxml");
	}
	
	@FXML
	public void customMade(MouseEvent event)  throws Exception {
		loadFxml("CustomOrder.fxml");
	}
	
	public void updateCountItems(int count)
	{
		this.numberOfItems.setText(count + " Items");
	}
	
	@FXML
	public void onNumberOfItems(MouseEvent event)  throws Exception {
		loadFxml("Cart.fxml");
	}
	
	@FXML
	public void updateAccount(MouseEvent event)  throws Exception {
		
		loadFxml("UpdateMyUserData.fxml");
		
	}
	
	@FXML
	public void orderHistory(MouseEvent event)  throws Exception {
		loadFxml("OrdersHistory.fxml");
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
			
			
		}
	
}
