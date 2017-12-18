package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import client.Product;
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
		
			
			@SuppressWarnings("unchecked")
			ArrayList<String> msgArr = (ArrayList<String>) msg; // cast to array list
			
			// switch - which action to do
			if((msgArr.get(0)).equals(Actions.getProducts.toString()))
			{
				// get products data from database
				productManagerDatabase.readStrFromDB((com.mysql.jdbc.Connection) conn,client);
				
			}
			if((msgArr.get(0)).equals(Actions.updateProducts.toString()))
			{
				// update product
				int id = Integer.parseInt(msgArr.get(1));
				String pname = msgArr.get(2);
				String ptype = msgArr.get(3);
				Product p = new Product(id,pname,ptype);
				productManagerDatabase.updateProduct(p, (com.mysql.jdbc.Connection) conn,client);
				
				
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
	
	public static void serverDetailsUpdate(String name,String user,String pass)
	{
		ServerController.DBName = name;
		ServerController.DBUserName = user;
		ServerController.DBPassward = pass;
	}

}