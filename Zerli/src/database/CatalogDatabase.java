package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.Deal;
import entity.Product;
import entity.ServerResponse;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class CatalogDatabase {
	public static void getProducts(Connection conn,  ConnectionToClient client,String searchQuery) throws SQLException {
		/*
		 * get list of products from database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		PreparedStatement ps2;
		ResultSet rs2; 
		String s1 = "select * from products";
		String s2 = "select * from deals where product_id=?";
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
					product.setImage(rs.getString("img"));
					product.setDealPrice(0);
					
					ps2 = (PreparedStatement) conn.prepareStatement(s2);
					ps2.setInt(1, product.getPid());
					rs2 = ps2.executeQuery();
					if(rs2.next())
					{
						int percent = rs2.getInt("percent");
						product.setDealPrice(product.getPrice()-product.getPrice()*percent/100);
					}
					
					
					// add to product array
					products.add(product);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetProductCatalog);
				sr.setValue(products);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
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
				sr.setAction(Actions.GetDealsCatalog);
				sr.setValue(deals);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
