package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.html.Br;

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

import cse.mlab.hvr.shared.Md5Utils;
import cse.mlab.hvr.shared.QA;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.Util;

public class TwitterSignup extends Composite {

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	//@UiField
	//AnchorListItem patientAnchor, researcherAnchor, supportAnchor;
	
	@UiField
	HTMLPanel patientCustomPanel, researcherCustomPanel, supportCustomPanel, showMessagePanel;
	@UiField
	Heading showMessage;
	
	@UiField
	//Button buttonFaq;
	Button buttonGotoRegister;
	
	//@UiField
	//PanelGroup generalPanelGroup, patientPanelGroup, researcherPanelGroup;
	SimpleFaqViewer generalFaqViewer, patientFaqviewer, researcherFaqViewer;
	
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
	
	@UiField
	Column websiteIntroPanel;

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
		initializeFaqs();
	}
	
	private void initializeFaqs(){
		ArrayList<QA> qaList1 = new ArrayList<>();
		QA genQa1 = new QA("What is the purpose of this initiative?", "The purpose of the Speech Marker Initiative is"
				+ " to find a connection between human speech patterns and"
				+ " certain medical conditions. Through prior research, a link"
				+ " between neurological functioning and certain acoustic"
				+ " features of the voice has been revealed.");
		QA genQa2 = new QA("What does this website contain?", "As a patient, this website contains several"
				+ " different speech tests, which can be completed periodically."
				+ " This website as also has features for researchers, such as"
				+ " data analytics and comparison tools to run on the database"
				+ " of completed tests.");
		qaList1.add(genQa1);
		qaList1.add(genQa2);
		generalFaqViewer = new SimpleFaqViewer(qaList1, "General", true);
		
		
		ArrayList<QA> qaList2 = new ArrayList<>();
		QA patQa1 = new QA("How do I take the tests?", "Once you have created your profile, you will be"
				+ " given a consent form. After reading the form and agreeing to"
				+ " its terms, you will complete a microphone test to ensure"
				+ " that your voice is picked up by the program. In order to"
				+ " have the best sound quality, it is recommended that you use"
				+ " headphones. After the microphone test, the instructions will"
				+ " be communicated verbally and you will read the words aloud"
				+ " that appear on the screen.");
		QA patQa2 = new QA("What are my recordings used for?", "The data that is collected from the speech"
				+ " recording tests is sent and processed in a secure and"
				+ " private manner. Acoustic features include format"
				+ " frequencies, pitch, shimmer, jitter, and more. These"
				+ " features are analyzed using significance tests and various"
				+ " algorithms to find a connection between neurological"
				+ " conditions and the human voice.");
		QA patQa3 = new QA("What incentives are there for taking these tests?", "Contributing to the University of Notre Dame's"
				+ " study of the link between brain injury and voice can lead to"
				+ " further development in the early detection and warning signs"
				+ " of mild traumatic brain injuries.");
		qaList2.add(patQa1);
		qaList2.add(patQa2);
		qaList2.add(patQa3);
		patientFaqviewer = new SimpleFaqViewer(qaList2, "Patient", false);
		
		
		ArrayList<QA> qaList3 = new ArrayList<>();
		QA resQa1 = new QA("What information can I use for research purposes?", "As a researcher, you will be able to specify"
				+ " which tests you wish to obtain data from, and can narrow"
				+ " your search with criteria such as age and gender. You can"
				+ " use the data analytics and comparison tools on the website"
				+ " to identify trends within your data set.");
		QA resQa2 = new QA("Can I create my own speech-based test?", "Yes, as a researcher, you will be able to upload"
				+ " your own separate speech recordings, and the existing"
				+ " algorithm will be run on your sound file. This can"
				+ " eventually be added to the repository and made available for"
				+ " other patients.");
		qaList3.add(resQa1);
		qaList3.add(resQa2);
		researcherFaqViewer = new SimpleFaqViewer(qaList3, "Researcher", false);
		supportCustomPanel.add(generalFaqViewer);
		supportCustomPanel.add(new Br());
		supportCustomPanel.add(patientFaqviewer);
		supportCustomPanel.add(new Br());
		supportCustomPanel.add(researcherFaqViewer);
	}
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
//		this.patientAnchorClicked(null);
		loadPatientPanel();
	}

	public void reset() {

	}
	
	@UiHandler("buttonGotoRegister")
	void gotoRegister(ClickEvent event){
		enableSignupForm(null);
		scrollToTop();
	}
	
	public native void scrollToTop()/*-{
		$wnd.scrollTo(0,0);
	}-*/;

	/*
	@UiHandler("buttonFaq")
	void faqButtonClicked(ClickEvent event){
		generalFaqViewer.expandFaqs();
		if(currentTab.equalsIgnoreCase("patient")){
//			togglePanelGroup(generalPanelGroup, false);
//			togglePanelGroup(patientPanelGroup, true);
//			togglePanelGroup(researcherPanelGroup, false);
			patientFaqviewer.expandFaqs();
			researcherFaqViewer.collapseFaqs();
		} else if (currentTab.equalsIgnoreCase("researcher")) {
//			togglePanelGroup(generalPanelGroup, false);
//			togglePanelGroup(patientPanelGroup, false);
//			togglePanelGroup(researcherPanelGroup, true);
			patientFaqviewer.collapseFaqs();
			researcherFaqViewer.expandFaqs();
			
		} else {
//			togglePanelGroup(generalPanelGroup, true);
//			togglePanelGroup(patientPanelGroup, false);
//			togglePanelGroup(researcherPanelGroup, false);			
			patientFaqviewer.collapseFaqs();
			researcherFaqViewer.expandFaqs();
		}
//		supportAnchorClciked(null);

	}
	*/
	/*
	private void togglePanelGroup(PanelGroup panelGroup, boolean openAll){
		for(int i=0;i<panelGroup.getWidgetCount();i++){
			try {
				PanelCollapse tempPanelCollapse = (PanelCollapse)((Panel)panelGroup.getWidget(i)).getWidget(1);
				tempPanelCollapse.setIn(openAll);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}*/
	
	/*
	removed from anchor list in nav bar
	@UiHandler("patientAnchor")
	void patientAnchorClicked(ClickEvent event){
		if(History.getToken().equalsIgnoreCase("patient")){
			loadPatientPanel();
		}else {
			History.newItem("patient");			
		}
	}*/
	
	protected void loadPatientPanel(){
		currentTab = "patient";
//		patientAnchor.setActive(true);
//		researcherAnchor.setActive(false);
//		supportAnchor.setActive(false);
		patientCustomPanel.setVisible(true);
		researcherCustomPanel.setVisible(false);
		supportCustomPanel.setVisible(false);
		showMessagePanel.setVisible(false);
//		buttonFaq.setVisible(true);		
	}
	
	/*
	 * removed from anchor
	@UiHandler("researcherAnchor")
	void researcherAnchorClicked(ClickEvent event){
		History.newItem("researcher");
	}*/
	
	protected void loadResearcherPanel(){
		currentTab = "researcher";
//		patientAnchor.setActive(false);
//		researcherAnchor.setActive(true);
//		supportAnchor.setActive(false);
		patientCustomPanel.setVisible(false);
		researcherCustomPanel.setVisible(true);
		supportCustomPanel.setVisible(false);
//		buttonFaq.setVisible(true);		
		showMessagePanel.setVisible(false);
	}
	
	/*
	 *removed from anchor
	@UiHandler("supportAnchor")
	void supportAnchorClciked(ClickEvent event){
		History.newItem("support");
	}
	*/
	protected void loadSupportPanel(){
		//togglePanelGroup(generalPanelGroup, true);
		generalFaqViewer.expandFaqs();
		
		currentTab = "support";
//		patientAnchor.setActive(false);
//		researcherAnchor.setActive(false);
//		supportAnchor.setActive(true);
		patientCustomPanel.setVisible(false);
		researcherCustomPanel.setVisible(false);
		supportCustomPanel.setVisible(true);	
//		buttonFaq.setVisible(false);		
		showMessagePanel.setVisible(false);
	}
	
	protected void showAuthMessage(String message){
		
//		patientAnchor.setActive(false);
//		researcherAnchor.setActive(false);
//		supportAnchor.setActive(false);
		patientCustomPanel.setVisible(false);
		researcherCustomPanel.setVisible(false);
		supportCustomPanel.setVisible(false);	
//		buttonFaq.setVisible(false);
		showMessagePanel.setVisible(true);
		showMessage.setText(message);
	}
	
	
	@UiHandler("buttonForgotPass")
	void resetPassword(ClickEvent event) {
		scrollToTop();
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
						enableLoginForm(null);
						String forgetMessage = "You will receive new password by email. You can change the password later.";
						showAuthMessage(forgetMessage);
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
		scrollToTop();
	}

	@UiHandler("buttonAlreadyAccount")
	void enableLoginForm(ClickEvent event) {
		formLogin.setVisible(true);
		resetLogin();
		formSignup.setVisible(false);
		scrollToTop();
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
		/*if (textLoginEmail.getText().isEmpty()
				&& textLoginPassword.getText().isEmpty()) {
			application.loggedIn("z@gmail.com", "test");

		} else {*/
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
									.trim(), response.getMessage());
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

		//}

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
				//Window.alert("have caught an exception" + e.getMessage());
				return;
			}

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
														//application.signedUP(email);
														resetSignup();
														enableLoginForm(null);
														String signupMessage = "Thank you for registering in ND Speech Marker Initiative. You will receive confirmation email to activate your account.";
														showAuthMessage(signupMessage);
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
