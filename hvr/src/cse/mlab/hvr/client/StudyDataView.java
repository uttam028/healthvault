package cse.mlab.hvr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.study.StudyOverview;
import cse.mlab.hvr.shared.study.StudyStatistics;

public class StudyDataView extends Composite {

	private static StudyDataViewUiBinder uiBinder = GWT
			.create(StudyDataViewUiBinder.class);

	interface StudyDataViewUiBinder extends UiBinder<Widget, StudyDataView> {
	}

	public StudyDataView(StudyOverview studyOverview, StudyStatistics studyStatistics) {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
}
