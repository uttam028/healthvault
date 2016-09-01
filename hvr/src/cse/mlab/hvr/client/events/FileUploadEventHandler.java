package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface FileUploadEventHandler extends EventHandler{

	public void actionAfterFileUpload(FileUploadEvent event);
}
