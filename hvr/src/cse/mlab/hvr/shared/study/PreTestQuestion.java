package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PreTestQuestion implements IsSerializable{
	private String testId;
	private String questionId;
	private String question;
	private String type;
	private String possibleAnswers;
	private int displayLevel;
	private int order;
	private boolean active;
	private boolean required;
	private String parentId;
	private boolean hasChild;
	private String childVisibleDependentAnswer;
	private String defaultAnswer;
	
	public PreTestQuestion() {
		// TODO Auto-generated constructor stub
	}
	
	public PreTestQuestion(String testId, String questionId, String question, String type, String possibleAnswers,
			int displayLevel, int order, boolean active, boolean required, String parentId, boolean hasChild,
			String childVisibleDependentAnswer, String defaultAnswer) {
		super();
		this.testId = testId;
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.possibleAnswers = possibleAnswers;
		this.displayLevel = displayLevel;
		this.order = order;
		this.active = active;
		this.required = required;
		this.parentId = parentId;
		this.hasChild = hasChild;
		this.childVisibleDependentAnswer = childVisibleDependentAnswer;
		this.defaultAnswer = defaultAnswer;
	}

	public String getTestId() {
		return testId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public String getQuestion() {
		return question;
	}

	public String getType() {
		return type;
	}

	public String getPossibleAnswers() {
		return possibleAnswers;
	}

	public int getDisplayLevel() {
		return displayLevel;
	}

	public int getOrder() {
		return order;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isRequired() {
		return required;
	}

	public String getParentId() {
		return parentId;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public String getChildVisibleDependentAnswer() {
		return childVisibleDependentAnswer;
	}

	public String getDefaultAnswer() {
		return defaultAnswer;
	}
	

}
