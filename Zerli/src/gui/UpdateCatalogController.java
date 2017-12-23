package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import client.Product;
import client.Request;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UpdateCatalogController extends Application implements Initializable  {
	
	public static UpdateCatalogController last;
	@FXML
	private TableView<Product> ProductsTable; // table of products
	@FXML TableColumn<Product, String> nameCol = new TableColumn<Product, String>("ID");
	
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
		
		@SuppressWarnings("unchecked")
		public void fillProductsInTable(ArrayList<Product> products)
		{
			//casting to ovservable list
			ObservableList<Product> ObserProducts = FXCollections.observableArrayList(products);
			// defining table columns
			
			TableColumn<Product, String> nameCol = new TableColumn<Product, String>("pname");
	        TableColumn<Product, String> typeCol = new TableColumn<Product, String>("ptype");
	        nameCol.setCellValueFactory(
	        	    new PropertyValueFactory<Product,String>("pname")
	        	);
	        
	        
	        ProductsTable.setItems(ObserProducts);
	        ProductsTable.getColumns().addAll(nameCol, typeCol);
	        
	        
	        System.out.println(ObserProducts);
	        //ProductsTable.getColumns().addAll(nameCol, typeCol);
	      
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// getProducts from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetProducts); 
			Client.clientConn.handleMessageFromClientUI(req);
		}
	
}
