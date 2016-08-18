package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class HealthStatusQuestion implements IsSerializable{
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
	
}
