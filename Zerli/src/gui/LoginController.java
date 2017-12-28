package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Address;
import client.Client;
import client.CreditCard;
import client.Request;
import client.User;
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

public class LoginController implements Initializable  {
	public static LoginController last;
	public static User myUser;
	public static CreditCard myCreditCard;
	public static Address myAddress;
	@FXML
	private TextField loginUsername;
	@FXML
	private PasswordField loginPassword;
	@FXML
	private Button loginButton;
	@FXML
	private Text loginMessage;
	
	@FXML
	public void onLoginButtonClick(ActionEvent event) throws Exception {
		
		// clicking on logging button
		String username = loginUsername.getText();
		String password = loginPassword.getText(); 
		
		// create request to validate data
		Request req = new Request(Actions.ValidLoginDataCheck,new User(username,password));
	
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
			
			
			// GO to main menu
			
			loginMessage.setText("valid data!");
			// permissions - show right menu
			if(LoginController.myUser.getPermissions() == 1)
			{
				// employee
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						GUIcontroller guic = new GUIcontroller();
						try {
							guic.loadFxml("MenuEmployee.fxml");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			else if(LoginController.myUser.getPermissions() == 2)
			{
				// Expert Manager
				{
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							GUIcontroller guic = new GUIcontroller();
							try {
								guic.loadFxml("ServiceExpert.fxml");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
			else if(LoginController.myUser.getPermissions() == 3)
			{
				// Customer Service
				{
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							GUIcontroller guic = new GUIcontroller();
							try {
								guic.loadFxml("MenuService.fxml");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
			else if(LoginController.myUser.getPermissions() == 4)
			{
				// Customer Service
				{
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							GUIcontroller guic = new GUIcontroller();
							try {
								guic.loadFxml("MenuShopManager.fxml");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
			else
			{
			
			/*
			 *  Move to main menu
			 */
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					GUIcontroller guic = new GUIcontroller();
					try {
						guic.loadFxml("MainMenu.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				});
			}
			
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
