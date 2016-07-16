package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.AnchorListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

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
	}

	protected void onLoad() {
		homeAnchorClicked(null);

	}

	@UiHandler("homeAnchor")
	void homeAnchorClicked(ClickEvent event) {
		History.newItem("home");
	}

	protected void loadHomePage() {
		homeAnchor.setActive(true);
		profileAnchor.setActive(false);

		mainPageContentPanel.clear();
		mainPageContentPanel.add(homePage);
	}

	@UiHandler("profileAnchor")
	void profileAnchorClicked(ClickEvent event) {
		History.newItem("profile");

	}

	protected void loadProfilePage() {
		homeAnchor.setActive(false);
		profileAnchor.setActive(true);

		mainPageContentPanel.clear();
		mainPageContentPanel.add(profilePage);
	}

	@UiHandler("signoutAnchor")
	void logout(ClickEvent clickEvent) {
		this.application.logout();
	}

	//
	//
	// /*
	// * This function sets the action to update the profile
	// */
	// @UiHandler("buttonUpdateProfile")
	// void updateProfile(ClickEvent event){
	// this.mainPageContentPanel.clear();
	// this.mainPageContentPanel.add(profilePage);
	// }
	//

	// @UiHandler("linkHome")
	// void loadHomePage(ClickEvent event){
	// if(currentPage != "main"){
	// loadLandingPage();
	// }
	// currentPage = "main";
	// History.newItem(currentPage);
	// }
	//
	// void loadVoicePage(String selectedTest){
	// currentPage = "voice";
	// voicePage.setVoiceTest(selectedTest);
	// loadVoiceRecordingPage();
	// }
	//
	// @UiHandler("linkVoice")
	// void loadVoicePage(ClickEvent event){
	// if(currentPage != "voice"){
	// loadVoiceRecordingPage();
	// }
	// currentPage = "voice";
	// History.newItem("voicepage");
	// }
	//
	// @UiHandler("linkHealth")
	// void loadHealthPage(ClickEvent event){
	// if(currentPage != "health"){
	// linkHome.setActive(false);
	// linkVoice.setActive(false);
	// linkHealth.setActive(true);
	// //linkAbout.setActive(false);
	// this.mainPageContentPanel.clear();
	// this.mainPageContentPanel.add(medicationPage);
	//
	// //this.leftPanel.clear();
	// ////this.leftPanel.add(this.healthVerticalPanel);
	// }
	// currentPage = "health";
	// History.newItem("healthpage");
	// }
	//
	// /*
	//
	//
	// protected void updateName(String firstName, String lastName) {
	// this.firstName = firstName;
	// this.lastName = lastName;
	// profileUpdated = true;
	// updateUserName();
	// }
	//
	// private void updateUserName(){
	//
	// if(profileUpdated){
	// //optionAnchor.setText(firstName + " " + lastName);
	// optionAnchor.setText(userId);
	// profileUpdated = false;
	// } else {
	// optionAnchor.setText(userId);
	// /*
	// greetingService.getProfile(userId, new AsyncCallback<UserProfile>() {
	//
	// @Override
	// public void onSuccess(UserProfile result) {
	// // TODO Auto-generated method stub
	// if(result != null){
	// firstName = result.getFirstName();
	// lastName = result.getLastName();
	// optionAnchor.setText(result.getFirstName() + " " + result.getLastName());
	// } else{
	// application.logout();
	// }
	// }
	//
	// @Override
	// public void onFailure(Throwable caught) {
	// // TODO Auto-generated method stub
	// application.logout();
	// }
	// });*/
	//
	// }
	//
	//
	// }

	// void loadConcussionPage() {
	// if (!currentPage.equalsIgnoreCase("concussion")) {
	// mainPageContentPanel.clear();
	// mainPageContentPanel.add(concussionPage);
	// }
	// currentPage = "concussion";
	// History.newItem(currentPage);
	//
	// }
	//
	// void loadDysarthriaPage(){
	// if (!currentPage.equalsIgnoreCase("dysarthria")) {
	// mainPageContentPanel.clear();
	// mainPageContentPanel.add(dysarthriaPage);
	// }
	// currentPage = "dysarthria";
	// History.newItem(currentPage);
	//
	// }
}
