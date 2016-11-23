package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface MicrophoneCheckEventHandler extends EventHandler{
	public void actionAfterMicrophoneCheck(MicrophoneCheckEvent event);
}