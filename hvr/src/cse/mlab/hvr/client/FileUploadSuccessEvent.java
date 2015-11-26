package cse.mlab.hvr.client;

import com.google.gwt.event.shared.GwtEvent;

public class FileUploadSuccessEvent extends GwtEvent<FileUploadSuccessEventHandler>{

	public static Type<FileUploadSuccessEventHandler> TYPE = new Type<FileUploadSuccessEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<FileUploadSuccessEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(FileUploadSuccessEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterFileUpload(this);
	}

}
