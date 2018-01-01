package database;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

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
		
		
		Calendar calendar = Calendar.getInstance();
		java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.buyProductFromCatalog);
		PreparedStatement ps;
		String s1 = "INSERT INTO orders (user_id,product_id,order_date,greeting_text,hours,minutes,price,payment_method) VALUES (?,?,?,?,?,?,?,?);";
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

private static java.sql.Date getCurrentDate() {
    java.util.Date today = new java.util.Date();
    return new java.sql.Date(today.getTime());
}
}
