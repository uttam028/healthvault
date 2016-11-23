package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class MicrophoneCheckEvent extends GwtEvent<MicrophoneCheckEventHandler>{
	public static Type<MicrophoneCheckEventHandler> TYPE = new Type<MicrophoneCheckEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MicrophoneCheckEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(MicrophoneCheckEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterMicrophoneCheck(this);
	}

}
