package com.phr.study;

import java.io.Serializable;

public class Compliance implements Serializable{
	private String id;
	private String timeFrame;
	private int numberOfParticipationInTimeFrame;
	private int percentageToMaintain;
	private String message;
	
	public Compliance() {
		// TODO Auto-generated constructor stub
	}

	public Compliance(String id, String timeFrame,
			int numberOfParticipationInTimeFrame, int percentageToMaintain,
			String message) {
		super();
		this.id = id;
		this.timeFrame = timeFrame;
		this.numberOfParticipationInTimeFrame = numberOfParticipationInTimeFrame;
		this.percentageToMaintain = percentageToMaintain;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}

	public int getNumberOfParticipationInTimeFrame() {
		return numberOfParticipationInTimeFrame;
	}

	public void setNumberOfParticipationInTimeFrame(
			int numberOfParticipationInTimeFrame) {
		this.numberOfParticipationInTimeFrame = numberOfParticipationInTimeFrame;
	}

	public int getPercentageToMaintain() {
		return percentageToMaintain;
	}

	public void setPercentageToMaintain(int percentageToMaintain) {
		this.percentageToMaintain = percentageToMaintain;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
}
