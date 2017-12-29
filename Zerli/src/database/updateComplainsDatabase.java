package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.Complain;
import client.Product;
import client.ServerResponse;
import client.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class updateComplainsDatabase {
	
	public static void getComplains(Connection conn,  ConnectionToClient client) throws SQLException {
		/*
		 * get list of products from database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from complains";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				rs = ps.executeQuery();
				ArrayList<Complain> complains = new ArrayList<Complain>();
				while ( rs.next() )
				{//int complainId, User user, String desc, boolean isAnswered, float compensation
					Complain complain = new Complain(
							rs.getInt("idComplains"),
							rs.getInt("userID"),
							rs.getString("desc"),
							rs.getString("status"),
							rs.getFloat("compensation"));
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetProducts);
				//sr.setValue(products);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public static void addComplain(Connection conn,  ConnectionToClient client,Complain complain) throws SQLException {

		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddComplain);
		PreparedStatement ps;
		String s1 = "INSERT INTO complains (idComplain, desc, userID, compensation, isAnswered ) VALUES (?,?,?,?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, complain.getComplainId());
				ps.setString(2, complain.getDesc());
				ps.setInt(3, complain.getUser().getId());
				ps.setFloat(4, complain.getCompensation());
				ps.setString(5, complain.getStatus());
				
				ps.executeUpdate();
				
				sr.setAnswer(Actions.AddComplain);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			sr.setAnswer(Actions.AddComplainError);
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
	
	public static void updateComplain(Connection conn,  ConnectionToClient client,Complain complain) throws SQLException {
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddComplain);
		PreparedStatement ps;
		String s1 = "update complains set desc=?,userID=?,isAnswered=?,compensation=? where id=?;";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, complain.getDesc());
				ps.setInt(2, complain.getUser().getId());
				ps.setString(3, complain.getStatus());
				ps.setFloat(4, complain.getCompensation());
				ps.setInt(5, complain.getComplainId());
				ps.executeUpdate();

				
				sr.setAnswer(Actions.ComplainAdded);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			
		}
	}
	
	public static void deleteComplain(Connection conn,  ConnectionToClient client,Complain complain) throws SQLException {
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteComplain);
		PreparedStatement ps;
		String s1 = "delete from products where id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, complain.getComplainId());
				ps.executeUpdate();

				
				sr.setAnswer(Actions.DeletedComplain);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			sr.setAnswer(Actions.DeletedComplainError);
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}

}
