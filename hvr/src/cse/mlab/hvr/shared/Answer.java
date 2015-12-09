package cse.mlab.hvr.shared;

import java.io.Serializable;

public class Answer implements Serializable{
	private static final long serialVersionUID = 5744986706947752922L;
	private int questionId;
	private String answer;
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
