package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import client.Client;
import entity.CustomMadeProduct;
import entity.Product;
import entity.Request;
import entity.Survey;
import entity.SurveyResults;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomMadeController extends GUIcontroller  {
	
	public static CustomMadeController last;
	@FXML private ComboBox<String> cmbType = new ComboBox<String>();
	@FXML private ComboBox<String> cmbColor = new ComboBox<String>();
	@FXML private TextField minTxt;
	@FXML private TextField maxTxt;
	@FXML private Text txt;
	
	
		
		
		@FXML
		public void onMenuClick(MouseEvent event) throws Exception {

			/*
			 *  Move to main menu
			 */
			loadFxmlMenu();
			
		}
		
		@FXML
		public void onbtnBuy(ActionEvent event) throws Exception {
			String color = cmbColor.getSelectionModel().getSelectedItem().toString();
			String type = cmbType.getSelectionModel().getSelectedItem().toString();
			Float min = Float.parseFloat(minTxt.getText());
			Float max = Float.parseFloat(maxTxt.getText());
			
			CustomMadeProduct cmp = new CustomMadeProduct();
			cmp.setColor(color);
			cmp.setMaxPrice(max);
			cmp.setMinPrice(min);
			cmp.setType(type);
			cmp.setMyUser(LoginController.myUser);
			
			sendRequestToServer(Actions.AddCustomOrder,cmp);
		}
		
	
		
		
		public void fillComboTypes(ArrayList<String> types) {
			ObservableList<String> obser = FXCollections.observableArrayList(types);
			cmbType.setItems(obser);
		}
		
		public void fillComboColors(ArrayList<String> colors) {
			ObservableList<String> obser = FXCollections.observableArrayList(colors);
			cmbColor.setItems(obser);
		}
		
		public void setMinPrice(Float price)
		{
			minTxt.setText(Float.toString(price));
		}
		
		public void setMaxPrice(Float price)
		{
			maxTxt.setText(Float.toString(price));
		}
		
		public void showAlert(String msg)
		{
			txt.setText(msg);
			txt.setOpacity(1);
		}
	
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get users from database
			sendRequestToServer(Actions.CustomOrderData);
		}
	
}
