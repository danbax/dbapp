package gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.Client;
import client.ImgFile;
import client.Order;
import client.Product;
import client.Request;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OrderHistoryController extends Application implements Initializable  {
	
	public static OrderHistoryController last;
	private ObservableList<Order> obserOrders;
	@FXML
	private TableView<Order> OrdersTable = new TableView<Order>(); // table of products
	
	
	
	File file; // Image file to upload
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/OrdersHistory.fxml"));
			Scene scene = new Scene(root);
			GUIcontroller.setCurrentScene(scene); // save scene
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		}
		
		
		
		@FXML
		public void onGetRefund(ActionEvent event) throws Exception {
			
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
						guic.loadFxmlMenu();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				});
			
		}
		
		@FXML
		public void onCancel(MouseEvent event)  throws Exception {
			
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<Order> orders)
		{
			/*
			 * This function fill the products table with data in ArrayList products
			 * Works only for first load of tableView
			 */
			if(obserOrders != null) {
				// if table alredy populate
				/*Dani: didn't find better solution */
				obserOrders.removeAll(obserOrders);
				for(Order o : orders)
				{
					obserOrders.add(o);
				}
				
			}
			else
			{
				OrdersTable.setEditable(false); // for updating
				
				//casting ArrayList to ObservableList
				obserOrders = FXCollections.observableArrayList(orders);
				
				// defining table columns
				TableColumn<Order, Integer> hoursCol = new TableColumn<Order, Integer>("Hours");
				hoursCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("hours")); 
		        //nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		        
		        OrdersTable.setItems(obserOrders);
		        OrdersTable.getColumns().add(hoursCol);
			}
		}
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			/*
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetProducts); 
			Client.clientConn.handleMessageFromClientUI(req);
			*/
			
		}
	
}
