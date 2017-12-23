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
import javafx.stage.Stage;

public class UpdateCatalogController extends Application implements Initializable  {
	
	public static UpdateCatalogController last;
	private ObservableList<Product> ObserProducts;
	@FXML
	private TableView<Product> ProductsTable = new TableView<Product>(); // table of products
	
	
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
			String pname = txtFieldPname.getText();
			String ptype = txtFieldPtype.getText();
			
			Product p = new Product(pname,ptype);
			Request req = new Request(Actions.AddProduct,p);
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			Client.clientConn.handleMessageFromClientUI(req);
			req.setAction(Actions.GetProducts); 
			Client.clientConn.handleMessageFromClientUI(req);
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
		
		public void refreshTableView(ArrayList<Product> products)
		{
			ObserProducts = FXCollections.observableArrayList(products);
			ProductsTable.refresh();
		}
		
		@SuppressWarnings("unchecked")
		public void fillProductsInTable(ArrayList<Product> products)
		{
			/*
			 * This function fill the products table with data in ArrayList products
			 * Works only for first load of tableView
			 */
			if(ObserProducts != null) {
				// if table alredy populate
				/*Dani: didn't find better solution */
				ObserProducts.removeAll(ObserProducts);
				for(Product p : products)
				{
					ObserProducts.add(p);
				}
				
			}
			else
			{
				ProductsTable.setEditable(true);
				
				//casting ArrayList to ObservableList
				ObserProducts = FXCollections.observableArrayList(products);
				
				// defining table columns
				TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
				TableColumn<Product, String> typeCol = new TableColumn<Product, String>("Type");
				
				//add data to columns
		        nameCol.setCellValueFactory(
		        	    new PropertyValueFactory<Product,String>("productName")
		        	);
		        
		        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		        nameCol.setOnEditCommit(
		            new EventHandler<CellEditEvent<Product, String>>() {
		                @Override
		                public void handle(CellEditEvent<Product, String> t) {
		                    ((Product) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setProductName(t.getNewValue());
		                    String xx = t.getNewValue();
		                    System.out.println( t.getTableView().getItems().get(
			                        t.getTablePosition().getRow()).getPid());
		                }
		            }
		        );

		        
		        typeCol.setCellValueFactory(
		        	    new PropertyValueFactory<Product,String>("productType")
		        	);
		        
		        
		        ProductsTable.setItems(ObserProducts);
		        ProductsTable.getColumns().addAll(nameCol, typeCol);
			}
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
