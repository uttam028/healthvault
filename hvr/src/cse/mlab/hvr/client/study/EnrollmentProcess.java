package cse.mlab.hvr.client.study;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FieldSet;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Legend;
import org.gwtbootstrap3.client.ui.NavTabs;
import org.gwtbootstrap3.client.ui.TabContent;
import org.gwtbootstrap3.client.ui.TabListItem;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Hr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.EnrollmentState;
import cse.mlab.hvr.client.EnrollmentState.EnrollState;
import cse.mlab.hvr.client.GreetingService;
import cse.mlab.hvr.client.GreetingServiceAsync;
import cse.mlab.hvr.client.Hvr;
import cse.mlab.hvr.client.MainPage;
import cse.mlab.hvr.client.SimpleFaqViewer;
import cse.mlab.hvr.client.events.EnrollmentEvent;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.study.HealthStatusQuestion;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

public class EnrollmentProcess extends Composite {
	@UiField
	HTMLPanel testProcessPanel;

	private HTMLPanel consentContenPanel;

	private NavTabs tabNavigation;
	private TabContent tabContent;

	private TabPane faqPane, healthPane, consentPane;
	private TabListItem faqTab, healthTab, consentTab;

	private Button acceptConsentButton, declineConsentButton, submitFormButton;

	// private String testId;
	// private SpeechTestMetadata metadata;
	private StudyPrefaceModel enrollmentData;

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private static EnrollmentProcessUiBinder uiBinder = GWT
			.create(EnrollmentProcessUiBinder.class);

	interface EnrollmentProcessUiBinder extends
			UiBinder<Widget, EnrollmentProcess> {
	}

