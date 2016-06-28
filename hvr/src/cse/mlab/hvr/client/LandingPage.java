package cse.mlab.hvr.client;


import org.gwtbootstrap3.client.ui.Anchor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LandingPage extends Composite{
	
//	@UiField
//	Button startSpeechFromLanding, startHealthFromLanding;
	@UiField
	Anchor buttonConcussion, buttonDysarthria;

	Hvr application;
	private static LandingPageUiBinder uiBinder = GWT
			.create(LandingPageUiBinder.class);

	interface LandingPageUiBinder extends UiBinder<Widget, LandingPage> {
	}

	public LandingPage(Hvr application) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
	}


	public LandingPage(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

//	@UiHandler("startSpeechFromLanding")
//	void loadSpeechFromLanding(ClickEvent event){
////		this.application.mainPage.loadVoicePage(null);
//	}
//	
//	@UiHandler("startHealthFromLanding")
//	void loadHealthFromLanding(ClickEvent event){
////		this.application.mainPage.loadHealthPage(null);
//	}
	
	@UiHandler("buttonConcussion")
	void loadConcussionPage(ClickEvent event){
		//this.application.mainPage.loadConcussionPage();
		this.application.mainPage.loadVoicePage("concussion");
	}
	
	@UiHandler("buttonDysarthria")
	void loadDysarthriaPage(ClickEvent event){
		//this.application.mainPage.loadDysarthriaPage();
		this.application.mainPage.loadVoicePage("dysarthria");
	}
}
