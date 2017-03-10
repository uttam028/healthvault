package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StudyStatistics implements IsSerializable {
	
	private int totalEnrollment;
	private int totalUserParticipation;
	private int totalCompletedSpeechtest;
	
	private int lastdayUserParticipation;
	private int lastdayCompletedSpeechtest;
	private int lastweekUserParticipation;
	private int lastweekCompletedSpeechtest;
	private int lastmonthUserParticipation;
	private int lastmonthCompletedSpeechtest;
	
	public StudyStatistics() {
		// TODO Auto-generated constructor stub
	}

	public StudyStatistics(int totalEnrollment, int totalUserParticipation,
			int totalCompletedSpeechtest, int lastdayUserParticipation,
			int lastdayCompletedSpeechtest, int lastweekUserParticipation,
			int lastweekCompletedSpeechtest, int lastmonthUserParticipation,
			int lastmonthCompletedSpeechtest) {
		super();
		this.totalEnrollment = totalEnrollment;
		this.totalUserParticipation = totalUserParticipation;
		this.totalCompletedSpeechtest = totalCompletedSpeechtest;
		this.lastdayUserParticipation = lastdayUserParticipation;
		this.lastdayCompletedSpeechtest = lastdayCompletedSpeechtest;
		this.lastweekUserParticipation = lastweekUserParticipation;
		this.lastweekCompletedSpeechtest = lastweekCompletedSpeechtest;
		this.lastmonthUserParticipation = lastmonthUserParticipation;
		this.lastmonthCompletedSpeechtest = lastmonthCompletedSpeechtest;
	}

	public int getTotalEnrollment() {
		return totalEnrollment;
	}

	public void setTotalEnrollment(int totalEnrollment) {
		this.totalEnrollment = totalEnrollment;
	}

	public int getTotalUserParticipation() {
		return totalUserParticipation;
	}

	public void setTotalUserParticipation(int totalUserParticipation) {
		this.totalUserParticipation = totalUserParticipation;
	}

	public int getTotalCompletedSpeechtest() {
		return totalCompletedSpeechtest;
	}

	public void setTotalCompletedSpeechtest(int totalCompletedSpeechtest) {
		this.totalCompletedSpeechtest = totalCompletedSpeechtest;
	}

	public int getLastdayUserParticipation() {
		return lastdayUserParticipation;
	}

	public void setLastdayUserParticipation(int lastdayUserParticipation) {
		this.lastdayUserParticipation = lastdayUserParticipation;
	}

	public int getLastdayCompletedSpeechtest() {
		return lastdayCompletedSpeechtest;
	}

	public void setLastdayCompletedSpeechtest(int lastdayCompletedSpeechtest) {
		this.lastdayCompletedSpeechtest = lastdayCompletedSpeechtest;
	}

	public int getLastweekUserParticipation() {
		return lastweekUserParticipation;
	}

	public void setLastweekUserParticipation(int lastweekUserParticipation) {
		this.lastweekUserParticipation = lastweekUserParticipation;
	}

	public int getLastweekCompletedSpeechtest() {
		return lastweekCompletedSpeechtest;
	}

	public void setLastweekCompletedSpeechtest(int lastweekCompletedSpeechtest) {
		this.lastweekCompletedSpeechtest = lastweekCompletedSpeechtest;
	}

	public int getLastmonthUserParticipation() {
		return lastmonthUserParticipation;
	}

	public void setLastmonthUserParticipation(int lastmonthUserParticipation) {
		this.lastmonthUserParticipation = lastmonthUserParticipation;
	}

	public int getLastmonthCompletedSpeechtest() {
		return lastmonthCompletedSpeechtest;
	}

	public void setLastmonthCompletedSpeechtest(int lastmonthCompletedSpeechtest) {
		this.lastmonthCompletedSpeechtest = lastmonthCompletedSpeechtest;
	}
	
	
	
}
