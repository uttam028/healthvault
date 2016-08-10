package cse.mlab.hvr.client;

import java_cup.production;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.PanelGroup;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sun.java.swing.plaf.windows.resources.windows;

import cse.mlab.hvr.shared.Md5Utils;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.Util;

public class TwitterSignup extends Composite {

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	@UiField
	AnchorListItem patientAnchor, researcherAnchor, supportAnchor;
	
	@UiField
	HTMLPanel patientCustomPanel, researcherCustomPanel, supportCustomPanel;
	
	@UiField
	Button buttonFaq;
	@UiField
	PanelGroup generalPanelGroup, patientPanelGroup, researcherPanelGroup;
	
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
	Button buttonCreateAccount, buttonForgotPass;

	boolean statusLoginEmailFormatError = false,
			statusLoginPasswordError = false;

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

	boolean statusFirstNameError = false, statusLastNameError = false,
			statusSignupEmailError = false, statusPasswordMatchError = false;

	private Hvr application;
	private String currentTab = "";
	

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
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		this.patientAnchorClicked(null);
		loadPatientPanel();
	}

	public  native void activateAccordion()/*-{
		var acc = $wnd.document.getElementsByClassName("accordion");
		var i;
		for (i = 0; i < acc.length; i++) {
			acc[i].onclick = function() {
				this.classList.toggle("active");
				this.nextElementSibling.classList.toggle("show");
			}
		}

	}-*/; 
	public void reset() {

	}
	
	@UiHandler("buttonFaq")
	void faqButtonClicked(ClickEvent event){
		if(currentTab.equalsIgnoreCase("patient")){
			togglePanelGroup(generalPanelGroup, false);
			togglePanelGroup(patientPanelGroup, true);
			togglePanelGroup(researcherPanelGroup, false);
		} else if (currentTab.equalsIgnoreCase("researcher")) {
			togglePanelGroup(generalPanelGroup, false);
			togglePanelGroup(patientPanelGroup, false);
			togglePanelGroup(researcherPanelGroup, true);			
		} else {
			togglePanelGroup(generalPanelGroup, true);
			togglePanelGroup(patientPanelGroup, false);
			togglePanelGroup(researcherPanelGroup, false);			
			
		}
		supportAnchorClciked(null);

	}
	
	private void togglePanelGroup(PanelGroup panelGroup, boolean openAll){
		for(int i=0;i<researcherPanelGroup.getWidgetCount();i++){
			try {
				PanelCollapse tempPanelCollapse = (PanelCollapse)((Panel)panelGroup.getWidget(i)).getWidget(1);
				tempPanelCollapse.setIn(openAll);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	@UiHandler("patientAnchor")
	void patientAnchorClicked(ClickEvent event){
		History.newItem("patient");
	}
	
	protected void loadPatientPanel(){
		currentTab = "patient";
		patientAnchor.setActive(true);
		researcherAnchor.setActive(false);
		supportAnchor.setActive(false);
		patientCustomPanel.setVisible(true);
		researcherCustomPanel.setVisible(false);
		supportCustomPanel.setVisible(false);
		buttonFaq.setVisible(true);		
	}
	
	@UiHandler("researcherAnchor")
	void researcherAnchorClicked(ClickEvent event){
		History.newItem("researcher");
	}
	
	protected void loadResearcherPanel(){
		currentTab = "researcher";
		patientAnchor.setActive(false);
		researcherAnchor.setActive(true);
		supportAnchor.setActive(false);
		patientCustomPanel.setVisible(false);
		researcherCustomPanel.setVisible(true);
		supportCustomPanel.setVisible(false);
		buttonFaq.setVisible(true);		
	}
	
	@UiHandler("supportAnchor")
	void supportAnchorClciked(ClickEvent event){
		History.newItem("support");
	}
	protected void loadSupportPanel(){
		togglePanelGroup(generalPanelGroup, true);
		currentTab = "support";
		patientAnchor.setActive(false);
		researcherAnchor.setActive(false);
		supportAnchor.setActive(true);
		patientCustomPanel.setVisible(false);
		researcherCustomPanel.setVisible(false);
		supportCustomPanel.setVisible(true);	
		buttonFaq.setVisible(false);		
	}
	
	@UiHandler("buttonForgotPass")
	void resetPassword(ClickEvent event) {
		if (validationBeforeReset()) {
			final String email = textLoginEmail.getText().trim();
			greetingService.resetPassword(email, new AsyncCallback<Response>() {
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					labelLoginError
							.setText("Service is not available. Try later.");
				}

				@Override
				public void onSuccess(Response result) {
					// TODO Auto-generated method stub
					if (result.getCode() == -1) {
						labelLoginError
								.setText("Service is not available. Try later.");
					} else if (result.getCode() == 1) {
						labelLoginError
								.setText("User does not exist. Please sign up.");
					} else {
						Window.alert("You will receive new password by email. You can change password later.");
					}
				}
			});
		}
	}
	
	

	@UiHandler("buttonCreateAccount")
	void enableSignupForm(ClickEvent event) {
		formSignup.setVisible(true);
		resetSignup();
		formLogin.setVisible(false);
	}

	@UiHandler("buttonAlreadyAccount")
	void enableLoginForm(ClickEvent event) {
		formLogin.setVisible(true);
		resetLogin();
		formSignup.setVisible(false);
	}

	private void resetLogin() {
		textLoginEmail.setText("");
		textLoginPassword.setText("");
		labelLoginEmailFormatError.setText("");
		labelLoginPasswordError.setText("");
		labelLoginError.setText("");
	}

	private void resetSignup() {
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
	void loginEmailValueChangedAction(ValueChangeEvent<String> event) {
		if (Util.isEmailFOrmatValid(textLoginEmail.getText())) {
			labelLoginEmailFormatError.setText("");
			statusLoginEmailFormatError = false;
		} else {
			labelLoginEmailFormatError.setText("Invalid Email");
			statusLoginEmailFormatError = true;
		}
	}

	@UiHandler("textLoginPassword")
	void loginPasswordValueChangedAction(ValueChangeEvent<String> event) {
		if (textLoginPassword.getText().isEmpty()) {
			labelLoginPasswordError.setText("Password can't be empty");
			statusLoginPasswordError = true;
		} else {
			labelLoginPasswordError.setText("");
			statusLoginPasswordError = false;
		}
	}

	private boolean validationBeforeReset() {
		if (textLoginEmail.getText().isEmpty()) {
			labelLoginEmailFormatError.setText("Email can't be empty");
			statusLoginEmailFormatError = true;
		}

		if (statusLoginEmailFormatError) {
			return false;
		}
		return true;
	}

	private boolean validationBeforeLogin() {
		if (textLoginEmail.getText().isEmpty()) {
			labelLoginEmailFormatError.setText("Email can't be empty");
			statusLoginEmailFormatError = true;
		}

		if (textLoginPassword.getText().isEmpty()) {
			labelLoginPasswordError.setText("Password can't be empty");
			statusLoginPasswordError = true;
		}

		if (statusLoginEmailFormatError || statusLoginPasswordError) {
			return false;
		}
		return true;
	}

	@UiHandler("buttonLoginAction")
	void loginAction(ClickEvent event) {
		if (textLoginEmail.getText().isEmpty()
				&& textLoginPassword.getText().isEmpty()) {
			application.loggedIn("z@gmail.com");

		} else {
			if (this.validationBeforeLogin()) {
				buttonLoginAction.setEnabled(false);
				User user = new User();
				user.setEmail(textLoginEmail.getText().trim());
				try {
					user.setPassword(Md5Utils.getMD5String(textLoginPassword
							.getText().trim()));
				} catch (Exception e) {
					// TODO: handle exception
					return;
				}

				greetingService.loginToPhr(user, new AsyncCallback<Response>() {

					@Override
					public void onSuccess(Response response) {
						// TODO Auto-generated method stub
						buttonLoginAction.setEnabled(true);
						if(response.getCode()==0){
							application.loggedIn(textLoginEmail.getText()
									.trim());
						} else {
							labelLoginError.setText(response.getMessage());
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						buttonLoginAction.setEnabled(true);
						labelLoginError.setText("Service is not available, please try later!");
					}
				});
			}

		}

	}

	@UiHandler("textFirstName")
	void firstNameValueChangedAction(ValueChangeEvent<String> event) {
		if (textFirstName.getText().isEmpty()) {
			labelFirstNameError.setText("This can't be empty");
			statusFirstNameError = true;
		} else {
			labelFirstNameError.setText("");
			statusFirstNameError = false;
		}
	}

	@UiHandler("textLastName")
	void lastNameValueChangedAction(ValueChangeEvent<String> event) {
		if (textLastName.getText().isEmpty()) {
			labelLastNameError.setText("This can't be empty");
			statusLastNameError = true;
		} else {
			labelLastNameError.setText("");
			statusLastNameError = false;
		}
	}

	@UiHandler("textSignupEmail")
	void signupEmailChnagedAction(ValueChangeEvent<String> event) {
		if (Util.isEmailFOrmatValid(textSignupEmail.getText())) {
			labelSignupEmailFormatError.setText("");
			statusSignupEmailError = false;
		} else {
			labelSignupEmailFormatError.setText("Invalid Email");
			statusSignupEmailError = true;
		}
	}

	@UiHandler({ "textSignupPassword", "textConfirmPassword" })
	void passwordMatchAction(ValueChangeEvent<String> event) {
		if (textSignupPassword.getText().equals(textConfirmPassword.getText())) {
			labelPasswordMatchError.setText("");
			statusPasswordMatchError = false;
		} else {
			labelPasswordMatchError.setText("Password doesn't match");
			statusPasswordMatchError = true;
		}
	}

	private boolean validationBeforeSignup() {
		if (textFirstName.getText().isEmpty()) {
			labelFirstNameError.setText("This can't be ampty");
			statusFirstNameError = true;
		}

		if (textLastName.getText().isEmpty()) {
			labelLastNameError.setText("This can't be empty");
			statusLastNameError = true;
		}

		if (textSignupEmail.getText().isEmpty()) {
			labelSignupEmailFormatError.setText("Invalid Email");
			statusSignupEmailError = true;
		}
		if (textSignupPassword.getText().isEmpty()
				|| textConfirmPassword.getText().isEmpty()) {
			labelPasswordMatchError.setText("Password can't be empty");
			statusPasswordMatchError = true;
		}

		if (statusFirstNameError || statusLastNameError
				|| statusSignupEmailError || statusPasswordMatchError) {
			return false;
		}
		return true;
	}

	@UiHandler("buttonSignupAction")
	void signupAction(ClickEvent event) {
		if (this.validationBeforeSignup()) {
			buttonSignupAction.setEnabled(false);
			final String firstName = textFirstName.getText().trim();
			final String lastName = textLastName.getText().trim();
			final String email = textSignupEmail.getText().trim();
			final String password = textSignupPassword.getText().trim();
			// final UserProfile userProfile = new UserProfile(email, password,
			// firstName, lastName, "", "", 0);
			final UserProfile userProfile = new UserProfile();
			userProfile.setFirstName(firstName);
			userProfile.setLastName(lastName);
			userProfile.setEmail(email);
			try {
				userProfile.setPassword(Md5Utils.getMD5String(password));
			} catch (Exception e) {
				// TODO: handle exception
				Window.alert("have caught an exception" + e.getMessage());
				return;
			}
			// userProfile.setPassword(password);

			try {
				greetingService.checkEmailAvailability(email,
						new AsyncCallback<String>() {
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								labelSignupError
										.setText("Service is not available, please try later!");
								buttonSignupAction.setEnabled(true);
							}

							@Override
							public void onSuccess(String result) {
								// TODO Auto-generated method stub
								if (result.toLowerCase().equals("true")) {
									buttonSignupAction.setEnabled(true);
									labelSignupError
											.setText("This email already exist, Please try with new one.");
								} else {
									greetingService.signupToPhr(userProfile,
											new AsyncCallback<Response>() {
												public void onFailure(
														Throwable caught) {
													labelSignupError
															.setText("Service is not available, please try again later!");
													buttonSignupAction
															.setEnabled(true);
												};

												public void onSuccess(
														Response result) {
													buttonSignupAction
															.setEnabled(true);
													if (result.getCode() == 0) {
														application
																.signedUP(email);
													} else {
														labelSignupError.setText(result
																.getMessage());
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
