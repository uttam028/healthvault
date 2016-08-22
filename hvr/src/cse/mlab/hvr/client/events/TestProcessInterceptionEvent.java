package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

import cse.mlab.hvr.client.TestInterceptState;

public class TestProcessInterceptionEvent extends
		GwtEvent<TestProcessInterceptionHandler> {
	private TestInterceptState interceptState;

	public TestProcessInterceptionEvent(TestInterceptState interceptState) {
		super();
		this.interceptState = interceptState;
	}

	public static Type<TestProcessInterceptionHandler> TYPE = new Type<TestProcessInterceptionHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TestProcessInterceptionHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(TestProcessInterceptionHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterTestInterception(this);
	}

	public TestInterceptState getInterceptState() {
		return interceptState;
	}
}
