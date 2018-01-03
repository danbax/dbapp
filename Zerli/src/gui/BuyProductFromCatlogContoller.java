package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Address;
import client.Client;
import entity.CreditCard;
import entity.Order;
import entity.Product;
import entity.Request;
import entity.User;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BuyProductFromCatlogContoller extends Application implements Initializable  {
	
	public static BuyProductFromCatlogContoller last;
	@FXML Text priceTXT; 
	@FXML ImageView imgView; 
	@FXML Text addressTXT; 
	@FXML Text creditCardTXT; 
	@FXML ComboBox<String> payMethod;
	@FXML ToggleButton addgc;
	@FXML DatePicker picDate;
	@FXML ComboBox<Integer> hours;
	@FXML ComboBox<Integer> minutes;
	@FXML AnchorPane greeting;
	@FXML TextArea greetingText;
	
	private Boolean isTgPressed = false;
	
	Address address = LoginController.myAddress;
	CreditCard creditCard = LoginController.myCreditCard;
	User user = LoginController.myUser; 
	
	@FXML
	public void onBuy(ActionEvent event) throws Exception {
		// buy button clicked
		
		LocalDate localDate = picDate.getValue();
		String greet;
		if(isTgPressed)
			greet = greetingText.getText();
		else
			greet = "";
		
		int h = Integer.parseInt(hours.getSelectionModel().getSelectedItem().toString());
		int m = Integer.parseInt(minutes.getSelectionModel().getSelectedItem().toString());
		
		int paymentMethod=0;
		
		if(payMethod.getSelectionModel().getSelectedItem().toString().equals("Cash"))
		{
			paymentMethod = 1;
		}
		if(payMethod.getSelectionModel().getSelectedItem().toString().equals("Credit card"))
		{
			paymentMethod = 2;
		}
		
		Order order = new Order();
		order.setDate(localDate);
		order.setGreeting(greet);
		order.setHours(h);
		order.setMinutes(m);
		order.setPrice(calculateTotalOrderPrice());
		order.setUser(user);
		order.setPaymentMethod(paymentMethod);
		
		
		Request req = new Request();
		req.setAction(Actions.buyProductFromCatalog);
		req.setValue(order);
		Client.clientConn.handleMessageFromClientUI(req);
		
		
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUIcontroller guic = new GUIcontroller();
				try {
					guic.loadFxml("OrdersHistory.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			});
		

	}
	
	@FXML
	public void onpayMethod(ActionEvent event) throws Exception {
		// pay method select 
	}
	
	@FXML
	public void onAddGrettingCard(ActionEvent event) throws Exception {
		
		if(isTgPressed)
			isTgPressed = false;
		else
			isTgPressed = true;
		
		// buy button clicked
		if(isTgPressed)
			greeting.setOpacity(1);
		else
		{
			greeting.setOpacity(0);
		}
		
	}
	
	
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
		
		public float calculateTotalOrderPrice()
		{
			float sum = 0;
			ArrayList<Product> products = CartController.last.getProductsCart();
			for(Product p : products)
				sum+=p.getPrice();
			return sum;			
		}
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			priceTXT.setText("Total: " + calculateTotalOrderPrice());
			
			if(address != null)
				addressTXT.setText(address.getCity() + ", " + address.getStreet() + " " + address.getNumber());
			if(creditCard != null)
				creditCardTXT.setText("******" + creditCard.getCardNumber().substring(creditCard.getCardNumber().length() - 4));
			
			
			ArrayList<Integer> hoursArray = new ArrayList<Integer>();
			for(int i=0; i<24; i++)
			{
				hoursArray.add(i);
			}
			ArrayList<Integer> minutesArray = new ArrayList<Integer>();
			for(int i=0; i<60; i++)
			{
				minutesArray.add(i);
			}
			ArrayList<String> payMeth = new ArrayList<String>();
			payMeth.add("Credit card");
			payMeth.add("Cash");
			ObservableList<Integer> obserHours = FXCollections.observableList(hoursArray);
			ObservableList<Integer> obserMinutes = FXCollections.observableList(minutesArray);
			ObservableList<String> obserPayMeth = FXCollections.observableList(payMeth);
			
			this.hours.setItems(obserHours);
			this.minutes.setItems(obserMinutes);
			this.payMethod.setItems(obserPayMeth);
		}	
}
