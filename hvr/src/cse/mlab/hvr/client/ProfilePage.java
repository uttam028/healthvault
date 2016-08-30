package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.LinkedGroupItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.events.LoadProfileItemEvent;
import cse.mlab.hvr.client.events.LoadProfileItemEventHandler;

public class ProfilePage extends Composite {
	
	
	@UiField
	HTMLPanel profileContentPanel;
	
	@UiField
	LinkedGroupItem personalInfoLink, accountInfoLink, medicalInfoLink;
	
	private PersonalInfoPage personalInfoPage;
	private AccountInfoPage accountInfoPage;
	private MedicalInfoPage medicalInfoPage;
	
	private String userId;

	private static ProfileUiBinder uiBinder = GWT.create(ProfileUiBinder.class);

	interface ProfileUiBinder extends UiBinder<Widget, ProfilePage> {
	}

	public ProfilePage() {
		initWidget(uiBinder.createAndBindUi(this));
		Hvr.getEventBus().addHandler(LoadProfileItemEvent.TYPE, new LoadProfileItemEventHandler() {
			
			@Override
			public void initiateLoadingProfileItem(LoadProfileItemEvent event) {
				// TODO Auto-generated method stub
				if(event.getProfilePageItem()== ProfilePageItem.PERSONAL){
					loadPersonalInfoPage();
				} else if(event.getProfilePageItem()== ProfilePageItem.ACCOUNT){
					loadAccountInfoPage();
				} else if(event.getProfilePageItem()== ProfilePageItem.MEDICAL){
					loadMedicalInfoPage();
				} else {
					loadPersonalInfoPage();
				}
			}
		});
	}
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		
	}
	
	@UiHandler("personalInfoLink")
	void personalInfoClicked(ClickEvent event){
		History.newItem("profile/personal");
	}
	
	protected void loadPersonalInfoPage(){
		profileContentPanel.clear();
		if(personalInfoPage == null){
			personalInfoPage = new PersonalInfoPage();
		}
		profileContentPanel.add(personalInfoPage);
	}
	
	@UiHandler("accountInfoLink")
	void accountInfoClicked(ClickEvent event){
		History.newItem("profile/account");
	}
	
	protected void loadAccountInfoPage(){
		profileContentPanel.clear();
		if(accountInfoPage == null){
			accountInfoPage = new AccountInfoPage();
		}
		profileContentPanel.add(accountInfoPage);
	} 
	
	@UiHandler("medicalInfoLink")
	void medicalInfoClicked(ClickEvent event){
		History.newItem("profile/medical");
	}
	protected void loadMedicalInfoPage(){
		profileContentPanel.clear();
		if(medicalInfoPage == null){
			medicalInfoPage = new MedicalInfoPage();
		}
		profileContentPanel.add(medicalInfoPage);
	}
	

}
