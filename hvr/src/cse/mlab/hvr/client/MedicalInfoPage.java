package cse.mlab.hvr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class MedicalInfoPage extends Composite {
	
	@UiField
	HTMLPanel resultPanel;
	
	@UiField
	HTMLPanel diagnosisPanel, medicationPanel;

	private static MedicalInfoPageUiBinder uiBinder = GWT
			.create(MedicalInfoPageUiBinder.class);

	interface MedicalInfoPageUiBinder extends UiBinder<Widget, MedicalInfoPage> {
	}

	public MedicalInfoPage() {
		initWidget(uiBinder.createAndBindUi(this));
		diagnosisPanel.add(new DiagnosisHistory(MainPage.getLoggedinUser()));
		medicationPanel.add(new Medications(MainPage.getLoggedinUser()));
	}

	
}
