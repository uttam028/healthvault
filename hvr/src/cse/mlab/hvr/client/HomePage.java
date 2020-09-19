package cse.mlab.hvr.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.services.ProfileService;
import cse.mlab.hvr.client.services.ProfileServiceAsync;
import cse.mlab.hvr.client.services.SpeechService;
import cse.mlab.hvr.client.services.SpeechServiceAsync;
import cse.mlab.hvr.client.study.MyStudyManager;
import cse.mlab.hvr.client.study.StudyPreface;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.study.MyStudyDataModel;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

public class HomePage extends Composite {

	private final ProfileServiceAsync profileService = GWT.create(ProfileService.class);
	private final SpeechServiceAsync speechService = GWT.create(SpeechService.class);
	
	@UiField
	HTMLPanel homepagePanel, openStudyPanel, messagePanel, dashboardPanel;
	
	//@UiField
	//PanelBody myStudiesPanelBody;
	private String userEmail;

	Hvr application;
	boolean homepageLoaded = false;
	private MessageBoardManager messageBoardManager;
	private MyStudyManager mystudyManager;
	
	private static LandingPageUiBinder uiBinder = GWT
			.create(LandingPageUiBinder.class);

	interface LandingPageUiBinder extends UiBinder<Widget, HomePage> {
	}

	public HomePage(Hvr application, String userEmail) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
		this.userEmail = userEmail;
		//dashboardPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		messageBoardManager = new MessageBoardManager();
		messagePanel.add(messageBoardManager);
		
		mystudyManager = new MyStudyManager();
		dashboardPanel.add(mystudyManager);
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!homepageLoaded) {
			
			profileService.profileUpdateRequired(this.userEmail, new AsyncCallback<Response>() {
				@Override
				public void onSuccess(Response result) {
					if(result.getCode()==1){
						displayMessage("Please update your profile", "profile/personal", true, "action_profile_personal", "Update");
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});

			
			openStudyPanel.clear();
			speechService.getOpenStudies(this.userEmail, new AsyncCallback<ArrayList<StudyPrefaceModel>>() {
						@Override
						public void onSuccess(ArrayList<StudyPrefaceModel> result) {
							openStudyPanel.clear();
							if(result == null || result.size()==0){
								openStudyPanel.add(new Label("No more studies to enroll"));
							}else {
								for (StudyPrefaceModel model : result) {
									StudyPreface openStudies = new StudyPreface(model);
									openStudyPanel.add(openStudies);
								}
							}
							homepageLoaded = true;
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});
			
			speechService.getMyStudies(this.userEmail, new AsyncCallback<ArrayList<MyStudyDataModel>>() {
				@Override
				public void onSuccess(ArrayList<MyStudyDataModel> result) {
					if(result == null || result.size()==0){
						mystudyManager.showEmptyMessage();
					} else {
						
						mystudyManager.addStudiesToManager(result);						
					}
				}
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	protected void displayMessage(String message, String url, boolean isHistoryToken, String tag, String buttonText) {
		messageBoardManager.addMessageToBoard(message, url, isHistoryToken, tag, buttonText);
	}
	
	protected void removeFromOpenStudies(String studyId){
		for(int i=0;i<openStudyPanel.getWidgetCount();i++){
			Widget child = openStudyPanel.getWidget(i);
			if(child instanceof StudyPreface){
				StudyPreface temp = (StudyPreface) child;
				if(temp.getStudyPrefaceModel().getStudyOverview().getId().equals(studyId)){
					openStudyPanel.remove(child);
					
				}
			}
		}
	}
	
	protected void updateParticipation(String studyId) {
		mystudyManager.updateStudyDataToDashBoard(studyId);
	}
	
	protected void addEnrolledStudyToMyStudy(StudyPrefaceModel study) {
		Date today = new Date();
		MyStudyDataModel mystudy = new MyStudyDataModel(study.getStudyOverview(), null, study.getQaList(), DateTimeFormat.getFormat("yyyy-MM-dd").format(today), "N/A", 0);
		mystudyManager.addStudyToDashboard(mystudy);
	}

	// @UiHandler("buttonConcussion")
	// void loadConcussionPage(ClickEvent event){
	// //this.application.mainPage.loadConcussionPage();
	// this.application.mainPage.loadVoicePage("concussion");
	// }
	//
	// @UiHandler("buttonDysarthria")
	// void loadDysarthriaPage(ClickEvent event){
	// //this.application.mainPage.loadDysarthriaPage();
	// this.application.mainPage.loadVoicePage("dysarthria");
	// }
}
