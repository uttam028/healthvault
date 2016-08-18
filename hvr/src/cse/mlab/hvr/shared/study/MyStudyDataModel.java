package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MyStudyDataModel implements IsSerializable{
	private StudyOverview studyOverview;
	private Compliance compliance;
	private String enrollmentDate;
	private String lastParticipationDate;
	private int totalParticipationCount;
	
	public MyStudyDataModel() {
		// TODO Auto-generated constructor stub
	}

	public MyStudyDataModel(StudyOverview studyOverview,
			Compliance compliance, String enrollmentDate,
			String lastParticipationDate, int totalParticipationCount) {
		super();
		this.studyOverview = studyOverview;
		this.compliance = compliance;
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

	public String getEnrollmentDate() {
		return enrollmentDate;
	}

	public String getLastParticipationDate() {
		return lastParticipationDate;
	}

	public int getTotalParticipationCount() {
		return totalParticipationCount;
	}
	
}
