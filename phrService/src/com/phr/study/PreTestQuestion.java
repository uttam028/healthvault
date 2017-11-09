package com.phr.study;
public class PreTestQuestion{
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

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPossibleAnswers(String possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}

	public void setDisplayLevel(int displayLevel) {
		this.displayLevel = displayLevel;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public void setChildVisibleDependentAnswer(String childVisibleDependentAnswer) {
		this.childVisibleDependentAnswer = childVisibleDependentAnswer;
	}

	public void setDefaultAnswer(String defaultAnswer) {
		this.defaultAnswer = defaultAnswer;
	}
	
	
	

}
