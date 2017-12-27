package client;

import java.io.Serializable;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

@SuppressWarnings("serial")
public class Product implements Serializable {
	private int pid;
	private String productName;
	private String productType;
	private float price;
	private ImgFile imgf;
	private String productId;
	private ImageView image; 
	
	public Product(	String name, String type) {
		this.setProductName(name);
		this.setProductType(type);
	}
	
	public Product(	int id, String name, String type) {
		this.setPid(id);
		this.setProductName(name);
		this.setProductType(type);
	}
	
	public Product(int id,String name, String type,String img) {
		this.setPid(id);
		this.setProductName(name);
		this.setProductType(type);
		//image
		ImageView imageView = new ImageView(img);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		this.setImage(imageView);
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

	public ImgFile getImgf() {
		return imgf;
	}

	public void setImgf(ImgFile imgf) {
		this.imgf = imgf;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}
	
	
	
	
}