	public EnrollmentProcess(StudyPrefaceModel enrollmentData) {
		initWidget(uiBinder.createAndBindUi(this));
		this.enrollmentData = enrollmentData;
		tabNavigation = new NavTabs();
		tabContent = new TabContent();
		testProcessPanel.add(tabNavigation);
		testProcessPanel.add(tabContent);

		faqPane = new TabPane();
		faqTab = new TabListItem("FAQ");

		healthPane = new TabPane();
		healthTab = new TabListItem("Health QA");

		consentPane = new TabPane();
		consentTab = new TabListItem("Consent");

		submitFormButton = new Button("Submit");
		submitFormButton.setType(ButtonType.SUCCESS);
		submitFormButton.setPull(Pull.RIGHT);
		submitFormButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (consentTab.isAttached()) {
					enableConsentTab();
				}
			}
		});

		declineConsentButton = new Button("Decline");
		declineConsentButton.setType(ButtonType.DANGER);
		// declineConsentButton.setSize(ButtonSize.LARGE);
		declineConsentButton.setPull(Pull.LEFT);
		declineConsentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// Hvr.getEventBus().fireEvent(
				// new TestCompletionEvent(new SpeechTestState(
				// EnrollmentProcess.this.testId,
				// TestState.DECLINED)));
				Hvr.getEventBus().fireEvent(new EnrollmentEvent(new EnrollmentState(EnrollmentProcess.this.enrollmentData, EnrollState.DECLINED)));
			}
		});

		acceptConsentButton = new Button("Accept");
		acceptConsentButton.setType(ButtonType.SUCCESS);
		// acceptConsentButton.setSize(ButtonSize.LARGE);
		acceptConsentButton.setPull(Pull.RIGHT);
		acceptConsentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// if (healthTab.isAttached()) {
				// enableHealthTab();
				// } else {
				// enableTestTab();
				// }
				String studyId = EnrollmentProcess.this.enrollmentData.getStudyOverview().getId();
				String email = MainPage.getLoggedinUser();
				greetingService.enrollToStudy(studyId, email, new AsyncCallback<Response>() {
					
					@Override
					public void onSuccess(Response result) {
						// TODO Auto-generated method stub
						if(result.getCode() == 0){
							Hvr.getEventBus().fireEvent(new EnrollmentEvent(new EnrollmentState(EnrollmentProcess.this.enrollmentData, EnrollState.SUCCESS)));
						}else {
							Hvr.getEventBus().fireEvent(new EnrollmentEvent(new EnrollmentState(EnrollmentProcess.this.enrollmentData, EnrollState.FAILURE)));
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Hvr.getEventBus().fireEvent(new EnrollmentEvent(new EnrollmentState(EnrollmentProcess.this.enrollmentData, EnrollState.FAILURE)));
					}
				});
			}
		});

		// greetingService.getSpeechTestMetadata(testId,
		// new AsyncCallback<SpeechTestMetadata>() {
		//
		// @Override
		// public void onSuccess(SpeechTestMetadata result) {
		// // TODO Auto-generated method stub
		// // playerTab.add(new CustomPlayerManager(result));
		// metadata = result;
		// loadProcess(result);
		// }
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// // TODO Auto-generated method stub
		// Window.alert("Fail to load the player, please try again");
		// }
		// });
		loadProcess();
	}

	private void loadProcess() {

		boolean tabEnabled = false;

		if (this.enrollmentData.getQaList() != null
				&& this.enrollmentData.getQaList().size() > 0) {
			updateTabState(faqPane, faqTab, true);
			loadFaq();
			tabEnabled = true;
			faqTab.setDataTargetWidget(faqPane);
			tabNavigation.add(faqTab);
			tabContent.add(faqPane);
		}

		if (this.enrollmentData.getHealthStatusQuestions() != null
				&& this.enrollmentData.getHealthStatusQuestions().size() > 0) {
			if (faqPane.isActive()) {
				faqTab.setActive(false);
				faqPane.setActive(false);

			}
			updateTabState(healthPane, healthTab, true);
			tabEnabled = true;
			loadHealthQAForm();
			healthTab.setDataTargetWidget(healthPane);
			tabNavigation.add(healthTab);
			tabContent.add(healthPane);
		}

		if (this.enrollmentData.getStudyOverview() != null
				&& this.enrollmentData.getStudyOverview()
						.isConsentFileAvailable()) {
			if (healthTab.isActive()) {
				updateTabState(consentPane, consentTab, false);
			} else {
				faqTab.setActive(false);
				faqPane.setActive(false);

				updateTabState(consentPane, consentTab, true);

				loadConsentForm();
			}

			tabEnabled = true;
			consentTab.setDataTargetWidget(consentPane);
			tabNavigation.add(consentTab);
			tabContent.add(consentPane);

		}

		// if (metadata.getSubTests() != null && metadata.getSubTests().size() >
		// 0) {
		// if (tabEnabled) {
		// testPane.setActive(false);
		// testTab.setActive(false);
		// testTab.setEnabled(false);
		// } else {
		// testPane.setActive(true);
		// testTab.setActive(true);
		// testTab.setEnabled(true);
		// tabEnabled = true;
		//
		// testPane.add(new CustomPlayerManager(metadata));
		// }
		// testTab.setDataTargetWidget(testPane);
		// tabNavigation.add(testTab);
		// tabContent.add(testPane);
		// }

	}

	private void updateTabState(TabPane tabPane, TabListItem tab, boolean enable) {
		tabPane.setActive(enable);
		tab.setActive(enable);
		tab.setEnabled(enable);
	}

	private void enableConsentTab() {
		// if(healthTab.isActive()){
		updateTabState(healthPane, healthTab, false);
		healthTab.setEnabled(true);
		// }
		updateTabState(consentPane, consentTab, true);
		loadConsentForm();
	}

	private void enableHealthTab() {
		updateTabState(healthPane, healthTab, true);

		loadHealthQAForm();
	}

	// private void enableTestTab() {
	// if (consentTab.isAttached()) {
	// updateTabState(consentPane, consentTab, false);
	// }
	// if (healthTab.isAttached()) {
	// updateTabState(healthPane, healthTab, false);
	// }
	// updateTabState(testPane, testTab, true);
	//
	// testPane.add(new CustomPlayerManager(metadata));
	//
	// }
	private void loadFaq() {
		faqPane.add(new SimpleFaqViewer(enrollmentData.getQaList(), "", true));
	}

	private void loadConsentForm() {
		if (consentContenPanel == null) {
			consentContenPanel = new HTMLPanel("Electronic Consent Form");
			consentContenPanel
					.addStyleName("col-lg-offset-1 col-lg-10 panel_border");
			consentPane.add(new Br());
			consentPane.add(consentContenPanel);

			consentContenPanel.add(new Br());

			Frame frame = new Frame(this.enrollmentData.getStudyOverview()
					.getConsentFileName());
			frame.setHeight("450px");
			frame.setWidth("100%");
			consentContenPanel.add(frame);
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Hr());
			consentContenPanel.add(declineConsentButton);
			consentContenPanel.add(acceptConsentButton);

		}
	}

	private void loadHealthQAForm() {
		ArrayList<HealthStatusQuestion> healthQuestions = this.enrollmentData
				.getHealthStatusQuestions();
		if (healthQuestions != null) {
			healthPane.add(new Br());
			healthPane.add(new Br());
			Form qaForm = new Form();
			qaForm.addStyleName("col-lg-offset-2 col-lg-8");
			FieldSet qaFieldSet = new FieldSet();
			qaForm.add(qaFieldSet);
			Legend formLegend = new Legend("Please answer below questions:");
			qaFieldSet.add(formLegend);
			for (int i = 0; i < healthQuestions.size(); i++) {
				FormGroup formGroup = new FormGroup();
				TextBox answerBox = new TextBox();
				FormLabel questionLabel = new FormLabel();
				questionLabel.setText((i + 1) + ". "
						+ healthQuestions.get(i).getQuestion());
				questionLabel.setPull(Pull.LEFT);
				formGroup.add(questionLabel);
				formGroup.add(new Br());
				formGroup.add(answerBox);
				qaFieldSet.add(formGroup);
			}
			qaForm.add(new Br());
			qaForm.add(submitFormButton);
			healthPane.add(qaForm);
		}

	}

}
