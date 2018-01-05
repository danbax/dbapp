package database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.CartProduct;
import entity.ReportOrders;
import entity.ServerResponse;
import enums.Actions;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class CartProudctDatabase {
	/* orderReport database */
	static int shop_id = ServerController.shop.getId();
	public static void getCart(Connection conn,  ConnectionToClient client) throws SQLException { 
		/*
		 * get list of cart items from database
		 */
		PreparedStatement ps;
		ResultSet rs;
		PreparedStatement ps2;
		ResultSet rs2;
		String s1 = "select * from cart where shop_id=?";
		String s2 = "select * from products where id=? and shop_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, shop_id);
				rs = ps.executeQuery();
				ArrayList<CartProduct> cart = new ArrayList<CartProduct>();
				
				while ( rs.next() )
				{
					// create 
					CartProduct cartProduct = new CartProduct();
					Date d = rs.getDate("order_date");
					if(d != null)
						cartProduct.setOrderDate(d.toLocalDate());
					cartProduct.setOrder_id(rs.getInt("id"));
					
					// get Product
					ps2 = (PreparedStatement) conn.prepareStatement(s2);
					ps2.setInt(1, rs.getInt("product_id"));
					ps2.setInt(2, shop_id);
					rs2 = ps2.executeQuery();
					
					if(rs2.next())
					{
						cartProduct.setProduct_type(rs2.getString("ptype"));
					}
					
					// add to product array
					cart.add(cartProduct);
				}
			/* refunds money */	
				
				ReportOrders report = new ReportOrders();
				report.setCart(cart);
				
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetCartOrders);
				sr.setValue(report);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
