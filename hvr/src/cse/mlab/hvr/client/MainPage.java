package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.AnchorListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.EnrollmentState.EnrollState;
import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.events.EnrollmentEvent;
import cse.mlab.hvr.client.events.EnrollmentEventHandler;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.client.events.SpeechTestEventHandler;
import cse.mlab.hvr.client.study.EnrollmentProcess;
import cse.mlab.hvr.client.study.SpeechTestProcess;
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
	private String firstName = "";
	private String lastName = "";
	private boolean profileUpdated = false;
	private static String currentPage = "main";
	private static MainPageUiBinder uiBinder = GWT
			.create(MainPageUiBinder.class);

	interface MainPageUiBinder extends UiBinder<Widget, MainPage> {
	}

	public MainPage(Hvr application, String email) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
		userId = email;
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
					public void actionAfterTestEvent(SpeechTestEvent event) {
						if (event.getTestState().getState() == TestState.START) {
							loadSpeechTestProcess(event.getTestState()
									.getTestId());
						} else {
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
		if (History.getToken().equalsIgnoreCase("home")) {
			loadHomePage();
		} else {
			History.newItem("home");
		}
		// Window.alert("home button clicked...");

	}

	protected void updateHomePageAfterEnrollment(EnrollmentEvent event) {
		if (event.getState().getState() == EnrollState.DECLINED) {
			loadHomePage();
		} else if (event.getState().getState() == EnrollState.FAILURE) {
			loadHomePage();
		} else if (event.getState().getState() == EnrollState.SUCCESS) {
			homePage.removeFromOpenStudies(event.getState().getStudy().getStudyOverview().getId());
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
		History.newItem("profile/personal");

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

	protected void loadSpeechTestProcess(String testId) {
		homeAnchor.setActive(false);
		profileAnchor.setActive(false);

		mainPageContentPanel.clear();
		mainPageContentPanel.add(new SpeechTestProcess(testId));

	}

	@UiHandler("signoutAnchor")
	void logout(ClickEvent clickEvent) {
		this.application.logout();
	}
	
	public static String getLoggedinUser() {
		return userId;
	}

}
