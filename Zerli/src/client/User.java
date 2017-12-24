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
	
	private String fname;
	private String lname;
	private String phone;
	
	private int permissions;
	private int logged;
	
	public User(int uid, String user,String pass,String fname,
			String lname,String phone,int permissions,int logged) {
		this.setUsername(user);
		this.setPassword(pass);
		this.setFname(fname);
		this.setLname(lname);
		this.setLogged(logged);
		this.setPermissions(permissions);
		this.setId(uid);
		this.setPhone(phone);
	}
	
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

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	public int getLogged() {
		return logged;
	}

	public void setLogged(int logged) {
		this.logged = logged;
	}
}
