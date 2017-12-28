package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import client.Client;
import client.CreditCard;
import client.Product;
import client.Request;
import client.Survey;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AuthorizeUsersController extends Application implements Initializable  {
	
	public static AuthorizeUsersController last;
	private ObservableList<User> ObserUsers;
	@FXML
	private TableView<User> UsersTable = new TableView<User>(); // table of products
	@FXML
	private TextField monthTxt;
	@FXML
	private TextField YearTxt;
	@FXML
	private TextField txtCardNumber;
	@FXML
	private TextField txtCVV;
	@FXML
	private Button btnAdd;
	@FXML ComboBox<String> subscribeCmb;
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/AuthorizeUsers.fxml"));
			Scene scene = new Scene(root);
			GUIcontroller.setCurrentScene(scene); // save scene
			primaryStage.setScene(scene);
			
			primaryStage.show();
		}
		
		
		
		@FXML
		public void onBtnClicked(ActionEvent event) throws Exception {
			/* authorize user*/
			String CreditCardNumber = txtCardNumber.getText();
			String cvv = txtCVV.getText();
			int month = Integer.parseInt(monthTxt.getText());
			int year = Integer.parseInt(YearTxt.getText());
			String s = this.subscribeCmb.getSelectionModel().getSelectedItem();
			User userToUpdate = UsersTable.getSelectionModel().getSelectedItem();
			int authorize=0;
			switch(s) {
			case "Regular":
				authorize=1;
				break;
			case "Monthly":
				authorize=2;
				break;
			case "Yearly":
				authorize=3;
				break;
			}
			
			CreditCard cc = new CreditCard();
			cc.setCardNumber(CreditCardNumber);
			cc.setCvv(cvv);
			cc.setExpMonth(month);
			cc.setExpYear(year);
			cc.setAuthorized(authorize);
			cc.setUser(userToUpdate);
			
			
			Request req = new Request(Actions.AuthorizeUser,cc);
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			Client.clientConn.handleMessageFromClientUI(req);
			req.setAction(Actions.GetNotAuthorizedUsers); 
			Client.clientConn.handleMessageFromClientUI(req);
			
		}
		
		@FXML
		public void onMenuClick(MouseEvent event) throws Exception {
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
		
		
		
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<User> users)
		{
			/*
			 * This function fill the users table with data in ArrayList users
			 */
			if(ObserUsers != null) {
				ObserUsers.removeAll(ObserUsers);
				for(User u : users)
				{
					ObserUsers.add(u);
				}
				
			}
			else
			{
				UsersTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserUsers = FXCollections.observableArrayList(users);
				
				// defining table columns
				TableColumn<User, String> fnameCol = new TableColumn<User, String>("First name");
				TableColumn<User, String> lnameCol = new TableColumn<User, String>("Last name");
				TableColumn<User, String> usernameCol = new TableColumn<User, String>("Username");
				TableColumn<User, String> phoneCol = new TableColumn<User, String>("phone");
				
				//add data to columns
				fnameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("fname")
		        	);
				
				lnameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("lname")
		        	);
				
				usernameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("username")
		        	);
				
				usernameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("phoneCol")
		        	);
		        
		        UsersTable.setItems(ObserUsers);
		        UsersTable.getColumns().addAll(fnameCol, lnameCol);
			}
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get users from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetNotAuthorizedUsers); 
			Client.clientConn.handleMessageFromClientUI(req);
			
			ArrayList<String> autorize = new ArrayList<String>();
			autorize.add("Regular");
			autorize.add("Monthly");
			autorize.add("Yearly");
			ObservableList<String> observableListautorize = FXCollections.observableList(autorize);
			this.subscribeCmb.setItems(observableListautorize);
			this.subscribeCmb.setPromptText("Select option");
		}
}
