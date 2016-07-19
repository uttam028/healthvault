package cse.mlab.hvr.client;


import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.Answer;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.UserProfile;

public class ProfilePageOld extends Composite{

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
	IntegerBox textProfileAge;
	@UiField
	IntegerBox textProfileFeet;
	@UiField
	IntegerBox textProfileInches;
	@UiField
	IntegerBox textProfileWeight;
	@UiField
	IntegerBox textProfileConcussions;
	@UiField
	IntegerBox textProfileDiagnosed;
	@UiField
	Label labelFirstNameError;
	@UiField
	Label labelLastNameError;
	@UiField
	Label labelDOBError;
	@UiField
	Label labelPhoneError;
	@UiField
	Label labelAgeError;
	@UiField
	Label labelHeightError;
	@UiField
	Label labelWeightError;
	@UiField
	Label labelConcussionsError;
	@UiField
	Label labelDiagnosedError;

	@UiField
	ListBox listProfileSports;
	@UiField
	ListBox listProfileStates;
	@UiField
	HTMLPanel profilePagePanel;
	
	private MainPage parent;
	private String userId;
	private static ProfilePageUiBinder uiBinder = GWT
			.create(ProfilePageUiBinder.class);

	interface ProfilePageUiBinder extends UiBinder<Widget, ProfilePageOld> {
	}

