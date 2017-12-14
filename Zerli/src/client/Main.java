package client;


import java.io.IOException;

import server.ConIF;

public class Main implements ConIF {

	final public static int DEFAULT_PORT = 5551;
	public static ClientController clientConn;

	// setup client connection
	public Main(String host, int port) {

		try {
			clientConn = new ClientController(host, port, this);

		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
	}

	// display message
	public void display(Object message) {
		System.out.println("> " + message);
	}

	public static void main(String[] args) {
		String host = "localhost";

		Main mainWindow = new Main(host, DEFAULT_PORT);	
		
		// show success message
		if (mainWindow != null)
			System.out.println("Connection to " + host + " succeeded!");
		
		// send message to server
		clientConn.handleMessageFromClientUI("update");
		clientConn.handleMessageFromClientUI("show");
		

	}
	
}
