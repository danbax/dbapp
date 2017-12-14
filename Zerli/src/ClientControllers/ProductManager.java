package ClientControllers;
import gui.SelectProductController;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import client.Main;
import client.Product;
import client.Request;
import enums.Actions;

public class ProductManager extends Application {
	
	public static ArrayList<Product> ListOfProducts = new ArrayList<Product>();	
	private static SelectProductController aFrame;
		
	public static void main( String args[] ) throws Exception
	   { 
        launch(args);		
	  } // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
		//Vector<Student> students=new Vector<Student>();
		String host = "localhost";
		
		Main mainWindow = new Main(host, Main.DEFAULT_PORT);	
		
		// show success message
		if (mainWindow != null)
			System.out.println("Connection to " + host + " succeeded!");
		
		ArrayList<Object> getProduct = new ArrayList<Object>();
		getProduct.add(Actions.getProducts);

		Main.clientConn.handleMessageFromClientUI(getProduct);
		
		
		if(ListOfProducts.isEmpty())
			System.out.println("empty");
							  		
		aFrame = new SelectProductController(); // create StudentFrame
		aFrame.start(arg0);
				
	}
	
	@SuppressWarnings("unchecked")
	public static void setStudentComboBox(Object msg) {
		ListOfProducts = (ArrayList<Product>) msg;
		aFrame.setStudentComboBox(msg);
		
	}
	
	public static void updateProduct(ArrayList<Object> updateProduct) {
		Main.clientConn.handleMessageFromClientUI(updateProduct);
		
	}
	
	
}
