package database;

import ocsf.server.ConnectionToClient;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import entity.Address;
import entity.CreditCard;
import entity.ServerResponse;
import entity.User;
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
				ps.setString(1, user);
				ps.setString(2, pass);
				rs = ps.executeQuery();
				ServerResponse sr = new ServerResponse(); // create server response
				if ( rs.next() )
				{
					// username exist
					
					// response from server
					sr.setAction(Actions.ValidLoginDataCheck);
					
					if(rs.getInt("logged")==1)
					{
						// if user already logged from different device
						sr.setAnswer(Actions.AlreadyLoggedIn);
						client.sendToClient(sr);
					}
					
					sr.setAnswer(Actions.UsernameExist);
					User myuser = new User(rs.getString("username"),rs.getString("password"));
					myuser.setId(rs.getInt("ID"));
					myuser.setFname(rs.getString("fname"));
					myuser.setLname(rs.getString("lname"));
					myuser.setPhone(rs.getString("phone"));
					myuser.setPermissions(rs.getInt("permissions"));
					myuser.setAuthorized(rs.getInt("authorized"));
					
					String s2 = "update users set logged=1 where id=?";
					try {
							ps = (PreparedStatement) conn.prepareStatement(s2);
							ps.setInt(1, myuser.getId());
							ps.executeUpdate();
					}
					catch (Exception e)
					{
						// TODO: handle exception
					}
					
					sr.setValue(myuser);
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
	
	public static void logout(Connection conn,  ConnectionToClient client,User user) throws SQLException {
		/*
		 * logout -> set user logged state to 0
		 */
		PreparedStatement ps;
		String s1 = "update users set logged=0 where id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, user.getId());
				ps.executeUpdate();
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public static void GetMyCreditCard(Connection conn,  ConnectionToClient client,User u) throws SQLException {
		/*
		 * return user credit card data
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from credit_card where user_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, u.getId());
				rs = ps.executeQuery();
				ServerResponse sr = new ServerResponse(); // create server response
				CreditCard cc = new CreditCard();
				if (rs.next())
				{
					// credit card exist
					cc.setId(rs.getInt("id"));
					cc.setCardNumber(rs.getString("card_number"));
					cc.setExpMonth(rs.getInt("expMonth"));
					cc.setExpYear(rs.getInt("expYear"));
					cc.setCvv(rs.getString("cvv"));
				}
				else
				{
					// credit card doesn't exist
					cc = null;
				}
				sr.setAction(Actions.GetMyCreditCard);
				sr.setValue(cc);
				client.sendToClient(sr); // send messeage to client 
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}	
	}
	
	public static void getMyAdress(Connection conn,  ConnectionToClient client,User u) throws SQLException {
		/*
		 * return user address data
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from address where user_id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, u.getId());
				rs = ps.executeQuery();
				ServerResponse sr = new ServerResponse(); // create server response
				Address adress = new Address();
				if (rs.next())
				{
					// credit card exist
					adress.setId(rs.getInt("id"));
					adress.setCity(rs.getString("city"));
					adress.setStreet(rs.getString("street"));
					adress.setNumber(rs.getInt("number"));
				}
				else
				{
					// credit card doesn't exist
					adress = null;
				}
				sr.setAction(Actions.GetMyAdress);
				sr.setValue(adress);
				client.sendToClient(sr); // send messeage to client
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}	
	}
}
