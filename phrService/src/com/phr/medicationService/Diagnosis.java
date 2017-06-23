package com.phr.medicationService;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Diagnosis {
	long id;
	String email;
	String condition;
	String diagnosisDate;
	String note;

	public Diagnosis() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDiagnosisDate() {
		return diagnosisDate;
	}

	public void setDiagnosisDate(String diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id:" + id + ", email:" + email + ", condition:"+ condition + ", diagnosis date:" + diagnosisDate + ", note:" + note;
	}
	
}
