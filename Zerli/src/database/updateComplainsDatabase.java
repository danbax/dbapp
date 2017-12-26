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
		String s1 = "select complains.idComplain, complains.desc, users.ID, users.fname, users.lname, "
				+ "complains.compensation, complains.isAnswered from complains,users where users.id=complains.userID";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				rs = ps.executeQuery();
				ArrayList<Complain> complains = new ArrayList<Complain>();
				while ( rs.next() )
				{//int complainId, User user, String desc, boolean isAnswered, float compensation
					// create product
					/*
					Complain complain = new Complain(rs.getInt("idComplains")));
					products.add(product);
					*/
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
		String s1 = "INSERT INTO complains (idComplains, desc, userID, compensation, isAnswered ) VALUES (?,?,?,?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, complain.getComplainId());
				ps.setString(2, complain.getDesc());
				ps.setInt(3, complain.getUser().getId());
				ps.setFloat(4, complain.getCompensation());
				ps.setInt(5, complain.getisAnswered());
				
				ps.executeUpdate();
				
				
				sr.setAnswer(Actions.ProductAdded);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
			sr.setAnswer(Actions.ProductAddedError);
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
	/*
	public static void updateProduct(Connection conn,  ConnectionToClient client,Product product) throws SQLException {
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddProduct);
		PreparedStatement ps;
		String s1 = "update products set pname=?,ptype=? where id=?;";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, product.getProductName());
				ps.setString(2, product.getProductType());
				ps.setInt(3, product.getPid());
				ps.executeUpdate();

				
				sr.setAnswer(Actions.ProductAdded);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			
		}
	}
	
	public static void deleteProduct(Connection conn,  ConnectionToClient client,Product product) throws SQLException {
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.DeleteProduct);
		PreparedStatement ps;
		String s1 = "delete from products where id=?";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setInt(1, product.getPid());
				ps.executeUpdate();

				
				sr.setAnswer(Actions.DeletedProduct);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			sr.setAnswer(Actions.DeletedProductError);
			try {
				client.sendToClient(sr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // send messeage to client
		}
	}
*/
}
