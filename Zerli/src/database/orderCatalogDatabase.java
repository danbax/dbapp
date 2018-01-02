package database;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.Order;
import client.Product;
import client.ServerResponse;
import client.Survey;
import client.SurveyConclusion;
import client.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class orderCatalogDatabase {
public static void order(Connection conn,  ConnectionToClient client,Order order) throws SQLException {
		
		
		Calendar calendar = Calendar.getInstance();
		java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.buyProductFromCatalog);
		PreparedStatement ps;
		String s1 = "INSERT INTO orders (user_id,product_id,order_date,greeting_text,hours,minutes,price,payment_method,status) VALUES (?,?,?,?,?,?,?,?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, order.getUser().getId());
				ps.setInt(2, order.getProduct().getPid());
				ps.setDate(3,  Date.valueOf(order.getDate()));
				ps.setString(4, order.getGreeting());
				ps.setInt(5, order.getHours());
				ps.setInt(6, order.getMinutes());
				ps.setFloat(7, order.getPrice());
				ps.setInt(8,order.getPaymentMethod());
				ps.setInt(9,0);
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

public static void getMyOrdersHistory(Connection conn,  ConnectionToClient client,User user) throws SQLException {
	/*
	 * get list from database
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
