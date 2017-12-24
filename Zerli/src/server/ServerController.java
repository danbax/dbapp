package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import client.Product;
import client.Request;
import client.User;
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
		
		
			/*
			@SuppressWarnings("unchecked")
			ArrayList<String> msgArr = (ArrayList<String>) msg; // cast to array list
			System.out.println(msgArr);
			*/
			
			Request req = (Request) msg;
			
			
			// switch - which action to do
			if(req.getAction() == Actions.ValidLoginDataCheck)
			{
				// get products data from database
				User u = (User) req.getValue();
				LoginManagerDatabase.isValidData((com.mysql.jdbc.Connection) conn,client,u.getUsername(),u.getPassword());
				
			}
			if(req.getAction() == Actions.GetProducts)
			{
				UpdateCatalogDatabase.getProducts((com.mysql.jdbc.Connection)conn, client);
			}
			if(req.getAction() == Actions.AddProduct)
			{
				UpdateCatalogDatabase.addProduct((com.mysql.jdbc.Connection)conn, client,(Product) req.getValue());
			}
			if(req.getAction() == Actions.UpdateProduct)
			{
				UpdateCatalogDatabase.updateProduct((com.mysql.jdbc.Connection)conn, client,(Product) req.getValue());
			}
			if(req.getAction() == Actions.DeleteProduct)
			{
				UpdateCatalogDatabase.deleteProduct((com.mysql.jdbc.Connection)conn, client,(Product) req.getValue());
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