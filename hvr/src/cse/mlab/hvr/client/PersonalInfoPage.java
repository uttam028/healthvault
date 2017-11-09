package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.study.PreTestAnswers;
import cse.mlab.hvr.shared.study.PreTestQuestion;;

public class PersonalInfoPage extends Composite{

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	@UiField
	HTMLPanel resultPanel, physicalQuestionPanel, profileQuestionPanel;
	
//	@UiField
//	Form personalInfoForm;
	
//	@UiField
//	TextBox textboxFirstName, textboxLastName, textboxBirthYear, textboxLanguage;
//	
//	@UiField
//	InlineRadio inlineRadioMale, inlineRadioFemale, inlineRadioOther;
//	
//	@UiField
//	InlineHelpBlock inlineHelpFirst, inlineHelpLast, inlineHelpGender, inlineHelpYear;
	
//	@UiField
//	Button buttonUpdate, buttonCancel;
	
	@UiField
	Button buttonPhysicalUpdate, buttonProfileUpdate;
	
	private boolean pageLoaded = false;
	private boolean isDirty = false;
	private UserProfile profile;

	private static PersonalInfoUiBinder uiBinder = GWT
			.create(PersonalInfoUiBinder.class);

	interface PersonalInfoUiBinder extends UiBinder<Widget, PersonalInfoPage> {
	}

