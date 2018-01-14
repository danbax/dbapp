package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import entity.Address;
import client.Client;
import entity.CreditCard;
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

public class UpdateMyDataController extends GUIcontroller  {
	
	public static UpdateMyDataController last;
	
	//payment
	@FXML private TextField creditCardTxt;
	@FXML private TextField MonthTxt;
	@FXML private TextField YearTxt;
	@FXML private TextField CVVTxt;
	
	@FXML private Button updatePayment;
	
	//general
	@FXML private TextField usernameTxt;
	@FXML private TextField fnameTxt;
	@FXML private TextField passwordTxt;
	@FXML private TextField lnameTxt;
	@FXML private TextField phoneTxt;
	
	@FXML private Button updateGeneral;
	
	//address
	@FXML private TextField cityTxt;
	@FXML private TextField StreetTxt;
	@FXML private TextField sNumberTxt; 
	
	@FXML private Button updateAdress;
	
	
	
		
		
		@FXML
		public void onMenuClick(MouseEvent event) throws Exception {

			/*
			 *  Move to main menu
			 */
			loadFxmlMenu();
			
		}
		
		@FXML
		public void onupdatePayment(ActionEvent event) throws Exception {
			
			String creditCard = creditCardTxt.getText();
			int expMonth = Integer.parseInt(MonthTxt.getText());
			int expYear = Integer.parseInt(YearTxt.getText());
			String cvv = CVVTxt.getText();
			
			if(LoginController.myCreditCard != null)
			{
				// update
				LoginController.myCreditCard.setCardNumber(creditCard);
				LoginController.myCreditCard.setExpMonth(expMonth);
				LoginController.myCreditCard.setExpYear(expYear);
				LoginController.myCreditCard.setCvv(cvv);
				
				ArrayList<Object> arr = new ArrayList<Object>();
				arr.add(LoginController.myCreditCard);
				arr.add(LoginController.myUser);
				
				sendRequestToServer(Actions.UpdateCreditCard,arr);
			}
			else
			{
				// insert new 
				CreditCard cc = new CreditCard();
				cc.setCardNumber(creditCard);
				cc.setExpMonth(expMonth);
				cc.setExpYear(expYear);
				cc.setCvv(cvv);
				
				ArrayList<Object> arr = new ArrayList<Object>();
				arr.add(cc);
				arr.add(LoginController.myUser);
				
				sendRequestToServer(Actions.AddCreditCard,arr);
				
				
			}
			
			sendRequestToServer(Actions.GetMyCreditCard,LoginController.myUser);
		}
		
		@FXML
		public void onupdateGeneral(ActionEvent event) throws Exception {
			
			String username = usernameTxt.getText();
			String fname = fnameTxt.getText();
			String password = passwordTxt.getText();
			String lname = lnameTxt.getText();
			String phone = phoneTxt.getText();
			
			if(LoginController.myUser != null)
			{
				// update my user
				LoginController.myUser.setUsername(username);
				LoginController.myUser.setFname(fname);
				LoginController.myUser.setPassword(password);
				LoginController.myUser.setLname(lname);
				LoginController.myUser.setPhone(phone);
			
				
				sendRequestToServer(Actions.updateUser,LoginController.myUser);
			}
		}
		
		@FXML
		public void onupdateAdress(ActionEvent event) throws Exception {
			String city = cityTxt.getText();
			String street = StreetTxt.getText();
			int number = Integer.parseInt(sNumberTxt.getText());
			
			if(LoginController.myAddress != null)
			{
				// update
				LoginController.myAddress.setCity(city);
				LoginController.myAddress.setStreet(street);
				LoginController.myAddress.setNumber(number);
				
				ArrayList<Object> arr = new ArrayList<Object>();
				arr.add(LoginController.myAddress);
				arr.add(LoginController.myUser);
				
				sendRequestToServer(Actions.UpdateAddress,arr);
			}
			else
			{
				// insert new 
				Address address = new Address();
				address.setCity(city);
				address.setStreet(street);
				address.setNumber(number);
				
				ArrayList<Object> arr = new ArrayList<Object>();
				arr.add(address);
				arr.add(LoginController.myUser);
				
				sendRequestToServer(Actions.AddAddress,arr);
			}
			
			sendRequestToServer(Actions.GetMyAdress,LoginController.myUser);
		}
		
		public void fillPayment()
		{
			if(LoginController.myCreditCard != null)  
			{
			creditCardTxt.setText(LoginController.myCreditCard.getCardNumber());
			MonthTxt.setText(Integer.toString(LoginController.myCreditCard.getExpMonth()));
			YearTxt.setText(Integer.toString(LoginController.myCreditCard.getExpYear()));
			CVVTxt.setText(LoginController.myCreditCard.getCvv());
			}
		}
		
		public void fillAddress()
		{
			if(LoginController.myAddress != null)
			{
				cityTxt.setText(LoginController.myAddress.getCity());
				StreetTxt.setText(LoginController.myAddress.getStreet());
				sNumberTxt.setText(Integer.toString(LoginController.myAddress.getNumber()));
			}
		}
		
		public void fillGeneral()
		{
			if(LoginController.myUser != null)
			{
				usernameTxt.setText(LoginController.myUser.getUsername());
				fnameTxt.setText(LoginController.myUser.getFname());
				passwordTxt.setText(LoginController.myUser.getPassword());
				lnameTxt.setText(LoginController.myUser.getLname());
				phoneTxt.setText(LoginController.myUser.getPhone());
			}
		}
		

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// fill boxes from myUser in Login Controller
			if(LoginController.myUser != null)
				fillGeneral();
			
			if(LoginController.myCreditCard != null)
				fillPayment();
			
			if(LoginController.myAddress != null)
				fillAddress();
		}
	
}
