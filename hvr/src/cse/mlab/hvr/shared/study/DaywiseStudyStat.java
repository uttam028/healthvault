package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DaywiseStudyStat implements IsSerializable{

	private String dataDate;
	private int enrollCount;
	private int participationCount;
	private int overallEnrollCount;
	private int overallParticipationCount;
	
	public DaywiseStudyStat() {
		// TODO Auto-generated constructor stub
	}

	public DaywiseStudyStat(String dataDate, int enrollCount,
			int participationCount, int overallEnrollCount,
			int overallParticipationCount) {
		super();
		this.dataDate = dataDate;
		this.enrollCount = enrollCount;
		this.participationCount = participationCount;
		this.overallEnrollCount = overallEnrollCount;
		this.overallParticipationCount = overallParticipationCount;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public int getEnrollCount() {
		return enrollCount;
	}

	public void setEnrollCount(int enrollCount) {
		this.enrollCount = enrollCount;
	}

	public int getParticipationCount() {
		return participationCount;
	}

	public void setParticipationCount(int participationCount) {
		this.participationCount = participationCount;
	}

	public int getOverallEnrollCount() {
		return overallEnrollCount;
	}

	public void setOverallEnrollCount(int overallEnrollCount) {
		this.overallEnrollCount = overallEnrollCount;
	}

	public int getOverallParticipationCount() {
		return overallParticipationCount;
	}

	public void setOverallParticipationCount(int overallParticipationCount) {
		this.overallParticipationCount = overallParticipationCount;
	}
	
	
}
