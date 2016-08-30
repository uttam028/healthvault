package com.phr.study;

import java.io.Serializable;

public class QA implements Serializable{
	private static final long serialVersionUID = 2;

	private String question;
	private String answer;

	public QA() {
		// TODO Auto-generated constructor stub
	}
	public QA(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}


}