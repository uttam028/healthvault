package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.NavbarBrand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.UserProfile;

public class MainPage extends Composite {

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);	
	
	@UiField
	NavbarBrand welcomeUserBrand;
	@UiField
	AnchorButton optionAnchor;
	
	@UiField
	AnchorListItem linkHome;
	@UiField
	AnchorListItem linkVoice;
	@UiField
	AnchorListItem linkHealth;
	@UiField
	AnchorListItem linkAbout;
	
	//@UiField
	//PanelHeader historyPanelCollapse;
	
	@UiField
	HTMLPanel leftPanel;
	@UiField
	VerticalPanel healthVerticalPanel;
	
	
	@UiField
	HTMLPanel mainPageContentPanel;
	
	@UiField HTMLPanel mainpagePanel;

	Hvr application;
	Contacts contacts;
	About aboutPage;
	ProfilePage profilePage;
	CholesterolPage cholesterolPage;
	VoicePage voicePage;
	LandingPage landingPage;
	Medications medicationPage;
	
	
	private String userId;
	private String firstName = "";
	private String lastName = "";
	private boolean profileUpdated = false;
	private static String currentPage = "";
	private static MainPageUiBinder uiBinder = GWT
			.create(MainPageUiBinder.class);

	interface MainPageUiBinder extends UiBinder<Widget, MainPage> {
	}
	public MainPage(Hvr application, String email) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
		this.userId = email;
		updateUserName();
		profilePage = new ProfilePage(this, this.userId);
		contacts = new Contacts();
		aboutPage = new About();
		cholesterolPage = new CholesterolPage();
		voicePage = new VoicePage();
		landingPage = new LandingPage(this.application);
		medicationPage = new Medications();
		loadLandingPage();
	}
	
	
	private void loadLandingPage(){
		linkHome.setActive(true);
		linkVoice.setActive(false);
		linkHealth.setActive(false);
		linkAbout.setActive(false);
		this.leftPanel.clear();
		this.mainPageContentPanel.clear();
		this.mainPageContentPanel.add(landingPage);
	}
	
	private void loadVoiceRecordingPage(){
		linkHome.setActive(false);
		linkVoice.setActive(true);
		linkHealth.setActive(false);
		linkAbout.setActive(false);
		this.leftPanel.clear();
		this.mainPageContentPanel.clear();
		this.mainPageContentPanel.add(voicePage);
	}

	@UiHandler("buttonLogoutAction")
	void logout(ClickEvent clickEvent){
		this.application.logout();
	}

	
	/*
	 * This function sets the action to update the profile
	 */
	@UiHandler("buttonUpdateProfile")
	void updateProfile(ClickEvent event){
		this.mainPageContentPanel.clear();
		this.mainPageContentPanel.add(profilePage);		
	}
	
	@UiHandler("linkHome")
	void loadHomePage(ClickEvent event){
		History.newItem("homepage");
		if(currentPage != "home"){
			loadLandingPage();
		}
		currentPage = "home";
	}
	
	@UiHandler("linkVoice")
	void loadVoicePage(ClickEvent event){
		if(currentPage != "voice"){
			loadVoiceRecordingPage();
		}
		currentPage = "voice";
		History.newItem("voicepage");		
	}
	
	@UiHandler("linkHealth")
	void loadHealthPage(ClickEvent event){
		if(currentPage != "health"){
			linkHome.setActive(false);
			linkVoice.setActive(false);
			linkHealth.setActive(true);
			linkAbout.setActive(false);
			this.mainPageContentPanel.clear();
			this.mainPageContentPanel.add(medicationPage);
			
			this.leftPanel.clear();
			this.leftPanel.add(this.healthVerticalPanel);
		}
		currentPage = "health";
		History.newItem("healthpage");
	}
	
	@UiHandler("linkAbout")
	void loadAboutPage(ClickEvent event){
		if(currentPage != "about"){
			linkHome.setActive(false);
			linkVoice.setActive(false);
			linkHealth.setActive(false);
			linkAbout.setActive(true);
			this.mainPageContentPanel.clear();
			this.mainPageContentPanel.add(aboutPage);
		}
		currentPage = "about";
		History.newItem("aboutpage");
	}
	
	
	
	protected void updateName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		profileUpdated = true;
		updateUserName();
	}
	
	private void updateUserName(){
		
		if(profileUpdated){
			//optionAnchor.setText(firstName + " " + lastName);
			optionAnchor.setText(userId);
			profileUpdated = false;
		} else {
			optionAnchor.setText(userId);
			/*
			greetingService.getProfile(userId, new AsyncCallback<UserProfile>() {
				
				@Override
				public void onSuccess(UserProfile result) {
					// TODO Auto-generated method stub
					if(result != null){
						firstName = result.getFirstName();
						lastName = result.getLastName();
						optionAnchor.setText(result.getFirstName() + " " + result.getLastName());
					} else{
						application.logout();
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					application.logout();
				}
			});*/
			
		}
		
		
	}
}