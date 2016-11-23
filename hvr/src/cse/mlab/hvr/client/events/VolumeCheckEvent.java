package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class VolumeCheckEvent extends GwtEvent<VolumeEventHandler>{
	public static Type<VolumeEventHandler> TYPE = new Type<VolumeEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<VolumeEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(VolumeEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterVolumeCheck(this);
	}

}
