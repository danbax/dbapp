package client;

import ocsf.client.*;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import enums.Actions;
import gui.AuthorizeUsersController;
import gui.CartController;
import gui.CatalogController;
import gui.CustomMadeController;
import gui.GUIcontroller;
import gui.LoginController;
import gui.MainMenuCustomer;
import gui.OrderHistoryController;
import gui.SatisfactionSurvey;
import gui.SurveyExpertController;
import gui.SurveyResultsController;
import gui.UpdateCatalogController;
import gui.UpdateUsersController;
import server.ConIF;

public class ClientController extends AbstractClient {
	
	ConIF clientUI;
	final public static int DEFAULT_PORT = 5551;

	public ClientController(String host, int port, ConIF clientUI)
			throws IOException {
		super(host, port);
		this.clientUI = clientUI;
		openConnection(); 
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		/*
		 * return message from server
		 */
		ServerResponse sr = (ServerResponse) msg;
		// decide which action perform
		if (sr.getAction() == Actions.ValidLoginDataCheck) {
			// checked id login data correct
			LoginController loginc = (LoginController)LoginController.last;
        	if(sr.getAnswer() == Actions.UsernameExist)
        	{
        		// login user
        		LoginController.myUser = (User) sr.getValue();
        		try {
					loginc.ShowLoginMessage(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else if(sr.getAnswer() == Actions.UsernameDoesNotExist) 
        	{
        		// show error
        		System.out.println("error"); 
        		try {
					loginc.ShowLoginMessage(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else if(sr.getAnswer() == Actions.AlreadyLoggedIn)
        	{
        		// show error
        		try {
					loginc.ShowLoginMessage(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		}
		
		if (sr.getAction() == Actions.GetProducts) {
			@SuppressWarnings("unchecked")
			ArrayList<Product> products = (ArrayList<Product>) sr.getValue();
			UpdateCatalogController.last.fillProductsInTable(products);
		}
		if (sr.getAction() == Actions.AddProduct) {
			
		}
		if (sr.getAction() == Actions.DeleteProduct) {
			if(sr.getAnswer() == Actions.DeletedProduct)
			{
				// deleted
			}
			else
			{
				// not deleted
			}
		}
		if(sr.getAction() == Actions.GetUsers)
		{
			ArrayList<User> users = (ArrayList<User>) sr.getValue();
			UpdateUsersController.last.fillUsersInTable(users);
		}
		
		if (sr.getAction() == Actions.GetSurveys) {
			@SuppressWarnings("unchecked")
			ArrayList<Survey> surveys = (ArrayList<Survey>) sr.getValue();
			SatisfactionSurvey.last.fillTable(surveys);
		}
		if (sr.getAction() == Actions.GetSurveyNames) {
			@SuppressWarnings("unchecked")
			ArrayList<Survey> surveys = (ArrayList<Survey>) sr.getValue();
			SurveyResultsController.last.fillComboSurveys(surveys);
		}
		if (sr.getAction() == Actions.GetSurveyData) {
			@SuppressWarnings("unchecked")
			Survey survey = (Survey) sr.getValue();
			System.out.println(survey.getQ1());
			SurveyResultsController.last.showBoxes(survey);
		}
		if (sr.getAction() == Actions.GetSurveyResults) {
			@SuppressWarnings("unchecked")
			ArrayList<SurveyResults> surveys = (ArrayList<SurveyResults>) sr.getValue();
			SurveyResultsController.last.fillTable(surveys);
			
		}
		if (sr.getAction() == Actions.GetNotAuthorizedUsers) {
			@SuppressWarnings("unchecked")
			ArrayList<User> users = (ArrayList<User>) sr.getValue();
			AuthorizeUsersController.last.fillTable(users);
			
		}
		
		if (sr.getAction() == Actions.GetSurveyNamesExpert) {
			@SuppressWarnings("unchecked")
			ArrayList<Survey> surveys = (ArrayList<Survey>) sr.getValue();
			SurveyExpertController.last.fillComboSurveys(surveys);
		}
		if (sr.getAction() == Actions.GetNumberOfVoters) {
			SurveyExpertController.last.setNumberVoters((Integer)sr.getValue()); 
		}
		if (sr.getAction() == Actions.GetAvgRes) {
			SurveyExpertController.last.setAvgResults((SurveyResults)sr.getValue());
		}
		if (sr.getAction() == Actions.GetConclusion) {
			SurveyExpertController.last.setConclusion((String)sr.getValue());
		}
		if (sr.getAction() == Actions.GetMyAdress) {
			LoginController.myAddress = (Address)sr.getValue();
		}
		if (sr.getAction() == Actions.GetMyCreditCard) {
			LoginController.myCreditCard = (CreditCard)sr.getValue();
		}
		
		if (sr.getAction() == Actions.GetProductCatalog) {
			CatalogController.last.setCatalogProducts((ArrayList<Product>) sr.getValue());
		}
		if (sr.getAction() == Actions.GetMyOrdersHistory) {
			@SuppressWarnings("unchecked")
			ArrayList<Order> orders = (ArrayList<Order>) sr.getValue();
			System.out.println("xxx");
			System.out.println(orders.get(0).getGreeting());
			OrderHistoryController.last.fillTable(orders); 
			
		}
	
		if (sr.getAction() == Actions.GetMyCart) {
			@SuppressWarnings("unchecked")
			ArrayList<Product> products = (ArrayList<Product>) sr.getValue();
			System.out.println("ppx"+products.get(0).getProductName());
			CartController.last.fillProductsInTable(products);
		}
		
		if (sr.getAction() == Actions.GetMyCartCountItems) {
			MainMenuCustomer.last.updateCountItems((int)sr.getValue());
		}
		if (sr.getAction() == Actions.CustomOrderData) {
			ArrayList<Object> obj = (ArrayList<Object>) sr.getValue();
			ArrayList<String> types = (ArrayList<String>) obj.get(0);
			ArrayList<String> colors = (ArrayList<String>) obj.get(1);
			Float max = (Float) obj.get(2);
			Float min = (Float) obj.get(3);
			
			colors.add("all");
			
			CustomMadeController.last.fillComboTypes(types);
			CustomMadeController.last.fillComboColors(colors);
			CustomMadeController.last.setMaxPrice(max);
			CustomMadeController.last.setMinPrice(min);
			
		}
		if (sr.getAction() == Actions.AddCustomOrder) {
			System.out.println("addcustom");
			if(sr.getAnswer() == Actions.CustomAdded)
			{
				System.out.println("added");
				// added
				CustomMadeController.last.showAlert("Added to cart");
			}
			else if(sr.getAnswer() == Actions.CustomNotAdded)
			{
				System.out.println("aaa");
				// not added
				CustomMadeController.last.showAlert("No item like this");
			}
		}
		
	}

	public void handleMessageFromClientUI(Object req) {
		/*
		 * req.get(0) = action
		 * req.get(1) = value
		 */
		try {
			sendToServer(req);
		} catch (IOException e) {
			clientUI.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}
	

	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