	public PersonalInfoPage() {
		initWidget(uiBinder.createAndBindUi(this));
//		showCancelButton(false);
	}

//	@UiHandler("buttonUpdate")
//	void updateInformation(ClickEvent event){
//		boolean formValidation = personalInfoForm.validate();
//		boolean genderValidation = validateGender();
//		
//		if(formValidation && genderValidation){
//			this.profile = populateDataFromFields();
//			greetingService.saveProfile(profile, new AsyncCallback<Response>() {
//				@Override
//				public void onSuccess(Response result) {
//					if(result.getCode()==0){
//						resultPanel.add(new MessagePanel("Your profile has been updated", "", false, "", ""));
//						
//					}else {
//						resultPanel.add(new MessagePanel("Please try later.", "", false, "", ""));
//					}
//					Timer timer = new Timer() {					
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							resultPanel.clear();
//						}
//					};
//					timer.schedule(5 * 1000);
//				}
//				@Override
//				public void onFailure(Throwable caught) {
//					resultPanel.add(new MessagePanel("Please try later.", "", false, "", ""));
//					Timer timer = new Timer() {					
//						@Override
//						public void run() {
//							resultPanel.clear();
//						}
//					};
//					timer.schedule(5 * 1000);
//					
//				}
//			});
//			showCancelButton(false);
//		}else {
//			showCancelButton(true);
//		}
//	}
	
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		if(!pageLoaded){
//			greetingService.getProfile(MainPage.getLoggedinUser(), new AsyncCallback<UserProfile>() {
//				
//				@Override
//				public void onSuccess(UserProfile result) {
//					// TODO Auto-generated method stub
//					if(result != null){
//						profile = result;
//						populateFieldsWithData();
//						pageLoaded = true;
//					}else {
//						pageLoaded = false;
//					}
//				}
//				
//				@Override
//				public void onFailure(Throwable caught) {
//					// TODO Auto-generated method stub
//					pageLoaded = false;
//				}
//			});
			
			greetingService.getProfileInformation(MainPage.getLoggedinUser(), new AsyncCallback<ArrayList<PreTestQuestion>>() {
				@Override
				public void onSuccess(ArrayList<PreTestQuestion> result) {
					for(int i=0;i<result.size();i++){
						profileQuestionPanel.add(new PreTestQuestionView(result.get(i)));
						//pretestPanel.add(new Br());
					}
					
					profileQuestionPanel.add(new Br());
					profileQuestionPanel.add(new Br());
					profileQuestionPanel.add(new Br());
					
					pageLoaded = true;
					
				}
				@Override
				public void onFailure(Throwable caught) {
					pageLoaded = false;
					
				}
			});
			
			greetingService.getPhysicalInformation(MainPage.getLoggedinUser(), new AsyncCallback<ArrayList<PreTestQuestion>>() {
				@Override
				public void onSuccess(ArrayList<PreTestQuestion> result) {
					for(int i=0;i<result.size();i++){
						physicalQuestionPanel.add(new PreTestQuestionView(result.get(i)));
						//pretestPanel.add(new Br());
					}
					
					physicalQuestionPanel.add(new Br());
					physicalQuestionPanel.add(new Br());
					physicalQuestionPanel.add(new Br());
				}
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
		}
//		textboxBirthYear.addValidator(new Validator<String>() {
//			@Override
//			public List<EditorError> validate(Editor<String> editor,
//					String value) {
//			    List<EditorError> result = new ArrayList<EditorError>();
//			    String valueStr = value == null ? "" : value.toString().trim();
//			    int year=0;
//			    try {
//					year = Integer.parseInt(valueStr);
//					if(year < 1920 || year > 2020){
//						year = 0;
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					year = 0;
//				}
//			    if (year == 0) {
//			      result.add(new BasicEditorError(textboxBirthYear, value, "Invalid Year"));
//			    }
//			    return result;			
//			}
//			@Override
//			public int getPriority() {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//		});
	}
	
	
	@UiHandler("buttonProfileUpdate")
	void updateProfileInformation(ClickEvent event){
		ArrayList<String> questions = new ArrayList<>();
		ArrayList<String> answers = new ArrayList<>();
		
		//pretestAnswers.
		for(int i=0;i<profileQuestionPanel.getWidgetCount();i++){
			Widget childWidget = profileQuestionPanel.getWidget(i);
			if(childWidget instanceof PreTestQuestionView){
				questions.add(((PreTestQuestionView)childWidget).getQuestionId());
				answers.add(((PreTestQuestionView)childWidget).getAnswer());
			}
			
		}
		PreTestAnswers profileQuestionAnswer = new PreTestAnswers(MainPage.getLoggedinUser(), "", "", questions, answers);
		greetingService.updateProfileInfo(profileQuestionAnswer, new AsyncCallback<Response>() {
			@Override
			public void onSuccess(Response result) {
				Notify.notify("Profile information has been updated.", NotifyType.SUCCESS);
			}
			

			@Override
			public void onFailure(Throwable caught) {
				Notify.notify("Failed to update, Please try later.", NotifyType.DANGER);
			}

			
		});
		
	}
	
	@UiHandler("buttonPhysicalUpdate")
	void updatePhysicalInformation(ClickEvent event){
		ArrayList<String> questions = new ArrayList<>();
		ArrayList<String> answers = new ArrayList<>();
		
		//pretestAnswers.
		for(int i=0;i<physicalQuestionPanel.getWidgetCount();i++){
			Widget childWidget = physicalQuestionPanel.getWidget(i);
			if(childWidget instanceof PreTestQuestionView){
				questions.add(((PreTestQuestionView)childWidget).getQuestionId());
				answers.add(((PreTestQuestionView)childWidget).getAnswer());
			}
			
		}
		PreTestAnswers physicalQuestionAnswer = new PreTestAnswers(MainPage.getLoggedinUser(), "", "", questions, answers);
		greetingService.updatePhysicalInfo(physicalQuestionAnswer, new AsyncCallback<Response>() {

			@Override
			public void onFailure(Throwable caught) {
				Notify.notify("Failed to update, Please try later.", NotifyType.DANGER);				
			}

			@Override
			public void onSuccess(Response result) {
				Notify.notify("Your information has been updated.", NotifyType.SUCCESS);
			}
			
		});

	}
	
	
//	private boolean validateGender(){
//		if(inlineRadioMale.getValue() || inlineRadioFemale.getValue() || inlineRadioOther.getValue()){
//			inlineHelpGender.setText("");
//			return true;
//		}else {
//			inlineHelpGender.setText("Select One");
//			return false;
//		}
//	}
//	
//	private UserProfile populateDataFromFields(){
//		String firstName = textboxFirstName.getText().trim();
//		String lastName = textboxLastName.getText().trim();
//		String gender = "";
//		if(inlineRadioMale.getValue()){
//			gender = "male";
//		}else if (inlineRadioFemale.getValue()) {
//			gender = "female";
//		}else if (inlineRadioOther.getValue()) {
//			gender = "other";
//		}
//		int year = Integer.parseInt(textboxBirthYear.getValue());
//		String language = textboxLanguage.getText().trim();
//		
//		UserProfile newProfile = new UserProfile(MainPage.getLoggedinUser(), "", firstName, lastName, gender, year, language, "", "", 0 , 0, "", "", "", "", "");	
//		return newProfile;
//	}
//	
//	private void populateFieldsWithData(){
//		UserProfile profile = this.getProfile();
//		if(isStringEmpty(profile.getFirstName())){
//			textboxFirstName.setText("");
//		} else {
//			textboxFirstName.setText(profile.getFirstName());
//		}
//		
//		if(isStringEmpty(profile.getLastName())){
//			textboxLastName.setText("");
//		} else {
//			textboxLastName.setText(profile.getLastName());
//		}
//		
//		if(isStringEmpty(profile.getGender())){
//			inlineRadioMale.setValue(false);
//			inlineRadioFemale.setValue(false);
//			inlineRadioOther.setValue(false);
//		} else {
//			if(profile.getGender().equalsIgnoreCase("male")){
//				inlineRadioMale.setValue(true);
//			}else if (profile.getGender().equalsIgnoreCase("female")) {
//				inlineRadioFemale.setValue(true);
//			} else {
//				inlineRadioOther.setValue(true);
//			}
//		}
//		
//		try {
//			int birthYear = profile.getBirthYear();
//			if(birthYear> 1900 && birthYear < 2030){
//				textboxBirthYear.setText(String.valueOf(birthYear));
//			} else {
//				textboxBirthYear.setText("");
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		if(isStringEmpty(profile.getPrimaryLanguage())){
//			textboxLanguage.setText("");
//		}else {
//			textboxLanguage.setText(profile.getPrimaryLanguage());
//		}
//		
//	}
	
//	private void updateFormDirtiness(boolean state){
//		if(state){
//			isDirty = true;
//			showCancelButton(true);
//		}else {
//			isDirty = false;
//			showCancelButton(false);
//		}
//	}

	
//	@UiHandler("textboxFirstName")
//	void handleKeyupFirstName(KeyUpEvent event){
//		if(textboxFirstName.getText().equalsIgnoreCase(this.profile.getFirstName())){
//			//updateFormDirtiness(false);
//		}else {
//			updateFormDirtiness(true);
//		}		
//	}
//	
//	
//	@UiHandler("textboxLastName")
//	void handleLastNameChange(KeyUpEvent event){
//		if(textboxLastName.getText().equalsIgnoreCase(this.profile.getLastName())){
//			//updateFormDirtiness(false);
//		}else {
//			updateFormDirtiness(true);
//		}
//	}
//	
//	@UiHandler("textboxBirthYear")
//	void handleBirthYearChange(KeyUpEvent event){
//		if(textboxBirthYear.getText().equalsIgnoreCase(String.valueOf(this.profile.getBirthYear()))){
//			//updateFormDirtiness(false);
//		}else {
//			updateFormDirtiness(true);
//		}
//	}
//	
//	@UiHandler("textboxLanguage")
//	void handleLanguageChange(KeyUpEvent event){
//		if(textboxLanguage.getText().equalsIgnoreCase(this.profile.getPrimaryLanguage())){
//			//updateFormDirtiness(false);
//		}else {
//			updateFormDirtiness(true);
//		}
//	}
//	@UiHandler("textboxPhoneNumber")
//	void handlePhoneNumnberChange(KeyUpEvent event){
//		if(textboxPhoneNumber.getText().equalsIgnoreCase(this.profile.getPhoneNumber())){
//			//updateFormDirtiness(false);
//		}else {
//			updateFormDirtiness(true);
//		}
//	}
//	@UiHandler("textboxAddress")
//	void handleAddressChange(KeyUpEvent event){
//		if(textboxAddress.getText().equalsIgnoreCase(this.profile.getAddress())){
//			//updateFormDirtiness(false);
//		}else {
//			updateFormDirtiness(true);
//		}
//	}
	
//	private void showCancelButton(boolean state){
//		if(state){
//			buttonCancel.setVisible(true);
//		}else {
//			buttonCancel.setVisible(false);
//		}
//	}

//	@UiHandler("buttonCancel")
//	void cancelButtonClicked(ClickEvent event){
//		isDirty = false;
//		buttonCancel.setVisible(false);
//		personalInfoForm.reset();
//		inlineHelpGender.setText("");
//		populateFieldsWithData();
//	}
	
	private boolean isStringEmpty(String message){
		if(message == null || message.isEmpty()){
			return true;
		}
		return false;
	}
	
	public UserProfile getProfile() {
		return profile;
	}
}
