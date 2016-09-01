package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class FileUploadEvent extends GwtEvent<FileUploadEventHandler>{
	

	public static Type<FileUploadEventHandler> TYPE = new Type<FileUploadEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<FileUploadEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(FileUploadEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterFileUpload(this);
	}

}
