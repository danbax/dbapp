package client;

import java.io.Serializable;

public class Complain implements Serializable {
	private int complainId;
	private User user;
	private String desc;
	private int isAnswered;
	private float compensation;
	public Complain (int complainId, User user, String desc, int isAnswered, float compensation)
	{
		this.setAnswered(isAnswered);
		this.setCompensation(compensation);
		this.setComplainId(complainId);
		this.setDesc(desc);
		this.setUser(user);
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
	public int getisAnswered() {
		return isAnswered;
	}
	public void setAnswered(int isAnswered) {
		this.isAnswered = isAnswered;
	}
	public float getCompensation() {
		return compensation;
	}
	public void setCompensation(float compensation) {
		this.compensation = compensation;
	}
	
}
