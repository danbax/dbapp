package gui;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.application.Application;
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

			//GUIcontroller guic = new GUIcontroller();
			//guic.loadFxml("loginForm.fxml");
			
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			
		}
	
}
