package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface TestCompletionEventHandler extends EventHandler {
	public void actionAfterTestCompleted(TestCompletionEvent event);

}
