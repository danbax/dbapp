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
import client.SurveyConclusion;
import client.SurveyResults;
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

public class SurveyExpertController extends Application implements Initializable  {
	
	public static SurveyExpertController last;
	private ObservableList<Survey> ObserSurveys;
	@FXML private ComboBox<Survey> selectSurveyCmb = new ComboBox<Survey>();
	@FXML private Text q1Txt;
	@FXML private Text q2Txt;
	@FXML private Text q3Txt;
	@FXML private Text q4Txt;
	@FXML private Text q5Txt;
	@FXML private Text q6Txt;
	@FXML private Text txtAvg1;
	@FXML private Text txtAvg2;
	@FXML private Text txtAvg3;
	@FXML private Text txtAvg4;
	@FXML private Text txtAvg5;
	@FXML private Text txtAvg6;
	@FXML private TextArea textAreaConclusion;
	@FXML private Button btnUpdate;
	@FXML private Text txtSurveyRes;
	@FXML private Text txtNumberVoters;
	Boolean isSet; // is conclusion exist in database
	
	
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
		public void start(Stage primaryStage) throws Exception {
			
			/*
			 * start select product frame
			 */
			
			Parent root = FXMLLoader.load(getClass().getResource("/main/resources/ServiceExpert.fxml"));
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
						guic.loadFxml("MenuEmployee.fxml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				});
			
		}
		
		@FXML
		public void onBtnClicked(ActionEvent event) throws Exception {
			// add Expert conclusion
			String conclusion = textAreaConclusion.getText();
			Survey s = this.selectSurveyCmb.getSelectionModel().getSelectedItem();
			SurveyConclusion sc = new SurveyConclusion();
			sc.setConclusion(conclusion);
			sc.setSurveyId(s.getId());
			
			
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			
			// if survey exist - > update ; else -> add
			System.out.println(isSet);
			if(isSet)
			{
				// update
				req.setAction(Actions.updateConclusion); 
			}
			else
			{
				// add
				req.setAction(Actions.addConclusion); 
			}
			
			req.setValue(sc);
			Client.clientConn.handleMessageFromClientUI(req);
			
		}
		
		
		public void fillComboSurveys(ArrayList<Survey> surveys) {
			ObserSurveys = FXCollections.observableArrayList(surveys);
			selectSurveyCmb.setItems(ObserSurveys);
		}
		
		
		
		@FXML
		public void selectedSurvey(ActionEvent event) throws Exception {
			Survey s = this.selectSurveyCmb.getSelectionModel().getSelectedItem();
			txtSurveyRes.setOpacity(1);
			
			// questions set;
			q1Txt.setText(s.getQ1());
			q2Txt.setText(s.getQ2());
			q3Txt.setText(s.getQ3());
			q4Txt.setText(s.getQ4());
			q5Txt.setText(s.getQ5());
			q6Txt.setText(s.getQ6());
			q1Txt.setOpacity(1);
			q2Txt.setOpacity(1);
			q3Txt.setOpacity(1);
			q4Txt.setOpacity(1);
			q5Txt.setOpacity(1);
			q6Txt.setOpacity(1);
			
			
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetNumberOfVoters); 
			req.setValue(s);
			Client.clientConn.handleMessageFromClientUI(req);
			
			req.setAction(Actions.GetAvgRes); 
			Client.clientConn.handleMessageFromClientUI(req);
			
			req.setAction(Actions.GetConclusion); 
			Client.clientConn.handleMessageFromClientUI(req);
			
			
		}
		
		public void setConclusion(String conclusion)
		{
			// is conclusion exist in database
			if(conclusion.equals("")) 
				isSet = false;
			else 
				isSet = true;
			
			System.out.println(isSet);
			
			textAreaConclusion.setText(conclusion);
			textAreaConclusion.setOpacity(1);
			
			
			btnUpdate.setOpacity(1);
		}
		
		public void setNumberVoters(int number)
		{
			txtNumberVoters.setText("Number of voters: " + Integer.toString(number));
			txtNumberVoters.setOpacity(1);
		}
		
		public void setAvgResults(SurveyResults sr)
		{
			txtAvg1.setText(Integer.toString(sr.getQ1()));
			txtAvg1.setOpacity(1);
			
			txtAvg2.setText(Integer.toString(sr.getQ2()));
			txtAvg2.setOpacity(1);
			
			txtAvg3.setText(Integer.toString(sr.getQ3()));
			txtAvg3.setOpacity(1);
			
			txtAvg4.setText(Integer.toString(sr.getQ4()));
			txtAvg4.setOpacity(1);
			
			txtAvg5.setText(Integer.toString(sr.getQ5()));
			txtAvg5.setOpacity(1);
			
			txtAvg6.setText(Integer.toString(sr.getQ6()));
			txtAvg6.setOpacity(1);
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			last = this;
			
			// get users from database
			Request req = new Request();
			Client mainClient = new Client(Client.host, Client.DEFAULT_PORT);
			req.setAction(Actions.GetSurveyNamesExpert); 
			Client.clientConn.handleMessageFromClientUI(req);
		}
	
}
