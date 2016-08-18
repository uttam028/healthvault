package cse.mlab.hvr.client.study;

import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.Hvr;
import cse.mlab.hvr.client.SpeechTestState;
import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.shared.study.MyStudyDataModel;

public class MyStudy extends Composite {
	
	@UiField
	Button participateButton;

	private MyStudyDataModel dataModel;
	private static MyStudyUiBinder uiBinder = GWT.create(MyStudyUiBinder.class);

	interface MyStudyUiBinder extends UiBinder<Widget, MyStudy> {
	}

	public MyStudy(MyStudyDataModel myStudyDataModel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dataModel = myStudyDataModel;
		participateButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Hvr.getEventBus().fireEvent(new SpeechTestEvent(new SpeechTestState(dataModel.getStudyOverview().getSpeechTestId(), 
						TestState.START)));
			}
		});
	}

}
