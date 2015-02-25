package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.gwtbootstrap3.client.ui.PanelHeader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
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
	AnchorListItem linkAbout;
	@UiField
	AnchorListItem linkContacts;
	
	@UiField
	PanelHeader historyPanelCollapse;
	
	
	@UiField
	Label testLabel;
	
	@UiField
	HTMLPanel mainPageContentPanel;
	
	@UiField HTMLPanel mainpagePanel;

	Hvr application;
	Contacts contacts;
	About aboutPage;
	ProfilePage profilePage;
	CholesterolPage cholesterolPage;
	
	
	private String userId;
	private String firstName = "";
	private String lastName = "";
	private boolean profileUpdated = false;
	private static MainPageUiBinder uiBinder = GWT
			.create(MainPageUiBinder.class);

	interface MainPageUiBinder extends UiBinder<Widget, MainPage> {
	}
	public MainPage(Hvr application, String email) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
		this.userId = email;
		updateUserName();
		contacts = new Contacts();
		aboutPage = new About();
		profilePage = new ProfilePage(this, this.userId);
		cholesterolPage = new CholesterolPage();
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
		linkHome.setActive(true);
		linkAbout.setActive(false);
		linkContacts.setActive(false);
		this.mainPageContentPanel.clear();
		this.mainPageContentPanel.add(new Label("This is a home page template"));		
	}
	
	@UiHandler("linkAbout")
	void loadAboutPage(ClickEvent event){
		linkHome.setActive(false);
		linkAbout.setActive(true);
		linkContacts.setActive(false);
		this.mainPageContentPanel.clear();
		this.mainPageContentPanel.add(aboutPage);
	}
	
	@UiHandler("linkContacts")
	void loadContactsPage(ClickEvent event){
		linkHome.setActive(false);
		linkAbout.setActive(false);
		linkContacts.setActive(true);
		
		this.mainPageContentPanel.clear();
		this.mainPageContentPanel.add(contacts);
		
	}
	
	protected void updateName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		profileUpdated = true;
		updateUserName();
	}
	
	private void updateUserName(){
		if(profileUpdated){
			optionAnchor.setText(firstName + " " + lastName);			
			profileUpdated = false;
		} else {
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
			});
		}
		
	}
	
	@UiHandler("anchorGlucose")
	void glucoseClickHandler(ClickEvent event){
		Window.alert("Glucose has been clicked");
	}
	
	@UiHandler("anchorCholesterol")
	void cholesterolClickHandler(ClickEvent event){
		this.mainPageContentPanel.clear();
		this.mainPageContentPanel.add(cholesterolPage);
	}
		

}
