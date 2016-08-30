package com.phr.study;

import java.io.Serializable;
import java.util.ArrayList;

public class MyStudyDataModel implements Serializable{
	private StudyOverview studyOverview;
	private Compliance compliance;
	private ArrayList<QA> qaList;
	private String enrollmentDate;
	private String lastParticipationDate;
	private int totalParticipationCount;
	
	public MyStudyDataModel() {
		// TODO Auto-generated constructor stub
	}

	public MyStudyDataModel(StudyOverview studyOverview, Compliance compliance,
			ArrayList<QA> qaList, String enrollmentDate,
			String lastParticipationDate, int totalParticipationCount) {
		super();
		this.studyOverview = studyOverview;
		this.compliance = compliance;
		this.qaList = qaList;
		this.enrollmentDate = enrollmentDate;
		this.lastParticipationDate = lastParticipationDate;
		this.totalParticipationCount = totalParticipationCount;
	}

	public StudyOverview getStudyOverview() {
		return studyOverview;
	}

	public Compliance getCompliance() {
		return compliance;
	}

	public ArrayList<QA> getQaList() {
		return qaList;
	}

	public String getEnrollmentDate() {
		return enrollmentDate;
	}

	public String getLastParticipationDate() {
		return lastParticipationDate;
	}

	public int getTotalParticipationCount() {
		return totalParticipationCount;
	}

	public void setCompliance(Compliance compliance) {
		this.compliance = compliance;
	}

	public void setQaList(ArrayList<QA> qaList) {
		this.qaList = qaList;
	}

	public void setEnrollmentDate(String enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public void setLastParticipationDate(String lastParticipationDate) {
		this.lastParticipationDate = lastParticipationDate;
	}

	public void setTotalParticipationCount(int totalParticipationCount) {
		this.totalParticipationCount = totalParticipationCount;
	}

	public void setStudyOverview(StudyOverview studyOverview) {
		this.studyOverview = studyOverview;
	}

	
}
