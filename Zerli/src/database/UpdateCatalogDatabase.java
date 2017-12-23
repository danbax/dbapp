package database;

import ocsf.server.ConnectionToClient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.Product;
import client.ServerResponse;
import enums.Actions;
public class UpdateCatalogDatabase {
	
	public static void getProducts(Connection conn,  ConnectionToClient client) throws SQLException {
		/*
		 * Checks if username,password exist in database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from products";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				rs = ps.executeQuery();
				ArrayList<Product> products = new ArrayList<Product>();
				while ( rs.next() )
				{
					// create product
					Product product = new Product(rs.getString("pname"),
							rs.getString("ptype"));
					products.add(product);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetProducts);
				sr.setValue(products);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
}
