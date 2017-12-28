package database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import client.CreditCard;
import client.ServerResponse;
import client.User;
import enums.Actions;
import ocsf.server.ConnectionToClient;

public class AuthorizeUsersDatabase {
	public static void getUsersNotAuthorized(Connection conn,  ConnectionToClient client) throws SQLException {
		/*
		 * get list of users from database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from users where authorized=0";
		
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);				
				rs = ps.executeQuery();
				ArrayList<User> users = new ArrayList<User>();
				while ( rs.next() )
				{
					// create product
					User user = new User(rs.getInt("id"),
							rs.getString("username"),
							rs.getString("password"));
					user.setFname(rs.getString("fname"));
					user.setLname(rs.getString("lname"));
					user.setPhone(rs.getString("phone"));
					users.add(user);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetNotAuthorizedUsers);
				sr.setValue(users);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public static void AuthorizeUser(Connection conn,  ConnectionToClient client,CreditCard creditCard) throws SQLException {
		/*
		 * update user In Database
		 * 
		 * subscribe = 1 : regular authorized
		 * subscribe = authorized = 2 : monthly subscribe
		 * subscribe = authorized = 3 : yearly subscribe
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AuthorizeUser);
		PreparedStatement ps;
		String s1 = "update users set authorized=? where id=?;";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, creditCard.getAuthorized());
				ps.setInt(2, creditCard.getUser().getId());
				ps.executeUpdate();

				/* insert credit card data into database */
				if(creditCard != null)
				{
					PreparedStatement ps2;
					String s2 = "INSERT INTO credit_card (card_number,expMonth,expYear,cvv,user_id) VALUES (?,?,?,?,?);";
					try {
						ps2 = (PreparedStatement) conn.prepareStatement(s2);
						ps2.setString(1, creditCard.getCardNumber());
						ps2.setInt(2, creditCard.getExpMonth());
						ps2.setInt(3, creditCard.getExpYear());
						ps2.setString(4, creditCard.getCvv());
						ps2.setInt(5, creditCard.getUser().getId());
						ps2.executeUpdate();
					}
					catch (Exception e)
					{
						
					}
				}
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			
		}
	}

}
