package cse.mlab.hvr.client.study;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.CustomPlayerManager;
import cse.mlab.hvr.client.GreetingService;
import cse.mlab.hvr.client.GreetingServiceAsync;
import cse.mlab.hvr.shared.study.SpeechTestMetadata;

public class SpeechTestProcess extends Composite {
	
	@UiField
	HTMLPanel testPanel;

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private static SpeechTestProcessUiBinder uiBinder = GWT
			.create(SpeechTestProcessUiBinder.class);

	interface SpeechTestProcessUiBinder extends
			UiBinder<Widget, SpeechTestProcess> {
	}

	public SpeechTestProcess(String testId) {
		initWidget(uiBinder.createAndBindUi(this));
		greetingService.getSpeechTestMetadata(testId, new AsyncCallback<SpeechTestMetadata>() {
			
			@Override
			public void onSuccess(SpeechTestMetadata result) {
				// TODO Auto-generated method stub
				testPanel.add(new CustomPlayerManager(result));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}


}
