package gui;

import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
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
			 */
			
		}
		
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/ClientIpSetForm.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/gui/ClientIpSetForm.css").toExternalForm());
			primaryStage.setTitle("Set IP");
			primaryStage.setScene(scene);
			
			primaryStage.show();
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			
		}
	
}
