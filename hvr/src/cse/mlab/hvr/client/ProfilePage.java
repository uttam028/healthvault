package cse.mlab.hvr.client;


import org.gwtbootstrap3.client.ui.IntegerBox;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.UserProfile;

public class ProfilePage extends Composite{

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);	
	
	@UiField
	TextBox textProfileFirstName;
	@UiField
	TextBox textProfileLastName;
	@UiField
	ListBox listProfileBirthMonth;
	@UiField
	IntegerBox textProfileBirthDay;
	@UiField
	IntegerBox textProfileBirthYear;
	@UiField
	IntegerBox textProfilePhone;
	@UiField
	TextArea textProfileAddress;
	
	@UiField
	Label labelFirstNameError;
	@UiField
	Label labelLastNameError;
	@UiField
	Label labelDOBError;
	@UiField
	Label labelPhoneError;
	
	
	@UiField
	HTMLPanel profilePagePanel;
	
	private MainPage parent;
	private String userId;
	private static ProfilePageUiBinder uiBinder = GWT
			.create(ProfilePageUiBinder.class);

	interface ProfilePageUiBinder extends UiBinder<Widget, ProfilePage> {
	}

	public ProfilePage(MainPage parent, String userId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parent = parent;
		this.userId = userId;
		listProfileBirthMonth.addItem("Month");
		listProfileBirthMonth.addItem("January");
		listProfileBirthMonth.addItem("February");
		listProfileBirthMonth.addItem("March");
		listProfileBirthMonth.addItem("April");
		listProfileBirthMonth.addItem("May");
		listProfileBirthMonth.addItem("June");
		listProfileBirthMonth.addItem("July");
		listProfileBirthMonth.addItem("August");
		listProfileBirthMonth.addItem("September");
		listProfileBirthMonth.addItem("October");
		listProfileBirthMonth.addItem("November");
		listProfileBirthMonth.addItem("December");
		listProfileBirthMonth.setSelectedIndex(0);
		listProfileBirthMonth.getElement().getFirstChildElement().setAttribute("disabled", "disabled");
	}
	@Override
	protected void onLoad() {
		listProfileBirthMonth.setSelectedIndex(0);
		greetingService.getProfile(userId, new AsyncCallback<UserProfile>() {
			
			@Override
			public void onSuccess(UserProfile result) {
				// TODO Auto-generated method stub
				if(result != null){
					textProfileFirstName.setText(result.getFirstName());
					textProfileLastName.setText(result.getLastName());
					textProfilePhone.setText(result.getMobileNum()<=0?(""):(result.getMobileNum()+""));
					textProfileAddress.setText(result.getAddress().replaceAll("''", "'"));
					
					try {
						DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
						dateFormat.parse(result.getBirthDay());
						
						textProfileBirthDay.setText(result.getBirthDay().substring(8));
						textProfileBirthYear.setText(result.getBirthDay().substring(0,4));
						listProfileBirthMonth.setSelectedIndex(Integer.parseInt(result.getBirthDay().substring(6,7)));
					} catch (Exception e) {
						// TODO: handle exception
						listProfileBirthMonth.setSelectedIndex(0);
					}
				} else{
					gotoHomePage(null);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				gotoHomePage(null);
			}
		});
		
//		textProfileFirstName.setText("this is by code");
		// TODO Auto-generated method stub
		super.onLoad();
//		VerticalPanel vp = new VerticalPanel();
	}
	@UiHandler("listProfileBirthMonth")
	void monthSelectionChangeHandler(ChangeEvent event){
		this.validateDOBEdgeCases();
	}
	
	@UiHandler("textProfileBirthDay")
	void dayKeyupHandler(KeyUpEvent event){
		try {
			int day = textProfileBirthDay.getValueOrThrow();
			labelDOBError.setText("");
			if (day < 1 || day > 31) {
				labelDOBError.setText("Invalid Date");
				return;
			}
			this.validateDOBEdgeCases();
			
		} catch (Exception e) {
			// TODO: handle exception
			if(textProfileBirthDay.getText().isEmpty()){
				labelDOBError.setText("");
			} else{
				labelDOBError.setText("Invalid Date");
			}
		}
	}
	
	@UiHandler("textProfileBirthYear")
	void yearKeyupHandler(KeyUpEvent event){
		try {
			int yearInText = textProfileBirthYear.getValueOrThrow();
			labelDOBError.setText("");
			JsDate jsDate = JsDate.create();
			int currentYear = jsDate.getFullYear();
			if (yearInText > currentYear || yearInText < currentYear - 100) {
				labelDOBError.setText("Invalid Date");
				return;
			}
			this.validateDOBEdgeCases();
			
		} catch (Exception e) {
			// TODO: handle exception
			if(textProfileBirthYear.getText().isEmpty()){
				labelDOBError.setText("");
			} else{
				labelDOBError.setText("Invcalid Date");				
			}
		}
	}
	
	private void validateDOBEdgeCases(){
		if(listProfileBirthMonth.getSelectedIndex()>0 && !textProfileBirthDay.getText().isEmpty() && !textProfileBirthYear.getText().isEmpty()){
			try {
				DateTimeFormat myDateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
				int month = listProfileBirthMonth.getSelectedIndex();
				String day = textProfileBirthDay.getText();
				String year = textProfileBirthYear.getText();
				myDateFormat.parseStrict(month + "/" + day + "/" + year);
				labelDOBError.setText("");
			} catch (Exception e) {
				// TODO: handle exception
				labelDOBError.setText("Invalid Date");
			}			
		}
	}
	@UiHandler("textProfileFirstName")
	void firstNameValueChangeHandler(ValueChangeEvent<String> event){
		if(textProfileFirstName.getText().trim().isEmpty()){
			labelFirstNameError.setText("This can't be empty");
		} else{
			labelFirstNameError.setText("");
		}
	}
	
	@UiHandler("textProfileLastName")
	void lastNameValueChangeHandler(ValueChangeEvent<String> event){
		if(textProfileLastName.getText().trim().isEmpty()){
			labelLastNameError.setText("This can't be empty");
		} else{
			labelLastNameError.setText("");
		}
	}
	
	@UiHandler("textProfilePhone")
	void phoneKeyupHandler(KeyUpEvent event){
		try {
			textProfilePhone.getValueOrThrow();
			labelPhoneError.setText("");
		} catch (Exception e) {
			// TODO: handle exception
			labelPhoneError.setText("Invalid Phone Number");
		}
	}
	
	
	@UiHandler("buttonProfileUpdateCancel")
	void gotoHomePage(ClickEvent event){
		this.removeFromParent();
		if(!this.textProfileFirstName.getText().isEmpty() && !this.textProfileLastName.getText().isEmpty()){
			parent.updateName(this.textProfileFirstName.getText(), this.textProfileLastName.getText());
		}
		parent.loadHomePage(event);
	}
	
	private boolean validateBeforeUpdateProfile(){
		if(textProfileFirstName.getText().isEmpty()){
			labelFirstNameError.setText("This can't be empty");
		}
		if(textProfileLastName.getText().isEmpty()){
			labelLastNameError.setText("This can't be empty");
		}
		if(listProfileBirthMonth.getSelectedIndex()>0 && (textProfileBirthDay.getText().isEmpty() || textProfileBirthYear.getText().isEmpty())){
			labelDOBError.setText("Invalid Date");
		}
		
		if(!labelFirstNameError.getText().isEmpty() || !labelLastNameError.getText().isEmpty() || !labelDOBError.getText().isEmpty() || !labelPhoneError.getText().isEmpty()){
			return false;
		}
		return true;
	}
	
	@UiHandler("buttonProfileUpdateSave")
	void saveProfileUpdate(ClickEvent event){
		if(validateBeforeUpdateProfile()){
			long phoneNumber = 0;
			try {
				phoneNumber = textProfilePhone.getValueOrThrow();
			} catch (Exception e) {
				// TODO: handle exception
			}
			String birthDay = "";
			try {
				birthDay = textProfileBirthYear.getText() + "/" + listProfileBirthMonth.getSelectedIndex() + "/" + textProfileBirthDay.getText();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			final UserProfile profile = new UserProfile();
			profile.setEmail(userId);
			profile.setFirstName(textProfileFirstName.getText().trim());
			profile.setLastName(textProfileLastName.getText().trim());
			if(phoneNumber > 0){
				profile.setMobileNum(phoneNumber);
			}
			if(!birthDay.isEmpty()){
				profile.setBirthDay(birthDay);
			}
			profile.setAddress(textProfileAddress.getText().trim().replaceAll("'", "''"));
			
			try {
				greetingService.saveProfile(profile, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						gotoHomePage(null);
					}
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						gotoHomePage(null);
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				gotoHomePage(null);
			}
		}
	}
}
