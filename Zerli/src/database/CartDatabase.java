package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.Order;
import client.Product;
import client.ServerResponse;
import client.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class CartDatabase {
	public static void addToCart(Connection conn,  ConnectionToClient client,Order order) throws SQLException {
		
		/* insert product to cart */
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddToCart);
		PreparedStatement ps;
		
		String s1 = "INSERT INTO cart (user_id,product_id,order_id) VALUES (?,?,0);";
		
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, order.getUser().getId());
				ps.setInt(2, order.getProduct().getPid());
				ps.executeUpdate();

				// update products in cart
				
			}
		catch (Exception e)
		{
			// TODO: handle exception
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
	
	public static void getMyCart(Connection conn,  ConnectionToClient client,User myUser) throws SQLException {
		/*
		 * get list of users from database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		PreparedStatement ps2;
		ResultSet rs2; 
		/*
		String s1 = "select products.pname,products.ptype,products.price,products.img,products.product_id"
				+ " from cart,products where cart.product_id=products.id and cart.order_id=0 and cart.user_id=?";
		*/
		String s1 = "select * from cart where order_id=0 and user_id =?"; // not ordered
		try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setInt(1, myUser.getId());
			rs = ps.executeQuery();
			
			ArrayList<Product> products = new ArrayList<Product>();
			while ( rs.next() )
			{
				System.out.println("ooo" + rs.getInt("product_id"));
				try {
				String s2 = "select * from products where id=?";
				ps2 = (PreparedStatement) conn.prepareStatement(s2);
				ps2.setInt(1, rs.getInt("product_id"));
				rs2 = ps2.executeQuery();
				
				
				
					if(rs2.next()) {
					System.out.println(rs2.getString("pname"));
					System.out.println(rs2.getString("products.pname"));
					// create product
					Product product = new Product(
							rs2.getInt("id"),
							rs2.getString("pname"),
							rs2.getString("ptype")
							);
					
					
					product.setPrice(rs2.getFloat("price"));
					product.setProductId(rs2.getString("product_id"));
					product.setImage("/serverImages/"+rs2.getString("img"));
					
					System.out.println(product);
					
					// add to product array
					products.add(product);
					}
				}
				catch (Exception e)
				{
					// TODO: handle exception
				}
			}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetMyCart);
				sr.setValue(products);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public static void getCountItemsMyCart(Connection conn,  ConnectionToClient client,User myUser) throws SQLException {
		/*
		 * get number of items in cart
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select count(id) as c from cart where order_id=0 and user_id =?"; // not ordered
		try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setInt(1, myUser.getId());
			rs = ps.executeQuery();
			int count =0;
			if(rs.next())
			{
				count=rs.getInt("c");
				
			}
			System.out.println(count);
			ServerResponse sr = new ServerResponse(); // create server response
			sr.setAction(Actions.GetMyCartCountItems);
			sr.setValue(count);
			
			client.sendToClient(sr); // send messeage to client
			
				
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public static void deleteFromCart(Connection conn,  ConnectionToClient client,Order order) throws SQLException {
		/*
		 * delete product from database
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteFromCart);
		PreparedStatement ps;
		String s1 = "delete from cart where user_id=? and product_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, order.getUser().getId());
				ps.setInt(2, order.getProduct().getPid());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
		
		}
	}

}
