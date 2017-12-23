package gui;

import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import client.Request;
import enums.Actions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientIpSetController implements Initializable  {
	@FXML
	private TextField txtIP;
	
	@FXML
	private Button saveIP;
	
		public void saveIPaction(ActionEvent event) throws Exception {
			/*
			 * update client ip
			 * start product manager
			 */
			String ip = txtIP.getText(); 
			Client.host = ip;
			
			/*
			 * HERE COME CODES THAT TAKES YOU TO THE CLASS PRODUCT MANAGER
			 * 
			  
			
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			
			Stage primaryStage = new Stage();
			
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/gui/loginForm.fxml").openStream());
	
			Scene scene = new Scene(root);			
			//scene.getStylesheets().add(getClass().getResource("/gui/selectProductFrame.css").toExternalForm());
			
			primaryStage.setScene(scene); 		
			primaryStage.show(); 
			*/
			
			GUIcontroller guic = new GUIcontroller();
			guic.loadFxml("loginForm.fxml");
		}
		
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientIpSetForm.fxml"));
			
			Scene scene = new Scene(root);
			
			//gui controller
			
			GUIcontroller.setCurrentScene(scene);
			
			//scene.getStylesheets().add(getClass().getResource("/gui/ClientIpSetForm.css").toExternalForm());
			primaryStage.setTitle("Set IP");
			primaryStage.setScene(scene);
			
			primaryStage.show();
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			
		}
	
}
