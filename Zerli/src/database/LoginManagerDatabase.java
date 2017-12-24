package database;

import ocsf.server.ConnectionToClient;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.ServerResponse;
import enums.Actions;
public class LoginManagerDatabase {
	
	public static void isValidData(Connection conn,  ConnectionToClient client,String user,String pass) throws SQLException {
		/*
		 * Checks if username,password exist in database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select id from users where username=? and password=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, user);
				ps.setString(2, pass);
				rs = ps.executeQuery();
				ServerResponse sr = new ServerResponse(); // create server response
				if ( rs.next() )
				{
					// username exist
					
					// response from server
					sr.setAction(Actions.ValidLoginDataCheck);
					sr.setAnswer(Actions.UsernameExist);
					System.out.println("login");
				}
				else
				{
					// username does not exist
					sr.setAction(Actions.ValidLoginDataCheck);
					sr.setAnswer(Actions.UsernameDoesNotExist);
					System.out.println("wrong data");
					
				}
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
}
