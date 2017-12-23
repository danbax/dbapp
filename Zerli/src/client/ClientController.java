package client;

import ocsf.client.*;
import java.io.*;
import java.util.ArrayList;
import enums.Actions;
import gui.LoginController;
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
		@SuppressWarnings("unchecked")
		ArrayList<String> serverMesseage = (ArrayList<String>) msg;
		System.out.println(serverMesseage);
		// decide which action perform
		if (serverMesseage.get(0).equals(Actions.ValidLoginDataCheck.toString())) {
			// checked id login data correct
			LoginController loginc = (LoginController)LoginController.last;
        	if(serverMesseage.get(1).equals(Actions.UsernameExist.toString()))
        	{
        		// login user
        		System.out.println("login");
        		try {
					loginc.ShowLoginMessage(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else
        	{
        		// show error
        		System.out.println("error");
        		try {
					loginc.ShowLoginMessage(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
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
