package entity;

import java.util.ArrayList;

public class ValidationResult {
	private boolean answer;
	private ArrayList<String> msg;
	
	public ValidationResult(boolean answer, ArrayList<String> msg)
	{
		this.answer = answer;
		this.setMsg(msg);
	}
	
	public boolean isAnswer() {
		return answer;
	}
	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public ArrayList<String> getMsg() {
		return msg;
	}

	public void setMsg(ArrayList<String> msg) {
		this.msg = msg;
	}

}
