package database;

import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.Refund;
import client.ServerResponse;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class RefundDatabase {
public static void addRefund(Connection conn,  ConnectionToClient client,Refund refund) throws SQLException {
		
	/*
	 * add refund on canceled product to database
	 */
	
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddRefund);
		PreparedStatement ps;
		String s1 = "INSERT INTO refunds (order_id,refund) VALUES (?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, refund.getOrder().getId());
				ps.setFloat(2, refund.getRefund());
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
