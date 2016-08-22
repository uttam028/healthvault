package cse.mlab.hvr.client;

public class TestInterceptState {
	
	public enum InterceptState{
		START, YES, NO;
	}
	private String studyId;
	private InterceptState state;
	public TestInterceptState(String studyId, InterceptState state) {
		super();
		this.studyId = studyId;
		this.state = state;
	}
	public String getStudyId() {
		return studyId;
	}
	public InterceptState getState() {
		return state;
	}
	
	
}
