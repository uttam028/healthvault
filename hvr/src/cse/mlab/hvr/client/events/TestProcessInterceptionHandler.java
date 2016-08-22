package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface TestProcessInterceptionHandler extends EventHandler {
	public void actionAfterTestInterception(TestProcessInterceptionEvent event);

}