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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UpdateUsersController extends Application implements Initializable  {
	
	public static UpdateUsersController last;
	private ObservableList<User> ObserUsers;
	@FXML
	private TableView<User> UsersTable = new TableView<User>(); // table of products
	
	@FXML
	private TextField txtSearch;
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/updateUsers.fxml"));
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
		public void onBtnSearchClick(ActionEvent event) throws Exception {
			// search user
			
			String searchQuery = txtSearch.getText();
			System.out.println(searchQuery);
			
			Request req = new Request(Actions.GetUsers,searchQuery);
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			Client.clientConn.handleMessageFromClientUI(req);
			
		}
		
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {

			/*
			 *  delete selected row from database
			 */
			
			// get selected item
			User user = UsersTable.getSelectionModel().getSelectedItem();
			if(user!= null)
			{
				
				// delete
				Request req = new Request();
    			req.setAction(Actions.DeleteUser);
    			req.setValue(user);
    			Client.clientConn.handleMessageFromClientUI(req);	
    			
    			// refresh table
    			Request req2 = new Request();
    			req2.setAction(Actions.GetUsers);
    			Client.clientConn.handleMessageFromClientUI(req2);
				
			}
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillUsersInTable(ArrayList<User> users)
		{
			/*
			 * This function fill the table with data in ArrayList 
			 */
			
			if(ObserUsers != null) {
				// if table alredy populate
				/*Dani: didn't find better solution */
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
				TableColumn<User, String> idCol = new TableColumn<User, String>("ID");
				TableColumn<User, String> usernameCol = new TableColumn<User, String>("username");
				TableColumn<User, String> passwordCol = new TableColumn<User, String>("password");
				
				//add data to columns
				idCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("id")
		        	);

		        
				usernameCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("username")
		        	);
		        
		      //update username
				usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
				 usernameCol.setOnEditCommit(
				            new EventHandler<CellEditEvent<User, String>>() {
				                @Override
				                public void handle(CellEditEvent<User, String> t) {
				                	// show new name in column
				                    ((User) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setUsername(t.getNewValue());
				                    
				                    //update new name to database
				                    String newUsername = t.getNewValue();
				                    User user = (User) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    user.setUsername(newUsername);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.updateUser);
				        			req.setValue(user);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
				
				passwordCol.setCellValueFactory(
		        	    new PropertyValueFactory<User,String>("password")
		        	);
				
				//update username
				passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
				passwordCol.setOnEditCommit(
				            new EventHandler<CellEditEvent<User, String>>() {
				                @Override
				                public void handle(CellEditEvent<User, String> t) {
				                	// show new name in column
				                    ((User) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setUsername(t.getNewValue());
				                    
				                    //update new name to database
				                    String newPassword = t.getNewValue();
				                    User user = (User) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    user.setPassword(newPassword);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.updateUser);
				        			req.setValue(user);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
		        
		      //update type
				passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
		        
		        UsersTable.setItems(ObserUsers);
		        UsersTable.getColumns().addAll(idCol, usernameCol,passwordCol);
			}
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get users from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetUsers); 
			Client.clientConn.handleMessageFromClientUI(req);
			
		}
	
}