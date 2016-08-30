package com.phr.study;

import java.io.Serializable;


public class HealthStatusQuestion implements Serializable{
	private String question;
	private String hint;
	private boolean answerRequired;
	private int sequenceId;
	private boolean active;
	public HealthStatusQuestion() {
		// TODO Auto-generated constructor stub
	}
	public HealthStatusQuestion(String question, String hint,
			boolean answerRequired, int sequenceId, boolean active) {
		super();
		this.question = question;
		this.hint = hint;
		this.answerRequired = answerRequired;
		this.sequenceId = sequenceId;
		this.active = active;
	}

	public String getQuestion() {
		return question;
	}
	public String getHint() {
		return hint;
	}
	public boolean isAnswerRequired() {
		return answerRequired;
	}
	public int getSequenceId() {
		return sequenceId;
	}
	public boolean isActive() {
		return active;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public void setAnswerRequired(boolean answerRequired) {
		this.answerRequired = answerRequired;
	}
	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

}
