package cse.mlab.hvr.shared.speechtest;

import com.google.gwt.user.client.rpc.IsSerializable;

public class HealthStatusQuestion implements IsSerializable{
	private String question;
	private String hint;
	private boolean isAnswerRequired;
	private int sequenceId;
	private boolean isActive;
	public HealthStatusQuestion() {
		// TODO Auto-generated constructor stub
	}
	public HealthStatusQuestion(String question, String hint,
			boolean isAnswerRequired, int sequenceId, boolean isActive) {
		super();
		this.question = question;
		this.hint = hint;
		this.isAnswerRequired = isAnswerRequired;
		this.sequenceId = sequenceId;
		this.isActive = isActive;
	}

	public String getQuestion() {
		return question;
	}

	public String getHint() {
		return hint;
	}

	public boolean isAnswerRequired() {
		return isAnswerRequired;
	}

	public int getSequenceId() {
		return sequenceId;
	}

	public boolean isActive() {
		return isActive;
	}
	
	
	
}
