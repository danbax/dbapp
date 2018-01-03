package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.Deal;
import entity.Product;
import entity.ServerResponse;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class DealsDatabase {
	public static void getDeals(Connection conn,  ConnectionToClient client) throws SQLException {
		/*
		 * get list of deals from database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from deals";
		
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				rs = ps.executeQuery();
				ArrayList<Deal> deals = new ArrayList<Deal>();
				while ( rs.next() )
				{
					// create product
					Deal deal = new Deal();
					deal.setId(rs.getInt("id"));
					deal.setProductId(rs.getInt("product_id"));
					deal.setPercent(rs.getInt("percent"));
							
					deals.add(deal);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetDeals);
				sr.setValue(deals);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public static void deleteDeal(Connection conn,  ConnectionToClient client,Deal deal) throws SQLException {
		/*
		 * delete deal from database
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteDeal);
		PreparedStatement ps;
		String s1 = "delete from deals where id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, deal.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
		
		}
	}
	
	public static void addDeal(Connection conn,  ConnectionToClient client,Deal deal) throws SQLException {
		/*
		 * add product to database
		 */
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddDeal);
		PreparedStatement ps;
		String s1 = "INSERT INTO deals (product_id,percent) VALUES (?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, deal.getProductId());
				ps.setInt(2, deal.getPercent());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void getProducts(Connection conn,  ConnectionToClient client) throws SQLException { 
		/*
		 * get list of products from database
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
					Product product = new Product(
							rs.getInt("id"),
							rs.getString("pname"),
							rs.getString("ptype")
							//,"serverImages/"+rs.getString("img")
							);
					
					
					product.setPrice(rs.getFloat("price"));
					product.setProductId(rs.getString("product_id"));
					product.setImage("/serverImages/"+rs.getString("img"));
					
					// add to product array
					products.add(product);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetProductsDeals);
				sr.setValue(products);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}


}
