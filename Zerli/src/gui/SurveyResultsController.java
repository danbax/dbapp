package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import client.Client;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SurveyResultsController extends Application implements Initializable  {
	
	public static SurveyResultsController last;
	private ObservableList<Survey> ObserSurveys;
	private ObservableList<SurveyResults> ObserSurveysResults;
	@FXML
	private TableView<SurveyResults> surveyTable = new TableView<SurveyResults>(); // table of products
	
	
	@FXML private ComboBox<Survey> selectSurvey = new ComboBox<Survey>();
	@FXML private ComboBox<Integer> cmbq1 = new ComboBox<Integer>();
	@FXML private ComboBox<Integer> cmbq2 = new ComboBox<Integer>();
	@FXML private ComboBox<Integer> cmbq3 = new ComboBox<Integer>();
	@FXML private ComboBox<Integer> cmbq4 = new ComboBox<Integer>();
	@FXML private ComboBox<Integer> cmbq5 = new ComboBox<Integer>();
	@FXML private ComboBox<Integer> cmbq6 = new ComboBox<Integer>();
	@FXML private Text txtq1 = new Text();
	@FXML private Text txtq2 = new Text();
	@FXML private Text txtq3 = new Text(); 
	@FXML private Text txtq4 = new Text();
	@FXML private Text txtq5 = new Text();
	@FXML private Text txtq6 = new Text();
	ObservableList<Integer> observableList;
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/SurveyResults.fxml"));
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
		
		@FXML
		public void onBtnAddClicked(ActionEvent event) throws Exception {
			// add survey results
			Survey s = this.selectSurvey.getSelectionModel().getSelectedItem();
			int q1 = this.cmbq1.getSelectionModel().getSelectedItem(); 
			int q2 = this.cmbq2.getSelectionModel().getSelectedItem();
			int q3 = this.cmbq3.getSelectionModel().getSelectedItem();
			int q4 = this.cmbq4.getSelectionModel().getSelectedItem();
			int q5 = this.cmbq5.getSelectionModel().getSelectedItem();
			int q6 = this.cmbq6.getSelectionModel().getSelectedItem();
			
			SurveyResults surveyRes = new SurveyResults(q1,q2,q3,q4,q5,q6,s.getId());
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.AddSurveyResults); 
			req.setValue(surveyRes);
			Client.clientConn.handleMessageFromClientUI(req);
			
			req.setAction(Actions.GetSurveyResults); 
			Client.clientConn.handleMessageFromClientUI(req);
			
		}
		
		
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {

		
			
			// get selected item
			SurveyResults survey = surveyTable.getSelectionModel().getSelectedItem();
			if(survey!= null)
			{
				
				// delete
				Request req = new Request();
    			req.setAction(Actions.DeleteSurveyResults);
    			req.setValue(survey);
    			Client.clientConn.handleMessageFromClientUI(req);	
    			
    			// refresh table
    			Request req2 = new Request();
    			req2.setAction(Actions.GetSurveyResults);
    			Client.clientConn.handleMessageFromClientUI(req2);
				
			}
			
		}
		
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<SurveyResults> surveys)
		{
			
			if(ObserSurveysResults != null) {
				// if table alredy populate
				
				ObserSurveysResults.removeAll(ObserSurveysResults);
				for(SurveyResults u : surveys)
				{
					ObserSurveysResults.add(u);
				}
				
			}
			else
			{
				surveyTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserSurveysResults = FXCollections.observableArrayList(surveys);
				
				// defining table columns
				TableColumn<SurveyResults, Integer> idCol = new TableColumn<SurveyResults, Integer>("ID");
				TableColumn<SurveyResults, Integer> surveyId = new TableColumn<SurveyResults, Integer>("Survey ID");
				TableColumn<SurveyResults, Integer> q1Col = new TableColumn<SurveyResults, Integer>("q1");
				TableColumn<SurveyResults, Integer> q2Col = new TableColumn<SurveyResults, Integer>("q2");
				TableColumn<SurveyResults, Integer> q3Col = new TableColumn<SurveyResults, Integer>("q3");
				TableColumn<SurveyResults, Integer> q4Col = new TableColumn<SurveyResults, Integer>("q4");
				TableColumn<SurveyResults, Integer> q5Col = new TableColumn<SurveyResults, Integer>("q5");
				TableColumn<SurveyResults, Integer> q6Col = new TableColumn<SurveyResults, Integer>("q6");
				
				//add data to columns
				idCol.setCellValueFactory(
		        	    new PropertyValueFactory<SurveyResults,Integer>("id")
		        	);

				surveyId.setCellValueFactory(
		        	    new PropertyValueFactory<SurveyResults,Integer>("surveyId")
		        	);

		        
				q1Col.setCellValueFactory(
		        	    new PropertyValueFactory<SurveyResults,Integer>("q1")
		        	);
				
				q2Col.setCellValueFactory(
		        	    new PropertyValueFactory<SurveyResults,Integer>("q2")
		        	);
				
				q3Col.setCellValueFactory(
		        	    new PropertyValueFactory<SurveyResults,Integer>("q3")
		        	);
				
				q4Col.setCellValueFactory(
		        	    new PropertyValueFactory<SurveyResults,Integer>("q4")
		        	);
				
				q5Col.setCellValueFactory(
		        	    new PropertyValueFactory<SurveyResults,Integer>("q5")
		        	);
				
				q6Col.setCellValueFactory(
		        	    new PropertyValueFactory<SurveyResults,Integer>("q6")
		        	);
				
				
		        /*
		      //update q1Col
				q1Col.setCellFactory(TextFieldTableCell.forTableColumn());
				q1Col.setOnEditCommit(
				            new EventHandler<CellEditEvent<Survey, String>>() {
				                @Override
				                public void handle(CellEditEvent<Survey, String> t) {
				                	// show  in column
				                    ((Survey) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setQ1(t.getNewValue());
				                    
				                    //update to database
				                    String newSurvey = t.getNewValue();
				                    Survey survey = (Survey) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    survey.setQ1(newSurvey);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.UpdateSurvey);
				        			req.setValue(survey);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
				
				//update q2Col
				q2Col.setCellFactory(TextFieldTableCell.forTableColumn());
				q2Col.setOnEditCommit(
				            new EventHandler<CellEditEvent<Survey, String>>() {
				                @Override
				                public void handle(CellEditEvent<Survey, String> t) {
				                	// show  in column
				                    ((Survey) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setQ2(t.getNewValue());
				                    
				                    //update to database
				                    String newSurvey = t.getNewValue();
				                    Survey survey = (Survey) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    survey.setQ2(newSurvey);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.UpdateSurvey);
				        			req.setValue(survey);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
				
				//update q2Col
				q3Col.setCellFactory(TextFieldTableCell.forTableColumn());
				q3Col.setOnEditCommit(
				            new EventHandler<CellEditEvent<Survey, String>>() {
				                @Override
				                public void handle(CellEditEvent<Survey, String> t) {
				                	// show  in column
				                    ((Survey) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setQ3(t.getNewValue());
				                    
				                    //update to database
				                    String newSurvey = t.getNewValue();
				                    Survey survey = (Survey) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    survey.setQ3(newSurvey);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.UpdateSurvey);
				        			req.setValue(survey);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
				//update q2Col
				q4Col.setCellFactory(TextFieldTableCell.forTableColumn());
				q4Col.setOnEditCommit(
				            new EventHandler<CellEditEvent<Survey, String>>() {
				                @Override
				                public void handle(CellEditEvent<Survey, String> t) {
				                	// show  in column
				                    ((Survey) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setQ4(t.getNewValue());
				                    
				                    //update to database
				                    String newSurvey = t.getNewValue();
				                    Survey survey = (Survey) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    survey.setQ4(newSurvey);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.UpdateSurvey);
				        			req.setValue(survey);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
				
				//update q2Col
				q5Col.setCellFactory(TextFieldTableCell.forTableColumn());
				q5Col.setOnEditCommit(
				            new EventHandler<CellEditEvent<Survey, String>>() {
				                @Override
				                public void handle(CellEditEvent<Survey, String> t) {
				                	// show  in column
				                    ((Survey) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setQ5(t.getNewValue());
				                    
				                    //update to database
				                    String newSurvey = t.getNewValue();
				                    Survey survey = (Survey) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    survey.setQ5(newSurvey);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.UpdateSurvey);
				        			req.setValue(survey);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
				
				//update q2Col
				q6Col.setCellFactory(TextFieldTableCell.forTableColumn());
				q6Col.setOnEditCommit(
				            new EventHandler<CellEditEvent<Survey, String>>() {
				                @Override
				                public void handle(CellEditEvent<Survey, String> t) {
				                	// show  in column
				                    ((Survey) t.getTableView().getItems().get(
				                        t.getTablePosition().getRow())
				                        ).setQ6(t.getNewValue());
				                    
				                    //update to database
				                    String newSurvey = t.getNewValue();
				                    Survey survey = (Survey) t.getTableView().getItems().get(
					                        t.getTablePosition().getRow());
				                    survey.setQ6(newSurvey);
				                    
				                    //send request to server
				                    Request req = new Request();
				        			req.setAction(Actions.UpdateSurvey);
				        			req.setValue(survey);
				        			Client.clientConn.handleMessageFromClientUI(req);
				                }
				            }
				        );
				     */   
				        
		        
				surveyTable.setItems(ObserSurveysResults);
				surveyTable.getColumns().addAll(idCol,surveyId, q1Col,q2Col, q3Col,q4Col, q5Col,q6Col);
			}
		} 
		
		
		public void fillComboSurveys(ArrayList<Survey> surveys) {
			ObserSurveys = FXCollections.observableArrayList(surveys);
			selectSurvey.setItems(ObserSurveys);
		}
		
		public void showBoxes(Survey survey)
		{
			txtq1.setText(survey.getQ1());
			txtq2.setText(survey.getQ2());
			txtq3.setText(survey.getQ3());
			txtq4.setText(survey.getQ4());
			txtq5.setText(survey.getQ5());
			txtq6.setText(survey.getQ6());
			
			// fill results with 1-10 values
			ArrayList<Integer> results = new ArrayList<Integer>();
			for(int i=1; i<11; i++)
			{
				results.add(i);
			}
			if(observableList == null)
			{
				//cast results to ObservableList
			    observableList = FXCollections.observableList(results);
			    this.cmbq1.setItems(observableList);
			    this.cmbq2.setItems(observableList);
			    this.cmbq3.setItems(observableList);
			    this.cmbq4.setItems(observableList);
			    this.cmbq5.setItems(observableList);
			    this.cmbq6.setItems(observableList);
			}
			
			this.cmbq1.setOpacity(1);
			this.cmbq2.setOpacity(1);
			this.cmbq3.setOpacity(1);
			this.cmbq4.setOpacity(1);
			this.cmbq5.setOpacity(1);
			this.cmbq6.setOpacity(1);
			this.txtq1.setVisible(true);
			this.txtq2.setVisible(true);
			this.txtq3.setVisible(true);
			this.txtq4.setVisible(true);
			this.txtq5.setVisible(true);
			this.txtq6.setVisible(true);
		}
		
		@FXML
		public void selectedSurvey(ActionEvent event) throws Exception {
			Survey s = this.selectSurvey.getSelectionModel().getSelectedItem();
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetSurveyData); 
			req.setValue(s);
			Client.clientConn.handleMessageFromClientUI(req);
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get users from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetSurveyNames); 
			Client.clientConn.handleMessageFromClientUI(req);
			
			req.setAction(Actions.GetSurveyResults); 
			Client.clientConn.handleMessageFromClientUI(req);
		}
	
}
