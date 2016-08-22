package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.events.LoadProfileItemEvent;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.client.events.SpeechTestEventHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Hvr implements EntryPoint {
	// Login login = null;
	// Signup signup = null;
	TwitterSignup twitterSignup = null;
	MainPage mainPage = null;
	final static SimpleEventBus eventBus = new SimpleEventBus();
	private static boolean loggedIn = false;
	private static boolean speechTestRunning =false; 

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get().clear();

		String paramValue = Window.Location.getParameter("confirmationcode");
		if (paramValue != null && paramValue.length() > 10) {
			// Window.alert("paramvalue:"+ paramValue);
		}
		twitterSignup = new TwitterSignup(this);
		RootPanel.get().add(twitterSignup);
		Hvr.getEventBus().addHandler(SpeechTestEvent.TYPE,
				new SpeechTestEventHandler() {

					@Override
					public void actionAfterTestEvent(SpeechTestEvent event) {
						if (event.getTestState().getState() == TestState.START) {
							speechTestRunning = true;
						}else {
							speechTestRunning = false;
						}
					}
				});


		// History.
		History.addValueChangeHandler(new ValueChangeHandler<String>() {

			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();
				// Parse the history token
				try {
					
					/*if(speechTestRunning && loggedIn){
						//Window.alert("what happens for current history");
						return;
					}*/
					
					// Window.alert("history token:"+ historyToken);
					if (historyToken.equalsIgnoreCase("patient")) {
						if (loggedIn) {
							History.forward();
						} else {
							twitterSignup.loadPatientPanel();

						}
					} else if (historyToken.equalsIgnoreCase("researcher")) {
						if (loggedIn) {
							History.forward();
						} else{
							twitterSignup.loadResearcherPanel();	
						}
						
					} else if (historyToken.equalsIgnoreCase("support")) {
						if (loggedIn) {
							History.forward();
						} else {
							twitterSignup.loadSupportPanel();
						}
						
					} else if (historyToken.equalsIgnoreCase("home")) {
						if(loggedIn){
							if(speechTestRunning){
								History.fireCurrentHistoryState();
							}else {
								mainPage.loadHomePage();								
							}
						} else {
							History.back();
						}
						
					} else if (historyToken.startsWith("profile")) {
						if(loggedIn){
							mainPage.loadProfilePage();
							String [] tokens = historyToken.split("/");
							if(tokens[1].equalsIgnoreCase("personal")){
								getEventBus().fireEvent(new LoadProfileItemEvent(ProfilePageItem.PERSONAL));
							} else if(tokens[1].equalsIgnoreCase("account")){
								getEventBus().fireEvent(new LoadProfileItemEvent(ProfilePageItem.ACCOUNT));
							} else if(tokens[1].equalsIgnoreCase("medical")){
								getEventBus().fireEvent(new LoadProfileItemEvent(ProfilePageItem.MEDICAL));
							} else {
								getEventBus().fireEvent(new LoadProfileItemEvent(ProfilePageItem.PERSONAL));
							}
						} else {
							History.back();
						}
						
					}/* else if(historyToken.startsWith("speechtest")){
						if(loggedIn){
							String [] tokens = historyToken.split("/");
							String studyId = tokens[1];
							String testId = tokens[2];
							Window.alert("study id:"+ studyId + ", test id:"+ testId);
							mainPage.loadSpeechTestProcess(studyId, testId);
							
						} else {
							History.back();
						}
					}*/

				} catch (IndexOutOfBoundsException e) {
					// mainPage.loadHomePage(null);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

	}

	public void signedUP(String userId) {
		// Window.alert("event has been passed to main page");
		RootPanel.get().clear();
		Div div = new Div();
		// mainPage = new (this, userId);
		Label signupLabel = new Label(
				"Thank you for signning up in ND Speech Repository project. You will receive confirmation email to activate your account.");
		div.add(new Br());
		div.add(new Br());
		div.add(signupLabel);
		RootPanel.get().add(div);
	}

	public void loggedIn(String userId) {
		RootPanel.get().clear();
		mainPage = new MainPage(this, userId);
		RootPanel.get().add(mainPage);
		History.newItem("home");
		loggedIn = true;
	}

	public void logout() {
		loggedIn = false;
		speechTestRunning = false;
		onModuleLoad();
	}
	
	public static void updateSpeechTestState(boolean running){
		speechTestRunning = running;
	}
	
	public static boolean isSpeechTestRunning(){
		return speechTestRunning;
	}

	public static EventBus getEventBus() {
		return eventBus;
	}

}
