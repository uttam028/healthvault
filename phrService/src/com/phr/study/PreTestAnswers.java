package com.phr.study;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PreTestAnswers implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String participationId;
	private String submitDate;
	private ArrayList<String> allQuestionId;
	private ArrayList<String> allQuestionAnswer;
	
	public PreTestAnswers() {
		// TODO Auto-generated constructor stub
	}
	
	public PreTestAnswers(String userId, String participationId, String submitDate, ArrayList<String> allQuestionId,
			ArrayList<String> allQuestionAnswer) {
		super();
		this.userId = userId;
		this.participationId = participationId;
		this.submitDate = submitDate;
		this.allQuestionId = allQuestionId;
		this.allQuestionAnswer = allQuestionAnswer;
	}

	public String getUserId() {
		return userId;
	}

	public String getParticipationId() {
		return participationId;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public ArrayList<String> getAllQuestionId() {
		return allQuestionId;
	}

	public ArrayList<String> getAllQuestionAnswer() {
		return allQuestionAnswer;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setParticipationId(String participationId) {
		this.participationId = participationId;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public void setAllQuestionId(ArrayList<String> allQuestionId) {
		this.allQuestionId = allQuestionId;
	}

	public void setAllQuestionAnswer(ArrayList<String> allQuestionAnswer) {
		this.allQuestionAnswer = allQuestionAnswer;
	}

	
	
}
