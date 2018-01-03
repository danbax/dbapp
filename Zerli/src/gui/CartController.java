package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.Client;
import entity.Order;
import entity.Product;
import entity.Request;
import enums.Actions;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CartController extends Application implements Initializable  {
	
	public static CartController last;
	private ObservableList<Product> ObserProducts;
	@FXML
	private TableView<Product> ProductsTable = new TableView<Product>(); // table of products
	private ArrayList<Product> productsCart;
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/Cart.fxml"));
			Scene scene = new Scene(root);
			GUIcontroller.setCurrentScene(scene); // save scene
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		}
		
		@FXML
		public void onOrder(ActionEvent event) throws Exception {
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					GUIcontroller guic = new GUIcontroller();
					try {
						guic.loadFxml("orderP.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				});
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
		public void deleteSelectedRow(MouseEvent event)  throws Exception {
			/*
			 *  delete selected row from database
			 */
			
			// get selected item
			Product product = ProductsTable.getSelectionModel().getSelectedItem();
			if(product!= null)
			{
				Order o = new Order();
				o.setUser(LoginController.myUser);
				o.setProduct(product);
				// delete
				Request req = new Request();
    			req.setAction(Actions.DeleteFromCart);
    			req.setValue(o);
    			Client.clientConn.handleMessageFromClientUI(req);	
    			
    			// refresh table
    			Request req2 = new Request();
    			req2.setAction(Actions.GetMyCart);
    			req2.setValue(LoginController.myUser);
    			Client.clientConn.handleMessageFromClientUI(req2);
				
			}
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillProductsInTable(ArrayList<Product> products)
		{
			
			this.setProductsCart(products); // set product cart
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
				ProductsTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserProducts = FXCollections.observableArrayList(products);
				
				// defining table columns
				TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
		        nameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("productName")); 
		        
		        TableColumn<Product, Float> priceCol = new TableColumn<Product, Float>("Price");
		        priceCol.setCellValueFactory(new PropertyValueFactory<Product,Float>("price")); 
		      
		   
		   
				
		        
		        ProductsTable.setItems(ObserProducts);
		        ProductsTable.getColumns().addAll(nameCol, priceCol);
			}
		}
		
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// getProducts from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetMyCart); 
			req.setValue(LoginController.myUser);
			Client.clientConn.handleMessageFromClientUI(req);
			
		}


		public ArrayList<Product> getProductsCart() {
			return productsCart;
		}

		public void setProductsCart(ArrayList<Product> productsCart) {
			this.productsCart = productsCart;
		}
	
}
