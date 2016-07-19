package cse.mlab.hvr.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.TestPrefaceModel;

public class HomePage extends Composite {

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	@UiField
	HTMLPanel homepagePanel, dashboardPanel, speechTestListPanel;

	Hvr application;
	boolean homepageLoaded = false;
	private static LandingPageUiBinder uiBinder = GWT
			.create(LandingPageUiBinder.class);

	interface LandingPageUiBinder extends UiBinder<Widget, HomePage> {
	}

	public HomePage(Hvr application) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!homepageLoaded) {
			greetingService
					.getAvailableSpeechTest(new AsyncCallback<ArrayList<TestPrefaceModel>>() {

						@Override
						public void onSuccess(ArrayList<TestPrefaceModel> result) {
							// TODO Auto-generated method stub
							for (TestPrefaceModel model : result) {
								TestPreface testPreface = new TestPreface(model);
								speechTestListPanel.add(testPreface);
							}
							homepageLoaded = true;
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});

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
