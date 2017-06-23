package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class PreTestSubmitEvent extends GwtEvent<PreTestSubmitEventHandler>{
	public static Type<PreTestSubmitEventHandler> TYPE = new Type<PreTestSubmitEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PreTestSubmitEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(PreTestSubmitEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterPreTestSubmit(this);
	}

}
