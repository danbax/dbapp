package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.impl.Disposer.Record;

import client.Client;
import client.Product;
import client.Request;
import client.Survey;
import client.User;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ManageSatisfactionSurvey extends Application implements Initializable  {
	
	public static ManageSatisfactionSurvey last;
	private ObservableList<Survey> ObserSurveys;
	@FXML
	private TableView<Survey> surveyTable = new TableView<Survey>(); // table of products
	
	@FXML private TextArea q1;
	@FXML private TextArea q2;
	@FXML private TextArea q3;
	@FXML private TextArea q4;
	@FXML private TextArea q5;
	@FXML private TextArea q6;
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/ManageSatisfactionSurvey.fxml"));
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
						guic.loadFxml("/main/resources/MainMenu.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				});
			
		}
		
		@FXML
		public void onBtnAddClicked(ActionEvent event) throws Exception {
			// add survey
			
			String q1String = q1.getText();
			String q2String = q2.getText();
			String q3String = q3.getText();
			String q4String = q4.getText();
			String q5String = q5.getText();
			String q6String = q6.getText();
			
			
			Survey survey = new Survey(q1String,q2String,q3String,q4String,q5String,q6String);
			Request req = new Request(Actions.AddSurvey,survey);
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			Client.clientConn.handleMessageFromClientUI(req);
			
			req.setAction(Actions.GetSurveys);
			Client.clientConn.handleMessageFromClientUI(req);
			
		}
		
		@FXML
		public void deleteSelectedRow(MouseEvent event)  throws Exception {

			/*
			 *  delete selected row from database
			 */
			
			// get selected item
			Survey survey = surveyTable.getSelectionModel().getSelectedItem();
			if(survey!= null)
			{
				
				// delete
				Request req = new Request();
    			req.setAction(Actions.DeleteSurvey);
    			req.setValue(survey);
    			Client.clientConn.handleMessageFromClientUI(req);	
    			
    			// refresh table
    			Request req2 = new Request();
    			req2.setAction(Actions.GetSurveys);
    			Client.clientConn.handleMessageFromClientUI(req2);
				
			}
			
		}
		
		
		
		@SuppressWarnings("unchecked")
		public void fillTable(ArrayList<Survey> surveys)
		{
			/*
			 * This function fill the table with data in ArrayList 
			*/ 
			System.out.println(surveys.get(0).getQ1());
			
			if(ObserSurveys != null) {
				// if table alredy populate
				/*Dani: didn't find better solution*/
				ObserSurveys.removeAll(ObserSurveys);
				for(Survey u : surveys)
				{
					ObserSurveys.add(u);
				}
				
			}
			else
			{
				surveyTable.setEditable(true); // for updating
				
				//casting ArrayList to ObservableList
				ObserSurveys = FXCollections.observableArrayList(surveys);
				
				// defining table columns
				TableColumn<Survey, String> idCol = new TableColumn<Survey, String>("ID");
				TableColumn<Survey, String> q1Col = new TableColumn<Survey, String>("q1");
				TableColumn<Survey, String> q2Col = new TableColumn<Survey, String>("q2");
				TableColumn<Survey, String> q3Col = new TableColumn<Survey, String>("q3");
				TableColumn<Survey, String> q4Col = new TableColumn<Survey, String>("q4");
				TableColumn<Survey, String> q5Col = new TableColumn<Survey, String>("q5");
				TableColumn<Survey, String> q6Col = new TableColumn<Survey, String>("q6");
				
				//add data to columns
				idCol.setCellValueFactory(
		        	    new PropertyValueFactory<Survey,String>("id")
		        	);

		        
				q1Col.setCellValueFactory(
		        	    new PropertyValueFactory<Survey,String>("q1")
		        	);
				
				q2Col.setCellValueFactory(
		        	    new PropertyValueFactory<Survey,String>("q2")
		        	);
				
				q3Col.setCellValueFactory(
		        	    new PropertyValueFactory<Survey,String>("q3")
		        	);
				
				q4Col.setCellValueFactory(
		        	    new PropertyValueFactory<Survey,String>("q4")
		        	);
				
				q5Col.setCellValueFactory(
		        	    new PropertyValueFactory<Survey,String>("q5")
		        	);
				
				q6Col.setCellValueFactory(
		        	    new PropertyValueFactory<Survey,String>("q6")
		        	);
		        
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
				        
				        
		        
				surveyTable.setItems(ObserSurveys);
				surveyTable.getColumns().addAll(idCol, q1Col,q2Col, q3Col,q4Col, q5Col,q6Col);
			}
		} 

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get users from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetSurveys); 
			Client.clientConn.handleMessageFromClientUI(req);
			
		}
	
}
