package cse.mlab.hvr.client;

import java.util.Date;

import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.events.LoadProfileItemEvent;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.client.events.SpeechTestEventHandler;
import cse.mlab.hvr.client.services.GreetingService;
import cse.mlab.hvr.client.services.GreetingServiceAsync;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.SpeechSession;

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
	private final GreetingServiceAsync service = GWT.create(GreetingService.class);
	
	final long SECONDS = 1000;
	final long MINUTES = 60*SECONDS;
	final long HOURS = 60*MINUTES;
	
	private static String browser="";



	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		getBrowser();
		
		loggedIn = false;
		speechTestRunning = false;
		
		final String cookies = Cookies.getCookie("speechmarker_login");
		if(cookies == null || cookies.isEmpty()){
			loadLogin();
		} else {
			service.getSessionInformation(new SpeechSession(cookies, "", 1), new AsyncCallback<Response>() {
				
				@Override
				public void onSuccess(Response result) {
					// TODO Auto-generated method stub
					if(result.getCode()==0){
						loggedIn(result.getMessage(), cookies);
						History.newItem("home");
					} else {
						loadLogin();
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					loadLogin();
				}
			});
		}
		

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
					
////					Window.alert("history token:"+ historyToken);
//					if (historyToken.equalsIgnoreCase("patient")) {
//						if (loggedIn) {
//							History.forward();
//						} else {
//							twitterSignup.loadPatientPanel();
//
//						}
//					} else if (historyToken.equalsIgnoreCase("researcher")) {
//						if (loggedIn) {
//							History.forward();
//						} else{
//							twitterSignup.loadResearcherPanel();	
//						}
//						
//					} else if (historyToken.equalsIgnoreCase("support")) {
//						if (loggedIn) {
//							History.forward();
//						} else {
//							twitterSignup.loadSupportPanel();
//						}
//						
//					} else 
						
					if (historyToken.equalsIgnoreCase("home")) {
						if(loggedIn){
							if(speechTestRunning){
								History.fireCurrentHistoryState();
							}else {
								mainPage.loadHomePage();								
							}
						} else {
							//History.back();
							//onModuleLoad();
							History.fireCurrentHistoryState();
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
							//History.back();
							//onModuleLoad();
							History.fireCurrentHistoryState();
						}
						
					} else if (historyToken.startsWith("admin")) {
						if(loggedIn){
							String [] tokens = historyToken.split("/");
							if(tokens[1].equals("0")){
								mainPage.loadAdminPage();
							} else {
								mainPage.getAdminPage().loadStudyManagement(null);
							}
							
						}else {
							//History.back();
							//onModuleLoad();
							History.fireCurrentHistoryState();

						}
						
					} else if (historyToken.startsWith("research")) {
						if(loggedIn){
							mainPage.loadResearchPage();
						}else {
							//History.back();
							//onModuleLoad();
							History.fireCurrentHistoryState();

						}
						
					} else if (historyToken.startsWith("contact")) {
						if(loggedIn){
							mainPage.loadContactPage();
						}else {
							//History.back();
							//onModuleLoad();
							History.fireCurrentHistoryState();

						}
					} else{
						//Window.alert("in else history token :" + historyToken);
						//History.newItem("home");
						
						if(loggedIn){
							//mainPage.loadHomePage();
							History.newItem("Home");
						} else {
							//onModuleLoad();
							History.fireCurrentHistoryState();

						}
					}
					/* else if(historyToken.startsWith("speechtest")){
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
	
	private void loadLogin(){
		RootPanel.get().clear();
		String paramValue = Window.Location.getParameter("emailverified");
		twitterSignup = new TwitterSignup(this);
		RootPanel.get().add(twitterSignup);
		if (paramValue != null) {
			//Window.alert("paramvalue:"+ paramValue);
			twitterSignup.showAuthMessage("Your account has been verified, please login to enroll in the study.");
		}			

	}

	public void loggedIn(String userId, String sessionId) {
		RootPanel.get().clear();
		mainPage = new MainPage(this, userId, sessionId);
		final Date dueDate = new Date();
		//CalendarUtil.addDaysToDate(dueDate, 1);
		dueDate.setTime(dueDate.getTime() + 30 * MINUTES);
		Cookies.setCookie("speechmarker_login", sessionId, dueDate);
		RootPanel.get().add(mainPage);
		//History.newItem("home");
		loggedIn = true;
	}

	public void logout(String userId, String sessionId) {
		loggedIn = false;
		speechTestRunning = false;
		service.logout(new SpeechSession(sessionId, userId, 0), new AsyncCallback<Response>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSuccess(Response result) {
				// TODO Auto-generated method stub
				
			}
		});
		Cookies.setCookie("speechmarker_login", "", new Date());
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
	
	public native void getBrowser()/*-{
		var nVer = navigator.appVersion;
		var nAgt = navigator.userAgent;
		var browserName  = navigator.appName;
		var fullVersion  = ''+parseFloat(navigator.appVersion); 
		var majorVersion = parseInt(navigator.appVersion,10);
		var nameOffset,verOffset,ix;
		
		// In Opera 15+, the true version is after "OPR/" 
		if ((verOffset=nAgt.indexOf("OPR/"))!=-1) {
		 browserName = "Opera";
		 fullVersion = nAgt.substring(verOffset+4);
		}
		// In older Opera, the true version is after "Opera" or after "Version"
		else if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
		 browserName = "Opera";
		 fullVersion = nAgt.substring(verOffset+6);
		 if ((verOffset=nAgt.indexOf("Version"))!=-1) 
		   fullVersion = nAgt.substring(verOffset+8);
		}
		// In MSIE, the true version is after "MSIE" in userAgent
		else if ((verOffset=nAgt.indexOf("Edge"))!=-1) {
		 browserName = "Microsoft Internet Explorer";
		 fullVersion = nAgt.substring(verOffset+5);
		}
		// In Chrome, the true version is after "Chrome" 
		else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
		 browserName = "Chrome";
		 fullVersion = nAgt.substring(verOffset+7);
		}
		// In Safari, the true version is after "Safari" or after "Version" 
		else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
		 browserName = "Safari";
		 fullVersion = nAgt.substring(verOffset+7);
		 if ((verOffset=nAgt.indexOf("Version"))!=-1) 
		   fullVersion = nAgt.substring(verOffset+8);
		}
		// In Firefox, the true version is after "Firefox" 
		else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
		 browserName = "Firefox";
		 fullVersion = nAgt.substring(verOffset+8);
		}
		// In most other browsers, "name/version" is at the end of userAgent 
		else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < 
		          (verOffset=nAgt.lastIndexOf('/')) ) 
		{
		 browserName = nAgt.substring(nameOffset,verOffset);
		 fullVersion = nAgt.substring(verOffset+1);
		 if (browserName.toLowerCase()==browserName.toUpperCase()) {
		  browserName = navigator.appName;
		 }
		}
		// trim the fullVersion string at semicolon/space if present
		if ((ix=fullVersion.indexOf(";"))!=-1)
		   fullVersion=fullVersion.substring(0,ix);
		if ((ix=fullVersion.indexOf(" "))!=-1)
		   fullVersion=fullVersion.substring(0,ix);
		
		majorVersion = parseInt(''+fullVersion,10);
		if (isNaN(majorVersion)) {
		 fullVersion  = ''+parseFloat(navigator.appVersion); 
		 majorVersion = parseInt(navigator.appVersion,10);
		}
		@cse.mlab.hvr.client.Hvr::browser=browserName;
	
}-*/;
	public static String getBrowserName(){
		return browser;
	}

}
