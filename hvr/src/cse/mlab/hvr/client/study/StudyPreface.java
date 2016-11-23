package cse.mlab.hvr.client.study;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Paragraph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.EnrollmentState;
import cse.mlab.hvr.client.EnrollmentState.EnrollState;
import cse.mlab.hvr.client.Hvr;
import cse.mlab.hvr.client.SimpleFaqViewer;
import cse.mlab.hvr.client.events.EnrollmentEvent;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

public class StudyPreface extends Composite {
	
	//@UiField
	//PanelHeader spHeader;
	@UiField
	Heading spHeading;
	//@UiField
	//Paragraph spDescription;
	@UiField
	Button enrollButton;
	
	//@UiField HTMLPanel spQuestionAnswerList;
	
	private StudyPrefaceModel studyPrefaceModel;

	private static StudyPrefaceUiBinder uiBinder = GWT
			.create(StudyPrefaceUiBinder.class);

	interface StudyPrefaceUiBinder extends UiBinder<Widget, StudyPreface> {
	}

	public StudyPreface(StudyPrefaceModel studyPrefaceModel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.studyPrefaceModel = studyPrefaceModel;
		//spHeader.setColor("red");
		spHeading.setText(this.studyPrefaceModel.getStudyOverview().getName());
		//spDescription.setText(this.studyPrefaceModel.getStudyOverview().getOverview());
		//spQuestionAnswerList.add(new QuestionAnswerList(this.studyPrefaceModel.getQaList()));
//		spQuestionAnswerList.add(new SimpleFaqViewer(this.studyPrefaceModel.getQaList(), "Learn More", false));
		enrollButton.setIcon(IconType.INFO_CIRCLE);
		enrollButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
//				Hvr.getEventBus().fireEvent(new StartSpeechTestEvent(TestPreface.this.testPrefaceModel.getId()));
				Hvr.getEventBus().fireEvent(new EnrollmentEvent(new EnrollmentState(StudyPreface.this.studyPrefaceModel, EnrollState.START)));
			}
		});
		
	}
	
	public StudyPrefaceModel getStudyPrefaceModel() {
		return studyPrefaceModel;
	}

}
