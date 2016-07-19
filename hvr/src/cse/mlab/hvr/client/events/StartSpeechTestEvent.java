package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class StartSpeechTestEvent extends
		GwtEvent<StartSpeechTestEventHandler> {
	private String testId = "";

	public StartSpeechTestEvent(String testId) {
		this.testId = testId;
	}

	public static Type<StartSpeechTestEventHandler> TYPE = new Type<StartSpeechTestEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<StartSpeechTestEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(StartSpeechTestEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionToStartSpeechTest(this);
	}

	public String getTestId() {
		return testId;
	}
}
