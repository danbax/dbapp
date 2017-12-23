package database;

import ocsf.server.ConnectionToClient;

import java.io.IOException;
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
	
	public static void addProduct(Connection conn,  ConnectionToClient client,Product product) throws SQLException {
		/*
		 * Checks if username,password exist in database
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddProduct);
		System.out.println(product.getProductName());
		PreparedStatement ps;
		String s1 = "INSERT INTO products (pname, ptype) VALUES (?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, product.getProductName());
				ps.setString(2, product.getProductType());
				ps.executeUpdate();

				
				sr.setAnswer(Actions.ProductAdded);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			sr.setAnswer(Actions.ProductAddedError);
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
}
