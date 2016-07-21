package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

import cse.mlab.hvr.client.SpeechTestState;

public class TestCompletionEvent extends GwtEvent<TestCompletionEventHandler> {

	private SpeechTestState testState;

	public TestCompletionEvent(SpeechTestState testState) {
		super();
		this.testState = testState;
	}

	public static Type<TestCompletionEventHandler> TYPE = new Type<TestCompletionEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TestCompletionEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(TestCompletionEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterTestCompleted(this);
	}

	public SpeechTestState getTestState() {
		return testState;
	}
}
