package client;

import java.io.IOException;
import server.ConIF;

public class Client implements ConIF {
	/*
	 * Client is connecting the client to the server
	 */
	
	//Daniel
	final public static int DEFAULT_PORT = 5551;
	public static ClientController clientConn;
	public static String host = "localhost"; 

	// setup client connection
	public Client(String host, int port) {
		System.out.println("Gay");
		System.out.println("Goi");
		System.out.println("Goi");
		System.out.println("Goi");
		try {
			clientConn = new ClientController(Client.host, Client.DEFAULT_PORT, this);
			

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
	
	public void Accept(Object arr)
	{
		/*
		 * send message to server
		 */
		try
		{
			clientConn.handleMessageFromClientUI(arr);// Sending the message from the client to the server.
		}catch (Exception ex){ // In case the message didn't arrive the program will show a message to the client.
	    	System.out.println("Unexpected error while reading from console!");
	    	}
	}
}
