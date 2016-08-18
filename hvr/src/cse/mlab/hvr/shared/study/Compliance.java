package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Compliance implements IsSerializable{
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
	
	
}