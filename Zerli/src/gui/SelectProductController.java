package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ClientControllers.ProductManager;

public class SelectProductController implements Initializable {
	private UpdateProductController sfc;	
	static ObservableList<String> list;
	@FXML
	Button btnStudentInfo;
	
	
	@FXML
	private ComboBox<String> cmbStudents;	
		
	@FXML
	private Button btnExit = null;
		
	public void itemInfo(ActionEvent event) throws Exception {
		/*
		 * move to update product screen
		 */
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/updateProductForm.fxml").openStream());
		
		UpdateProductController studentFormController = loader.getController();		
		studentFormController.loadStudent(ProductManager.ListOfProducts.get(cmbStudents.getSelectionModel().getSelectedIndex()));
		
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/updateProductForm.css").toExternalForm());
		
		primaryStage.setScene(scene);		
		primaryStage.show(); 
	}

	public void start(Stage primaryStage) throws Exception {
		
		/*
		 * start select product frame
		 */
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui/selectProductFrame.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/selectProductFrame.css").toExternalForm());
		primaryStage.setTitle("Product update tool");
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		//System.exit(0);			
	}
	
	
	
	public void loadStudent(Product p1) {
		this.sfc.loadStudent(p1);
		}
	
	// creating list of Products
		@SuppressWarnings("unchecked")
		public void setStudentComboBox(Object msg) {
			if(msg instanceof ArrayList)
			{
				ArrayList<String> al = new ArrayList<String>();	
				for(Product p: (ArrayList<Product>) msg)
				{
					al.add(p.getProductName());
				}
				
				list = FXCollections.observableArrayList(al);
				cmbStudents.setItems(list);
			}
			
		}
	

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			setStudentComboBox(ProductManager.ListOfProducts);
			
		}
		
}
