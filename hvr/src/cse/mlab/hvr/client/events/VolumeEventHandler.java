package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface VolumeEventHandler extends EventHandler{
	public void actionAfterVolumeCheck(VolumeCheckEvent event);
}
