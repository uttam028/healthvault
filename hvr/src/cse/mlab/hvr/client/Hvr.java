package cse.mlab.hvr.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Hvr implements EntryPoint {
//	Login login = null;
//	Signup signup = null;
	TwitterSignup twitterSignup = null;
	MainPage mainPage = null;
	final static SimpleEventBus eventBus = new SimpleEventBus();


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get().clear();
//		Button loginButton = new Button("LogIn");
//		Button signupButton = new Button("SignUp");
//		Button twitterSignupButton = new Button("TwitterSignUp");
		


//		login = new Login(this);
//		RootPanel.get().add(loginButton);
//		RootPanel.get().add(signupButton);
//		RootPanel.get().add(twitterSignupButton);

//		signup = new Signup(this);
		twitterSignup = new TwitterSignup(this);
		RootPanel.get().add(twitterSignup);

//		loginButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				RootPanel.get().remove(signup);
//				RootPanel.get().remove(twitterSignup);
//				login.reset();
//				RootPanel.get().add(login);
//			}
//		});
//
//		signupButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				RootPanel.get().remove(login);
//				RootPanel.get().remove(twitterSignup);
//				signup.reset();
//				RootPanel.get().add(signup);
//
//			}
//		});
//		
//		twitterSignupButton.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				RootPanel.get().remove(login);
//				RootPanel.get().remove(signup);
//				twitterSignup.reset();
//				RootPanel.get().add(twitterSignup);
//			}
//		});

	    History.addValueChangeHandler(new ValueChangeHandler<String>() {
	        public void onValueChange(ValueChangeEvent<String> event) {
	          String historyToken = event.getValue();

	          // Parse the history token
	          try {
	        	  
	        	if(historyToken.startsWith("home")){
	        		mainPage.loadHomePage(null);
	        	} else if(historyToken.startsWith("voice")){
	        		mainPage.loadVoicePage(null);
	        	} else if(historyToken.startsWith("health")){
	        		mainPage.loadHealthPage(null);
	        	} else if(historyToken.startsWith("about")){
	        		mainPage.loadAboutPage(null);
	        	} else{
	        		mainPage.loadHomePage(null);
	        	}

	          } catch (IndexOutOfBoundsException e) {
	            mainPage.loadHomePage(null);
	          }
	        }
	      });

	}

	public void signedUP(String userId) {
		// Window.alert("event has been passed to main page");
		RootPanel.get().clear();
		mainPage = new MainPage(this, userId);
		RootPanel.get().add(mainPage);
	}

	public void loggedIn(String userId) {
		// Window.alert("event has been passed to main page");
		RootPanel.get().clear();
		mainPage = new MainPage(this, userId);
		RootPanel.get().add(mainPage);
	}
	
	public void logout() {
		onModuleLoad();
	}
	
	public static EventBus getEventBus(){
		return eventBus;
	}
}