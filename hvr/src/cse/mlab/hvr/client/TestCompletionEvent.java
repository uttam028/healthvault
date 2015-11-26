package cse.mlab.hvr.client;

import com.google.gwt.event.shared.GwtEvent;

public class TestCompletionEvent extends GwtEvent<TestCompletionEventHandler>{
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

}
