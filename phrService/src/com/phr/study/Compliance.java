package com.phr.study;

import java.io.Serializable;

public class Compliance implements Serializable{
	private String id;
	private String timeFrame;
	private int numberOfParticipationInTimeFrame;
	private int percentageToMaintain;
	
	public Compliance() {
		// TODO Auto-generated constructor stub
	}

	public Compliance(String id, String timeFrame,
			int numberOfParticipationInTimeFrame, int percentageToMaintain) {
		super();
		this.id = id;
		this.timeFrame = timeFrame;
		this.numberOfParticipationInTimeFrame = numberOfParticipationInTimeFrame;
		this.percentageToMaintain = percentageToMaintain;
	}

	public String getId() {
		return id;
	}

	public String getTimeFrame() {
		return timeFrame;
	}

	public int getNumberOfParticipationInTimeFrame() {
		return numberOfParticipationInTimeFrame;
	}

	public int getPercentageToMaintain() {
		return percentageToMaintain;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}

	public void setNumberOfParticipationInTimeFrame(
			int numberOfParticipationInTimeFrame) {
		this.numberOfParticipationInTimeFrame = numberOfParticipationInTimeFrame;
	}

	public void setPercentageToMaintain(int percentageToMaintain) {
		this.percentageToMaintain = percentageToMaintain;
	}
	
	
}
