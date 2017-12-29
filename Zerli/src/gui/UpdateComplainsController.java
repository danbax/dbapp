package gui;

import java.awt.TextArea;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.Client;
import client.Complain;
import client.ImgFile;
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

public class UpdateComplainsController extends Application implements Initializable  {
	
	public static UpdateComplainsController last;
	private ObservableList<Complain> ObserProducts;
	@FXML
	private TableView<Complain> ComplainsTable = new TableView<Complain>(); // table of complains
	
	
	@FXML
	private TextField txtFieldComplainID;
	@FXML
	private TextField txtFieldUserID;
	@FXML
	private TextArea txtAreaDesc;

	
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnDeleteComplain;
	
	
	File file; // Image file to upload
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/Complains.fxml"));
			Scene scene = new Scene(root);
			GUIcontroller.setCurrentScene(scene); // save scene
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		}
		

		
		
		@FXML
		public void onBtnAddClicked(ActionEvent event) throws Exception {
			if (file != null) {
			// get data from form
			int complainID = Integer.parseInt(txtFieldComplainID.getText());
			int userID = Integer.parseInt(txtFieldUserID.getText());
			String desc = txtAreaDesc.getText();
			
			
			// create product
			Complain c = new Complain(complainID, userID, desc, "Pending", 0);
			
			
			// send request to server
			Request req = new Request(Actions.AddComplain,c);
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			Client.clientConn.handleMessageFromClientUI(req);
			req.setAction(Actions.GetComplain); 
			Client.clientConn.handleMessageFromClientUI(req);
			}
			else
			{
				// no picture selected
			}
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
						guic.loadFxml("MenuEmployee.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				});
			
		}
		
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {
			// add product to database, clean form, show message "added succefully"

			/*
			 *  delete selected row from database
			 */
			
			// get selected item
			Complain complain = ComplainsTable.getSelectionModel().getSelectedItem();
			if(complain!= null)
			{
				
				System.out.println(complain.getComplainId());
				// delete
				Request req = new Request();
    			req.setAction(Actions.DeleteComplain);
    			req.setValue(complain);
    			Client.clientConn.handleMessageFromClientUI(req);	
    			
    			// refresh table
    			Request req2 = new Request();
    			req2.setAction(Actions.GetComplain);
    			Client.clientConn.handleMessageFromClientUI(req2);
				
			}
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillComplainsInTable(ArrayList<Complain> complains)
		{
			/*
			 * This function fill the products table with data in ArrayList products
			 * Works only for first load of tableView
			 */
			if(ObserProducts != null) {
				// if table already populate
				/*Dani: didn't find better solution */
				ObserProducts.removeAll(ObserProducts);
				for(Complain c : complains)
				{
					ObserProducts.add(c);
				}
				
			}
			else
			{
				ComplainsTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserProducts = FXCollections.observableArrayList(complains);
				
				// defining table columns
				TableColumn<Complain, Integer> complainIDcol = new TableColumn<Complain, Integer>("ID");
				TableColumn<Complain, Integer> userIDcol = new TableColumn<Complain, Integer>("User ID");
				TableColumn<Complain, String> descriptionCol = new TableColumn<Complain, String>("Description");
				TableColumn<Complain, String> statusCol = new TableColumn<Complain, String>("Status");
				TableColumn<Complain, String> compenseCol = new TableColumn<Complain, String>("Compensation");
				
				//add data to columns
				complainIDcol.setCellValueFactory(
		        	    new PropertyValueFactory<Complain,Integer>("complainId"));
				complainIDcol.setCellFactory(TextFieldTableCell.forTableColumn());
		        complainIDcol.setOnEditCommit(
		            new EventHandler<CellEditEvent<Complain, Integer>>() {
		                @Override
		                public void handle(CellEditEvent<Complain, Integer> t) {
		                	// show new name in column
		                    ((Complain) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setComplainId(complainId);(t.getNewValue());
		                    
		                    //update new name to database
		                    String newName = t.getNewValue();
		                    Product productToUpdate = (Complain) t.getTableView().getItems().get(
			                        t.getTablePosition().getRow());
		                    productToUpdate.setProductName(newName);
		                    
		                    //send request to server
		                    Request req = new Request();
		        			req.setAction(Actions.UpdateComplain);
		        			req.setValue(productToUpdate);
		        			Client.clientConn.handleMessageFromClientUI(req);
		                }
		            }
		        );
		        
		        TableColumn<Complain, Integer> userIDcol = new TableColumn<Complain, Integer>("userID");
		        userIDcol.setCellValueFactory(
		        	    new PropertyValueFactory<Product,Float>("price")
		        	);
		        PriceCol.setOnEditCommit(
			            new EventHandler<CellEditEvent<Product, Float>>() {
			                @Override
			                public void handle(CellEditEvent<Product, Float> t) {
			                	// show new name in column
			                    ((Product) t.getTableView().getItems().get(
			                        t.getTablePosition().getRow())
			                        ).setPrice(t.getNewValue());
			                    
			                    //update new name to database
			                    Float newPrice = t.getNewValue();
			                    Product productToUpdate = (Product) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow());
			                    productToUpdate.setPrice(newPrice);
			                    
			                    //send request to server
			                    Request req = new Request();
			        			req.setAction(Actions.UpdateProduct);
			        			req.setValue(productToUpdate);
			        			Client.clientConn.handleMessageFromClientUI(req);
			                }
			            }
			        );
		        
		        //update name
		       

		        
		        typeCol.setCellValueFactory(
		        	    new PropertyValueFactory<Product,String>("productType")
		        	);
		        
		      //update type
		        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
		        typeCol.setOnEditCommit(
		            new EventHandler<CellEditEvent<Product, String>>() {
		                @Override
		                public void handle(CellEditEvent<Product, String> t) {
		                	// show new name in column
		                    ((Product) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setProductName(t.getNewValue());
		                    
		                    //update new name to database
		                    String newType = t.getNewValue();
		                    Product productToUpdate = (Product) t.getTableView().getItems().get(
			                        t.getTablePosition().getRow());
		                    productToUpdate.setProductType(newType);
		                    
		                    //send request to server
		                    Request req = new Request();
		        			req.setAction(Actions.UpdateProduct);
		        			req.setValue(productToUpdate);
		        			Client.clientConn.handleMessageFromClientUI(req);
		                }
		            }
		        );
		        
		      
		        /*
		        TableColumn<Product, ProductImage> imageCol = new TableColumn<Product, ProductImage>("Image");
		        imageCol.setCellValueFactory(new PropertyValueFactory<Product, ProductImage>("productImg"));
		        imageCol.setPrefWidth(60);
		      */
				
		        
		        ProductsTable.setItems(ObserProducts);
		        ProductsTable.getColumns().addAll(nameCol, typeCol,PriceCol);
			}
		}
		
		public ImgFile getImgFile(String LocalfilePath)
		{
			// string IMG - path to file
			
			  String extension = "";
			  String fileName = "";

			  // get file exe( e.x "filename.jpg" ==> jpg
			  int i = LocalfilePath.lastIndexOf('.');
			  if (i >= 0) {
			      extension = LocalfilePath.substring(i+1);
			  }
			 
			
			  ImgFile msg= new ImgFile(LocalfilePath);
			  msg.setExe(extension);
			  try{

				      File newFile = new File (LocalfilePath);
				      		      
				      byte [] mybytearray  = new byte [(int)newFile.length()];
				      FileInputStream fis = new FileInputStream(newFile);
				      BufferedInputStream bis = new BufferedInputStream(fis);			  
				      
				      msg.initArray(mybytearray.length);
				      msg.setSize(mybytearray.length);
				      
				      bis.read(msg.getMybytearray(),0,mybytearray.length);		      
				    }
				catch (Exception e) {
					System.out.println("Error send (Files)msg) to Server");
				}
			  return msg;
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// getProducts from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetComplain); 
			Client.clientConn.handleMessageFromClientUI(req);
			
		}
	
}
