package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class HealthStatusQuestion implements IsSerializable{
	private String question;
	private String hint;
	private boolean answerRequired;
	private int sequenceId;
	private boolean active;
	private String question_type;
	private String default_answer;
	private String answer_options;
	
	public HealthStatusQuestion() {
		// TODO Auto-generated constructor stub
	}
	
	
	public HealthStatusQuestion(String question, String hint,
			boolean answerRequired, int sequenceId, boolean active,
			String question_type, String default_answer, String answer_options) {
		super();
		this.question = question;
		this.hint = hint;
		this.answerRequired = answerRequired;
		this.sequenceId = sequenceId;
		this.active = active;
		this.question_type = question_type;
		this.default_answer = default_answer;
		this.answer_options = answer_options;
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
	public String getQuestion_type() {
		return question_type;
	}
	public String getDefault_answer() {
		return default_answer;
	}
	public String getAnswer_options() {
		return answer_options;
	}
	
}
