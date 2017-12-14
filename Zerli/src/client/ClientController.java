package client;

import ocsf.client.*;
import java.io.*;
import java.util.ArrayList;

import ClientControllers.ProductManager;
import enums.Actions;
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
		 ProductManager.setStudentComboBox(msg);
	}

	public void handleMessageFromClientUI(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
System.out.println(msg);
		try {
			sendToServer(msg);
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
