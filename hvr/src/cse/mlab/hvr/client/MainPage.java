package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.AnchorListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sun.java.swing.plaf.windows.resources.windows;

import cse.mlab.hvr.client.EnrollmentState.EnrollState;
import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.TestInterceptState.InterceptState;
import cse.mlab.hvr.client.events.EnrollmentEvent;
import cse.mlab.hvr.client.events.EnrollmentEventHandler;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.client.events.SpeechTestEventHandler;
import cse.mlab.hvr.client.events.TestProcessInterceptionEvent;
import cse.mlab.hvr.client.study.EnrollmentProcess;
import cse.mlab.hvr.client.study.SpeechTestProcess;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.study.StudyOverview;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

public class MainPage extends Composite {

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	@UiField
	AnchorListItem homeAnchor, profileAnchor, signoutAnchor;

	@UiField
	HTMLPanel mainPageContentPanel;

	@UiField
	HTMLPanel mainpagePanel;

	Hvr application;

	// ProfilePageOld profilePage;
	// VoicePage voicePage;
	HomePage homePage;
	ProfilePage profilePage;

	private static String userId;
	private String sessionId;

	//	private String firstName = "";
//	private String lastName = "";
//	private boolean profileUpdated = false;
//	private static String currentPage = "main";

	private final static String surveyUrl = "https://docs.google.com/forms/d/e/1FAIpQLSclWU3XZ2ZaWDgbqVaMgXBPhrHZMDx3-vRUgroGj0bgW-k8tA/viewform";

	private static MainPageUiBinder uiBinder = GWT
			.create(MainPageUiBinder.class);

	interface MainPageUiBinder extends UiBinder<Widget, MainPage> {
	}

	public MainPage(Hvr application, String email, String sessionId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
		userId = email;
		this.sessionId = sessionId;
		// updateUserName();
		// profilePage = new ProfilePage(this, this.userId);

		homePage = new HomePage(this.application, userId);
		profilePage = new ProfilePage();

		Hvr.getEventBus().addHandler(EnrollmentEvent.TYPE,
				new EnrollmentEventHandler() {

					@Override
					public void actionForEnrollment(EnrollmentEvent event) {
						// TODO Auto-generated method stub
						if (event.getState().getState() == EnrollState.START) {
							loadEnrollmentProcess(event.getState().getStudy());
						} else {
							updateHomePageAfterEnrollment(event);
						}
					}
				});

		Hvr.getEventBus().addHandler(SpeechTestEvent.TYPE,
				new SpeechTestEventHandler() {

					@Override
					public void actionAfterTestEvent(final SpeechTestEvent event) {
						Hvr.updateSpeechTestState(false);
						if (event.getTestState().getState() == TestState.START) {

							greetingService.startParticipation(event
									.getTestState().getStudyId(), userId,
									new AsyncCallback<Response>() {

										@Override
										public void onSuccess(Response result) {
											if (result.getCode() == 0) {
												loadSpeechTestProcess(event
														.getTestState()
														.getStudyId(), event
														.getTestState()
														.getTestId());
												Hvr.updateSpeechTestState(true);
												// History.newItem("speechtest/"
												// +
												// event.getTestState().getStudyId()+
												// "/" +
												// event.getTestState().getTestId());
											} else {
												homePage.displayMessage(
														"Unable to load the study. Please try later.",
														"", false,
														"message_error");
											}
										}

										@Override
										public void onFailure(Throwable caught) {
											homePage.displayMessage(
													"Unable to load the study. Please try later.",
													"", false, "message_error");
										}
									});

						} else if (event.getTestState().getState() == TestState.COMPLETED) {
							// https://docs.google.com/forms/d/e/1FAIpQLSclWU3XZ2ZaWDgbqVaMgXBPhrHZMDx3-vRUgroGj0bgW-k8tA/viewform
							homePage.displayMessage(
									"Thank you for your participation to the study. Please fill up the survey to help us to improve your experience.",
									surveyUrl, false,
									"message_participation_completed");
							homePage.updateParticipation(event.getTestState()
									.getStudyId());
							loadHomePage();
							greetingService.endParticipation(event
									.getTestState().getStudyId(), userId,
									new AsyncCallback<Response>() {
										@Override
										public void onSuccess(Response result) {
										}

										@Override
										public void onFailure(Throwable caught) {
										}
									});
						} else {
							homePage.displayMessage(
									"Please fill up the survey to help us to improve your experience.",
									surveyUrl, false,
									"message_participation_incomplete");
							loadHomePage();
						}
					}
				});
		// homeAnchorClicked(null);
		loadHomePage();
	}

