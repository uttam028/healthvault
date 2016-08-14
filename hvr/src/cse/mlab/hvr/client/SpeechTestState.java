package cse.mlab.hvr.client;

public class SpeechTestState {
	public enum TestState{
		START, DECLINED, SAVED, COMPLETED;
	}
	
	private String testId;
	private TestState state;
	
	public SpeechTestState(String testId, TestState state) {
		super();
		this.testId = testId;
		this.state = state;
	}

	public String getTestId() {
		return testId;
	}

	public TestState getState() {
		return state;
	}
	
}
