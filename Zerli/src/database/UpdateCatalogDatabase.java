package database;

import ocsf.server.ConnectionToClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import client.Product;
import client.ServerResponse;
import enums.Actions;
public class UpdateCatalogDatabase {
	
	public static void getProducts(Connection conn,  ConnectionToClient client) throws SQLException {
		/*
		 * get list of products from database
		 */
		PreparedStatement ps;
		ResultSet rs; 
		String s1 = "select * from products";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				rs = ps.executeQuery();
				ArrayList<Product> products = new ArrayList<Product>();
				while ( rs.next() )
				{
					// create product
					Product product = new Product(
							rs.getInt("id"),
							rs.getString("pname"),
							rs.getString("ptype")
							//,"serverImages/"+rs.getString("img")
							);
					
					
					product.setPrice(rs.getFloat("price"));
					product.setProductId(rs.getString("product_id"));
					product.setImage("/serverImages/"+rs.getString("img"));
					
					// add to product array
					products.add(product);
				}
				
				ServerResponse sr = new ServerResponse(); // create server response
				sr.setAction(Actions.GetProducts);
				sr.setValue(products);
				
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public static void addProduct(Connection conn,  ConnectionToClient client,Product product) throws SQLException {
		/*
		 * Checks if username,password exist in database
		 */
		
		File file = new File("");
		String fileName = new SimpleDateFormat("ssmmyyyyHHMMdd").format(new Date()); // create filename by date
		String filePath = file.getAbsolutePath()+"\\Zerli\\src\\serverImages\\"+fileName+"."+product.getImgf().getExe();
		
		// save image to serverImages folder
		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			   fos.write(product.getImgf().getMybytearray());
			   fos.close();
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddProduct);
		System.out.println(product.getProductName());
		PreparedStatement ps;
		String s1 = "INSERT INTO products (pname, ptype,price,img,product_ID) VALUES (?,?,?,?,?);";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, product.getProductName());
				ps.setString(2, product.getProductType());
				ps.setFloat(3, product.getPrice());
				ps.setString(4, fileName+"."+product.getImgf().getExe());
				ps.setString(5, product.getProductId());
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
	
	public static void updateProduct(Connection conn,  ConnectionToClient client,Product product) throws SQLException {
		/*
		 * updateProductInDatabase
		 */
		ServerResponse sr = new ServerResponse(); // create server response
		sr.setAction(Actions.AddProduct);
		PreparedStatement ps;
		String s1 = "update products set pname=?,ptype=?,price=? where id=?;";
		try {
				ps = (PreparedStatement) conn.prepareStatement(s1);
				ps.setString(1, product.getProductName());
				ps.setString(2, product.getProductType());
				ps.setFloat(3, product.getPrice());
				ps.setInt(4, product.getPid());
				ps.executeUpdate();

				
				sr.setAnswer(Actions.ProductAdded);
				client.sendToClient(sr); // send messeage to client
			}
		catch (Exception e)
		{
			
		}
	}
	
	public static void deleteProduct(Connection conn,  ConnectionToClient client,Product product) throws SQLException {
		/*
		 * delete product from database
		 */
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
}
