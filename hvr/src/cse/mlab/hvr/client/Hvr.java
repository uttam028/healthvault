package cse.mlab.hvr.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Hvr implements EntryPoint {
//	Login login = null;
//	Signup signup = null;
	TwitterSignup twitterSignup = null;
	MainPage mainPage = null;


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
}
