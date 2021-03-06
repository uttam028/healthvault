package cse.mlab.hvr.client;


public class SpeechTestState {
	public enum TestState{
		START, SAVED, INCOMPLETE, COMPLETED;
	}
	
	private String studyId;
	private String testId;
	private String participationId;
	private TestState state;
	public SpeechTestState(String studyId, String testId, TestState state) {
		super();
		this.studyId = studyId;
		this.testId = testId;
		this.state = state;
		this.participationId = "";
	}
	
	
	
	public SpeechTestState(String studyId, String testId, String participationId, TestState state) {
		super();
		this.studyId = studyId;
		this.testId = testId;
		this.participationId = participationId;
		this.state = state;
	}



	public String getParticipationId() {
		return participationId;
	}
	
	public String getStudyId() {
		return studyId;
	}
	public String getTestId() {
		return testId;
	}
	public TestState getState() {
		return state;
	}
	
	
}
