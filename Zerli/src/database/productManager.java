package database;

import client.Product;
import ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
public class productManager {

	public static void readStrFromDB(Connection conn,  ConnectionToClient client) throws SQLException {
		/*
		 * return array list of products rows from database
		 */
		ResultSet rs = null;
		Statement stmt;
		
		ArrayList<Product> prodcuts=new ArrayList<Product>();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT pid,pname,ptype FROM products");
			
			while (rs.next()) {
				Product p = new Product(rs.getInt(1),rs.getString(2),rs.getString(3)); 
				prodcuts.add(p);
			}
			rs.close();
			client.sendToClient(prodcuts);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateProduct(Product p, Connection conn) {
		
		/*
		 * input: product
		 * update product on database by id of the object.
		 */

		String s1 = "UPDATE products SET pname=?,ptype=? WHERE pid=?";

			try {
					PreparedStatement ps = (PreparedStatement) conn.prepareStatement(s1);
					ps.setString(1, p.getProductName());
					ps.setString(2,p.getProductType());
					ps.setInt(3,p.getProductID());
					ps.executeUpdate();
				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
	    }
	
	public static void insertProduct(Product p, Connection conn) {
		
	      String query = " insert into products (pname, ptype)"
	    	        + " values (?, ?)";

	      	try {
	    	      PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
	    	      preparedStmt.setString (1, p.getProductName());
	    	      preparedStmt.setString (2, p.getProductType());
	    	      preparedStmt.execute();}
	      	catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void deleteProduct(int pid, Connection conn) {
		
	      String query = " delete from products where pid=?;";

	    	    
	      	try {
	    	      PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
	    	      preparedStmt.setInt (1,pid);
	    	      preparedStmt.execute();}
	      	catch (SQLException e) {e.printStackTrace();}	    	      
		
	}
}
