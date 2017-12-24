package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class MainMenuController extends Application implements Initializable  {
	
	@FXML Text textHello;
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
	@FXML
	public void onLogout(MouseEvent event) throws Exception {
		// logout - change user in database to logout; move to login page

		/*
		 *  change database state to logout
		*/
		
		
		/*
		 *  Move to login page
		 
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
		*/
	}
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/MainMenu.fxml"));
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			textHello.setText(LoginController.myUser.getFname()+LoginController.myUser.getLname());
		}
	
}
