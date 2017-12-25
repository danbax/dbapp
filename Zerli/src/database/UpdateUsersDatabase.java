package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.Product;
import client.ServerResponse;
import client.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class UpdateUsersDatabase {
	public static void getUsers(Connection conn,  ConnectionToClient client,String searchText) throws SQLException {
		/*
		 * get list of users from database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from users";
		
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				if(searchText != "" && searchText != null)
				{
					s1 = s1 + " where username=? or password=? or fname=? or lname=? or phone=?";
					ps = (PreparedStatement) conn.prepareStatement(s1);
					ps.setString(1,searchText);
					ps.setString(2,searchText);
					ps.setString(3,searchText);
					ps.setString(4,searchText);
					ps.setString(5,searchText);
					System.out.println(searchText);
				}
				
				
				rs = ps.executeQuery();
				ArrayList<User> users = new ArrayList<User>();
				while ( rs.next() )
				{
					// create product
					User user = new User(rs.getInt("id"),
							rs.getString("username"),
							rs.getString("password"));
					users.add(user);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetUsers);
				sr.setValue(users);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public static void updateUsers(Connection conn,  ConnectionToClient client,User user) throws SQLException {
		/*
		 * update user In Database
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.updateUser);
		PreparedStatement ps;
		String s1 = "update users set username=?,password=? where id=?;";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				ps.setInt(3, user.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			
		}
	}
	
	public static void deleteUser(Connection conn,  ConnectionToClient client,User user) throws SQLException {
		/*
		 * delete product from database
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteUser);
		PreparedStatement ps;
		String s1 = "delete from users where id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, user.getId());
				ps.executeUpdate();

				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
		
		}
	}
}
