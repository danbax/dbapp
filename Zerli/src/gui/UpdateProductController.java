package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientControllers.ProductManager;
import client.Main;
import client.Product;
import database.productManager;
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

public class UpdateProductController extends productManager implements Initializable  {
	ArrayList<String> str = new ArrayList<String>();
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
		
	
	ObservableList<String> list;
		
	public void loadStudent(Product product){
		this.p=product;
		this.txtName.setText(p.getProductName());
		this.txtSurname.setText(p.getProductType());
	}
	
	@FXML
		public void backBtnAction(ActionEvent event) throws Exception {
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/gui/selectProductFrame.fxml").openStream());
			
			Scene scene = new Scene(root);			
			scene.getStylesheets().add(getClass().getResource("/gui/selectProductFrame.css").toExternalForm());
			
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
		@FXML
		public void saveBtnAction(ActionEvent event) throws Exception {
			String pname = txtName.getText();
			String ptype = txtSurname.getText();
			int pid = p.getProductID();
			
			//debug
			System.out.println(pname+"-"+ptype+"-"+pid);
			
			ArrayList<Object> updateProduct = new ArrayList<Object>();
			updateProduct.add(Actions.updateProducts);
			updateProduct.add(Integer.toString(pid));
			updateProduct.add(pname);
			updateProduct.add(ptype);
			//ProductManager.updateProduct(updateProduct);
			Main.clientConn.handleMessageFromClientUI(updateProduct);
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			
		}
	
}
