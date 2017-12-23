package client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable {
	private int pid;
	private String productName;
	private String productType;
	
	
	public Product(	String name, String type) {
		this.setProductName(name);
		this.setProductType(type);
	}
	
	public Product(	int id, String name, String type) {
		this.setPid(id);
		this.setProductName(name);
		this.setProductType(type);
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	
	
	
}
