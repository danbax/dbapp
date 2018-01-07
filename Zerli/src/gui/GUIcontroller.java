package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import entity.Request;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIcontroller  implements Initializable {
	private static Scene currentScene;
	private  Stage primaryStage;
	private  static Stage currentStage;
	private  FXMLLoader loader;
	private  Pane root;
	
	public GUIcontroller(){
		
	}
	
	public void loadFxml(String fxmlFile) throws IOException
	{
		// For avoiding bugs
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				try {
					
					currentScene.getWindow().hide(); //hiding primary window
					primaryStage = new Stage();
					//primaryStage.initStyle(StageStyle.UNDECORATED); // remove close button
					//primaryStage.resizableProperty().setValue(Boolean.FALSE);
					GUIcontroller.setCurrentStage(primaryStage);
					loader = new FXMLLoader();
					root = loader.load(getClass().getResource("/main/resources/"+fxmlFile).openStream());
					Scene scene = new Scene(root);	
					scene.getStylesheets().add(getClass().getResource("/main/resources/AppStyle.css").toExternalForm());
					GUIcontroller.currentScene = scene;
					primaryStage.setScene(scene); 		
					primaryStage.show(); 
					
				} catch (IOException e) {
					// show problems
					e.printStackTrace();
				}
			}
			
			});
		
	}
	
	public void loadFxmlMenu() throws IOException
	{
		/*
		 * load menu relevant to user
		 */
		String fxmlFile = "";
		int permission = LoginController.myUser.getPermissions();
		switch(permission)
		{
		case 1:
			fxmlFile = "MenuEmployee.fxml";
			break;
		case 2:
			fxmlFile = "ServiceExpert.fxml";
			break;
		case 3:
			fxmlFile = "MenuService.fxml";
			break;
		case 4:
			fxmlFile = "MenuShopManager.fxml";
			break;	
		case 5:
			fxmlFile = "MenuCustomers.fxml";
			break;	
		case 6:
			fxmlFile = "MenuNetworkManager.fxml";
			break;		
		}
			
		
		loadFxml(fxmlFile);
	}
	
	public void logout() throws IOException
	{
		/* logout */
		Request req = new Request();
		req.setAction(Actions.Logout);
		req.setValue(LoginController.myUser);
		Client.clientConn.handleMessageFromClientUI(req);	
		
		// Move to loginForm
		loadFxml("loginForm.fxml");
	}

	public static Scene getCurrentScene() {
		return currentScene;
	}

	public static void setCurrentScene(Scene currentScene) {
		GUIcontroller.currentScene = currentScene;
	}

	public static Stage getCurrentStage() {
		return currentStage;
	}

	public static void setCurrentStage(Stage currentStage) {
		GUIcontroller.currentStage = currentStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	
	
}
