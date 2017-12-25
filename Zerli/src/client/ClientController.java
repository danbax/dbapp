package client;

import ocsf.client.*;
import java.io.*;
import java.util.ArrayList;

import enums.Actions;
import gui.LoginController;
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
		
		
	}

	public void handleMessageFromClientUI(Object req) {
		/*
		 * send arraylist<string> to server
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
