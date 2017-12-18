package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientControllers.ProductManager;
import client.Client;
import client.Product;
import enums.Actions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateProductController implements Initializable  {
	private Product p;
		
	@FXML
	private Label lblStID;
	@FXML
	private Label lblName;
	@FXML
	private Label lblSurname;
	@FXML
	private Label lblFaculty;
	
	@FXML
	private TextField txtStID;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSurname;
	
	@FXML
	private Button btnClose;
	
	@FXML
	private Label txtUpdate;
		
	
	ObservableList<String> list;
		
	public void loadStudent(Product product){
		this.p=product;
		this.txtName.setText(p.getProductName());
		this.txtSurname.setText(p.getProductType()); 
	}
	
	
		public void backBtnAction(ActionEvent event) throws Exception {
			ProductManager.updateDataComboBox();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/gui/selectProductFrame.fxml").openStream());
			
			Scene scene = new Scene(root);			
			scene.getStylesheets().add(getClass().getResource("/gui/selectProductFrame.css").toExternalForm());
			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		
		public void saveBtnAction(ActionEvent event) throws Exception {
			String host = "localhost";
			
			Client mainWindow = new Client(host, Client.DEFAULT_PORT);	
			
			// show success message
			if (mainWindow != null)
				System.out.println("Connection to " + host + " succeeded!");
			
			int id = p.getProductID();
			String pname = txtName.getText();
			String ptype = txtSurname.getText();
			String idString = Integer.toString(id);
			
			ArrayList<String> up = new ArrayList<String>();
			up.add(Actions.updateProducts.toString());
			up.add(idString);
			up.add(pname);
			up.add(ptype);
			mainWindow.Accept(up);
			txtUpdate.setText("updated!");
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			
		}
	
}
