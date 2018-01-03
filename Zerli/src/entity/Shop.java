package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Shop implements Serializable {
	private int id;
	private String shopName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	public String toString()
	{
		return this.getShopName();
	}
}
