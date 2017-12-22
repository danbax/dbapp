package database;

import ocsf.server.ConnectionToClient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import enums.Actions;
public class LoginManagerDatabase {
	static ArrayList<String> arr = new ArrayList<String>();
	
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
				if ( rs.next() )
				{
					// username exist
					System.out.println("exist");
					
					// response from server
					arr.clear();
					arr.add(Actions.ValidLoginDataCheck.toString());
					arr.add(Actions.UsernameExist.toString());
					System.out.println("login");
				}
				else
				{
					// username does not exist
					arr.clear();
					arr.add(Actions.ValidLoginDataCheck.toString());
					arr.add(Actions.UsernameDoesNotExist.toString());
					System.out.println("error");
					
				}
				client.sendToClient(arr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
}