	public ProfilePageOld(MainPage parent, String userId) {
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
	
	void loadSports(){
		listProfileSports.addItem("Pick a Sport");
		listProfileSports.addItem("Lacrosse");
		listProfileSports.addItem("Soccer");
		listProfileSports.addItem("Football");
		listProfileSports.addItem("Rugby");
		listProfileSports.addItem("Basketball");
		listProfileSports.addItem("Tennis");
		listProfileSports.addItem("Baseball");
		listProfileSports.addItem("Cricket");
		listProfileSports.setSelectedIndex(0);
	}
	
	void loadStates(){
		listProfileStates.addItem("Pick a State");
		listProfileStates.addItem("Alabama");
		listProfileStates.addItem("Alaska");
		listProfileStates.addItem("Arizona");
		listProfileStates.addItem("Arkansas");
		listProfileStates.addItem("California");
		listProfileStates.addItem("Colorado");
		listProfileStates.addItem("Connecticut");
		listProfileStates.addItem("Delaware");
		listProfileStates.addItem("Florida");
		listProfileStates.addItem("Georgi");
		listProfileStates.addItem("Hawaii");
		listProfileStates.addItem("Idaho");
		listProfileStates.addItem("Indiana");
		listProfileStates.addItem("Iowa");
		listProfileStates.addItem("Kansas");
		listProfileStates.addItem("Kentucky");
		listProfileStates.addItem("Louisiana");
		listProfileStates.addItem("Maine");
		listProfileStates.addItem("Maryland");
		listProfileStates.addItem("Massachusetts");
		listProfileStates.addItem("Michigan");
		listProfileStates.addItem("Minnesota");
		listProfileStates.addItem("Mississippi");
		listProfileStates.addItem("Missouri");
		listProfileStates.addItem("Montana");
		listProfileStates.addItem("Nebraska");
		listProfileStates.addItem("Nevada");
		listProfileStates.addItem("New Hampshire");
		listProfileStates.addItem("New Jersey");
		listProfileStates.addItem("New Mexico");
		listProfileStates.addItem("New York");
		listProfileStates.addItem("North Carolina");
		listProfileStates.addItem("North Dakota");
		listProfileStates.addItem("Ohio");
		listProfileStates.addItem("Oklahoma");
		listProfileStates.addItem("Oregon");
		listProfileStates.addItem("Pennsylvania");
		listProfileStates.addItem("Rhode Island");
		listProfileStates.addItem("South Carolina");
		listProfileStates.addItem("South Dakota");
		listProfileStates.addItem("Tennessee");
		listProfileStates.addItem("Texas");
		listProfileStates.addItem("Utah");
		listProfileStates.addItem("Vermont");
		listProfileStates.addItem("Virginia");
		listProfileStates.addItem("Washington");
		listProfileStates.addItem("West Virginia");
		listProfileStates.addItem("Wisconsin");
		listProfileStates.addItem("Wyoming");
		listProfileStates.setSelectedIndex(0);
	}
	
	
	
	@Override
	protected void onLoad() {
		loadSports();
		loadStates();
		listProfileBirthMonth.setSelectedIndex(0);
		greetingService.getProfile(userId, new AsyncCallback<UserProfile>() {
			
			@Override
			public void onSuccess(UserProfile result) {
				// TODO Auto-generated method stub
				if(result != null){
					textProfileFirstName.setText(result.getFirstName());
					textProfileLastName.setText(result.getLastName());
					//textProfilePhone.setText(result.getMobileNum()<=0?(""):(result.getMobileNum()+""));
					if(result.getAddress() == null){
						textProfileAddress.setText("");
					} else{
						textProfileAddress.setText(result.getAddress().replaceAll("''", "'"));
					}
					
					try {
						DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
						dateFormat.parse(result.getBirthDay());
						
						textProfileBirthDay.setText(result.getBirthDay().substring(8));
						textProfileBirthYear.setText(result.getBirthDay().substring(0,4));
						listProfileBirthMonth.setSelectedIndex(Integer.parseInt(result.getBirthDay().substring(5,7)));
					} catch (Exception e) {
						// TODO: handle exception
						listProfileBirthMonth.setSelectedIndex(0);
					}
					
					
				} else{
//					gotoHomePage(null);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
//				gotoHomePage(null);
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
				labelDOBError.setText("Invalid Date");				
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
	
	@UiHandler("textProfileAge")
	void ageKeyupHandler(KeyUpEvent event){
		try {
			int age = textProfileAge.getValueOrThrow();
			labelAgeError.setText("");
			if(age < 1 || age > 150){
				labelAgeError.setText("Invalid Age");
				return;
			}
		} catch (Exception e){
			// TODO: Handle exception
			if(textProfileAge.getText().isEmpty()){
				labelAgeError.setText("");
			} else {
				labelAgeError.setText("Invalid Age");
			}
		}
	}
	
	@UiHandler("textProfileFeet")
	void feetKeyupHandler(KeyUpEvent event){
		try {
			int feet = textProfileFeet.getValueOrThrow();
			labelHeightError.setText("");
			if(feet < 0 || feet > 10){
				labelHeightError.setText("Invalid Input");
				return;
			}
		} catch (Exception e){
			// TODO: Handle exception
			if(textProfileFeet.getText().isEmpty() && textProfileInches.getText().isEmpty()){
				labelHeightError.setText("");
			} else {
				labelHeightError.setText("Invalid Input");
			}
		}
	}
	
	@UiHandler("textProfileInches")
	void inchesKeyupHandler(KeyUpEvent event){
		try {
			int inch = textProfileInches.getValueOrThrow();
			labelHeightError.setText("");
			if(inch < 0 || inch > 11){
				labelHeightError.setText("Invalid Input");
				return;
			}
		} catch (Exception e){
			// TODO: Handle exception
			if(textProfileFeet.getText().isEmpty() && textProfileInches.getText().isEmpty()){
				labelHeightError.setText("");
			} else {
				labelHeightError.setText("Invalid Input");
			}
		}
	}
	
	@UiHandler("textProfileWeight")
	void weightKeyupHandler(KeyUpEvent event){
		try {
			int weight = textProfileWeight.getValueOrThrow();
			labelWeightError.setText("");
			if(weight < 1)
				labelWeightError.setText("Invalid Weight");
		} catch (Exception e) {
			// TODO: handle exception
			if(textProfileWeight.getText().isEmpty()){
				labelWeightError.setText("");
			} else {
				labelWeightError.setText("Invalid Weight");
			}
		}
	}
	
	@UiHandler("textProfileConcussions")
	void concussionsKeyupHandler(KeyUpEvent event){
		try {
			textProfileConcussions.getValueOrThrow();
			labelConcussionsError.setText("");
		} catch (Exception e) {
			// TODO: handle exception
			labelConcussionsError.setText("Invalid Input");
		}
	}
	
	@UiHandler("textProfileDiagnosed")
	void diagnosedKeyupHandler(KeyUpEvent event){
		try {
			textProfileDiagnosed.getValueOrThrow();
			labelDiagnosedError.setText("");
		} catch (Exception e) {
			// TODO: handle exception
			labelDiagnosedError.setText("Invalid Input");
		}
	}
	
//	@UiHandler("buttonProfileUpdateCancel")
//	void gotoHomePage(ClickEvent event){
//		this.removeFromParent();
//		if(!this.textProfileFirstName.getText().isEmpty() && !this.textProfileLastName.getText().isEmpty()){
//			parent.updateName(this.textProfileFirstName.getText(), this.textProfileLastName.getText());
//		}
//		parent.loadHomePage(event);
//	}
	
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
				if(listProfileBirthMonth.getSelectedIndex()<=0 || textProfileBirthDay.getText().isEmpty() || textProfileBirthYear.getText().isEmpty()){
					birthDay = "";
				} else{
					birthDay = textProfileBirthYear.getText() + "/" + listProfileBirthMonth.getSelectedIndex() + "/" + textProfileBirthDay.getText();					
				}

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
			Window.alert("user email : "+ userId );
			
			profile.setAddress(textProfileAddress.getText().trim().replaceAll("'", "''"));
			
			List<Answer> qa = new ArrayList<Answer>();
			
			if(!textProfileConcussions.getText().isEmpty()){
				Answer first = new Answer();
				first.setQuestionId(1);
				first.setAnswer(textProfileConcussions.getText());
				qa.add(first);
			}
			
			
			profile.setQuestionAnswer(qa);
			
			
			
			try {
				greetingService.saveProfile(profile, new AsyncCallback<Response>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
//						gotoHomePage(null);
					}
					@Override
					public void onSuccess(Response result) {
						// TODO Auto-generated method stub
//						gotoHomePage(null);
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
//				gotoHomePage(null);
			}
		}
	}
}
