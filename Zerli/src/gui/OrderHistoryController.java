package gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.Client;
import entity.ImgFile;
import entity.Order;
import entity.Product;
import entity.Refund;
import entity.Request;
import entity.User;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OrderHistoryController extends GUIcontroller  {
	
	public static OrderHistoryController last;
	private ObservableList<Order> obserOrders;
	@FXML
	private TableView<Order> OrdersTable = new TableView<Order>(); // table of products
	@FXML private Text refundText;
	@FXML private Button refund;
	
	
	
	
	
	
	File file; // Image file to upload
	
	
		
		
		
		@FXML
		public void onGetRefund(ActionEvent event) throws Exception {
			Order o = OrdersTable.getSelectionModel().getSelectedItem();
			
			sendRequestToServer(Actions.CancelOrder,o);
			
			//refund add
			Refund refund = new Refund();
			refund.setOrder(o);
			refund.setRefund(o.calculateRefund());
			
			sendRequestToServer(Actions.AddRefund,refund);
			
			
			//refresh
			
			sendRequestToServer(Actions.GetMyOrdersHistory,LoginController.myUser);
			
			
		}
		
		@FXML
		public void onMenuClick(MouseEvent event) throws Exception {
			// add product to database, clean form, show message "added succefully"

			/*
			 *  Move to main menu
			 */
			loadFxmlMenu();
			
		}
		
		@FXML
		public void onCancel(MouseEvent event)  throws Exception {
			Order o = OrdersTable.getSelectionModel().getSelectedItem();
			if(o.getStatus().equals("canceled"))
			{
				refundText.setText("Already canceled!");
				refund.setOpacity(0);
			}
				
			if(o.getStatus().equals("delivered"))
			{
				refundText.setText("Already delivered!");
				refund.setOpacity(0);
			}
				
			if(o.getStatus().equals("pending"))
			{
			// calculate refund
				float refundC = o.calculateRefund();
				refundText.setText("Canceled! Your refund: " + refundC);
				refund.setOpacity(1);
			}
			
			refundText.setOpacity(1);
			
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
				
				TableColumn<Order, Integer> minCol = new TableColumn<Order, Integer>("Minutes");
				minCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("minutes"));
				
				TableColumn<Order, Float> priceCol = new TableColumn<Order, Float>("Price");
				priceCol.setCellValueFactory(new PropertyValueFactory<Order, Float>("price")); 
				
				TableColumn<Order, LocalDate> arrivingCol = new TableColumn<Order, LocalDate>("Arriving date");
				arrivingCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDate>("date")); 
				
				TableColumn<Order, String> greetingCol = new TableColumn<Order, String>("Greeting text");
				greetingCol.setCellValueFactory(new PropertyValueFactory<Order, String>("greeting")); 
				
				TableColumn<Order, String> statusCol = new TableColumn<Order, String>("status");
				statusCol.setCellValueFactory(new PropertyValueFactory<Order, String>("status")); 
		        
		        OrdersTable.setItems(obserOrders);
		        OrdersTable.getColumns().addAll(greetingCol,priceCol,arrivingCol,hoursCol,minCol,statusCol); 
			}
		}
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			sendRequestToServer(Actions.GetMyOrdersHistory,LoginController.myUser);
			
			
		}
	
}
