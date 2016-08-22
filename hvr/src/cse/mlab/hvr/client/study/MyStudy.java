package cse.mlab.hvr.client.study;

import java.util.Date;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Hr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.Hvr;
import cse.mlab.hvr.client.SimpleFaqViewer;
import cse.mlab.hvr.client.SpeechTestState;
import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.shared.study.MyStudyDataModel;

public class MyStudy extends Composite {
	
	@UiField
	Button participateButton;
	
	@UiField
	HTMLPanel statusPanel, aboutPanel, statusContentPanel, aboutContentPanel;
	
	@UiField
	HTMLPanel headerPanel;
	
	@UiField
	Heading studyHeading;
	@UiField
	Column statusColumn, aboutColumn;
	
	Label enrollmentDateLabel, numberOfParticipationLabel, lastParticipationLabel, complianceMessage;

	private MyStudyDataModel dataModel;
	private static MyStudyUiBinder uiBinder = GWT.create(MyStudyUiBinder.class);

	interface MyStudyUiBinder extends UiBinder<Widget, MyStudy> {
	}

	public MyStudy(MyStudyDataModel myStudyDataModel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dataModel = myStudyDataModel;
		studyHeading.setText(myStudyDataModel.getStudyOverview().getName());
		if(myStudyDataModel.getStudyOverview().getOverview() != null){
			studyHeading.setSubText(myStudyDataModel.getStudyOverview().getOverview());
			headerPanel.add(new Br());
		} else {
			headerPanel.add(new Br());
		}
		//aboutContentPanel.add(new SimpleFaqViewer(myStudyDataModel., title));
		
		statusContentPanel.setVisible(true);
		aboutContentPanel.setVisible(false);
		participateButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Hvr.getEventBus().fireEvent(new SpeechTestEvent(new SpeechTestState(dataModel.getStudyOverview().getId(),
						dataModel.getStudyOverview().getSpeechTestId(), 
						TestState.START)));
			}
		});
		
		
		enrollmentDateLabel = new Label();
		numberOfParticipationLabel = new Label();
		lastParticipationLabel = new Label();
		complianceMessage = new Label();
		updateStudyLabels();
		
		statusContentPanel.add(new Hr());
		statusContentPanel.add(new Br());
		statusContentPanel.add(enrollmentDateLabel);
		statusContentPanel.add(numberOfParticipationLabel);
		statusContentPanel.add(new Br());
		statusContentPanel.add(lastParticipationLabel);
		statusContentPanel.add(new Br());
		statusContentPanel.add(complianceMessage);
		statusContentPanel.add(new Br());
		
		
		aboutContentPanel.add(new SimpleFaqViewer(myStudyDataModel.getQaList(), ""));
		
		
		statusPanel.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				statusContentPanel.setVisible(true);
				aboutContentPanel.setVisible(false);
				statusColumn.getElement().getStyle().setBackgroundColor("#67e4de");
				aboutColumn.getElement().getStyle().setBackgroundColor("white");
			}
		}, ClickEvent.getType());
		
		aboutPanel.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				statusContentPanel.setVisible(false);
				aboutContentPanel.setVisible(true);
				statusColumn.getElement().getStyle().setBackgroundColor("white");
				aboutColumn.getElement().getStyle().setBackgroundColor("#67e4de");
			}
		}, ClickEvent.getType());
		
	}
	
	protected MyStudyDataModel getStudyModel() {
		return this.dataModel;
	}
	
	public void updateDataModel(String studyId){
		if(this.dataModel.getStudyOverview().getId().equals(studyId)){
			this.dataModel.setTotalParticipationCount(this.dataModel.getTotalParticipationCount() + 1);
			Date today = new Date();
			this.dataModel.setLastParticipationDate(DateTimeFormat.getFormat("yyyy-MM-dd").format(today));
			updateStudyLabels();
		}
	}
	
	private void updateStudyLabels(){
		if(this.dataModel.getEnrollmentDate() == null || this.dataModel.getEnrollmentDate().isEmpty()){
			enrollmentDateLabel.setText("Enrollment Date : N/A");
		} else {
			enrollmentDateLabel.setText("Enrollment Date : "+ this.dataModel.getEnrollmentDate());
		}
		numberOfParticipationLabel.setText("Total Particiaption : "+ this.dataModel.getTotalParticipationCount());
		if(this.dataModel.getLastParticipationDate()==null || this.dataModel.getLastParticipationDate().isEmpty()){
			lastParticipationLabel.setText("Last Participation : N/A");
		} else {
			lastParticipationLabel.setText("Last Participation : "+ this.dataModel.getLastParticipationDate());
		}

		complianceMessage.setText("A message related to compliance");
	}

}
