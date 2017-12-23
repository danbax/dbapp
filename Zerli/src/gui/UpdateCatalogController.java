package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import client.Request;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateCatalogController extends Application implements Initializable  {
	@FXML
	private TableView ProductsTable;
	/*
	 @FXML private TableColumn<User, String> UserId;
	 @FXML private TableColumn<User, String> UserName;
	 @FXML private TableColumn<User, String> Active;
	*/
	 
	@FXML
	private TextField txtFieldPname;
	@FXML
	private TextField txtFieldPtype;
	@FXML
	private Button btnAdd;
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/updateCatalog.fxml"));
			Scene scene = new Scene(root);
			GUIcontroller.setCurrentScene(scene); // save scene
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		}
		
		
		
		@FXML
		public void onBtnAddClicked(ActionEvent event) throws Exception {
			// add product to database, clean form, show message "added succefully"
		}
		
		@FXML
		public void onMenuClick(ActionEvent event) throws Exception {
			// add product to database, clean form, show message "added succefully"

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

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			// getProducts from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetProducts); 
			Client.clientConn.handleMessageFromClientUI(req);	
		}
	
}
