package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Address;
import client.Client;
import entity.CreditCard;
import entity.Product;
import entity.Request;
import entity.Shop;
import entity.User;
import enums.Actions;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController extends GUIcontroller  {
	public static LoginController last;
	public static User myUser;
	public static CreditCard myCreditCard;
	public static Address myAddress;
	public static ArrayList<Product> cartProduct;
	public static Shop shop;
	
	@FXML private TextField loginUsername;
	@FXML private PasswordField loginPassword;
	@FXML private Button loginButton;
	@FXML private Text loginMessage;
	
	@FXML
	public void onLoginButtonClick(ActionEvent event) throws Exception {
		
		// clicking on logging button
		String username = loginUsername.getText();
		String password = loginPassword.getText(); 
		
		// create request to validate data
		User user = new User(username,password);
		user.setShop(shop);
		Request req = new Request(Actions.ValidLoginDataCheck,user);
	
		// send request to server
		@SuppressWarnings("unused")
		Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);	 
		Client.clientConn.handleMessageFromClientUI(req);	
		
	}
	
	
	@FXML
	public void ShowLoginMessage(int isValid) throws IOException
	{
		/*
		 * show error message if data is invalid
		 */
		
		if(isValid==1) 
		{ 
			// get my address and credit card
			Request req = new Request();
			req.setValue(myUser);
			req.setAction(Actions.GetMyAdress);
			Client.clientConn.handleMessageFromClientUI(req);
			req.setAction(Actions.GetMyCreditCard);
			Client.clientConn.handleMessageFromClientUI(req);
			
			// GO to main menu
			
			
			loadFxmlMenu();
			
		}
		else if(isValid==0)
		{
			loginMessage.setText("Wrong username or password");
		}
		else if(isValid==2)
		{
			loginMessage.setText("User already logged in");
		}
		
	}
	
	
		
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start frame
			 */
			Parent root = FXMLLoader.load(getClass().getResource("/gui/loginForm.fxml"));
			
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("/gui/ClientIpSetForm.css").toExternalForm());
			primaryStage.setTitle("Set IP");
			primaryStage.setScene(scene);
			
			primaryStage.show();
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last=this;
			
		}
		
		
	
}
