package ClientControllers;
import gui.SelectProductController;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import client.Client;
import client.Product;
import enums.Actions;

public class ProductManager extends Application {
	
	public static ArrayList<Product> ListOfProducts = new ArrayList<Product>();	
	private static SelectProductController selectProductFrame;
		
	public static void main( String args[] ) throws Exception
	   { 
		updateDataComboBox();
        launch(args);		
	  } // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
		/*
		 * start select product frame
		 */
		updateDataComboBox(); 	  	
		selectProductFrame = new SelectProductController(); // create StudentFrame
		selectProductFrame.start(arg0);
	}
	
	public static void updateDataComboBox()
	{
		/*
		 * update combobox from database
		 */
		@SuppressWarnings("unused")
		Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);	
		
		ArrayList<String> get = new ArrayList<String>();
		get.add(Actions.getProducts.toString());
		
		Client.clientConn.handleMessageFromClientUI(get);	  	
	}
	
	@SuppressWarnings("unchecked")
	public static void setProductComboBox(Object msg) {
		/*
		 * put arraylist into student combobox
		 */
		ListOfProducts = (ArrayList<Product>) msg;
		selectProductFrame.setStudentComboBox(msg);
	}
	
	
	
	
}
