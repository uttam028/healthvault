package cse.mlab.hvr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WebrtcPlayer extends Composite {

	private static WebrtcPlayerUiBinder uiBinder = GWT
			.create(WebrtcPlayerUiBinder.class);

	interface WebrtcPlayerUiBinder extends UiBinder<Widget, WebrtcPlayer> {
	}

	public WebrtcPlayer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public WebrtcPlayer(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}

class MediaRecorder extends JavaScriptObject {

}