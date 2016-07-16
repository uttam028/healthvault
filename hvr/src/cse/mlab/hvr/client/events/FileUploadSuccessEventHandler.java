package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface FileUploadSuccessEventHandler extends EventHandler{

	public void actionAfterFileUpload(FileUploadSuccessEvent event);
}
