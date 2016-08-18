package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.PanelBody;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.study.MyStudy;
import cse.mlab.hvr.client.study.StudyPreface;
import cse.mlab.hvr.shared.study.MyStudyDataModel;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

public class HomePage extends Composite {

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	@UiField
	HTMLPanel homepagePanel, openStudyPanel;
	@UiField
	VerticalPanel dashboardPanel;
	
	//@UiField
	//PanelBody myStudiesPanelBody;
	private String userEmail;

	Hvr application;
	boolean homepageLoaded = false;
	private static LandingPageUiBinder uiBinder = GWT
			.create(LandingPageUiBinder.class);

	interface LandingPageUiBinder extends UiBinder<Widget, HomePage> {
	}

	public HomePage(Hvr application, String userEmail) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
		this.userEmail = userEmail;
		dashboardPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!homepageLoaded) {
			greetingService
					.getOpenStudies(this.userEmail, new AsyncCallback<ArrayList<StudyPrefaceModel>>() {

						@Override
						public void onSuccess(ArrayList<StudyPrefaceModel> result) {
							// TODO Auto-generated method stub
							for (StudyPrefaceModel model : result) {
								StudyPreface openStudies = new StudyPreface(model);
								openStudyPanel.add(openStudies);
							}
							homepageLoaded = true;
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});
			
			greetingService.getMyStudies(new AsyncCallback<ArrayList<MyStudyDataModel>>() {
				@Override
				public void onSuccess(ArrayList<MyStudyDataModel> result) {
					// TODO Auto-generated method stub
					for(MyStudyDataModel model: result){
						dashboardPanel.add(new MyStudy(model));
					}
				}
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
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
