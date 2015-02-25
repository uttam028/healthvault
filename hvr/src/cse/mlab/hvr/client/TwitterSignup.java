package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.Util;

public class TwitterSignup extends Composite {
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	@UiField
	Form formLogin;
	@UiField
	TextBox textLoginEmail;
	@UiField
	Input textLoginPassword;
	@UiField
	Button buttonLoginAction;
	@UiField
	Label labelLoginEmailFormatError;
	@UiField
	Label labelLoginPasswordError;
	@UiField
	Label labelLoginError;
	@UiField
	Button buttonCreateAccount;
	
	boolean statusLoginEmailFormatError = false, statusLoginPasswordError = false;
	
	
	@UiField
	Form formSignup;
	@UiField
	TextBox textFirstName;
	@UiField
	TextBox textLastName;
	@UiField
	TextBox textSignupEmail;
	@UiField
	Input textSignupPassword;
	@UiField
	Input textConfirmPassword;
	@UiField
	Label labelFirstNameError;
	@UiField
	Label labelLastNameError;
	@UiField
	Label labelSignupEmailFormatError;
	@UiField
	Label labelPasswordMatchError;
	@UiField
	Button buttonSignupAction;
	@UiField
	Button buttonAlreadyAccount;
	@UiField
	Label labelSignupError;
	
	boolean statusFirstNameError = false, statusLastNameError = false, statusSignupEmailError = false, statusPasswordMatchError = false;
	
	private Hvr application;
	
	private static TwitterSignupUiBinder uiBinder = GWT
			.create(TwitterSignupUiBinder.class);

	interface TwitterSignupUiBinder extends UiBinder<Widget, TwitterSignup> {
	}

