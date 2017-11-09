package cse.mlab.hvr.client;

public class PreTestState {
	public enum InternalState {
		BASIC, VOLUME, MICROPHONE, QUESTION;
	}

	private InternalState state;

	public PreTestState(InternalState state) {
		super();
		this.state = state;
	}

	public InternalState getState() {
		return state;
	}
}
