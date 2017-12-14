package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import client.Product;
import client.Request;
import database.*;
import enums.Actions;

import ocsf.server.*;

public class ServerController extends AbstractServer {

	final public static int DEFAULT_PORT = 5551;
	
	
	private static String DBName = "zerli";
	private static String DBUserName = "root";
	private static String DBPassward = "dbapp1605";

	public ServerController(int port) {
		super(port);
	}

	/*
	 * handleMessageFromClient connect to database and decides what to do with request
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		/*
		 * Handles message from client
		 * input: messeage
		 * output: Database action
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {/* handle the error */
		}

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DBName, DBUserName, DBPassward);
			System.out.println("SQL connection succeed");
			
			System.out.println(msg);
	
			ArrayList<Object> arr = (ArrayList<Object>) msg;
			Actions act = (Actions) arr.get(0);
			
			if(act.equals(Actions.getProducts))
			{
				productManager.readStrFromDB((com.mysql.jdbc.Connection) conn,client);
				
			}
			if(act.equals(Actions.updateProducts))
			{
				// update product
				Product p = (Product) arr.get(1);
				productManager.updateProduct(p, (com.mysql.jdbc.Connection) conn);
				
			}
				
		
			conn.close();

		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}


	@Override
	protected void serverStarted() {
		System.out.println("Server listening for connections on port "
				+ getPort());
	}

	@Override
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	public static void main(String[] args) {
		// conect to server
		int port;

		port = DEFAULT_PORT; // Set port to 5555
		ServerController server = new ServerController(port);

		DBUserName= "root";
		DBPassward= "dbapp1605";

		try {
			server.listen(); // Start listening for connections.
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
		// TODO Auto-generated method stub
	}
}