package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.html.Paragraph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.events.StartSpeechTestEvent;
import cse.mlab.hvr.shared.TestPrefaceModel;

public class TestPreface extends Composite {
	
	@UiField
	PanelHeader tpHeader;
	@UiField
	Heading tpHeading;
	@UiField
	Paragraph tpDescription;
	@UiField
	Button tpStartButton;
	
	@UiField HTMLPanel tpQuestionAnswerList;
	
	private TestPrefaceModel testPrefaceModel;

	private static TestPrefaceUiBinder uiBinder = GWT
			.create(TestPrefaceUiBinder.class);

	interface TestPrefaceUiBinder extends UiBinder<Widget, TestPreface> {
	}

	public TestPreface(TestPrefaceModel testPrefaceModel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.testPrefaceModel = testPrefaceModel;
		tpHeader.setColor("red");
		tpHeading.setText(this.testPrefaceModel.getDisplayName());
		tpDescription.setText(this.testPrefaceModel.getDescription());
		tpQuestionAnswerList.add(new QuestionAnswerList(this.testPrefaceModel.getQaList()));
		tpStartButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
//				Hvr.getEventBus().fireEvent(new StartSpeechTestEvent(TestPreface.this.testPrefaceModel.getId()));
				Hvr.getEventBus().fireEvent(new StartSpeechTestEvent("1"));
			}
		});
	}


}
