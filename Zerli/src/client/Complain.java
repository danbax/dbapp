package client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Complain implements Serializable {
	/**
	 * 
	 */
	private int complainId;
	private User user; 
	private int userID;
	private String desc;
	private String status;
	private float compensation;
	public Complain (int complainId, User user, String desc, String status, float compensation)
	{
		this.setStatus(status);
		this.setCompensation(compensation);
		this.setComplainId(complainId);
		this.setDesc(desc);
		this.setUser(user);
		this.setUserID(this.user.getId());
	}	
	
	public Complain (int complainId, int userID, String desc, String status, float compensation)
	{
		this.setStatus(status);
		this.setCompensation(compensation);
		this.setComplainId(complainId);
		this.setDesc(desc);
		this.setUserID(this.user.getId());
	}	
	
	
	public int getComplainId() {
		return complainId;
	}
	public void setComplainId(int complainId) {
		this.complainId = complainId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public float getCompensation() {
		return compensation;
	}
	public void setCompensation(float compensation) {
		this.compensation = compensation;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
