package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Compliance implements IsSerializable{
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


	public String getTimeFrame() {
		return timeFrame;
	}


	public int getNumberOfParticipationInTimeFrame() {
		return numberOfParticipationInTimeFrame;
	}

	public int getPercentageToMaintain() {
		return percentageToMaintain;
	}

	public String getMessage() {
		return message;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}

	public void setNumberOfParticipationInTimeFrame(int numberOfParticipationInTimeFrame) {
		this.numberOfParticipationInTimeFrame = numberOfParticipationInTimeFrame;
	}

	public void setPercentageToMaintain(int percentageToMaintain) {
		this.percentageToMaintain = percentageToMaintain;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
