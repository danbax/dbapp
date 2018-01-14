package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import client.Client;
import entity.Deal;
import entity.Product;
import entity.Request;
import entity.User;
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
import javafx.scene.control.ComboBox;
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

public class UpdateDealsController extends GUIcontroller  {
	
	public static UpdateDealsController last;
	private ObservableList<Deal> ObserDeal;
	@FXML private TableView<Deal> DealsTable = new TableView<Deal>(); // table of products
	
	@FXML private ComboBox<Product> cmbProduct;
	@FXML private ComboBox<Integer> cmbDiscount;
	
	
		@FXML
		public void onMenuClick(MouseEvent event) throws Exception {

			/*
			 *  Move to main menu
			 */
			loadFxmlMenu();
			
		} 
		
		@FXML
		public void onbtnAdd(ActionEvent event) throws Exception {
			// add deal
			Product p = this.cmbProduct.getSelectionModel().getSelectedItem();
			int d = this.cmbDiscount.getSelectionModel().getSelectedItem();
			
			Deal deal = new Deal();
			deal.setPercent(d);
			deal.setProductId(p.getPid());
			
			sendRequestToServer(Actions.AddDeal,deal);
			sendRequestToServer(Actions.GetDeals);
		}
		
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {

			/*
			 *  delete selected row from database
			 */
			
			// get selected item
			Deal deal = DealsTable.getSelectionModel().getSelectedItem();
			if(deal!= null)
			{
				sendRequestToServer(Actions.DeleteDeal,deal);
				sendRequestToServer(Actions.GetDeals);
			}
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<Deal> deals)
		{
			/*
			 * This function fill the table with data in ArrayList 
			 */
			
			
			if(ObserDeal != null) {
				// if table alredy populate
				/*Dani: didn't find better solution */
				ObserDeal.removeAll(ObserDeal);
				for(Deal d: deals)
				{
					ObserDeal.add(d);
				}
				
			}
			else
			{
				DealsTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserDeal = FXCollections.observableArrayList(deals);
				
				// defining table columns
				TableColumn<Deal, Integer> idCol = new TableColumn<Deal, Integer>("ID");
				TableColumn<Deal, Integer> percentCol = new TableColumn<Deal, Integer>("percent");
				TableColumn<Deal, Integer> productIdCol = new TableColumn<Deal, Integer>("Product ID");
				
				//add data to columns
				idCol.setCellValueFactory(new PropertyValueFactory<Deal,Integer>("id"));
				percentCol.setCellValueFactory( new PropertyValueFactory<Deal,Integer>("percent"));
				productIdCol.setCellValueFactory( new PropertyValueFactory<Deal,Integer>("productId"));
				
		        
				DealsTable.setItems(ObserDeal);
				DealsTable.getColumns().addAll(idCol, percentCol,productIdCol);
			}
		}
		
		public void fillComboProduct(ArrayList<Product> products)
		{
			ObservableList observableList = FXCollections.observableList(products);
		    this.cmbProduct.setItems(observableList);
		}
		
		public void fillComboPercent()
		{
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for(int i=1; i<100;i++)
			{
				arr.add(i);
			}
			
			ObservableList observableList = FXCollections.observableList(arr);
		    this.cmbDiscount.setItems(observableList);
		}
		
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get deals from database
			sendRequestToServer(Actions.GetDeals);
			sendRequestToServer(Actions.GetProductsDeals);
			fillComboPercent();
			
		}
	
}
