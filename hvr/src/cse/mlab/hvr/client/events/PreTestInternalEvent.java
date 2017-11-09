package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

import cse.mlab.hvr.client.PreTestState;

public class PreTestInternalEvent extends GwtEvent<PreTestInternalEventHandler> {

	private PreTestState preTestState;

	public PreTestInternalEvent(PreTestState preTestState) {
		super();
		this.preTestState = preTestState;
	}

	public static Type<PreTestInternalEventHandler> TYPE = new Type<PreTestInternalEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PreTestInternalEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(PreTestInternalEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterPreTestEvent(this);
	}

	public PreTestState getPreTestState() {
		return preTestState;
	}
}
