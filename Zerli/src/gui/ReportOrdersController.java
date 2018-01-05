package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Client;
import entity.CartProduct;
import entity.ReportOrders;
import entity.ReportRevenue;
import entity.Request;
import entity.Revenue;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReportOrdersController extends Application implements Initializable {
	public static ReportOrdersController last;
	private ObservableList<CartProduct> Obser;
	@FXML
	private TableView<CartProduct> table = new TableView<CartProduct>(); // table of products
	@FXML private ComboBox<Integer> cmbq;
	@FXML private ComboBox<Integer> cmbYear;
	
	@FXML private BarChart<String, Integer> barChart;
	@FXML NumberAxis yAxis;
	@FXML CategoryAxis xAxis;
	
	
	@FXML private Text sumEarning;
	@FXML private Text dateTXT;
	@FXML private TextField txtSearch;
	
	public ReportOrders report; // this report
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/ReportOrders.fxml"));
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
		
	
		
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<CartProduct> cart)
		{
			/*
			 * This function fill the table with data in ArrayList 
			 */
			
			if(Obser != null) {
				// if table alredy populate
				/*Dani: didn't find better solution */
				Obser.removeAll(Obser);
				for(CartProduct c: cart)
				{
					Obser.add(c);
				}
				
			}
			else
			{
				
				//casting ArrayList to ObservableList
				Obser = FXCollections.observableArrayList(cart);
				
				// defining table columns
				TableColumn<CartProduct, LocalDate> idCol = new TableColumn<CartProduct, LocalDate>("Date");
				
				//add data to columns
				idCol.setCellValueFactory( new PropertyValueFactory<CartProduct, LocalDate>("orderDate"));
				
				TableColumn<CartProduct, String> typeCol = new TableColumn<CartProduct, String>("Product type");
				typeCol.setCellValueFactory( new PropertyValueFactory<CartProduct, String>("product_type"));
		        
		        
		    
				table.setItems(Obser);
				table.getColumns().addAll(idCol, typeCol);

		        
			}
		}
		
		public void TextNames(ReportRevenue rp,int year,int quarter)
		{
			// change Text of sum earnings
			
		}
		
		@FXML
		public void changeYear(ActionEvent event) throws Exception {
			selectYearAndQuarter();
		}
		
		@FXML
		public void changeQ(ActionEvent event) throws Exception {
			selectYearAndQuarter();
		}
		
		public void selectYearAndQuarter()
		{
			// on select year and quarter
			boolean isq = cmbq.getSelectionModel().isEmpty();
			boolean isy = cmbYear.getSelectionModel().isEmpty();
			if(isy==false && isq==false) // checks if 2 combobox selected
			{
				int quarter = this.cmbq.getSelectionModel().getSelectedItem(); 
				int year = this.cmbYear.getSelectionModel().getSelectedItem(); 
				
				this.fillTable(report.getCartByQY(year, quarter));
				this.fillBarChart(report, year, quarter);
			}
			
		}
		
		
		public void fillBarChart(ReportOrders report,int year,int quarter)
		{
			
			// init bar chart
			barChart.setAnimated(true);
			barChart.setTitle("chart");
	        xAxis.setLabel("types");       
	        yAxis.setLabel("money");
			barChart.getData().clear();
			
			for(String type: report.getProductTypes(year, quarter))
			{
				XYChart.Series<String, Integer> ser1 = new Series<String, Integer>();
				ser1.setName(type); 
				ser1.getData().add(new Data<String, Integer>("number",(int) report.getNumberOfProductsByType(type, year, quarter)));  
				barChart.getData().addAll(ser1);
			}
	        
	        
		}
		
		public void fillComboYears(ArrayList<Integer> years) {
			ObservableList obser = FXCollections.observableArrayList(years);
			this.cmbYear.setItems(obser);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
	        
	     
		Request req = new Request();
		Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
		req.setAction(Actions.GetCartOrders); 
		Client.clientConn.handleMessageFromClientUI(req);
		
		// add quarters to combo
		ArrayList<Integer> quarters = new ArrayList<Integer>();
		quarters.add(1);
		quarters.add(2);
		quarters.add(3);
		quarters.add(4);
		ObservableList obser = FXCollections.observableArrayList(quarters);
		this.cmbq.setItems(obser);
		
		}
}
