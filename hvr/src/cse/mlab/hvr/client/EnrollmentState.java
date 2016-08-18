package cse.mlab.hvr.client;

import cse.mlab.hvr.shared.study.StudyPrefaceModel;

public class EnrollmentState {
	public enum EnrollState{
		START, DECLINED, FAILURE, SUCCESS;
	}

	private StudyPrefaceModel study;
	private EnrollState state;
	public EnrollmentState(StudyPrefaceModel study, EnrollState state) {
		super();
		this.study = study;
		this.state = state;
	}
	public StudyPrefaceModel getStudy() {
		return study;
	}
	public EnrollState getState() {
		return state;
	}
	
}
