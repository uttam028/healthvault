package cse.mlab.hvr.client.study;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.FieldSet;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Legend;
import org.gwtbootstrap3.client.ui.NavTabs;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.TabContent;
import org.gwtbootstrap3.client.ui.TabListItem;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconType;
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

	private Button acceptConsentButton, declineConsentButton, submitFormButton, backFromFaqButton, startEnrollButton;

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
		faqTab = new TabListItem("Overview");
		backFromFaqButton = new Button("Back");
		backFromFaqButton.setIcon(IconType.ARROW_LEFT);
		backFromFaqButton.setSize(ButtonSize.LARGE);
		backFromFaqButton.setType(ButtonType.DANGER);
		backFromFaqButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Hvr.getEventBus().fireEvent(new EnrollmentEvent(new EnrollmentState(EnrollmentProcess.this.enrollmentData, EnrollState.DECLINED)));
			}
		});
		
		startEnrollButton = new Button("Enroll");
		startEnrollButton.setIcon(IconType.ARROW_RIGHT);
		startEnrollButton.setIconPosition(IconPosition.RIGHT);
		startEnrollButton.setSize(ButtonSize.LARGE);
		startEnrollButton.setType(ButtonType.PRIMARY);
		startEnrollButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(healthTab.isAttached()){
					enableHealthTab();
				} else{
					enableConsentTab();
				}
			}
		});

		healthPane = new TabPane();
		healthTab = new TabListItem("Health QA");

		consentPane = new TabPane();
		consentTab = new TabListItem("Consent");

		submitFormButton = new Button("Submit");
		submitFormButton.setType(ButtonType.SUCCESS);
		submitFormButton.setPull(Pull.RIGHT);
		submitFormButton.setSize(ButtonSize.LARGE);
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
		declineConsentButton.setIcon(IconType.THUMBS_O_DOWN);
		declineConsentButton.setType(ButtonType.DANGER);
		declineConsentButton.setSize(ButtonSize.LARGE);
		//declineConsentButton.setPull(Pull.LEFT);
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
		acceptConsentButton.setIcon(IconType.THUMBS_O_UP);
		acceptConsentButton.setType(ButtonType.SUCCESS);
		acceptConsentButton.setSize(ButtonSize.LARGE);
		//acceptConsentButton.setPull(Pull.RIGHT);
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
						Window.alert("fail from server :"+ caught.getMessage());
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
			updateTabState(faqPane, faqTab, true);
		}

		if (this.enrollmentData.getHealthStatusQuestions() != null
				&& this.enrollmentData.getHealthStatusQuestions().size() > 0) {
			/*if (faqPane.isActive()) {
				faqTab.setActive(false);
				faqPane.setActive(false);

			}*/
			if(faqTab.isActive()){
				updateTabState(healthPane, healthTab, false);
			} else {
				updateTabState(healthPane, healthTab, true);
			}
			
			tabEnabled = true;
			loadHealthQAForm();
			healthTab.setDataTargetWidget(healthPane);
			tabNavigation.add(healthTab);
			tabContent.add(healthPane);
		}

		if (this.enrollmentData.getStudyOverview() != null
				&& this.enrollmentData.getStudyOverview()
						.isConsentFileAvailable()) {
			if (faqTab.isActive() || healthTab.isActive()) {
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
		try {
			updateTabState(faqPane, faqTab, false);
			faqTab.setEnabled(false);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// if(healthTab.isActive()){
		updateTabState(healthPane, healthTab, false);
		healthTab.setEnabled(true);
		// }
		updateTabState(consentPane, consentTab, true);
		loadConsentForm();
	}

	private void enableHealthTab() {
		try {
			updateTabState(faqPane, faqTab, false);
			faqTab.setEnabled(false);
		} catch (Exception e) {
			// TODO: handle exception
		}
		updateTabState(healthPane, healthTab, true);

		//loadHealthQAForm();
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
		faqPane.add(new Br());
		Heading faqHead = new Heading(HeadingSize.H3);
		faqHead.setText(enrollmentData.getStudyOverview().getName());
		faqHead.setSubText(enrollmentData.getStudyOverview().getOverview());
		faqPane.add(faqHead);
		faqPane.add(new Br());
		faqPane.add(new Br());
		
		faqPane.add(new SimpleFaqViewer(enrollmentData.getQaList(), "", true));
		faqPane.add(new Br());
		faqPane.add(new Br());
		faqPane.add(new Br());
		Row r = new Row();
		faqPane.add(r);
		Column c1 = new Column(ColumnSize.LG_6);
		Column c2 = new Column(ColumnSize.LG_6);
		r.add(c1);
		r.add(c2);
		c1.add(backFromFaqButton);
		c2.add(startEnrollButton);

//		faqPane.add(backFromFaqButton);
//		faqPane.add(startEnrollButton);
		faqPane.add(new Br());
		faqPane.add(new Br());
		faqPane.add(new Br());
		faqPane.add(new Br());
		faqPane.add(new Br());
		faqPane.add(new Br());
		faqPane.add(new Br());
		faqPane.add(new Br());
		faqPane.add(new Br());
	}

	private void loadConsentForm() {
		if (consentContenPanel == null) {
			consentContenPanel = new HTMLPanel("");
			Heading heading = new Heading(HeadingSize.H3, "Read the electronic consent form and Accept to complete enrollment process");
			consentContenPanel.add(heading);
			//consentContenPanel.addStyleName("panel_border");
			consentPane.add(new Br());
			consentPane.add(consentContenPanel);

			consentContenPanel.add(new Br());

			Frame frame = new Frame(this.enrollmentData.getStudyOverview()
					.getConsentFileName());
			frame.setHeight("750px");
			frame.setWidth("100%");
			consentContenPanel.add(frame);
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Hr());
			
			Row r = new Row();
			consentContenPanel.add(r);
			Column c1 = new Column(ColumnSize.LG_6);
			Column c2 = new Column(ColumnSize.LG_6);
			r.add(c1);
			r.add(c2);
			c1.add(declineConsentButton);
			c2.add(acceptConsentButton);
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Br());

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
			qaForm.add(new Br());
			qaForm.add(new Br());
			qaForm.add(new Br());
			qaForm.add(new Br());
			healthPane.add(qaForm);
		}

	}

}
