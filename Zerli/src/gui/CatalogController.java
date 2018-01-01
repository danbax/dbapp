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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CatalogController extends Application implements Initializable  {
	
	public static CatalogController last;
	@FXML FlowPane flowPane;
	@FXML TextField txtSearch;
	
	public static Product selectedProduct; 
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/Catalog.fxml")); 
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
						guic.loadFxmlMenu();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				});
			
		}
		
		
	
		public void setCatalogProducts(ArrayList<Product> products)
		{
			// flow pane - infinite pane to hold products
			flowPane.setPadding(new Insets(10, 10, 10, 10));
		    flowPane.setVgap(4);
		    flowPane.setHgap(4);
		    flowPane.setPrefWrapLength(300);
		    flowPane.setPrefHeight(600);
		   
		    
		    for (Product p: products) {
		    	System.out.println(p.getPrice());
			    System.out.println(p.getImage());
			    System.out.println(p.getProductName());
		    }
		    System.out.println();System.out.println();

		    for (Product p: products) {
		    	GridPane gridpane = new GridPane(); // to show product data
		    	ImageView productImg = new ImageView();
		        // set Product data
		    	try {
		    		productImg.setImage(new Image("/serverImages/"+p.getImage()));
		    		productImg.setFitWidth(100);
		    		productImg.setFitHeight(100);
		    	}  catch(Throwable e) {
		            // prints stacktace for creating image
		            e.printStackTrace();
		         }
			    Text productName = new Text(p.getProductName());
			    Text price = new Text(Float.toString(p.getPrice()));
			    Button PurcasheBtn = new Button("buy");
			    PurcasheBtn.setUserData(p);
			    
			    PurcasheBtn.setOnAction(new EventHandler<ActionEvent>() {
			        @Override public void handle(ActionEvent e) {
			        	if(LoginController.myUser.getAuthorized() == 0)
			        	{
			        		PurcasheBtn.setText("Not authorized");
			        	}
			        	else
			        	{
			        		// update selected product id
			        		Button thisBtn = (Button)e.getSource();
			        		selectedProduct = (Product)thisBtn.getUserData();

			        		Platform.runLater(new Runnable(){
			    				@Override
			    				public void run() {
			    					// TODO Auto-generated method stub
			    					GUIcontroller guic = new GUIcontroller();
			    					try {
			    						guic.loadFxml("orderProductFromCatalog.fxml");
			    					} catch (IOException e) {
			    						// TODO Auto-generated catch block
			    						e.printStackTrace();
			    					}
			    				}
			    				
			    				});
			        				
			        		
			        	}
			        }
			    });

			    // put information in grid
		        GridPane.setRowIndex(productName, 0);
		        GridPane.setRowIndex(productImg, 1);
		        GridPane.setRowIndex(price, 2);
		        GridPane.setRowIndex(PurcasheBtn, 3);
		        gridpane.getChildren().addAll(PurcasheBtn,productImg, productName,price);
		        
		        GridPane.setHalignment(productName, HPos.CENTER);
		        GridPane.setHalignment(productImg, HPos.CENTER);
		        GridPane.setHalignment(PurcasheBtn, HPos.CENTER);
		        GridPane.setHalignment(price, HPos.CENTER);
		        
		        try {
		        	Platform.runLater(new Runnable() {
		                 @Override public void run() {
		        	flowPane.getChildren().add(gridpane);
		                 }
		             });
		    	}  catch(Throwable e) {
		            e.printStackTrace();
		         }
		        
		    	}
		}
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetProductCatalog); 
			req.setValue(null);
			Client.clientConn.handleMessageFromClientUI(req);
			
			
		    
		}	
}
