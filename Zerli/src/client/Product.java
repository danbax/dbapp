package client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable {
	private String pname;
	private int pid;
	private String ptype;
	
	
	public Product(	String name, String type) {
		this.pname = name;
		this.ptype = type;
	}
	
	public Product(	int id, String name, String type) {
		this.pid = id;
		this.pname = name;
		this.ptype = type;
	}
	
	public String getProductName() {
		return this.pname;
	}
	
	public int getProductID() {
		return this.pid;
	}
	
	public String getProductType() {
		return this.ptype;
	}
	
	public void setProductName(String name) {
		this.pname = name;
	}
	
	public String toString() { 
	    return "id:"+this.pid +" Name: '" + this.pname + "', Type: '" + this.ptype;
	} 
	
	
}
