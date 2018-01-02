package client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomMadeProduct implements Serializable {
	private String type;
	private float minPrice;
	private float maxPrice;
	private String color;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
}
