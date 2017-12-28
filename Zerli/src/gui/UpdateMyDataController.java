package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import client.Client;
import client.Product;
import client.Request;
import client.User;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UpdateMyDataController extends Application implements Initializable  {
	
	public static UpdateMyDataController last;
	
	//payment
	@FXML private TextField creditCardTxt;
	@FXML private TextField MonthTxt;
	@FXML private TextField YearTxt;
	@FXML private TextField CVVTxt;
	
	@FXML private Button updatePayment;
	
	//general
	@FXML private TextField usernameTxt;
	@FXML private TextField fnameTxt;
	@FXML private TextField passwordTxt;
	@FXML private TextField lnameTxt;
	@FXML private TextField phoneTxt;
	
	@FXML private Button updateGeneral;
	
	//address
	@FXML private TextField cityTxt;
	@FXML private TextField StreetTxt;
	@FXML private TextField sNumberTxt;
	
	@FXML private Button updateAdress;
	
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/UpdateMyData.fxml"));
			Scene scene = new Scene(root);
			GUIcontroller.setCurrentScene(scene); // save scene
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		}
		
		
		@FXML
		public void onMenuClick(MouseEvent event) throws Exception {

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
		
		@FXML
		public void onupdatePayment(ActionEvent event) throws Exception {
			
			
		}
		
		@FXML
		public void onupdateGeneral(ActionEvent event) throws Exception {
			
			
		}
		
		@FXML
		public void onupdateAdress(ActionEvent event) throws Exception {
			
			
		}
		
		public void fillPayment()
		{
			if(LoginController.myCreditCard != null)
			{
			creditCardTxt.setText(LoginController.myCreditCard.getCardNumber());
			MonthTxt.setText(Integer.toString(LoginController.myCreditCard.getExpMonth()));
			YearTxt.setText(Integer.toString(LoginController.myCreditCard.getExpYear()));
			CVVTxt.setText(LoginController.myCreditCard.getCvv());
			}
		}
		
		public void fillAddress()
		{
			if(LoginController.myAddress != null)
			{
				cityTxt.setText(LoginController.myAddress.getCity());
				StreetTxt.setText(LoginController.myAddress.getStreet());
				sNumberTxt.setText(Integer.toString(LoginController.myAddress.getNumber()));
			}
		}
		
		public void fillGeneral()
		{
			if(LoginController.myUser != null)
			{
				usernameTxt.setText(LoginController.myUser.getUsername());
				fnameTxt.setText(LoginController.myUser.getFname());
				passwordTxt.setText(LoginController.myUser.getPassword());
				lnameTxt.setText(LoginController.myUser.getLname());
				phoneTxt.setText(LoginController.myUser.getPhone());
			}
		}
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// fill boxes from myUser in Login Controller
			if(LoginController.myUser != null)
				fillGeneral();
			
			if(LoginController.myCreditCard != null)
				fillPayment();
			
			if(LoginController.myAddress != null)
				fillAddress();
		}
	
}
