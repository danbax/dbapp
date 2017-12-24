package database;

import ocsf.server.ConnectionToClient;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.ServerResponse;
import client.User;
import enums.Actions;
public class LoginManagerDatabase {
	
	public static void isValidData(Connection conn,  ConnectionToClient client,String user,String pass) throws SQLException {
		/*
		 * Checks if username,password exist in database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from users where username=? and password=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				System.out.println(user+pass);
				ps.setString(1, user);
				ps.setString(2, pass);
				rs = ps.executeQuery();
				ServerResponse sr = new ServerResponse(); // create server response
				if ( rs.next() )
				{
					// username exist
					
					int id = rs.getInt("ID");
					String fname = rs.getString("fname");
					String lname = rs.getString("lname");
					String phone = rs.getString("phone");
					int permissions = rs.getInt("permissions"); // kind of user
					String username = rs.getString("username");
					String password = rs.getString("password");
					int logged = rs.getInt("logged"); // 0 - not logged in, 1 - logged in
					
					User myUser = new User(id, username,password,fname,
							lname,phone,permissions,logged);
					
					// response from server
					sr.setAction(Actions.ValidLoginDataCheck);
					sr.setAnswer(Actions.UsernameExist);
					sr.setValue(myUser);
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
