package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Compliance implements IsSerializable{
	private String id;
	private String timeFrame;
	private int numberOfParticipationInTimeFrame;
	private int percentageToMaintain;
}
