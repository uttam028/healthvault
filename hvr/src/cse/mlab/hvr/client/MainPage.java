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

import cse.mlab.hvr.client.events.EnrollmentEvent;
import cse.mlab.hvr.client.events.EnrollmentEventHandler;
import cse.mlab.hvr.client.events.TestCompletionEvent;
import cse.mlab.hvr.client.events.TestCompletionEventHandler;
import cse.mlab.hvr.client.study.EnrollmentProcess;
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

	private String userId;
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
		this.userId = email;
		// updateUserName();
		// profilePage = new ProfilePage(this, this.userId);

		homePage = new HomePage(this.application);
		profilePage = new ProfilePage();
		
		Hvr.getEventBus().addHandler(EnrollmentEvent.TYPE, new EnrollmentEventHandler() {
			
			@Override
			public void actionForEnrollment(EnrollmentEvent event) {
				// TODO Auto-generated method stub
				loadEnrollmentProcess(event.getState().getStudy());
			}
		});
		
		Hvr.getEventBus().addHandler(TestCompletionEvent.TYPE, new TestCompletionEventHandler() {
			
			@Override
			public void actionAfterTestCompleted(TestCompletionEvent event) {
				loadHomePage();
			}
		});
//		homeAnchorClicked(null);
		loadHomePage();
	}

	protected void onLoad() {
//		homeAnchorClicked(null);

	}

	@UiHandler("homeAnchor")
	void homeAnchorClicked(ClickEvent event) {
		if(History.getToken().equalsIgnoreCase("home")){
			loadHomePage();
		} else{
			History.newItem("home");			
		}
		//Window.alert("home button clicked...");
		
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

	@UiHandler("signoutAnchor")
	void logout(ClickEvent clickEvent) {
		this.application.logout();
	}


}
