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

import cse.mlab.hvr.client.events.LoadProfileItemEvent;

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
		

		// History.
		History.addValueChangeHandler(new ValueChangeHandler<String>() {

			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();
				// Parse the history token
				try {
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
							mainPage.loadHomePage();
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
						
					}
					// else if(historyToken.equalsIgnoreCase("main")){
					// mainPage.loadHomePage(null);
					// } else if (historyToken.equalsIgnoreCase("concussion")) {
					// mainPage.loadConcussionPage();
					// } else if (historyToken.equalsIgnoreCase("dysarthria")) {
					// mainPage.loadDysarthriaPage();
					// } else {
					// mainPage.loadHomePage(null);
					// }
					//
					// if(historyToken.startsWith("home")){
					// mainPage.loadHomePage(null);
					// } else if(historyToken.startsWith("voice")){
					// mainPage.loadVoicePage(null);
					// } else if(historyToken.startsWith("health")){
					// mainPage.loadHealthPage(null);
					// } /*else if(historyToken.startsWith("about")){
					// mainPage.loadAboutPage(null);
					// } */else{
					// mainPage.loadHomePage(null);
					// }

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
		loggedIn = true;
	}

	public void logout() {
		loggedIn = false;
		onModuleLoad();
	}

	public static EventBus getEventBus() {
		return eventBus;
	}

}