	public TwitterSignup(Hvr application) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
		formLogin.setVisible(true);
		formSignup.setVisible(false);
		
	}
	
	public void reset(){
		
	}
	@UiHandler("buttonCreateAccount")
	void enableSignupForm(ClickEvent event){
		formSignup.setVisible(true);
		resetSignup();
		formLogin.setVisible(false);
	}
	
	@UiHandler("buttonAlreadyAccount")
	void enableLoginForm(ClickEvent event){
		formLogin.setVisible(true);
		resetLogin();
		formSignup.setVisible(false);
	}
	
	private void resetLogin(){
		textLoginEmail.setText("");
		textLoginPassword.setText("");
		labelLoginEmailFormatError.setText("");
		labelLoginPasswordError.setText("");
		labelLoginError.setText("");
	}
	
	private void resetSignup(){
		textFirstName.setText("");
		textLastName.setText("");
		textSignupEmail.setText("");
		textSignupPassword.setText("");
		textConfirmPassword.setText("");
		labelFirstNameError.setText("");
		labelLastNameError.setText("");
		labelSignupEmailFormatError.setText("");
		labelPasswordMatchError.setText("");
		labelSignupError.setText("");
	}
	
	@UiHandler("textLoginEmail")
	void loginEmailValueChangedAction(ValueChangeEvent<String> event){
		if(Util.isEmailFOrmatValid(textLoginEmail.getText())){
			labelLoginEmailFormatError.setText("");
			statusLoginEmailFormatError = false;
		} else {
			labelLoginEmailFormatError.setText("Invalid Email");
			statusLoginEmailFormatError = true;
		}
	}
	
	@UiHandler("textLoginPassword")
	void loginPasswordValueChangedAction(ValueChangeEvent<String> event){
		if(textLoginPassword.getText().isEmpty()){
			labelLoginPasswordError.setText("Password can't be empty");
			statusLoginPasswordError = true;
		} else{
			labelLoginPasswordError.setText("");
			statusLoginPasswordError = false;
		}
	}
	
	private boolean validationBeforeLogin(){
		if(textLoginEmail.getText().isEmpty()){
			labelLoginEmailFormatError.setText("Email can't be empty");
			statusLoginEmailFormatError = true;
		}
		
		if(textLoginPassword.getText().isEmpty()){
			labelLoginPasswordError.setText("Password can't be empty");
			statusLoginPasswordError = true;
		}
		
		if(statusLoginEmailFormatError || statusLoginPasswordError){
			return false;
		}
		return true;
	}
	
	@UiHandler("buttonLoginAction")
	void loginAction(ClickEvent event){
//		application.loggedIn("ah@gmail.com");
		if(this.validationBeforeLogin()){
			buttonLoginAction.setEnabled(false);
			User user = new User();
			user.setEmail(textLoginEmail.getText());
			user.setPassword(textLoginPassword.getText());
			
			greetingService.loginToPhr(user, new AsyncCallback<String>() {
				
				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					buttonLoginAction.setEnabled(true);
					if (result.toLowerCase().startsWith("true")) {
						application.loggedIn(textLoginEmail.getText().trim());
					} else {
						labelLoginError.setText("Incorrect email or password. Please try again with correct one.");
					}
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					buttonLoginAction.setEnabled(true);
					Window.alert("Service is not available, please try again later!");
				}
			});
		}
	}
	
	
	
	@UiHandler("textFirstName")
	void firstNameValueChangedAction(ValueChangeEvent<String> event){
		if(textFirstName.getText().isEmpty()){
			labelFirstNameError.setText("This can't be empty");
			statusFirstNameError = true;
		} else{
			labelFirstNameError.setText("");
			statusFirstNameError = false;
		}
	}
	
	@UiHandler("textLastName")
	void lastNameValueChangedAction(ValueChangeEvent<String> event){
		if(textLastName.getText().isEmpty()){
			labelLastNameError.setText("This can't be empty");
			statusLastNameError = true;
		} else{
			labelLastNameError.setText("");
			statusLastNameError = false;
		}
	}
	
	@UiHandler("textSignupEmail")
	void signupEmailChnagedAction(ValueChangeEvent<String> event){
		if(Util.isEmailFOrmatValid(textSignupEmail.getText())){
			labelSignupEmailFormatError.setText("");
			statusSignupEmailError = false;
		} else{
			labelSignupEmailFormatError.setText("Invalid Email");
			statusSignupEmailError = true;			
		}
	}
	
	@UiHandler({"textSignupPassword","textConfirmPassword"})
	void passwordMatchAction(ValueChangeEvent<String> event){
		if(textSignupPassword.getText().equals(textConfirmPassword.getText())){
			labelPasswordMatchError.setText("");
			statusPasswordMatchError = false;
		} else{
			labelPasswordMatchError.setText("Password doesn't match");
			statusPasswordMatchError = true;
		}
	}
	
	private boolean validationBeforeSignup(){
		if(textFirstName.getText().isEmpty()){
			labelFirstNameError.setText("This can't be ampty");
			statusFirstNameError = true;
		}
		
		if(textLastName.getText().isEmpty()){
			labelLastNameError.setText("This can't be empty");
			statusLastNameError = true;
		}
		
		if(textSignupEmail.getText().isEmpty()){
			labelSignupEmailFormatError.setText("Invalid Email");
			statusSignupEmailError = true;
		}
		if(textSignupPassword.getText().isEmpty() || textConfirmPassword.getText().isEmpty()){
			labelPasswordMatchError.setText("Password can't be empty");
			statusPasswordMatchError = true;
		}
		
		if(statusFirstNameError || statusLastNameError || statusSignupEmailError || statusPasswordMatchError){
			return false;
		}
		return true;
	}
	
	@UiHandler("buttonSignupAction")
	void signupAction(ClickEvent event){
		if(this.validationBeforeSignup()){
			buttonSignupAction.setEnabled(false);
			final String firstName = textFirstName.getText().trim();
			final String lastName = textLastName.getText().trim();
			final String email = textSignupEmail.getText().trim();
			final String password = textSignupPassword.getText().trim();
//			final UserProfile userProfile = new UserProfile(email, password,
//					firstName, lastName, "", "", 0);
			final UserProfile userProfile = new UserProfile();
			userProfile.setFirstName(firstName);
			userProfile.setLastName(lastName);
			userProfile.setEmail(email);
			userProfile.setPassword(password);
			
			try {
				greetingService.checkEmailAvailability(email, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						labelSignupError.setText("Service is not available, please try again later!");
						buttonSignupAction.setEnabled(true);
					}
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						if (result.toLowerCase().equals("true")) {
							buttonSignupAction.setEnabled(true);
							labelSignupError.setText("This email already exist, Please try with new one.");
						} else {
							greetingService.signupToPhr(userProfile, new AsyncCallback<String>() {
								public void onFailure(Throwable caught) {
									labelSignupError.setText("Service is not available, please try again later!");
									buttonSignupAction.setEnabled(true);									
								};
								public void onSuccess(String result) {
									buttonSignupAction.setEnabled(true);
									if(result.toLowerCase().startsWith("error")){
										labelSignupError.setText(result);
									} else {
										application.signedUP(email);
									}
								};
							});	
						}
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				Window.alert("Service is not available, please try again later!");
				buttonSignupAction.setEnabled(true);				
			}

		}
	}
	
}