	protected void onLoad() {
		// homeAnchorClicked(null);

	}

	@UiHandler("homeAnchor")
	void homeAnchorClicked(ClickEvent event) {

		if (Hvr.isSpeechTestRunning()) {
			interceptNavigation("logout");
		} else {

			if (History.getToken().equalsIgnoreCase("home")) {
				loadHomePage();
			} else {
				History.newItem("home");
			}
			// Window.alert("home button clicked...");
		}
	}

	protected void updateHomePageAfterEnrollment(EnrollmentEvent event) {
		if (event.getState().getState() == EnrollState.DECLINED) {
			loadHomePage();
		} else if (event.getState().getState() == EnrollState.FAILURE) {
			homePage.displayMessage(
					"Sorry, your enrollment to the study is not successful. Please try later.",
					"", false, "message_enrollment_failure");
			loadHomePage();
		} else if (event.getState().getState() == EnrollState.SUCCESS) {
			homePage.removeFromOpenStudies(event.getState().getStudy()
					.getStudyOverview().getId());
			homePage.addEnrolledStudyToMyStudy(event.getState().getStudy());
			homePage.displayMessage(
					"Thank you for enrolling to "
							+ event.getState().getStudy().getStudyOverview()
									.getName()
							+ ". Please participate to the study from 'My Studies' section.",
					"", false, "message_enrollment_success");
			loadHomePage();
		}
	}

	protected void loadHomePage() {
		homeAnchor.setActive(true);
		profileAnchor.setActive(false);

		mainPageContentPanel.clear();
		mainPageContentPanel.add(homePage);
	}

	@UiHandler("profileAnchor")
	void profileAnchorClicked(ClickEvent event) {
		if (Hvr.isSpeechTestRunning()) {
			interceptNavigation("logout");
		} else {

			History.newItem("profile/personal");
		}
	}

	protected void loadProfilePage() {
		homeAnchor.setActive(false);
		profileAnchor.setActive(true);

		mainPageContentPanel.clear();
		mainPageContentPanel.add(profilePage);
	}

	protected void loadEnrollmentProcess(StudyPrefaceModel study) {
		homeAnchor.setActive(false);
		profileAnchor.setActive(false);

		mainPageContentPanel.clear();
		mainPageContentPanel.add(new EnrollmentProcess(study));

	}

	protected void loadSpeechTestProcess(String studyId, String testId) {
		homeAnchor.setActive(false);
		profileAnchor.setActive(false);

		mainPageContentPanel.clear();
		mainPageContentPanel.add(new SpeechTestProcess(studyId, testId));

	}

	@UiHandler("signoutAnchor")
	void logout(ClickEvent clickEvent) {
		if (Hvr.isSpeechTestRunning()) {
			interceptNavigation("logout");
		} else {
			userId = "";
			this.application.logout(userId, this.sessionId);
		}
	}

	public static String getLoggedinUser() {
		return userId;
	}

	private void interceptNavigation(String navToken) {
		Hvr.updateSpeechTestState(false);
		Hvr.getEventBus().fireEvent(
				new TestProcessInterceptionEvent(new TestInterceptState("2",
						InterceptState.START)));
	}

}
