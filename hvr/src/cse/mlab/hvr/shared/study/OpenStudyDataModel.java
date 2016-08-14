package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OpenStudyDataModel implements IsSerializable{
	private StudyOverview studyOverview;
	private String enrollmentDate;
	private String lastParticipationDate;
	private int totalParticipationCount;
}
