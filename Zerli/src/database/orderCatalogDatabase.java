package database;

import java.io.IOException;
import java.sql.Date;
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

public class orderCatalogDatabase {
public static void order(Connection conn,  ConnectionToClient client,Order order) throws SQLException {
		
	/*
	 * create order with products in cart
	 * change cart items to product related 
	 */
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.buyProductFromCatalog);
		PreparedStatement ps;
		PreparedStatement ps2;
		ResultSet rs2; 
		String s1 = "INSERT INTO orders (user_id,order_date,greeting_text,hours,minutes,price,payment_method,status) VALUES (?,?,?,?,?,?,?,0);";
		String s2 = "select id from orders where user_id = ? order by id desc limit 1";
		String s3 = "update cart set order_id=? where user_id=? and order_id=0";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, order.getUser().getId());
				ps.setDate(2,  Date.valueOf(order.getDate()));
				ps.setString(3, order.getGreeting());
				ps.setInt(4, order.getHours());
				ps.setInt(5, order.getMinutes());
				ps.setFloat(6, order.getPrice());
				ps.setInt(7,order.getPaymentMethod());
				ps.executeUpdate(); 

				

				try {
						ps2 = (PreparedStatement) conn.prepareStatement(s2);
						ps2.setInt(1, order.getUser().getId());
						rs2 = ps2.executeQuery();
						
						if(rs2.next())
						{
							ps2 = (PreparedStatement) conn.prepareStatement(s3);
							ps2.setInt(1, rs2.getInt("id"));
							ps2.setInt(2, order.getUser().getId());
							ps2.executeUpdate();
						}

						// update products in cart
						
						client.sendToClient(sr); // send messeage to client
						
					}
				catch (Exception e)
				{
					
				}
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

public static void getMyOrdersHistory(Connection conn,  ConnectionToClient client,User user) throws SQLException {
	/*
	 * get list of orders from database
	 */
	PreparedStatement ps;
	ResultSet rs; 
	Product product = null;
	/*
	String s1 = "select orders.id as oid,orders.order_date,orders.greeting_text as gt,orders.hours,orders.minutes,orders.price,orders.payment_method"
			+ ",products.pname,products.ptype,products.price,products.img,products.product_ID,products.id as pid"
			+ " from orders,products where orders.user_id = ? and orders.product_id=products.id";
			*/
	String s1 = "select * from orders where user_id = ?";
	try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setInt(1, user.getId());
			rs = ps.executeQuery();
			ArrayList<Order> orders = new ArrayList<Order>();
			
			while ( rs.next() )
			{
				try {	
					String s2 = "select * from products where id = ?";
					PreparedStatement ps2 = (PreparedStatement) conn.prepareStatement(s2);
					ps2.setInt(1, rs.getInt("product_id"));
					ResultSet rs2 = ps2.executeQuery();
					
					
					if(rs2.next())
					{
						
						//create product
						product = new Product(rs2.getString("pname"),rs2.getString("ptype"));
						product.setImage(rs2.getString("img"));
						product.setPid(rs2.getInt("id"));
						product.setPrice(rs2.getFloat("price"));
						product.setProductId(rs2.getString("product_id"));
					}
				}
				catch (Exception e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				
				//create order
				
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setGreeting(rs.getString("greeting_text"));
				order.setHours(rs.getInt("hours"));
				order.setMinutes(rs.getInt("minutes"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				order.setPrice(rs.getFloat("price")); 
				order.setProduct(product);
				order.setUser(user);
				//date
				Date d = rs.getDate("order_date");
				System.out.println(d);
				if(d!= null) 
					order.setDate(d.toLocalDate());
				//status
				if(rs.getInt("status") == 0)
				{
					order.setStatus("pending");
				}
				else
				{
					order.setStatus("canceled");
				}
				
				
				orders.add(order);
			}
			
			ServerResponse sr = new ServerResponse(); // create server response
			sr.setAction(Actions.GetMyOrdersHistory);
			sr.setValue(orders);
			
			client.sendToClient(sr); // send messeage to client
		}
	catch (Exception e)
	{
		// TODO: handle exception
		e.printStackTrace();
	}
}

public static void CancelOrder(Connection conn,  ConnectionToClient client,Order o) throws SQLException {
	/*
	 * change order status to canceled
	 */
	ServerResponse sr = new ServerResponse(); // create server response
	sr.setAction(Actions.updateConclusion);
	PreparedStatement ps;
	String s1 = "update orders set status=1 where id=?";
	try {
			ps = (PreparedStatement) conn.prepareStatement(s1);
			ps.setInt(1, o.getId());
			ps.executeUpdate();

			
			client.sendToClient(sr); // send messeage to client
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


}
