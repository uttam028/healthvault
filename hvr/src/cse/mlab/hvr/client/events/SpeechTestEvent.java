package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

import cse.mlab.hvr.client.SpeechTestState;

public class SpeechTestEvent extends GwtEvent<SpeechTestEventHandler> {

	private SpeechTestState testState;

	public SpeechTestEvent(SpeechTestState testState) {
		super();
		this.testState = testState;
	}

	public static Type<SpeechTestEventHandler> TYPE = new Type<SpeechTestEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SpeechTestEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(SpeechTestEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterTestEvent(this);
	}

	public SpeechTestState getTestState() {
		return testState;
	}
}
