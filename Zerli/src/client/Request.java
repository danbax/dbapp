package client;
import enums.Actions;;

public class Request {
	/*
	 * object represent request to server
	 * instead of sending arraylist
	 * 
	 * ********** NOT WORKING YET ********** *
	 */
	private Actions action;
	private Object value;
	
	public Request()
	{
		
	}
	public Request(Actions act)
	{
		this.action = act;
		value = null;
	}
	
	public Request(Actions act,Object val)
	{
		action = act;
		value = val;
	}
	
	
	public Actions getAction() {
		return action;
	}
	public void setAction(Actions action) {
		this.action = action;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object values) {
		this.value = values;
	}
	
	
	
}
