package client;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2176148656112261925L;
	private int id;
	private String username;
	private String password;
	
	public User(String user,String pass) {
		this.setUsername(user);
		this.setPassword(pass);
	}
	
	public User(int uid,String user,String pass) {
		this.setId(uid);
		this.setUsername(user);
		this.setPassword(pass);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}