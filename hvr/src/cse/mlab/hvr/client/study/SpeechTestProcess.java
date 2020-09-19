package cse.mlab.hvr.client.study;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.CustomPlayerManager;
import cse.mlab.hvr.client.Hvr;
import cse.mlab.hvr.client.TestInterceptState;
import cse.mlab.hvr.client.TestInterceptState.InterceptState;
import cse.mlab.hvr.client.events.TestProcessInterceptionEvent;
import cse.mlab.hvr.client.services.SpeechService;
import cse.mlab.hvr.client.services.SpeechServiceAsync;
import cse.mlab.hvr.shared.study.SpeechTest;

public class SpeechTestProcess extends Composite {
	
	@UiField
	HTMLPanel testPanel;
	
	@UiField
	Button buttonTryLater;	

	private final SpeechServiceAsync service = GWT.create(SpeechService.class);

	private static SpeechTestProcessUiBinder uiBinder = GWT
			.create(SpeechTestProcessUiBinder.class);

	interface SpeechTestProcessUiBinder extends
			UiBinder<Widget, SpeechTestProcess> {
	}

	public SpeechTestProcess(final String studyId, String testId, final String participationId) {
		initWidget(uiBinder.createAndBindUi(this));
		buttonTryLater.setIcon(IconType.CLOSE);
		buttonTryLater.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Hvr.getEventBus().fireEvent(
						new TestProcessInterceptionEvent(new TestInterceptState(studyId,
								InterceptState.START)));

			}
		});
		service.getSpeechTestMetadata(testId, new AsyncCallback<SpeechTest>() {
			
			@Override
			public void onSuccess(SpeechTest result) {
				// TODO Auto-generated method stub
				testPanel.clear();
				testPanel.add(CustomPlayerManager.getInstance());
				//testPanel.add(new CustomPlayerManager(studyId, result));
				CustomPlayerManager.getInstance().startPlayer(studyId, result, participationId);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}


}
